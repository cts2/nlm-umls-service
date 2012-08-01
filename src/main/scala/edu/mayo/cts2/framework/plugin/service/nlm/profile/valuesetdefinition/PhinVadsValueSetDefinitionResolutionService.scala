package edu.mayo.cts2.framework.plugin.service.nlm.profile.valuesetdefinition

import java.lang.Override
import scala.collection.JavaConversions._
import org.springframework.stereotype.Component
import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntry
import edu.mayo.cts2.framework.model.service.core.NameOrURI
import edu.mayo.cts2.framework.plugin.service.nlm.profile.AbstractService
import edu.mayo.cts2.framework.service.profile.codesystemversion.CodeSystemVersionReadService
import edu.mayo.cts2.framework.model.extension.LocalIdValueSetDefinition
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.name.ValueSetDefinitionReadId
import edu.mayo.cts2.framework.plugin.service.nlm.phinvads.dao.PhinVadsDao
import javax.annotation.Resource
import gov.cdc.vocab.service.dto.input.ValueSetVersionSearchCriteriaDto
import java.util.Set
import edu.mayo.cts2.framework.model.valuesetdefinition.{ ResolvedValueSet, ValueSetDefinition }
import edu.mayo.cts2.framework.model.command.{ Page, ResolvedReadContext }
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.{ ResolvedValueSetResult, ResolvedValueSetResolutionEntityQuery, ValueSetDefinitionReadService, ValueSetDefinitionResolutionService }
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry
import edu.mayo.cts2.framework.model.core._
import gov.cdc.vocab.service.bean.ValueSetConcept

@Component
class PhinVadsValueSetDefinitionResolutionService extends AbstractService with ValueSetDefinitionResolutionService {

  @Resource
  var phinVadsDao: PhinVadsDao = _

  def getSupportedMatchAlgorithms: Set[_ <: MatchAlgorithmReference] = null

  def getSupportedSearchReferences: Set[_ <: PropertyReference] = null

  def getSupportedSortReferences: Set[_ <: PropertyReference] = null

  def getKnownProperties: Set[PredicateReference] = null

  def resolveDefinition(
    id: ValueSetDefinitionReadId,
    codeSystemVersions: Set[NameOrURI],
    codeSystemVersionTag: NameOrURI,
    query: ResolvedValueSetResolutionEntityQuery,
    sort: SortCriteria,
    readContext: ResolvedReadContext,
    page: Page): ResolvedValueSetResult[EntitySynopsis] = {

    val valueSetName = id.getValueSet().getName()

    val valueSetOid = phinVadsDao.getValueSetOid(valueSetName)

    val valueSetVersion =
      phinVadsDao.vocabService.getValueSetVersionByValueSetOidAndVersionNumber(valueSetOid, null)

    val versionId = valueSetVersion.getValueSetVersion().getId()

    val synopsis: Seq[EntitySynopsis] = phinVadsDao.vocabService.
      getValueSetConceptsByValueSetVersionId(versionId, 1, 50).
      getValueSetConcepts().map(transformValueSetConcept)

    new ResolvedValueSetResult(null, synopsis, true)
  }

  private def transformValueSetConcept = (entry: ValueSetConcept) => {
    val synopsis: EntitySynopsis = new EntitySynopsis()
    synopsis.setDesignation(entry.getCdcPreferredDesignation())
    synopsis.setName(entry.getConceptCode())
    synopsis.setNamespace(entry.getCodeSystemConceptName())
    synopsis
  }: EntitySynopsis

  def resolveDefinitionAsEntityDirectory(p1: ValueSetDefinitionReadId, p2: Set[NameOrURI], p3: NameOrURI, p4: ResolvedValueSetResolutionEntityQuery, p5: SortCriteria, p6: ResolvedReadContext, p7: Page): ResolvedValueSetResult[EntityDirectoryEntry] = null

  def resolveDefinitionAsCompleteSet(p1: ValueSetDefinitionReadId, p2: Set[NameOrURI], p3: NameOrURI, p4: ResolvedReadContext): ResolvedValueSet = null
}
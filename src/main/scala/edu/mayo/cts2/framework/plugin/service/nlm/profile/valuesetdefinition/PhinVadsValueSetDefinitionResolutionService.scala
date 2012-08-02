package edu.mayo.cts2.framework.plugin.service.nlm.profile.valuesetdefinition

import java.util.Set

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

import org.springframework.stereotype.Component

import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.model.command.ResolvedReadContext
import edu.mayo.cts2.framework.model.core._
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry
import edu.mayo.cts2.framework.model.service.core.NameOrURI
import edu.mayo.cts2.framework.model.valuesetdefinition.ResolvedValueSet
import edu.mayo.cts2.framework.model.valuesetdefinition.ResolvedValueSetHeader
import edu.mayo.cts2.framework.plugin.service.nlm.phinvads.dao.PhinVadsDao
import edu.mayo.cts2.framework.plugin.service.nlm.profile.AbstractService
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.ResolvedValueSetResolutionEntityQuery
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.ResolvedValueSetResult
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.ValueSetDefinitionResolutionService
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.name.ValueSetDefinitionReadId
import gov.cdc.vocab.service.bean.ValueSetConcept
import javax.annotation.Resource

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

    val valueSetConcepts = phinVadsDao.vocabService.
      getValueSetConceptsByValueSetVersionId(versionId, 1, 50).
      getValueSetConcepts().asScala
      
    val synopsis: Seq[EntitySynopsis] = valueSetConcepts.map(transformValueSetConcept)

    new ResolvedValueSetResult(buildHeader(), synopsis, true)
  }

  private def buildHeader(): ResolvedValueSetHeader = {
    val header = new ResolvedValueSetHeader()
    
    val valueDefSetRef = new ValueSetDefinitionReference()
    valueDefSetRef.setValueSet(new ValueSetReference("unknown"))
    valueDefSetRef.setValueSetDefinition(new NameAndMeaningReference("unknown"))
    header.setResolutionOf(valueDefSetRef)
    
    header
  }
  
  private def transformValueSetConcept = (entry: ValueSetConcept) => {
    val synopsis: EntitySynopsis = new EntitySynopsis()
    synopsis.setDesignation(entry.getCdcPreferredDesignation())
    synopsis.setName(entry.getConceptCode())
    synopsis.setNamespace("ns")
    synopsis.setUri("uri:urn:" + entry.getId);
    
    synopsis
  }: EntitySynopsis

  def resolveDefinitionAsEntityDirectory(p1: ValueSetDefinitionReadId, p2: Set[NameOrURI], p3: NameOrURI, p4: ResolvedValueSetResolutionEntityQuery, p5: SortCriteria, p6: ResolvedReadContext, p7: Page): ResolvedValueSetResult[EntityDirectoryEntry] = null

  def resolveDefinitionAsCompleteSet(p1: ValueSetDefinitionReadId, p2: Set[NameOrURI], p3: NameOrURI, p4: ResolvedReadContext): ResolvedValueSet = null
}
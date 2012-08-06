package edu.mayo.cts2.framework.plugin.service.nlm.profile.valuesetdefinition

import java.io.File
import java.util.Set
import scala.collection.JavaConversions._
import scala.collection.mutable.MutableList
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.model.command.ResolvedReadContext
import edu.mayo.cts2.framework.model.core.EntitySynopsis
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference
import edu.mayo.cts2.framework.model.core.NameAndMeaningReference
import edu.mayo.cts2.framework.model.core.PredicateReference
import edu.mayo.cts2.framework.model.core.PropertyReference
import edu.mayo.cts2.framework.model.core.SortCriteria
import edu.mayo.cts2.framework.model.core.ValueSetDefinitionReference
import edu.mayo.cts2.framework.model.core.ValueSetReference
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry
import edu.mayo.cts2.framework.model.service.core.NameOrURI
import edu.mayo.cts2.framework.model.valuesetdefinition.ResolvedValueSet
import edu.mayo.cts2.framework.model.valuesetdefinition.ResolvedValueSetHeader
import edu.mayo.cts2.framework.plugin.service.nlm.profile.AbstractService
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.ResolvedValueSetResolutionEntityQuery
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.ResolvedValueSetResult
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.ValueSetDefinitionResolutionService
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.name.ValueSetDefinitionReadId
import gov.ahrq.ushik.webservices.USHIK
import gov.ahrq.ushik.webservices.USHIKService
import gov.cdc.vocab.service.bean.ValueSetConcept
import net.liftweb.json._
import net.liftweb.json.Extraction._
import net.liftweb.json.Printer._
import edu.mayo.cts2.framework.plugin.service.nlm.umls.dao.UmlsDao
import javax.annotation.Resource
import edu.mayo.cts2.framework.plugin.service.nlm.namespace.NamespaceResolutionService
import edu.mayo.cts2.framework.plugin.service.nlm.ushik.dao.UshikDao

@Component
class UshikValueSetDefinitionResolutionService extends AbstractService with ValueSetDefinitionResolutionService {

  @Resource
  var umlsDao: UmlsDao = _
  
  @Resource
  var ushikDao: UshikDao = _

  @Resource
  var namespaceResolutionService: NamespaceResolutionService = _

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

    val valueSetId = id.getName()

    val codeMap = ushikDao.valueSetEntries.get(valueSetId).getOrElse(return null);

    val codes = codeMap.foldLeft(List[EntitySynopsis]())(
      (list, map) => {
        val sab = ushikDao.sabs.get(map.get("codesystem").get).get
        val code = map.get("code").get
        val synopsis = new EntitySynopsis()
        synopsis.setName(code)
        synopsis.setNamespace(sab)
        synopsis.setUri(namespaceResolutionService.prefixToUri(sab) + code)
        synopsis.setDesignation(map.getOrElse("designation", null))

        list ++ List(synopsis)
      })

    new ResolvedValueSetResult[EntitySynopsis](buildHeader(), codes, true)
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

class VsCode(_code: String, _codeSystem: String) {
  val code: String = _code
  val codeSystem: String = _codeSystem
}
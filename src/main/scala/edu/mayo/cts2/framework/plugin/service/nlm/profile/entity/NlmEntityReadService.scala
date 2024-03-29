package edu.mayo.cts2.framework.plugin.service.nlm.profile.entity

import scala.Option.option2Iterable
import scala.collection.JavaConversions.seqAsJavaList
import scala.collection.JavaConverters.asScalaBufferConverter
import scala.collection.JavaConverters.mapAsScalaMapConverter
import org.elasticsearch.action.get.GetResponse
import org.springframework.stereotype.Component
import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.model.command.ResolvedReadContext
import edu.mayo.cts2.framework.model.core.CodeSystemReference
import edu.mayo.cts2.framework.model.core.CodeSystemVersionReference
import edu.mayo.cts2.framework.model.core.Definition
import edu.mayo.cts2.framework.model.core.EntityReference
import edu.mayo.cts2.framework.model.core.NameAndMeaningReference
import edu.mayo.cts2.framework.model.core.SortCriteria
import edu.mayo.cts2.framework.model.core.URIAndEntityName
import edu.mayo.cts2.framework.model.core.VersionTagReference
import edu.mayo.cts2.framework.model.directory.DirectoryResult
import edu.mayo.cts2.framework.model.entity.Designation
import edu.mayo.cts2.framework.model.entity.EntityDescription
import edu.mayo.cts2.framework.model.entity.EntityList
import edu.mayo.cts2.framework.model.entity.EntityListEntry
import edu.mayo.cts2.framework.model.entity.NamedEntityDescription
import edu.mayo.cts2.framework.model.service.core.EntityNameOrURI
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.nlm.index.dao.ElasticSearchIndexDao
import edu.mayo.cts2.framework.plugin.service.nlm.profile.AbstractService
import edu.mayo.cts2.framework.service.profile.entitydescription.name.EntityDescriptionReadId
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionReadService
import javax.annotation.Resource
import edu.mayo.cts2.framework.plugin.service.nlm.umls.UmlsService
import edu.mayo.cts2.framework.plugin.service.nlm.namespace.NamespaceResolutionService
import edu.mayo.cts2.framework.plugin.service.nlm.umls.UmlsConstants
import edu.mayo.cts2.framework.model.core.Property
import edu.mayo.cts2.framework.model.core.StatementTarget
import edu.mayo.cts2.framework.model.core.PredicateReference

@Component
class NlmEntityReadService extends AbstractService
  with EntityDescriptionReadService {

  @Resource
  var indexDao: ElasticSearchIndexDao = _

  @Resource
  var namespaceResolutionService: NamespaceResolutionService = _

  def readEntityDescriptions(p1: EntityNameOrURI, p2: SortCriteria, p3: ResolvedReadContext, p4: Page): DirectoryResult[EntityListEntry] = throw new RuntimeException()

  def availableDescriptions(p1: EntityNameOrURI, p2: ResolvedReadContext): EntityReference = throw new RuntimeException()

  def readEntityDescriptions(p1: EntityNameOrURI, p2: ResolvedReadContext): EntityList = throw new RuntimeException()

  def getKnownCodeSystems: java.util.List[CodeSystemReference] = throw new RuntimeException()

  def getKnownCodeSystemVersions: java.util.List[CodeSystemVersionReference] = throw new RuntimeException()

  def read(id: EntityDescriptionReadId, context: ResolvedReadContext = null): EntityDescription = {

    val key = getKey(id).getOrElse(return null)

    val result = indexDao.get("entity", key, entityFormatter)

    result.getOrElse(return null)
  }

  private def getKey(id: EntityDescriptionReadId): Option[String] = {

    val csvName = this.umlsService.getRSab(id.getCodeSystemVersion().getName())

    if (csvName.isDefined) {
      Some(List(csvName.get, ":", id.getEntityName().getName()).reduceLeft(_ + _))
    } else {
      None
    }
  }

  private def buildCuiProperty(cui: String): Property = {

    val cuiProp: Property = new Property()

    val target: StatementTarget = new StatementTarget()
    target.setLiteral(ModelUtils.createOpaqueData(cui))

    cuiProp.addValue(target)
    
    val predicate = new PredicateReference()
    predicate.setName(UmlsConstants.NLM_CUI_NS_PREFIX)
    predicate.setNamespace(UmlsConstants.NLM_NS_PREFIX)
    predicate.setUri(UmlsConstants.NLM_CUI_NS)
   
    cuiProp.setPredicate(predicate)
    
    cuiProp
  }

  def entityFormatter = (getResponse: GetResponse) => {
    var source = getResponse.getSource()

    var entity = new NamedEntityDescription()

    val code = source.get("code").toString()
    val sab = source.get("sab").toString()
    val cui = source.get("cui").toString()
    val descriptions = source.get("descriptions").asInstanceOf[java.util.List[java.util.HashMap[String, String]]].asScala
    val definitions = source.get("definitions").asInstanceOf[java.util.List[java.util.HashMap[String, String]]].asScala

    descriptions.flatMap((x) => x.asScala.get("value")).foreach((value: String) => {
      val designation = new Designation()
      designation.setValue(ModelUtils.toTsAnyType(value))
      entity.addDesignation(designation)
    })

    definitions.flatMap((x) => x.asScala.get("value")).foreach((value: String) => {
      val definition = new Definition()
      definition.setValue(ModelUtils.toTsAnyType(value))
      entity.addDefinition(definition)
    })

    entity.setEntityID(ModelUtils.createScopedEntityName(code, sab))
    entity.addAlternateEntityID(ModelUtils.createScopedEntityName(cui, UmlsConstants.NLM_CUI_NS_PREFIX))

    entity.setAbout(namespaceResolutionService.prefixToUri(sab) + code)

    val entityType = new URIAndEntityName()
    entityType.setName("Class")
    entityType.setNamespace("owl")
    entityType.setUri("http://www.w3.org/2002/07/owl#Class")

    entity.addEntityType(entityType)

    entity.setDescribingCodeSystemVersion(
      buildCodeSystemVersionReference(sab))
      
    entity.addProperty(buildCuiProperty(cui))

    var entityDescription = new EntityDescription()
    entityDescription.setNamedEntity(entity)

    entityDescription
  }: EntityDescription

  def exists(p1: EntityDescriptionReadId, p2: ResolvedReadContext): Boolean = throw new RuntimeException()

  def getSupportedVersionTags: java.util.List[VersionTagReference] =
    List[VersionTagReference](CURRENT_TAG)
}
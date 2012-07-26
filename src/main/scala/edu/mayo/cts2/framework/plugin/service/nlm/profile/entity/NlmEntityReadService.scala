package edu.mayo.cts2.framework.plugin.service.nlm.profile.entity

import scala.annotation.implicitNotFound
import org.elasticsearch.action.get.GetResponse
import org.springframework.stereotype.Component
import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.model.command.ResolvedReadContext
import edu.mayo.cts2.framework.model.core.CodeSystemReference
import edu.mayo.cts2.framework.model.core.CodeSystemVersionReference
import edu.mayo.cts2.framework.model.core.EntityReference
import edu.mayo.cts2.framework.model.core.NameAndMeaningReference
import edu.mayo.cts2.framework.model.core.SortCriteria
import edu.mayo.cts2.framework.model.core.VersionTagReference
import edu.mayo.cts2.framework.model.directory.DirectoryResult
import edu.mayo.cts2.framework.model.entity.EntityDescription
import edu.mayo.cts2.framework.model.entity.EntityList
import edu.mayo.cts2.framework.model.entity.EntityListEntry
import edu.mayo.cts2.framework.model.entity.NamedEntityDescription
import edu.mayo.cts2.framework.model.service.core.DocumentedNamespaceReference
import edu.mayo.cts2.framework.model.service.core.EntityNameOrURI
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.nlm.index.dao.ElasticSearchIndexDao
import edu.mayo.cts2.framework.plugin.service.nlm.model.VersionedNameParser
import edu.mayo.cts2.framework.plugin.service.nlm.profile.AbstractService
import edu.mayo.cts2.framework.service.profile.entitydescription.name.EntityDescriptionReadId
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionReadService
import javax.annotation.Resource
import edu.mayo.cts2.framework.model.core.URIAndEntityName
import edu.mayo.cts2.framework.model.entity.Designation
import scala.collection.JavaConverters._
import edu.mayo.cts2.framework.model.core.Definition

@Component
class NlmEntityReadService extends AbstractService with EntityDescriptionReadService with VersionedNameParser {

  @Resource
  var indexDao: ElasticSearchIndexDao = _

  def readEntityDescriptions(p1: EntityNameOrURI, p2: SortCriteria, p3: ResolvedReadContext, p4: Page): DirectoryResult[EntityListEntry] = throw new RuntimeException()

  def availableDescriptions(p1: EntityNameOrURI, p2: ResolvedReadContext): EntityReference = throw new RuntimeException()

  def readEntityDescriptions(p1: EntityNameOrURI, p2: ResolvedReadContext): EntityList = throw new RuntimeException()

  def getKnownCodeSystems: java.util.List[CodeSystemReference] = throw new RuntimeException()

  def getKnownCodeSystemVersions: java.util.List[CodeSystemVersionReference] = throw new RuntimeException()

  def getSupportedVersionTags: java.util.List[VersionTagReference] = throw new RuntimeException()

  def read(id: EntityDescriptionReadId, context: ResolvedReadContext = null): EntityDescription = {

    val result = indexDao.get("entity", getKey(id), entityFormatter)
    
    if(result.isDefined){
      result get
    } else {
      null
    }
  }

  private def getKey(id: EntityDescriptionReadId): String = {

    val csvName = this.toVersionedName(id.getCodeSystemVersion().getName())

    List(csvName.getName(), ":", id.getEntityName().getName()).reduceLeft(_ + _)
  }

  def entityFormatter = (getResponse: GetResponse) => {
    var source = getResponse.getSource()

    var entity = new NamedEntityDescription()

    val code = source.get("code").toString()
    val sab = source.get("sab").toString()
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

    entity.setAbout("http://test/org")

    val entityType = new URIAndEntityName()
    entityType.setName("Class")
    entityType.setNamespace("owl")
    entityType.setUri("http://www.w3.org/2002/07/owl#Class")

    entity.addEntityType(entityType)

    val ref = new CodeSystemVersionReference()
    ref.setCodeSystem(new CodeSystemReference(sab))
    ref.setVersion(new NameAndMeaningReference(sab))

    entity.setDescribingCodeSystemVersion(ref)

    var entityDescription = new EntityDescription()
    entityDescription.setNamedEntity(entity)

    entityDescription
  }: EntityDescription

  def exists(p1: EntityDescriptionReadId, p2: ResolvedReadContext): Boolean = throw new RuntimeException()

  def getKnownNamespaceList: java.util.List[DocumentedNamespaceReference] = throw new RuntimeException()

}
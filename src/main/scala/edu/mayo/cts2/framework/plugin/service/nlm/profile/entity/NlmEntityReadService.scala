package edu.mayo.cts2.framework.plugin.service.nlm.profile.entity

import java.util.List
import scala.annotation.implicitNotFound
import org.elasticsearch.action.get.GetResponse
import org.springframework.stereotype.Component
import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.model.command.ResolvedReadContext
import edu.mayo.cts2.framework.model.core.CodeSystemReference
import edu.mayo.cts2.framework.model.core.CodeSystemVersionReference
import edu.mayo.cts2.framework.model.core.EntityReference
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
import edu.mayo.cts2.framework.plugin.service.nlm.index.ElasticSearchIndexDao
import edu.mayo.cts2.framework.plugin.service.nlm.profile.AbstractService
import edu.mayo.cts2.framework.service.profile.entitydescription.name.EntityDescriptionReadId
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionReadService
import javax.annotation.Resource
import edu.mayo.cts2.framework.model.core.NameAndMeaningReference
import edu.mayo.cts2.framework.plugin.service.nlm.model.VersionedNameParser

@Component
class NlmEntityReadService extends AbstractService with EntityDescriptionReadService with VersionedNameParser {

  @Resource
  var indexDao: ElasticSearchIndexDao = _

  def readEntityDescriptions(p1: EntityNameOrURI, p2: SortCriteria, p3: ResolvedReadContext, p4: Page): DirectoryResult[EntityListEntry] = throw new RuntimeException()

  def availableDescriptions(p1: EntityNameOrURI, p2: ResolvedReadContext): EntityReference = throw new RuntimeException()

  def readEntityDescriptions(p1: EntityNameOrURI, p2: ResolvedReadContext): EntityList = throw new RuntimeException()

  def getKnownCodeSystems: List[CodeSystemReference] = throw new RuntimeException()

  def getKnownCodeSystemVersions: List[CodeSystemVersionReference] = throw new RuntimeException()

  def getSupportedVersionTags: List[VersionTagReference] = throw new RuntimeException()

  def read(id: EntityDescriptionReadId, context: ResolvedReadContext = null): EntityDescription = {

    indexDao.get("entity", getKey(id), entityFormatter)
  }

  def getKey(id: EntityDescriptionReadId): String = {

    val csvName = this.toVersionedName(id.getCodeSystemVersion().getName())

    csvName.getName() + ":" + id.getEntityName().getName()
  }

  def entityFormatter = (getResponse: GetResponse) => {
    var source = getResponse.getSource()

    var entity = new NamedEntityDescription()

    val sab = source.get("code").toString()

    entity.setEntityID(ModelUtils.createScopedEntityName(
      source.get("sab").toString,
      sab))

    val ref = new CodeSystemVersionReference()
    ref.setCodeSystem(new CodeSystemReference(sab))
    ref.setVersion(new NameAndMeaningReference(sab))

    entity.setDescribingCodeSystemVersion(ref)

    var entityDescription = new EntityDescription()
    entityDescription.setNamedEntity(entity)

    entityDescription
  }: EntityDescription

  def exists(p1: EntityDescriptionReadId, p2: ResolvedReadContext): Boolean = throw new RuntimeException()

  def getKnownNamespaceList: List[DocumentedNamespaceReference] = throw new RuntimeException()

}
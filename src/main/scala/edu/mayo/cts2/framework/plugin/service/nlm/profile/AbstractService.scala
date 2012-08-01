package edu.mayo.cts2.framework.plugin.service.nlm.profile

import edu.mayo.cts2.framework.service.profile.BaseService
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.model.core.SourceReference
import edu.mayo.cts2.framework.model.service.core.DocumentedNamespaceReference
import edu.mayo.cts2.framework.model.core.OpaqueData
import edu.mayo.cts2.framework.model.core.VersionTagReference
import scala.collection.JavaConversions._
import edu.mayo.cts2.framework.model.core.CodeSystemVersionReference
import edu.mayo.cts2.framework.plugin.service.nlm.umls.UmlsService
import javax.annotation.Resource
import edu.mayo.cts2.framework.model.core.NameAndMeaningReference
import edu.mayo.cts2.framework.model.core.CodeSystemReference
import edu.mayo.cts2.framework.core.url.UrlConstructor

abstract class AbstractService extends BaseService {

  val MAYO = "Mayo Clinic"
  val DEFAULT_VERSION = "1.0"
  val DESCRIPTION = "CTS2 Service Implementation using the NLM UMLS."
  val CURRENT_TAG = {
    new VersionTagReference("CURRENT")
  }

  @Resource
  var umlsService: UmlsService = _
  
  @Resource
  var urlConstructor: UrlConstructor = _
  
  def setUmlsService(service: UmlsService){
    umlsService = service
  }

  override def getServiceVersion(): String = {
    DEFAULT_VERSION
  }

  override def getServiceProvider(): SourceReference = {
    var ref = new SourceReference()
    ref.setContent(MAYO)

    ref
  }

  override def getServiceDescription(): OpaqueData = {
    return ModelUtils.createOpaqueData(DESCRIPTION)
  }

  override def getServiceName(): String = {
    return this.getClass().getCanonicalName()
  }

  def buildCodeSystemVersionReference(sab: String) = {
    val ref = new CodeSystemVersionReference()
    val versionRef = new NameAndMeaningReference()
    val codeSystemRef = new CodeSystemReference()

    val vsab = umlsService.getVSab(sab).getOrElse( throw new RuntimeException("SAB: " + sab + " not found."))
    val csUri = umlsService.getUriFromRSab(sab)
    val csvUri = umlsService.getUriFromVSab(vsab)

    versionRef.setContent(vsab)
    versionRef.setUri(csvUri)

    codeSystemRef.setContent(sab)
    codeSystemRef.setUri(csUri)
    codeSystemRef.setHref(urlConstructor.createCodeSystemUrl(sab))

    ref.setCodeSystem(codeSystemRef)
    ref.setVersion(versionRef)

    ref
  }

  def getKnownNamespaceList: java.util.List[DocumentedNamespaceReference] = List[DocumentedNamespaceReference]()

}


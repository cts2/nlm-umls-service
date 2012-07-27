package edu.mayo.cts2.framework.plugin.service.nlm.profile

import edu.mayo.cts2.framework.service.profile.BaseService
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.model.core.SourceReference
import edu.mayo.cts2.framework.model.service.core.DocumentedNamespaceReference
import edu.mayo.cts2.framework.model.core.OpaqueData
import edu.mayo.cts2.framework.model.core.VersionTagReference
import scala.collection.JavaConversions._

abstract class AbstractService extends BaseService {

  val MAYO = "Mayo Clinic"
  val DEFAULT_VERSION = "1.0"
  val DESCRIPTION = "CTS2 Service Implementation using the NLM UMLS."
  val CURRENT_TAG = {
    new VersionTagReference("CURRENT")
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


  def getKnownNamespaceList: java.util.List[DocumentedNamespaceReference] = List[DocumentedNamespaceReference]()

}


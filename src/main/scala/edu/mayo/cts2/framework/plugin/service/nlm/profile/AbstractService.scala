package edu.mayo.cts2.framework.plugin.service.nlm.profile

import edu.mayo.cts2.framework.model.service.core.BaseService
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.model.core.SourceReference
import edu.mayo.cts2.framework.model.service.core.DocumentedNamespaceReference
import edu.mayo.cts2.framework.model.core.OpaqueData

abstract class AbstractService extends BaseService {

  var MAYO = "Mayo Clinic"
  var DEFAULT_VERSION = "1.0"
  var DESCRIPTION = "CTS2 Service Implementation using the NLM UMLS."

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

}


package edu.mayo.cts2.framework.plugin.service.nlm.namespace

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import clojure.lang.RT
import edu.mayo.cts2.framework.plugin.service.nlm.umls.UmlsService

@Component("namespaceResolutionService")
class NamespaceResolutionService {

  RT.loadResourceScript("cts2/ns/NamespaceService.clj");

  val getNsUriFn = RT.`var`("cts2.ns", "getNsUri")
  val getNsPrefixFn = RT.`var`("cts2.ns", "getNsPrefix")

  @Value("${namespaceServiceUrl}")
  var namespaceServiceUrl: String = _
  
  var umlsService: UmlsService = _

  def prefixToUri(prefix: String): String = {

    var uri = getNsUriFn.invoke(namespaceServiceUrl, prefix)

    if (uri != null) {
      uri.toString()
    } else {
      umlsService.getUriFromRSab(prefix)
    }
  }

  def uriToPrefix(prefix: String): String = {
    var uri = getNsPrefixFn.invoke(namespaceServiceUrl, prefix)

    if (uri != null) {
      uri.toString()
    } else {
      throw new UnsupportedOperationException()
    }
  }

}
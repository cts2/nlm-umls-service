package edu.mayo.cts2.framework.plugin.service.nlm.profile.codesystem

import java.lang.Override
import scala.collection.JavaConversions._
import org.springframework.stereotype.Component
import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntry
import edu.mayo.cts2.framework.model.command.ResolvedReadContext
import edu.mayo.cts2.framework.model.core.VersionTagReference
import edu.mayo.cts2.framework.model.service.core.NameOrURI
import edu.mayo.cts2.framework.plugin.service.nlm.profile.AbstractService
import edu.mayo.cts2.framework.service.profile.codesystemversion.CodeSystemVersionReadService
import edu.mayo.cts2.framework.plugin.service.nlm.umls.UmlsService
import javax.annotation.Resource
import edu.mayo.cts2.framework.service.profile.codesystem.CodeSystemReadService
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntry
import edu.mayo.cts2.framework.plugin.service.nlm.umls.dao.UmlsDao
import org.springframework.beans.factory.InitializingBean
import edu.mayo.cts2.framework.model.core.SourceAndRoleReference
import edu.mayo.cts2.framework.model.core.SourceReference
import edu.mayo.cts2.framework.model.core.RoleReference
import edu.mayo.cts2.framework.plugin.service.nlm.umls.UmlsConstants

@Component
class NlmCodeSystemReadService
  extends AbstractService
  with CodeSystemReadService {

  def currentFilter(map: Map[String, String]) = {
    map.get("CURVER").get.equals("Y")
  }

  @Resource
  var umlsDao: UmlsDao = _

  var codeSystems: Map[String, CodeSystemCatalogEntry] = _

  def cache() {
    codeSystems = umlsDao.getMrsabRows().
      filter(currentFilter).
      map(mapToCodeSystem).
      foldLeft[Map[String, CodeSystemCatalogEntry]](Map[String, CodeSystemCatalogEntry]()) {
        (result, current) =>
          {
            List(result, current).reduce(_ ++ _)
          }
      }
  }

  def mapToCodeSystem = (map: Map[String, String]) => {
    val cs = new CodeSystemCatalogEntry()
    val rsab = map.get("RSAB").get
    val formalName = map.get("SSN").get
 
    cs.setCodeSystemName(rsab)
    cs.setFormalName(formalName)
    map.get("SLC").foreach { 
      (slc:String) => {
        cs.addSourceAndRole(buildSourceAndRole(slc, UmlsConstants.SOURCE_LICENSE_CONTACT))
      }
    }
    map.get("SCC").foreach { 
      (scc:String) => {
            cs.addSourceAndRole(buildSourceAndRole(scc, UmlsConstants.SOURCE_CONTENT_CONTACT))
      }
    }

    Map(rsab -> cs)
  }
  
  def buildSourceAndRole(name:String, role:String) : SourceAndRoleReference = {
    val sourceAndRole = new SourceAndRoleReference()
    val sourceRef = new SourceReference(name)
    val roleRef = new RoleReference(role)
    
    sourceAndRole.setSource(sourceRef)
    sourceAndRole.setRole(roleRef)
    
    sourceAndRole
  }

  @Override
  def read(identifier: NameOrURI,
    readContext: ResolvedReadContext): CodeSystemCatalogEntry = {

    if (codeSystems == null) {
      cache()
    }

    codeSystems.get(identifier.getName).get
  }

  @Override
  def exists(identifier: NameOrURI, readContext: ResolvedReadContext): Boolean = {
    throw new UnsupportedOperationException()
  }

  def getSupportedTags: java.util.List[VersionTagReference] =
    List[VersionTagReference](CURRENT_TAG)

}
package edu.mayo.cts2.framework.plugin.service.nlm.profile.codesystemversion

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

@Component
class NlmCodeSystemVersionService extends AbstractService with CodeSystemVersionReadService {

  /**
   * This is incomplete... this is only here to map the 'CURRENT' tag to a CodeSystemVersionName.
   */
  @Override
  def readByTag(
    codeSystem: NameOrURI,
    tag: VersionTagReference, readContext: ResolvedReadContext): CodeSystemVersionCatalogEntry = {

    if (tag.getContent() == null || !tag.getContent().equals("CURRENT")) {
      throw new RuntimeException("Only 'CURRENT' tag is supported")
    }

    val vsab = umlsService.getVSab(codeSystem.getName).getOrElse( 
        throw new RuntimeException("SAB: " + codeSystem.getName + " not found."))
    val version = new CodeSystemVersionCatalogEntry()
    version.setCodeSystemVersionName(vsab)

    version
  }

  @Override
  def existsByTag(parentIdentifier: NameOrURI,
    tag: VersionTagReference, readContext: ResolvedReadContext): Boolean = {
    throw new UnsupportedOperationException()
  }

  @Override
  def read(identifier: NameOrURI,
    readContext: ResolvedReadContext): CodeSystemVersionCatalogEntry = {
    throw new UnsupportedOperationException()
  }

  @Override
  def exists(identifier: NameOrURI, readContext: ResolvedReadContext): Boolean = {
    throw new UnsupportedOperationException()
  }

  @Override
  def existsVersionId(codeSystem: NameOrURI,
    officialResourceVersionId: String): Boolean = {
    throw new UnsupportedOperationException()
  }

  @Override
  def getCodeSystemByVersionId(
    codeSystem: NameOrURI, officialResourceVersionId: String,
    readContext: ResolvedReadContext): CodeSystemVersionCatalogEntry = {
    throw new UnsupportedOperationException()
  }

  def getSupportedTags: java.util.List[VersionTagReference] =
    List[VersionTagReference](CURRENT_TAG)

}
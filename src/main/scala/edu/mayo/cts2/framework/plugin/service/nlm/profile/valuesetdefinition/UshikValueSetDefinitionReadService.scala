package edu.mayo.cts2.framework.plugin.service.nlm.profile.valuesetdefinition

import java.lang.Override
import scala.collection.JavaConversions._
import org.springframework.stereotype.Component
import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntry
import edu.mayo.cts2.framework.model.command.ResolvedReadContext
import edu.mayo.cts2.framework.model.core.VersionTagReference
import edu.mayo.cts2.framework.model.service.core.NameOrURI
import edu.mayo.cts2.framework.plugin.service.nlm.profile.AbstractService
import edu.mayo.cts2.framework.service.profile.codesystemversion.CodeSystemVersionReadService
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.ValueSetDefinitionReadService
import edu.mayo.cts2.framework.model.extension.LocalIdValueSetDefinition
import edu.mayo.cts2.framework.model.valuesetdefinition.ValueSetDefinition
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.name.ValueSetDefinitionReadId
import edu.mayo.cts2.framework.plugin.service.nlm.phinvads.dao.PhinVadsDao
import javax.annotation.Resource
import gov.cdc.vocab.service.dto.input.ValueSetVersionSearchCriteriaDto

@Component
class UshikValueSetDefinitionReadService extends AbstractService with ValueSetDefinitionReadService {

  /**
   * This is incomplete... this is only here to map the 'CURRENT' tag to a CodeSystemVersionName.
   */
  @Override
  def readByTag(
    valueSet: NameOrURI,
    tag: VersionTagReference, readContext: ResolvedReadContext): LocalIdValueSetDefinition = {

    if (tag.getContent() == null || !tag.getContent().equals("CURRENT")) {
      throw new RuntimeException("Only 'CURRENT' tag is supported")
    }

    val valueSetName = valueSet.getName()

    if (valueSetName.startsWith("A_") || valueSetName.startsWith("N_")) {
      val localIdDefinition =
        new LocalIdValueSetDefinition(valueSetName, null)

      localIdDefinition
    } else {
      null
    }
  }

  @Override
  def existsByTag(valueSet: NameOrURI,
    tag: VersionTagReference, readContext: ResolvedReadContext): Boolean = {
    throw new UnsupportedOperationException()
  }

  @Override
  def read(identifier: ValueSetDefinitionReadId,
    readContext: ResolvedReadContext): LocalIdValueSetDefinition = {
    throw new UnsupportedOperationException()
  }

  @Override
  def exists(identifier: ValueSetDefinitionReadId, readContext: ResolvedReadContext): Boolean = {
    throw new UnsupportedOperationException()
  }

  def getSupportedTags: java.util.List[VersionTagReference] =
    List[VersionTagReference](CURRENT_TAG)

}
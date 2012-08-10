package edu.mayo.cts2.framework.plugin.service.nlm.profile.valueset

import java.lang.Override
import scala.collection.JavaConversions._
import org.springframework.stereotype.Component
import com.caucho.hessian.client.HessianProxyFactory
import edu.mayo.cts2.framework.model.service.core.NameOrURI
import edu.mayo.cts2.framework.plugin.service.nlm.profile.AbstractService
import gov.cdc.vocab.service.VocabService
import gov.cdc.vocab.service.bean.ValueSet
import collection.JavaConversions._
import javax.annotation.Resource
import java.util.Set
import edu.mayo.cts2.framework.service.profile.valueset.{ValueSetQuery, ValueSetReadService, ValueSetQueryService}
import edu.mayo.cts2.framework.model.command.{Page, ResolvedReadContext}
import edu.mayo.cts2.framework.model.directory.DirectoryResult
import edu.mayo.cts2.framework.model.valueset.{ValueSetCatalogEntrySummary, ValueSetCatalogEntry}
import edu.mayo.cts2.framework.model.core.{PropertyReference, PredicateReference, SortCriteria, MatchAlgorithmReference}
import gov.cdc.vocab.service.dto.input.CodeSystemSearchCriteriaDto

@Component
class UshikValueSetQueryService
  extends AbstractService
  with ValueSetQueryService {

  def getSupportedMatchAlgorithms: Set[_ <: MatchAlgorithmReference] = null

  def getSupportedSearchReferences: Set[_ <: PropertyReference] = null

  def getSupportedSortReferences: Set[_ <: PropertyReference] = null

  def getKnownProperties: Set[PredicateReference] = null

  def getResourceSummaries(query: ValueSetQuery, sort: SortCriteria, page: Page): DirectoryResult[ValueSetCatalogEntrySummary] = {
    new DirectoryResult[ValueSetCatalogEntrySummary](null,true)
  }

  def getResourceList(p1: ValueSetQuery, p2: SortCriteria, p3: Page): DirectoryResult[ValueSetCatalogEntry] = null

  def count(p1: ValueSetQuery): Int = 0

 
}
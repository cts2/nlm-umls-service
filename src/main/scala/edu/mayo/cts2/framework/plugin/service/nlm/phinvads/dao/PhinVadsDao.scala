package edu.mayo.cts2.framework.plugin.service.nlm.profile.valueset

import java.lang.Override
import scala.collection.JavaConversions._
import org.springframework.stereotype.Component
import com.caucho.hessian.client.HessianProxyFactory
import edu.mayo.cts2.framework.model.command.ResolvedReadContext
import edu.mayo.cts2.framework.model.service.core.NameOrURI
import edu.mayo.cts2.framework.model.valueset.ValueSetCatalogEntry
import edu.mayo.cts2.framework.plugin.service.nlm.profile.AbstractService
import edu.mayo.cts2.framework.service.profile.valueset.ValueSetReadService
import gov.cdc.vocab.service.VocabService
import gov.cdc.vocab.service.bean.ValueSet
import edu.mayo.cts2.framework.model.valuesetdefinition.ResolvedValueSetSummary
import gov.cdc.vocab.service.bean.ValueSetVersion

@Component
class PhinVadsDao {

  val phinvadsServiceUrl = "http://phinvads.cdc.gov/vocabService/v2"

  val vocabService: VocabService = initPhinVadsClient()

  var valueSets: Seq[ValueSet] = cacheValueSets()

  var valueSetVersions: Seq[ValueSetVersion] = cacheValueSetVersions()

  private def initPhinVadsClient() = {
    val factory = new HessianProxyFactory();

    factory.create(classOf[VocabService], phinvadsServiceUrl).asInstanceOf[VocabService]
  }

  def getValueSets(): Seq[ValueSet] = {
    valueSets
  }

  def getValueSetVersions(): Seq[ValueSetVersion] = {
    valueSetVersions
  }
  
  private def cacheValueSets() = {
    vocabService.getAllValueSets().getValueSets()
  }

  private def cacheValueSetVersions() = {
    vocabService.getAllValueSetVersions().getValueSetVersions()
  }

}
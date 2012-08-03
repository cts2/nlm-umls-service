package edu.mayo.cts2.framework.plugin.service.nlm.phinvads.dao

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
import org.springframework.beans.factory.InitializingBean
import com.caucho.hessian.io.SerializerFactory
import javax.annotation.Resource

@Component
class PhinVadsDao extends InitializingBean {

  val phinvadsServiceUrl = "http://phinvads.cdc.gov/vocabService/v2"
    
  @Resource
  var serializerFactory:SerializerFactory = _

  var vocabService: VocabService = _

  var valueSets: Seq[ValueSet] = _

  var valueSetByNameMap:Map[String, ValueSet] = _
  
  var valueSetVersions: Seq[ValueSetVersion] = _
  
  def afterPropertiesSet() {
    vocabService = initPhinVadsClient()
    valueSets= cacheValueSets()
    valueSetByNameMap = valueSets.foldLeft(Map[String, ValueSet]())((map, valueSet) => {
      map ++ Map(valueSet.getCode() -> valueSet)
    })
    
    valueSetVersions = cacheValueSetVersions()
  }

  private def initPhinVadsClient() = {
    val factory = new HessianProxyFactory();
    factory.setSerializerFactory(serializerFactory)
    factory.create(classOf[VocabService], phinvadsServiceUrl).asInstanceOf[VocabService]
  }

  def getValueSets(): Seq[ValueSet] = {
    valueSets
  }

  def getValueSetOid(valueSetName:String): String = {
    valueSetByNameMap.get(valueSetName).get.getOid
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
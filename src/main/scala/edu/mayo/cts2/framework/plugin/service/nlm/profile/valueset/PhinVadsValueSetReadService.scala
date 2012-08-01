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

@Component
class PhinVadsValueSetReadService
  extends AbstractService
  with ValueSetReadService {

  val phinvadsServiceUrl = "http://phinvads.cdc.gov/vocabService/v2"
    
  var vocabService: VocabService = _
  
  var valueSets:Iterable[ValueSetCatalogEntry] = _

  def initPhinVadsClient() = {
     val factory = new HessianProxyFactory();

     vocabService = factory.create(classOf[VocabService], phinvadsServiceUrl).asInstanceOf[VocabService] 
  }
  
  def transformPhinVadsValueSet = (phinvadsvs:ValueSet) => {
    val entry:ValueSetCatalogEntry = new ValueSetCatalogEntry()
    
    entry.setValueSetName(phinvadsvs.getName())

    entry
  }:ValueSetCatalogEntry
  
  def cache(){
    initPhinVadsClient()
    valueSets = vocabService.getAllValueSets().getValueSets().map( transformPhinVadsValueSet )
  }
  
  @Override
  def read(identifier: NameOrURI,
    readContext: ResolvedReadContext): ValueSetCatalogEntry = {

    null
  }

  @Override
  def exists(identifier: NameOrURI, readContext: ResolvedReadContext): Boolean = {
    throw new UnsupportedOperationException()
  }
}
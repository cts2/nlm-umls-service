package edu.mayo.cts2.framework.plugin.service.nlm.umls

import org.springframework.stereotype.Component
import edu.mayo.cts2.framework.plugin.service.nlm.umls.dao.UmlsDao
import javax.annotation.Resource
import org.springframework.beans.factory.InitializingBean
import edu.mayo.cts2.framework.plugin.service.nlm.umls.dao.SabResult

@Component
class UmlsService {

  val NLM_NS = "http:/id.nlm.nih.gov/cui/"

  @Resource
  var umlsDao: UmlsDao = _

  var rsabToVsabMap: Map[String, String] = _
  var rsabToCuiMap: Map[String, String] = _
  var vsabToRsabMap: Map[String, String] = _
  var vsabToCuiMap: Map[String, String] = _

  def cache() {
    if (rsabToVsabMap == null ||
      rsabToCuiMap == null ||
      vsabToCuiMap == null ||
      vsabToCuiMap == null) {

      rsabToVsabMap = Map[String, String]()
      rsabToCuiMap = Map[String, String]()
      vsabToRsabMap = Map[String, String]()
      vsabToCuiMap = Map[String, String]()

      umlsDao.getSabs.foreach((result: SabResult) => {
        rsabToVsabMap += result.rsab -> result.vsab
        rsabToCuiMap += result.rsab -> result.rcui
        vsabToRsabMap += result.vsab -> result.rsab
        vsabToCuiMap += result.vsab -> result.vcui
      })
    }
  }

  private def get(sab: String, map: Map[String, String]) = {
    map.getOrElse(sab, throw new RuntimeException("SAB: " + sab + " not found."))
  }

  def getVSab(rsab: String): String = {
    cache()
    get(rsab, rsabToVsabMap)
  }

  def getRSab(rsab: String): String = {
    cache()
    get(rsab, vsabToRsabMap)
  }

  def getUriFromRSab(rsab: String): String = {
    cache()
    NLM_NS + get(rsab, rsabToCuiMap)
  }

  def getUriFromVSab(vsab: String): String = {
    cache()
    NLM_NS + get(vsab, vsabToCuiMap)
  }
}

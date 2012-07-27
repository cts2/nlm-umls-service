package edu.mayo.cts2.framework.plugin.service.nlm.umls

import org.springframework.stereotype.Component
import edu.mayo.cts2.framework.plugin.service.nlm.umls.dao.UmlsDao
import javax.annotation.Resource
import org.springframework.beans.factory.InitializingBean
import edu.mayo.cts2.framework.plugin.service.nlm.umls.dao.SabResult

@Component
class UmlsService {

  @Resource
  var umlsDao: UmlsDao = _
  
  var rsabToVsabMap: Map[String, String] = _
  var vsabToRsabMap: Map[String, String] = _

  def cache() {
    if (rsabToVsabMap == null || rsabToVsabMap == null) {
      rsabToVsabMap = Map[String, String]()
      vsabToRsabMap = Map[String, String]()

      umlsDao.getSabs.foreach((result: SabResult) => {
        rsabToVsabMap += result.rsab -> result.vsab
        vsabToRsabMap += result.vsab -> result.rsab
      })
    }
  }

  def getSab(sab: String, map: Map[String, String]) = {
    map.getOrElse(sab, throw new RuntimeException("SAB: " + sab + " not found."))
  }

  def getVSab(rsab: String): String = {
    cache()
    getSab(rsab, rsabToVsabMap)
  }

  def getRSab(rsab: String): String = {
    cache()
    getSab(rsab, vsabToRsabMap)
  }
}

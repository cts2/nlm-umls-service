package edu.mayo.cts2.framework.plugin.service.nlm.umls.dao

import scala.collection.JavaConverters.asScalaBufferConverter
import scala.reflect.BeanProperty
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.stereotype.Component

import javax.annotation.Resource

@Component
class UmlsDao {

  @Resource
  var template: SqlSessionTemplate = _

  def getSabs(): Iterable[SabResult] = {
    template.selectList[SabResult]("getSabs").asScala
  }

  def getMrsabRows(): Iterable[Map[String, String]] = {
    template.selectList[java.util.HashMap[String, String]]("getMrsabRows").map(x => x.asScala.toMap)
  }

  def getStrFromScui = (scui: String, sab:String) => {
    template.selectOne("getStrFromScui", Map("scui" -> scui, "sab" -> sab).asJava )
  }: String

  def getStrFromCode = (code: String, sab:String) => {
    template.selectOne("getStrFromCode", Map("code" -> code, "sab" -> sab).asJava )
  }: String
}

class SabResult {
  @scala.reflect.BeanProperty
  var rsab: String = _
  @scala.reflect.BeanProperty
  var vsab: String = _
  @scala.reflect.BeanProperty
  var rcui: String = _
  @scala.reflect.BeanProperty
  var vcui: String = _
}

package edu.mayo.cts2.framework.plugin.service.nlm.umls.dao

import scala.collection.JavaConverters.asScalaBufferConverter
import scala.reflect.BeanProperty

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

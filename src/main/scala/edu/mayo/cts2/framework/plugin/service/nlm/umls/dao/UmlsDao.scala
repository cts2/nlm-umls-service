package edu.mayo.cts2.framework.plugin.service.nlm.umls.dao
import org.springframework.stereotype.Component
import org.mybatis.spring.SqlSessionTemplate
import javax.annotation.Resource
import javax.annotation.Resource
import org.springframework.stereotype.Component
import scala.reflect.BeanProperty
import scala.collection.JavaConverters._

@Component
class UmlsDao {

  @Resource
  var template: SqlSessionTemplate = _

  def getSabs():Iterable[SabResult] = {
    template.selectList[SabResult]("getSabs").asScala
  }
}

class SabResult {
  @scala.reflect.BeanProperty
  var rsab: String = _
  @scala.reflect.BeanProperty
  var vsab: String = _
}

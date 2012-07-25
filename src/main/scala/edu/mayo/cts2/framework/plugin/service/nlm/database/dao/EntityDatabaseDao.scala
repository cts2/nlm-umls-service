package edu.mayo.cts2.framework.plugin.service.nlm.database.dao;

import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Component
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntry
import org.mybatis.spring.SqlSessionTemplate
import org.apache.ibatis.session.ResultHandler
import org.apache.ibatis.session.ResultContext
import javax.annotation.Resource

@Component
class EntityDatabaseDao {

  @Resource
  var template: SqlSessionTemplate = _

  def getEntities(handler: (EntityResult) => Any) = {
    object DaoResultHandler extends ResultHandler {
      def handleResult(context: ResultContext) = {
        var o = context.getResultObject()
        handler(o.asInstanceOf[EntityResult])
      }
    }

    template.select("getEntities", DaoResultHandler)
  }

}

class EntityResult {
  var sab: String = _
  var code: String = _
  var rank: Int = _
  var definition: String = _
  var description: String = _
}

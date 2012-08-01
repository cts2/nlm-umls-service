package edu.mayo.cts2.framework.plugin.service.nlm.database.dao;

import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Component
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntry
import org.mybatis.spring.SqlSessionTemplate
import org.apache.ibatis.session.ResultHandler
import org.apache.ibatis.session.ResultContext
import javax.annotation.Resource
import org.springframework.beans.factory.InitializingBean
import javax.sql.DataSource
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.Connection
import java.sql.DatabaseMetaData
import org.apache.log4j.Logger

@Component
class EntityDatabaseDao extends InitializingBean {
  
  val log = Logger.getLogger(this.getClass())

  @Resource
  var template: SqlSessionTemplate = _

  @Resource
  var dataSource: DataSource = _

  var getEntitiesSql: String = _

  def afterPropertiesSet() {
    val connection: Connection = dataSource.getConnection()
    val metaData: DatabaseMetaData = connection.getMetaData()

    val dbType = metaData.getDatabaseProductName()
    log.info("Found DB Type: " + dbType)
    
    if (dbType.equals("MySQL")) {
      getEntitiesSql = "getEntitiesMysql"
    } else {
      getEntitiesSql = "getEntitiesOther"
    }
    
    connection.close()
  }

  def getEntities(handler: (EntityResult) => Any) = {
    object DaoResultHandler extends ResultHandler {
      def handleResult(context: ResultContext) = {
        var o = context.getResultObject()
        handler(o.asInstanceOf[EntityResult])
      }
    }

    template.select(getEntitiesSql, DaoResultHandler)
  }

}

class EntityResult {
  var sab: String = _
  var code: String = _
  var cui: String = _
  var rank: Int = _
  var definition: String = _
  var description: String = _
}

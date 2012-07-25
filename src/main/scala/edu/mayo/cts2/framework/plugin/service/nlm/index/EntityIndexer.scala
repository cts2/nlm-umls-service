package edu.mayo.cts2.framework.plugin.service.nlm.index

import javax.annotation.Resource
import edu.mayo.cts2.framework.plugin.service.nlm.database.dao.EntityDatabaseDao
import edu.mayo.cts2.framework.plugin.service.nlm.database.dao.EntityResult
import scala.collection.mutable.ListBuffer
import org.springframework.stereotype.Component
import org.elasticsearch.common.xcontent.XContentFactory._

@Component
class EntityIndexer {

  @Resource
  var indexDao: ElasticSearchIndexDao = _

  @Resource
  var databaseDao: EntityDatabaseDao = _

  def toIndexable(results: ListBuffer[EntityResult]): Indexable = {
    results.sortWith((x, y) => x.rank < y.rank)

    var firstResult = results(0)

    var map = jsonBuilder()
    try {

      map.startObject().
        field("code", firstResult.code).
        field("sab", firstResult.sab)

      map = map.startArray("definitions")
      for (next: EntityResult <- results) {
        if (next.definition != null) {
          map = map.startObject().field("value", "k").endObject()
        }
      }
      map = map.endArray()

      map = map.startArray("descriptions")
      for (next: EntityResult <- results) {
        if (next.description != null) {
          map = map.startObject().field("value", next.description).endObject()
        }
      }

      map = map.endArray().endObject()

    } catch {

      case e: Exception => e.printStackTrace()
    }

    new Indexable(firstResult.sab + ":" + firstResult.code, map)
  }

  def indexEntities() {
    val batchSize: Int = 25000

    val toIndexBuffer = ListBuffer[Indexable]()

    var batchCount: Int = 0

    var total: Int = 0;

    var currentCode: String = null

    var rowBuffer = ListBuffer[EntityResult]()

    databaseDao.getEntities((result: EntityResult) => {

      var code: String = result.code
      if (currentCode == null) {
        currentCode = code
      }
      if (code.equals(currentCode)) {
        rowBuffer += result
      } else {
        toIndexBuffer += toIndexable(rowBuffer)
        rowBuffer.clear()
        batchCount += 1
        total += 1

        currentCode = code
        rowBuffer += result
      }

      if (batchCount == batchSize) {
        println("Indexed: " + total)
        indexDao.index("entity", toIndexBuffer.toList)
        toIndexBuffer.clear()
        batchCount = 0;
      }
    })

    indexDao.index("entity", toIndexBuffer.toList)
  }

}
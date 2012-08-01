package edu.mayo.cts2.framework.plugin.service.nlm.index

import scala.collection.mutable.ListBuffer
import org.elasticsearch.action.admin.indices.stats.IndicesStats
import org.elasticsearch.action.admin.indices.stats.IndicesStatsRequestBuilder
import org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import edu.mayo.cts2.framework.plugin.service.nlm.database.dao.EntityDatabaseDao
import edu.mayo.cts2.framework.plugin.service.nlm.database.dao.EntityResult
import edu.mayo.cts2.framework.plugin.service.nlm.index.dao.ElasticSearchIndexDao
import edu.mayo.cts2.framework.plugin.service.nlm.index.dao.Indexable
import javax.annotation.Resource
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest
import org.apache.log4j.Logger

class EntityIndexer extends InitializingBean {

  val log = Logger.getLogger(this.getClass())

  @Resource
  var indexDao: ElasticSearchIndexDao = _

  @Resource
  var databaseDao: EntityDatabaseDao = _

  @scala.reflect.BeanProperty
  var lazyIndexInit: Boolean = false

  def afterPropertiesSet() {
    if (!lazyIndexInit) {
      popluateIndexIfNecessary()
    }
  }

  private def popluateIndexIfNecessary() {
    val count = getDocsCount()

    if (count == 0) {
      log.warn("Empty index found... reindexing.")
      this.indexEntities()
    }
  }

  def getDocsCount(): Long = {
    val stats: IndicesStats = new IndicesStatsRequestBuilder(
      indexDao.client.admin().indices()).setIndices(IndexConstants.UMLS_INDEX_NAME).execute().get()

    val count: Long =
      stats.index(IndexConstants.UMLS_INDEX_NAME).getTotal().getDocs().getCount()

    count
  }

  def toIndexable(results: ListBuffer[EntityResult]): Indexable = {
    results.sortWith((x, y) => x.rank < y.rank)

    var firstResult = results(0)

    var map = jsonBuilder()

    map.startObject().
      field("code", firstResult.code).
      field("sab", firstResult.sab).
      field("cui", firstResult.cui)

    map = map.startArray("definitions")
    for (next: EntityResult <- results) {
      if (next.definition != null) {
        map = map.startObject().field("value", next.definition).endObject()
      }
    }
    map = map.endArray()

    map = map.startArray("descriptions")
    results.zipWithIndex foreach { 
      case(next, 0) => { 
        map = map.startObject().
       		field("value", next.description).
        	field("preferred", true).
        	endObject()
      }
      case(next, i) => { 
       map = map.startObject().field("value", next.description).endObject()
      }
    }
  
    map = map.endArray().endObject()

    new Indexable(firstResult.sab + ":" + firstResult.code, map)
  }

  def indexEntities() {
    val batchSize: Int = 25000

    val toIndexBuffer = ListBuffer[Indexable]()

    var batchCount: Int = 0

    var total: Int = 0;

    var currentCode: String = null

    var rowBuffer = ListBuffer[EntityResult]()

    log.info("Starting Indexing...")
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
        log.info("Indexed: " + total)
        indexDao.index("entity", toIndexBuffer.toList)
        toIndexBuffer.clear()
        batchCount = 0;
      }
    })

    indexDao.index("entity", toIndexBuffer.toList)

    log.info("Finished Indexing, Total: " + total)

    val refresh = new RefreshRequest(IndexConstants.UMLS_INDEX_NAME)

    val failures = indexDao.client.admin().indices().refresh(refresh).get().failedShards()
    if (failures > 0) {
      throw new RuntimeException("Indexing failures.")
    }
  }

}
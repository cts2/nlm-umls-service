package edu.mayo.cts2.framework.plugin.service.nlm.index.dao

import scala.Array.fallbackCanBuildFrom
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.Client
import org.elasticsearch.common.xcontent.XContentBuilder
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.TextQueryBuilder
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.SearchHits
import org.springframework.stereotype.Component
import javax.annotation.Resource
import edu.mayo.cts2.framework.plugin.service.nlm.index.IndexConstants

@Component
class ElasticSearchIndexDao {

  val INDEX_NAME = IndexConstants.UMLS_INDEX_NAME

  @Resource
  var client: Client = _

  def get[T](indexType: String, key: String, formatter: (GetResponse) => T): Option[T] = {
    var request = new GetRequest(INDEX_NAME, indexType, key);
    var result = client.get(request)

    var response = result.get()

    if (response exists) {
      Some(formatter(response))
    } else {
      None
    }

  }

  def index(indexType: String, batch: List[Indexable]) = {

    var bulkRequest = new BulkRequest()

    for (val it <- batch) {
      var request = new IndexRequest(INDEX_NAME, indexType, it.id)
      request.source(it.content)
      bulkRequest.add(request)
    }

    var response = client.bulk(bulkRequest).get()

    if (response.hasFailures()) {
      throw new RuntimeException(response.buildFailureMessage())
    }
  }

  def query(indexType: String, query: QueryBuilder, start: Int, pageSize: Int) = {
    var response = client.prepareSearch(INDEX_NAME).
      setQuery(query).
      setFrom(start).
      setSize(pageSize).
      execute().
      actionGet()

    response.hits()
  }: SearchHits

  def runQuery[T](results: SearchHits, formatter: (SearchHit) => T): List[T] = {
    (for (hit <- results.getHits()) yield formatter(hit)).toList
  }
  
}

object test {
  
   def main(args: Array[String]) {
/*
    val q = () => {
      query("entity", new TextQueryBuilder("entity.code_system", "SNOMEDCT"), 0, 20)
    }

    val f = (hit: SearchHit) => {
      hit.toString()
    }

    //val list = new ElasticSearchIndexDao().runQuery(q, f)

    //for(i <- list) println(i)
*/
    
    List("hi", "there", "mr").zipWithIndex foreach { 
       
       case(el, 0) => println("FIRST: " + el)
       case(el, i) => println(i + ": " + el)
       println("->hi")
     
    }

  }
   
}

class Indexable(_id: String, _content: XContentBuilder) {
  val id: String = _id
  val content = _content
}
package edu.mayo.cts2.framework.plugin.service.nlm.index.dao
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import edu.mayo.cts2.framework.plugin.service.nlm.index.ElasticSearchIndexDao
import edu.mayo.cts2.framework.plugin.service.nlm.profile.entity.NlmEntityReadService
import org.junit.Test
import javax.annotation.Resource
import static org.junit.Assert.*
import org.elasticsearch.client.Client
import org.junit.Before
import org.elasticsearch.node.Node
import static org.elasticsearch.node.NodeBuilder.*
import org.junit.After
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress

/*
@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(
  locations =
    Array("/test-nlm-umls-context.xml"))
    */
class ElasticSearchIndexDaoTest {

  //@Resource
 // var service: ElasticSearchIndexDao = _

  def client = new TransportClient()
        .addTransportAddress(new InetSocketTransportAddress("bmidev4", 9200))


  @Before
  void setup() {
    def node = nodeBuilder().local(true).node();
    client = node.client();
    
  //  service = new ElasticSearchIndexDao(client)
  }

  @After
  void teardown() {
    client.close();
  }

  @Test
  void TestSetUp() {
    assertNotNull(client)
  }

  @Test
  void TestQuery() {
    //assertNotNull(service.query())
  }
}
package edu.mayo.cts2.framework.plugin.service.nlm.index.dao
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.junit.After
import org.junit.Before
import org.junit.Test

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
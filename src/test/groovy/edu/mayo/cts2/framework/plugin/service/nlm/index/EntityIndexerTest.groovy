package edu.mayo.cts2.framework.plugin.service.nlm.index

import javax.annotation.Resource

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import edu.mayo.cts2.framework.plugin.service.nlm.index.EntityIndexer

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration("/test-nlm-umls-context.xml")
class EntityIndexerTest {

	@Resource
	def EntityIndexer indexer

	@Test
	@Ignore
	void TestIndex(){
		indexer.indexEntities()
	}
}
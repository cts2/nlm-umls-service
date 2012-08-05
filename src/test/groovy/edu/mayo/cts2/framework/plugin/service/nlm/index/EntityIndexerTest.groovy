package edu.mayo.cts2.framework.plugin.service.nlm.index

import static org.junit.Assert.*

import javax.annotation.Resource

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import edu.mayo.cts2.framework.plugin.service.nlm.profile.valuesetdefinition.UshikValueSetDefinitionResolutionService;
import edu.mayo.cts2.framework.plugin.service.nlm.test.AbstractTestBase;

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration("/test-nlm-umls-context.xml")
class EntityIndexerTest {

	@Resource
	def UshikValueSetDefinitionResolutionService service
	
	@Resource
	def EntityIndexer indexer

	@Test
	void TestSetUP(){
		assertNotNull indexer
	}
	
	@Test
	void TestGetDocsCount(){
		assertTrue indexer.getDocsCount() > 0
	}
	
}
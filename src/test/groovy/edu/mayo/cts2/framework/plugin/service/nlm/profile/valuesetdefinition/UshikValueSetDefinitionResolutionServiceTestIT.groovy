package edu.mayo.cts2.framework.plugin.service.nlm.profile.valuesetdefinition

import javax.annotation.Resource
import javax.xml.transform.stream.StreamResult

import org.junit.Test

import edu.mayo.cts2.framework.core.xml.DelegatingMarshaller
import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.nlm.test.AbstractTestBase
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.name.ValueSetDefinitionReadId


class UshikValueSetDefinitionResolutionServiceTestIT extends AbstractTestBase {

	@Resource
	def UshikValueSetDefinitionResolutionService service

	def marshaller = new DelegatingMarshaller()


	@Test
	void TestSetUp() {
		assertNotNull service
	}
	

	@Test
	void TestValueSetsAreCached() {
		assertNotNull service.valueSets
	}
	
	@Test
	void TestGetSummariesNotNull() {
		def id = new ValueSetDefinitionReadId("Q_112", ModelUtils.nameOrUriFromName("__TEST__"))
		
		assertNotNull service.resolveDefinition(id, null, null, null , null, null, new Page())
	}
	
	@Test
	void TestGetSummariesSize() {
		def id = new ValueSetDefinitionReadId("Q_112", ModelUtils.nameOrUriFromName("__TEST__"))
		
		assertEquals 1, service.resolveDefinition(id, null, null, null , null, null, new Page()).entries.size()
	}
	
	

}

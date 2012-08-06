package edu.mayo.cts2.framework.plugin.service.nlm.profile.valuesetdefinition

import javax.annotation.Resource

import org.junit.Test

import edu.mayo.cts2.framework.core.xml.DelegatingMarshaller
import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.nlm.namespace.NamespaceResolutionService
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
	void TestGetSummariesNotNull() {
		def id = new ValueSetDefinitionReadId("Q_112", ModelUtils.nameOrUriFromName("Q_112"))
		
		assertNotNull service.resolveDefinition(id, null, null, null , null, null, new Page())
	}
	
	@Test
	void TestGetSummariesSize() {
		def id = new ValueSetDefinitionReadId("Q_112", ModelUtils.nameOrUriFromName("Q_112"))
		
		assertEquals 1, service.resolveDefinition(id, null, null, null , null, null, new Page()).entries.size()
	}
	
	

}

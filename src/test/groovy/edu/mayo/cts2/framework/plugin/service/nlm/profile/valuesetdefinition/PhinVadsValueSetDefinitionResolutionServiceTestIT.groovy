package edu.mayo.cts2.framework.plugin.service.nlm.profile.valuesetdefinition

import javax.annotation.Resource

import org.junit.Test

import edu.mayo.cts2.framework.core.xml.DelegatingMarshaller
import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.nlm.test.AbstractTestBase
import edu.mayo.cts2.framework.service.profile.valueset.ValueSetQuery
import edu.mayo.cts2.framework.service.profile.valuesetdefinition.name.ValueSetDefinitionReadId

class PhinVadsValueSetDefinitionResolutionServiceTestIT extends AbstractTestBase {

	@Resource
	def PhinVadsValueSetDefinitionResolutionService service

	def marshaller = new DelegatingMarshaller()


	@Test
	void TestSetUp() {
		assertNotNull service
	}

	@Test
	void TestGetSummariesNotNull() {
		def id = new ValueSetDefinitionReadId("1", ModelUtils.nameOrUriFromName("PHVS_AbnormalFlag_HL7_2x"))
		
		assertNotNull service.resolveDefinition(id, null, null, null , null, null, new Page());
	}

}

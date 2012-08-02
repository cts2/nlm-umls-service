package edu.mayo.cts2.framework.plugin.service.nlm.profile.valuesetdefinition

import javax.annotation.Resource
import javax.xml.transform.stream.StreamResult

import org.junit.Test

import edu.mayo.cts2.framework.core.xml.DelegatingMarshaller
import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.nlm.test.AbstractTestBase
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
	
	@Test
	void TestGetSummariesSize() {
		def id = new ValueSetDefinitionReadId("1", ModelUtils.nameOrUriFromName("PHVS_AbnormalFlag_HL7_2x"))
		
		assertTrue service.resolveDefinition(id, null, null, null , null, null, new Page()).entries.size() > 1
	}
	
	@Test
	void TestGetSummariesSizeDifferent() {
		def id = new ValueSetDefinitionReadId("1", ModelUtils.nameOrUriFromName("PHVS_AdministrativeGender_HL7_V3"))
		
		assertTrue service.resolveDefinition(id, null, null, null , null, null, new Page()).entries.size() > 1
	}
	
	@Test
	void TestGetSummariesEntriesValidXml() {
		def id = new ValueSetDefinitionReadId("1", ModelUtils.nameOrUriFromName("PHVS_AbnormalFlag_HL7_2x"))
		
		def entries = service.resolveDefinition(id, null, null, null , null, null, new Page()).entries
		
		assertTrue entries.size() > 1
		
		entries.each {
			marshaller.marshal(it, new StreamResult(new StringWriter()))
		}
	}
	
	@Test
	void TestGetSummariesHeadingNotNull() {
		def id = new ValueSetDefinitionReadId("1", ModelUtils.nameOrUriFromName("PHVS_AbnormalFlag_HL7_2x"))
		
		def heading = service.resolveDefinition(id, null, null, null , null, null, new Page()).resolvedValueSetHeader
		
		assertNotNull heading
	}
	
	@Test
	void TestGetSummariesHeadingValidXml() {
		def id = new ValueSetDefinitionReadId("1", ModelUtils.nameOrUriFromName("PHVS_AbnormalFlag_HL7_2x"))
		
		def heading = service.resolveDefinition(id, null, null, null , null, null, new Page()).resolvedValueSetHeader
		
		marshaller.marshal(heading, new StreamResult(new StringWriter()))
	}

}

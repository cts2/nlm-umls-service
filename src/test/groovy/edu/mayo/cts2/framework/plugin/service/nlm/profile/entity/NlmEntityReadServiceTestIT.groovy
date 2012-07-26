package edu.mayo.cts2.framework.plugin.service.nlm.profile.entity

import static org.junit.Assert.*
import java.io.StringWriter

import javax.annotation.Resource
import javax.xml.transform.stream.StreamResult

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import edu.mayo.cts2.framework.core.xml.DelegatingMarshaller
import edu.mayo.cts2.framework.service.profile.entitydescription.name.EntityDescriptionReadId

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration("/test-nlm-umls-context.xml")
class NlmEntityReadServiceTestIT {

	@Resource
	def NlmEntityReadService service
	
	def marshaller = new DelegatingMarshaller()

	@Test
	void TestSetUp() {
		assertNotNull service
	}

	@Test
	void TestRead() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("C12727", "NCI"),
			ModelUtils.nameOrUriFromName("NCI-Latest"))

		assertNotNull service.read(id, null)
	}
	
	@Test
	void TestReadValidXml() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("C12727", "NCI"),
			ModelUtils.nameOrUriFromName("NCI-Latest"))

		def ed = service.read(id, null)
		assertNotNull ed
		
		marshaller.marshal(ed, new StreamResult(new StringWriter()))
	}
	
	@Test
	void TestReadHasDesignations() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("C12727", "NCI"),
			ModelUtils.nameOrUriFromName("NCI-Latest"))

		def ed = service.read(id, null)
		assertNotNull ed
		
		assertTrue ed.choiceValue.designation.size() > 0
	}
	
}

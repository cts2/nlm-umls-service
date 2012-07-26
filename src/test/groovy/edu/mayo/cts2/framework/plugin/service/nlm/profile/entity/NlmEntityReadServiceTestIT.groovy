package edu.mayo.cts2.framework.plugin.service.nlm.profile.entity

import static org.junit.Assert.*

import javax.annotation.Resource
import javax.xml.transform.stream.StreamResult

import org.junit.Test

import edu.mayo.cts2.framework.core.xml.DelegatingMarshaller
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.nlm.test.AbstractTestBase
import edu.mayo.cts2.framework.service.profile.entitydescription.name.EntityDescriptionReadId


class NlmEntityReadServiceTestIT extends AbstractTestBase {

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
			ModelUtils.createScopedEntityName("001", "MCM"),
			ModelUtils.nameOrUriFromName("MCM-Latest"))

		assertNotNull service.read(id, null)
	}
	
	@Test
	void TestReadNotFound() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("__INVALID__", "MCM"),
			ModelUtils.nameOrUriFromName("MCM-Latest"))

		assertNull service.read(id, null)
	}
	
	@Test
	void TestReadValidXml() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("001", "MCM"),
			ModelUtils.nameOrUriFromName("MCM-Latest"))

		def ed = service.read(id, null)
		assertNotNull ed
		
		marshaller.marshal(ed, new StreamResult(new StringWriter()))
	}
	
	@Test
	void TestReadHasDesignations() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("001", "MCM"),
			ModelUtils.nameOrUriFromName("MCM-Latest"))

		def ed = service.read(id, null)
		assertNotNull ed
		
		assertTrue ed.choiceValue.designation.size() > 0
	}
	
}

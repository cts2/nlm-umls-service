package edu.mayo.cts2.framework.plugin.service.nlm.profile.codesystem

import static org.junit.Assert.*

import javax.annotation.Resource
import javax.xml.transform.stream.StreamResult

import org.junit.Test

import edu.mayo.cts2.framework.core.xml.DelegatingMarshaller
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.nlm.test.AbstractTestBase


class NlmCodeSystemReadServiceTestIT extends AbstractTestBase {

	@Resource
	def NlmCodeSystemReadService service

	def marshaller = new DelegatingMarshaller()


	@Test
	void TestSetUp() {
		assertNotNull service
	}

	@Test
	void TestRead() {
		def id = ModelUtils.nameOrUriFromName("SNOMEDCT")

		assertNotNull service.read(id, null)
	}

	@Test
	void TestHasContacts() {
		def id = ModelUtils.nameOrUriFromName("SNOMEDCT")

		assertEquals 2, service.read(id, null).sourceAndRoleCount
	}

	@Test
	void TestReadValidXml() {
		def id = ModelUtils.nameOrUriFromName("SNOMEDCT")

		def ed = service.read(id, null)
		assertNotNull ed

		marshaller.marshal(ed, new StreamResult(new StringWriter()))
	}
}

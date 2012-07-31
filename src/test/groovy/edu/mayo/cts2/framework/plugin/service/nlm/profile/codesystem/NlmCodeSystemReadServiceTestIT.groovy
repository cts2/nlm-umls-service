package edu.mayo.cts2.framework.plugin.service.nlm.profile.codesystem

import static org.junit.Assert.*

import javax.annotation.Resource

import org.junit.Before
import org.junit.Test

import edu.mayo.cts2.framework.core.xml.DelegatingMarshaller
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.nlm.namespace.NamespaceResolutionService
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

}

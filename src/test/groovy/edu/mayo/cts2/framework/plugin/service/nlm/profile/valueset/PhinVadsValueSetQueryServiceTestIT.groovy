package edu.mayo.cts2.framework.plugin.service.nlm.profile.valueset

import javax.annotation.Resource

import org.junit.Test

import edu.mayo.cts2.framework.core.xml.DelegatingMarshaller
import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.plugin.service.nlm.test.AbstractTestBase
import edu.mayo.cts2.framework.service.profile.valueset.ValueSetQuery

class PhinVadsValueSetQueryServiceTestIT extends AbstractTestBase {

	@Resource
	def PhinVadsValueSetQueryService service

	def marshaller = new DelegatingMarshaller()


	@Test
	void TestSetUp() {
		assertNotNull service
	}

	@Test
	void TestGetSummariesNotNull() {
		assertNotNull service.getResourceSummaries(null as ValueSetQuery, null, new Page())
	}

    @Test
    void TestGetSummariesSize() {
        assertTrue service.getResourceSummaries(null as ValueSetQuery, null, new Page()).entries.size() > 1
    }

	
}

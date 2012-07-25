package edu.mayo.cts2.framework.plugin.service.nlm.profile.entity

import static org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.ContextConfiguration

import edu.mayo.cts2.framework.service.profile.entitydescription.name.EntityDescriptionReadId
import javax.annotation.Resource

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration("/test-nlm-umls-context.xml")
class NlmEntityReadServiceTest {

	@Resource
	def NlmEntityReadService service

	@Test
	void TestSetUp() {
		assertNotNull service
	}

	@Test
	void TestRead() {
		def id = new EntityDescriptionReadId(null,null,null)

		assertNotNull service.read(id, null)
	}
}

package edu.mayo.cts2.framework.plugin.service.nlm.umls

import static org.junit.Assert.*

import javax.annotation.Resource

import org.junit.Test

import edu.mayo.cts2.framework.plugin.service.nlm.test.AbstractTestBase

class UmlsServiceTestIT extends AbstractTestBase {

	@Resource
	UmlsService service

	@Test
	void TestSetUp(){
		assertNotNull service
	}
	
	@Test
	void TestGetRSab(){
		assertEquals "COSTAR", service.getRSab("COSTAR_89-95")
	}
	
	@Test
	void TestGetVSab(){
		assertEquals "COSTAR_89-95", service.getVSab("COSTAR")
	}
}


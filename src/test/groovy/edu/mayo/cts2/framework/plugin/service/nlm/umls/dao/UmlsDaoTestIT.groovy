package edu.mayo.cts2.framework.plugin.service.nlm.umls.dao

import static org.junit.Assert.*

import javax.annotation.Resource

import org.junit.Test

import edu.mayo.cts2.framework.plugin.service.nlm.test.AbstractTestBase

class UmlsDaoTestIT extends AbstractTestBase {

	@Resource
	UmlsDao dao

	@Test
	void TestSetUp(){
		assertNotNull dao
	}
	
	@Test
	void TestGetSabsNotNull(){
		assertNotNull dao.getSabs()
	}
}


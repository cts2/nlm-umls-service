package edu.mayo.cts2.framework.plugin.service.nlm.index.dao
import javax.annotation.Resource

import org.junit.Test

import edu.mayo.cts2.framework.plugin.service.nlm.test.AbstractTestBase

class ElasticSearchIndexDaoTestIT extends AbstractTestBase {

	@Resource
	ElasticSearchIndexDao dao

	@Test
	void TestSetUp() {
		assertNotNull dao
	}
}
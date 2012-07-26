package edu.mayo.cts2.framework.plugin.service.nlm.profile.entity

import static org.junit.Assert.*

import javax.annotation.Resource
import javax.sql.DataSource

import org.dbunit.database.DatabaseConnection
import org.dbunit.database.IDatabaseConnection
import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.xml.FlatXmlDataSet
import org.elasticsearch.client.Client
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.nlm.test.AbstractTestBase
import edu.mayo.cts2.framework.service.profile.entitydescription.name.EntityDescriptionReadId


public class NlmEntityReadServiceTest {
	
	@Test
	void TestGetKey() {
		def svc = new NlmEntityReadService()
		
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("C12727", "NCI"),
			ModelUtils.nameOrUriFromName("NCI-Latest"))
		
		def key = svc.getKey(id)
		
		assertEquals "NCI:C12727", key
	}
	
}

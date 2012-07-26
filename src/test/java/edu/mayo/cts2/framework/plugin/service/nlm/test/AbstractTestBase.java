package edu.mayo.cts2.framework.plugin.service.nlm.test;

import static org.elasticsearch.common.settings.ImmutableSettings.settingsBuilder;

import java.io.File;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.mayo.cts2.framework.plugin.service.nlm.index.EntityIndexer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-nlm-umls-context.xml")
public abstract class AbstractTestBase extends DataSourceBasedDBTestCase {

	File tempFolder;

	@Autowired
	DelegatingClientFactory delegatingClientFactory;

	@Autowired
	EntityIndexer indexer;

	@Autowired
	DataSource dataSource;
	
	@Override
	@Before
	public void setUp() throws Exception {
		String ddl = IOUtils.toString(new ClassPathResource("database/db.ddl").getInputStream());
		
		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		for(String command : ddl.split(";")){
			String sql = command.trim();
			if(StringUtils.isNotBlank(sql)){
				template.execute(sql);
			}
		}
		
		super.setUp();
		this.prepare();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		
		new JdbcTemplate(dataSource).execute("DROP SCHEMA PUBLIC CASCADE");
		if (tempFolder.exists()) {
			FileUtils.deleteDirectory(tempFolder);
		}
	}

	private void prepare() throws Exception {
		
		// path.data location
		tempFolder = new File(FileUtils.getTempDirectory(), UUID.randomUUID()
				.toString());
		String tempFolderName = tempFolder.getCanonicalPath();

		if (tempFolder.exists()) {
			FileUtils.deleteDirectory(tempFolder);
		}
		if (!tempFolder.mkdir()) {
			fail("Could not create a temporary folder [" + tempFolderName + "]");
		}

		// Make sure that the index and metadata are not stored on the disk
		// path.data folder is created but we make sure it is removed after test
		// finishes
		Settings settings = settingsBuilder().put("index.store.type", "memory")
				.put("gateway.type", "none").put("path.data", tempFolderName)
				.build();

		Node node = NodeBuilder.nodeBuilder().settings(settings).local(true)
				.node();

		delegatingClientFactory.setNode(node);

		indexer.indexEntities();
	}

	@Override
	protected DataSource getDataSource() {
		return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new ClassPathResource(
				"database/db-content.xml").getInputStream());
	}
}
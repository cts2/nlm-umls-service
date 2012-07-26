package edu.mayo.cts2.framework.plugin.service.nlm.test;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.elasticsearch.common.settings.ImmutableSettings.settingsBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-nlm-umls-context.xml")
public abstract class AbstractTestBase {

	File tempFolder;
	
	@Autowired
	DelegatingClientFactory delegatingClientFactory;

	@Before
	void prepare() throws IOException {

		// path.data location
		tempFolder = new File(FileUtils.getTempDirectory(), UUID.randomUUID().toString());
		String tempFolderName = tempFolder.getCanonicalPath();

		if (tempFolder.exists()) {
			FileUtils.deleteDirectory(tempFolder);
		}
		if (!tempFolder.mkdir()) {
			fail("Could not create a temporary folder ["+tempFolderName+"]");
		}

		// Make sure that the index and metadata are not stored on the disk
		// path.data folder is created but we make sure it is removed after test finishes
		Settings settings = settingsBuilder()
				.put("index.store.type", "memory")
				.put("gateway.type", "none")
				.put("path.data", tempFolderName)
				.build();

		Node node = NodeBuilder.nodeBuilder()
				.settings(settings)
				.local(true)
				.node();

		delegatingClientFactory.setNode(node)
	}

	@After
	void cleanup() throws IOException {
		if (tempFolder.exists()) {
			FileUtils.deleteDirectory(tempFolder);
		}
	}
}
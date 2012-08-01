package edu.mayo.cts2.framework.plugin.service.nlm.profile.entity

import static org.junit.Assert.*

import javax.annotation.Resource
import javax.xml.transform.stream.StreamResult

import org.junit.Before
import org.junit.Test

import edu.mayo.cts2.framework.core.xml.DelegatingMarshaller
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.nlm.namespace.NamespaceResolutionService
import edu.mayo.cts2.framework.plugin.service.nlm.test.AbstractTestBase
import edu.mayo.cts2.framework.plugin.service.nlm.umls.UmlsConstants
import edu.mayo.cts2.framework.service.profile.entitydescription.name.EntityDescriptionReadId


class NlmEntityReadServiceTestIT extends AbstractTestBase {

	@Resource
	def NlmEntityReadService service

	def marshaller = new DelegatingMarshaller()

	@Before
	void setUpNs() {
		service.namespaceResolutionService = {
			prefixToUri: {ns -> ns}
		} as NamespaceResolutionService
	}
	
	@Test
	void TestSetUp() {
		assertNotNull service
	}

	@Test
	void TestRead() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("001", "MCM"),
			ModelUtils.nameOrUriFromName("MCM92"))

		assertNotNull service.read(id, null)
	}
	
	@Test
	void TestReadNotFound() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("__INVALID__", "MCM"),
			ModelUtils.nameOrUriFromName("MCM92"))

		assertNull service.read(id, null)
	}
	
	@Test
	void TestReadNotFoundBadVSab() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("001", "MCM"),
			ModelUtils.nameOrUriFromName("MCM92__INVALID"))

		assertNull service.read(id, null)
	}
	
	@Test
	void TestReadValidXml() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("001", "MCM"),
			ModelUtils.nameOrUriFromName("MCM92"))

		def ed = service.read(id, null)
		assertNotNull ed
		
		marshaller.marshal(ed, new StreamResult(new StringWriter()))
	}
	
	@Test
	void TestReadHasDesignations() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("001", "MCM"),
			ModelUtils.nameOrUriFromName("MCM92"))

		def ed = service.read(id, null)
		assertNotNull ed
		
		assertTrue ed.choiceValue.designation.size() > 0
	}
	
	@Test
	void TestReadHasCuiAlternateId() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("001", "MCM"),
			ModelUtils.nameOrUriFromName("MCM92"))

		def ed = service.read(id, null)
		assertNotNull ed
		
		assertEquals 1, ed.choiceValue.alternateEntityID.size()
		
		assertEquals "CUI", 
			ed.choiceValue.getAlternateEntityID(0).namespace
			
		assertEquals "C0150091",
			ed.choiceValue.getAlternateEntityID(0).name
	}
	
	@Test
	void TestReadHasDefinition() {
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("001", "MCM"),
			ModelUtils.nameOrUriFromName("MCM92"))

		def ed = service.read(id, null)
		assertNotNull ed
		
		assertEquals 1, ed.choiceValue.definition.size()
		
		def definition = 
		"""Investigation of therapeutic alternatives in which individuals of one time period and under one treatment are compared with individuals at a subsequent time, treated in a different fashion. If the disorder is not fatal and the "before" treatment is not curative, the same individuals may be studied in the before and after periods, strengthening the design through increased group comparability for the two periods."""
		
		assertEquals definition, ed.choiceValue.getDefinition(0).value.content
	}
	

}

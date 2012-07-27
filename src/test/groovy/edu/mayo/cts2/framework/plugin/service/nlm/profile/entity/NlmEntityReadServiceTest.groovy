package edu.mayo.cts2.framework.plugin.service.nlm.profile.entity

import static org.junit.Assert.*

import org.easymock.EasyMock
import org.junit.Test

import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.nlm.umls.UmlsService
import edu.mayo.cts2.framework.service.profile.entitydescription.name.EntityDescriptionReadId


public class NlmEntityReadServiceTest {
	
	@Test
	void TestGetKey() {
		def svc = new NlmEntityReadService()
		def umlsService = EasyMock.createMock(UmlsService)
		EasyMock.expect(umlsService.getRSab("NCI-2.0")).andReturn("NCI").once()
		EasyMock.replay(umlsService)
		
		svc.umlsService = umlsService
		
		def id = new EntityDescriptionReadId(
			ModelUtils.createScopedEntityName("C12727", "NCI"),
			ModelUtils.nameOrUriFromName("NCI-2.0"))
		
		def key = svc.getKey(id)
		
		assertEquals "NCI:C12727", key
	}
	
}

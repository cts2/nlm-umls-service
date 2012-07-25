package edu.mayo.cts2.framework.plugin.service.nlm.provider

import static org.junit.Assert.*

import javax.annotation.Resource

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionReadService

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration("/test-nlm-umls-context.xml")
class NlmServiceProviderTest {

  @Resource
  def NlmServiceProvider provider

  @Test
  void TestSetUp() {
    assertNotNull provider
  }

  @Test
  void TestGetEntityRead() {
    assertNotNull provider.getService(EntityDescriptionReadService.class)
  }

}

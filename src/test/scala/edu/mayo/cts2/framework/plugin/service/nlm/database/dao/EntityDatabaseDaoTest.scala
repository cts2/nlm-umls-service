package edu.mayo.cts2.framework.plugin.service.nlm.database.dao

import org.junit.Assert._
import javax.annotation.Resource
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import scala.Function1
import org.scalatest.junit.AssertionsForJUnit
import org.junit.Ignore

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(
  locations =
    Array("/test-nlm-umls-context.xml"))
class EntityDatabaseDaoTest extends AssertionsForJUnit {

  @Resource
  var dao: EntityDatabaseDao = _

  @Test
  def TestSetUp() {
    assertNotNull(dao)
  }

  @Test
  @Ignore
  def TestGetEntities() {
    dao.getEntities((result: EntityResult) => {
      println(result)
    })
  }

}

package edu.mayo.cts2.framework.plugin.service.nlm.ushik.dao;

import gov.ahrq.ushik.webservices.USHIKService
import net.liftweb.json.DefaultFormats
import gov.ahrq.ushik.webservices.USHIK
import org.apache.commons.io.FileUtils
import java.io.File
import edu.mayo.cts2.framework.plugin.service.nlm.umls.dao.UmlsDao
import javax.annotation.Resource
import scala.collection.JavaConversions._
import scala.collection.mutable.MutableList
import org.apache.commons.io.IOUtils
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import gov.ahrq.ushik.webservices.QualityDataElement

@Component
class UshikDao {

  val ushikService: USHIK = new USHIKService().getUSHIKPort()

  @Resource
  var umlsDao: UmlsDao = _

  val measuresPath = "cache/ushik/valueSets.json"

  def sabs = Map(
    "CPT" -> "CPT",
    "SNOMED-CT" -> "SNOMEDCT",
    "ICD-10-CM" -> "ICD10CM",
    "LOINC" -> "LOINC",
    "CVX" -> "CVX",
    "RxNorm" -> "RXNORM",
    "HL7" -> "HL7V3.0",
    "ICD-9-CM" -> "ICD9CM",
    "HCPCS" -> "HCPCS")

  val valueSetEntries: Map[String, Seq[Map[String, String]]] = com.codahale.jerkson.Json.parse[Map[String, Seq[Map[String, String]]]](
    IOUtils.toString(
      new ClassPathResource(measuresPath).getInputStream()))
      
  var valueSets: Map[String, QualityDataElement] = _//cacheValueSets()

  def cacheValueSets() = {
    val measures: Seq[Int] = getAllMeasureIds()
 
    val valueSets = measures.foldLeft(Map[String, QualityDataElement]())(
      (map, measureId: Int) => {

        map ++ ushikService.getMeasure(getSession(), measureId).getQualityDataElements().foldLeft(Map[String, QualityDataElement]())(
          (innerMap, qde) => {
            
        	  println(qde.getOid())
            innerMap ++ Map(qde.getQDSId -> qde)
          })
      })
      
      valueSets
  }

  def getAllMeasureIds() = {
    val measures: Seq[Int] = ushikService.getAllMeaningfulUseMeasures(getSession()).
      getListItems().
      foldLeft(Seq[Int]())(
        (list, measure) => {
          list ++ List(measure.getItemKey())
        })

    measures
  }

  private def cacheValueSetEntries() = {
    def lookupByCode = umlsDao.getStrFromCode
    def lookupByScui = umlsDao.getStrFromScui

    def codeLookupFuctnions = Map(
      "CPT" -> lookupByCode,
      "SNOMED-CT" -> lookupByCode,
      "ICD-10-CM" -> lookupByCode,
      "LOINC" -> lookupByCode,
      "CVX" -> lookupByCode,
      "RxNorm" -> lookupByScui,
      "HL7" -> lookupByScui,
      "ICD-9-CM" -> lookupByCode,
      "HCPCS" -> lookupByCode)

    val measures: Seq[Int] = getAllMeasureIds()

    // val measures = List(145236000)
    val cache = measures.foldLeft(Map[String, Seq[Map[String, String]]]())(
      (map, measureId: Int) => {

        var tempMap = Map()

        map ++ ushikService.getMeasure(getSession(), measureId).getQualityDataElements().foldLeft(Map[String, Seq[Map[String, String]]]())(
          (innerMap, qde) => {
            var codes = MutableList[Map[String, String]]()

            qde.getCodeList().getCodes().foreach((code) =>
              {
                val fn = codeLookupFuctnions.get(code.getTaxonomy())
                val sab = sabs.get(code.getTaxonomy())

                if (fn.isDefined && sab.isDefined) {
                  codes += Map(
                    "code" -> code.getValue(),
                    "codesystem" -> code.getTaxonomy(),
                    "designation" -> fn.get(code.getValue(), sab.get))
                } else {
                  println("Problem for: " + code.getTaxonomy())
                }
              })

            println("Finished: " + qde.getQDSId())
            innerMap ++ Map(qde.getQDSId() -> codes)
          })
      })

    cache.foreach((x) => println("Key: " + x._1 + " Size: " + x._2.size()))
    println("Writing Cache")

    implicit val formats = DefaultFormats

    val jsonCache = net.liftweb.json.Serialization.write(cache)

    FileUtils.writeStringToFile(new File("json.out"), jsonCache)

  }

  private def getSession(): String = {
    ushikService.authenticateSession("", "")
  }
}

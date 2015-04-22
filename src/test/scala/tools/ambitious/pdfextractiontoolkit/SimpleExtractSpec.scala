package tools.ambitious.pdfextractiontoolkit

import org.apache.pdfbox.pdmodel.PDDocument
import org.nerdpower.tabula.extractors.BasicExtractionAlgorithm
import org.nerdpower.tabula.{ObjectExtractor, Page}
import org.scalatest._

class SimpleExtractSpec extends FlatSpec {

  val SIMPLE_TEST_1_TABLE = getClass.getResource("/simplePDFs/SimpleTest1Table.pdf")

  "The SimpleTest1Table.pdf table" should "have the value \"10\" in its first cell" in {
    val objectExtractor = new ObjectExtractor(PDDocument.load(SIMPLE_TEST_1_TABLE))
    val wholePage: Page = objectExtractor.extract(1)
    val tablePageArea = wholePage.getArea(81, 108, 305, 312)

    val extractionAlgorithm = new BasicExtractionAlgorithm
    val table = extractionAlgorithm.extract(tablePageArea).get(0)

    assert(table.getCell(0, 0).getText.trim == "10")
  }
}

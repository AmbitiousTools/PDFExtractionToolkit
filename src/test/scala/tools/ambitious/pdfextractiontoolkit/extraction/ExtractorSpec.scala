package tools.ambitious.pdfextractiontoolkit.extraction

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.model._
import tools.ambitious.pdfextractiontoolkit.model.constraints.{FirstOccurrenceOfStringExtractionCondition, PageNumberExtractionCondition}
import tools.ambitious.pdfextractiontoolkit.model.geometry.{PositivePoint, Rectangle, Size}
import tools.ambitious.pdfextractiontoolkit.util.CSVUtil

class ExtractorSpec extends FreeSpec {
  s"An ${Extractor.getClass.getSimpleName} with a document and valid stencil" - {
    val document: Document = Document.fromPDFPath(simpleTest1TableURL)

    val window: Rectangle = Rectangle.fromCornerCoords(108, 81, 312, 305)
    val condition = PageNumberExtractionCondition.atPage(1)
    val tableExtractor = DatumExtractor.using(window, condition)

    val extractor: Extractor = Extractor.fromDocumentAndExtractors(document, tableExtractor)

    "should be able to extract the table and have it match the values from it's corresponding CSV file" in {
      val tables: List[Table] = extractor.run

      document.close()

      val table: Table = tables.head
      val tableFromCSV: Table = CSVUtil.tableFromURL(simpleTest1TableCSVURL)

      assert(table == tableFromCSV)
    }
  }

  s"An ${Extractor.getClass.getSimpleName} with one document and a stencil using " +
    s"the ${FirstOccurrenceOfStringExtractionCondition.getClass.getSimpleName} constraint" - {
    val document: Document = Document.fromPDFPath(simpleTest2Tables2TitleURL)

    val constraintWindow: Rectangle = Rectangle.fromCornerAndSize(PositivePoint.at(185.38, 165.62), Size.widthAndHeight(112.64, 16.16))
    val extractionCondition = FirstOccurrenceOfStringExtractionCondition.usingWindowForString(constraintWindow, "An example Title")

    val window: Rectangle = Rectangle.fromCornerAndSize(PositivePoint.at(168.48, 273.95), Size.widthAndHeight(213.54, 303.5))
    
    val tableExtractor: DatumExtractor = DatumExtractor.using(window, extractionCondition)

    val extractor: Extractor = Extractor.fromDocumentAndExtractors(document, tableExtractor)

    "should be able to extract the table and have it match the values from it's corresponding CSV file" in {

      val tables: List[Table] = extractor.run

      val table: Table = tables.head
      val tableFromCSV: Table = CSVUtil.tableFromURL(simpleTest2Tables2TitlePage2CSVURL)

      assert(table == tableFromCSV)
    }
  }
}

package tools.ambitious.pdfextractiontoolkit.library.extraction

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.library.extraction.extractionconstraints.{FirstOccurrenceOfStringExtractionConstraint, PageNumberExtractionConstraint}
import tools.ambitious.pdfextractiontoolkit.library.extraction.tableextractors.RegionBasedTableExtractor
import tools.ambitious.pdfextractiontoolkit.library.model.geometry.{PositivePoint, Rectangle, Size}
import tools.ambitious.pdfextractiontoolkit.library.model.{Document, Table}
import tools.ambitious.pdfextractiontoolkit.library.util.CSVUtil

import scala.concurrent.Await
import scala.concurrent.duration._

class ExtractorSpec extends FreeSpec {
  s"An ${Extractor.getClass.getSimpleName} with a document and a ${PageNumberExtractionConstraint.getClass.getSimpleName}" - {
    val document: Document = Document.fromPDFPath(simpleTest1TableURL)

    val region: Rectangle = Rectangle.fromCornerCoords(108, 81, 312, 305)
    val tableExtractor = RegionBasedTableExtractor.forRegion(region)
    val extractionConstraint = PageNumberExtractionConstraint.withPageNumberAndTableExtractor(1, tableExtractor)

    val extractor: Extractor = Extractor.fromDocumentAndConstraints(document, extractionConstraint)

    "should be able to extract the table and have it match the values from it's corresponding CSV file" in {
      val extractionResult: ExtractionResult = Await.result(extractor.extractTables, 60.seconds)

      document.close()

      val table: Table = extractionResult(document)(extractionConstraint)
      val tableFromCSV: Table = CSVUtil.tableFromURL(simpleTest1TableCSVURL)

      assert(table == tableFromCSV)
    }
  }

  s"An ${Extractor.getClass.getSimpleName} with one document and a ${FirstOccurrenceOfStringExtractionConstraint.getClass.getSimpleName}" - {
    val document: Document = Document.fromPDFPath(simpleTest2Tables2TitleURL)

    val region: Rectangle = Rectangle.fromCornerAndSize(PositivePoint.at(168.48, 273.95), Size.fromWidthAndHeight(213.54, 303.5))
    val tableExtractor = RegionBasedTableExtractor.forRegion(region)

    val textRegion: Rectangle = Rectangle.fromCornerAndSize(PositivePoint.at(185.38, 165.62), Size.fromWidthAndHeight(112.64, 16.16))
    val extractionConstraint = FirstOccurrenceOfStringExtractionConstraint.withTextAndTableExtractor("An example Title", textRegion, tableExtractor)

    val extractor: Extractor = Extractor.fromDocumentAndConstraints(document, extractionConstraint)

    "should be able to extract the table and have it match the values from it's corresponding CSV file" in {
      val extractionResult: ExtractionResult = Await.result(extractor.extractTables, 60.seconds)

      document.close()

      val table: Table = extractionResult(document)(extractionConstraint)
      val tableFromCSV: Table = CSVUtil.tableFromURL(simpleTest2Tables2TitlePage2CSVURL)

      assert(table == tableFromCSV)
    }
  }
}

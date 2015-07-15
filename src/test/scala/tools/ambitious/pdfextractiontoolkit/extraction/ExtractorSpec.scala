package tools.ambitious.pdfextractiontoolkit.extraction

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.extraction.tableextractors.{TableExtractor, FirstOccurrenceOfStringTableExtractor, PageNumberTableExtractor}
import tools.ambitious.pdfextractiontoolkit.model.geometry.{PositivePoint, Rectangle, Size}
import tools.ambitious.pdfextractiontoolkit.model.{Document, Table}
import tools.ambitious.pdfextractiontoolkit.util.CSVUtil
import scala.concurrent.Await
import scala.concurrent.duration._

class ExtractorSpec extends FreeSpec {
  s"An ${Extractor.getClass.getSimpleName} with a document and a ${PageNumberTableExtractor.getClass.getSimpleName}" - {
    val document: Document = Document.fromPDFPath(simpleTest1TableURL)

    val window: Rectangle = Rectangle.fromCornerCoords(108, 81, 312, 305)
    val tableExtractor = PageNumberTableExtractor.withPageNumberAndRegion(1, window)

    val extractor: Extractor = Extractor.fromDocumentAndExtractors(document, tableExtractor)

    "should be able to extract the table and have it match the values from it's corresponding CSV file" in {
      val tables: Map[Document, Map[TableExtractor, Table]] = Await.result(extractor.extractTables, 60.seconds)

      document.close()

      val table: Table = tables(document)(tableExtractor)
      val tableFromCSV: Table = CSVUtil.tableFromURL(simpleTest1TableCSVURL)

      assert(table == tableFromCSV)
    }
  }

  s"An ${Extractor.getClass.getSimpleName} with one document and a ${FirstOccurrenceOfStringTableExtractor.getClass.getSimpleName}" - {
    val document: Document = Document.fromPDFPath(simpleTest2Tables2TitleURL)

    val region: Rectangle = Rectangle.fromCornerAndSize(PositivePoint.at(168.48, 273.95), Size.fromWidthAndHeight(213.54, 303.5))
    val textRegion: Rectangle = Rectangle.fromCornerAndSize(PositivePoint.at(185.38, 165.62), Size.fromWidthAndHeight(112.64, 16.16))
    val tableExtractor = FirstOccurrenceOfStringTableExtractor.withTextAndRegion("An example Title", textRegion, region)

    val extractor: Extractor = Extractor.fromDocumentAndExtractors(document, tableExtractor)

    "should be able to extract the table and have it match the values from it's corresponding CSV file" in {
      val tables: Map[Document, Map[TableExtractor, Table]] = Await.result(extractor.extractTables, 60.seconds)

      document.close()

      val table: Table = tables(document)(tableExtractor)
      val tableFromCSV: Table = CSVUtil.tableFromURL(simpleTest2Tables2TitlePage2CSVURL)

      assert(table == tableFromCSV)
    }
  }
}

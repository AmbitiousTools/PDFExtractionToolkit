package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.extraction._
import tools.ambitious.pdfextractiontoolkit.model.geometry.{PositivePoint, Rectangle, Size}
import tools.ambitious.pdfextractiontoolkit.model.{Document, Table}
import tools.ambitious.pdfextractiontoolkit.util.CSVUtil

import scala.concurrent.Await
import scala.concurrent.duration._

class FirstOccurrenceOfStringTableExtractorSpec extends FreeSpec {
  val simpleTest2Tables2TitleURL = getClass.getResource("/simplePDFs/SimpleTest2Tables1Title.pdf")

  s"A ${FirstOccurrenceOfStringExtractionConstraint.getClass.getSimpleName} with string 'An example Title' and " +
    s"appropriate Window for SimpleTest2Tables1Title.pdf" - {
    val region = Rectangle.fromCornerAndSize(PositivePoint.at(168.48, 273.95), Size.fromWidthAndHeight(213.54, 303.5))

    val regionBasedTranslator = RegionBasedPageToTableTranslator.forRegion(region)

    val textRegion = Rectangle.fromCornerAndSize(PositivePoint.at(185.38, 165.62), Size.fromWidthAndHeight(112.64, 16.16))
    val tableExtractor = FirstOccurrenceOfStringExtractionConstraint.withTextAndTranslator("An example Title", textRegion, regionBasedTranslator)

    "when put through a walker with test document SimpleTest2Tables1Title.pdf" - {
      val document: Document = Document.fromPDFPath(simpleTest2Tables2TitleURL)
      val walker: DocumentWalker = DocumentWalker.toWalkWithTableExtractor(document, tableExtractor)
      val tables: Map[ExtractionConstraint, Table] = Await.result(walker.getTables, 60.seconds)

      "should return the table at page 2" in {
        val table: Option[Table] = tables.get(tableExtractor)
        val tableFromCSV: Table = CSVUtil.tableFromURL(simpleTest2Tables2TitlePage2CSVURL)

        assert(table.get == tableFromCSV)
      }
    }
  }
}

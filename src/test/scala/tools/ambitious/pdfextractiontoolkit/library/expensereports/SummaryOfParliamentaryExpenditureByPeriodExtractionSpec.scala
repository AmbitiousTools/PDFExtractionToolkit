package tools.ambitious.pdfextractiontoolkit.library.expensereports

import org.scalatest.{BeforeAndAfterEach, FlatSpec}
import tools.ambitious.pdfextractiontoolkit.library.extraction.extractionconstraints.FirstOccurrenceOfStringExtractionConstraint
import tools.ambitious.pdfextractiontoolkit.library.extraction.tableextractors.RegionBasedTableExtractor
import tools.ambitious.pdfextractiontoolkit.library.extraction.{ExtractionResult, Extractor}
import tools.ambitious.pdfextractiontoolkit.library.model.geometry.{PositivePoint, Rectangle, Size}
import tools.ambitious.pdfextractiontoolkit.library.model.{Document, Table}

import scala.concurrent.Await
import scala.concurrent.duration._

class SummaryOfParliamentaryExpenditureByPeriodExtractionSpec extends FlatSpec with BeforeAndAfterEach {

  val abbottTonyReport = getClass.getResource("/expenseReports/P34_ABBOTT_Tony.pdf")
  val leighAndrewReport = getClass.getResource("/expenseReports/P34_LEIGH_Andrew.pdf")

  val textToFind = "Summary of Parliamentary Expenditure by Period"

  val tableRegion = Rectangle.fromCornerAndSize(PositivePoint.at(34, 138), Size.fromWidthAndHeight(540, 608))
  val tableExtractor = RegionBasedTableExtractor.forRegion(tableRegion)

  val textRegion = Rectangle.fromCornerAndSize(PositivePoint.at(165, 90), Size.fromWidthAndHeight(280, 25))

  val extractionConstraint = FirstOccurrenceOfStringExtractionConstraint.withTextAndTableExtractor(textToFind, textRegion, tableExtractor)

  val abbottTonyDocument = Document.fromPDFPath(abbottTonyReport)
  val leighAndrewDocument = Document.fromPDFPath(leighAndrewReport)

  val extractor = Extractor.fromDocumentsAndConstraints(List(abbottTonyDocument, leighAndrewDocument), extractionConstraint)
  val extractionResult: ExtractionResult = Await.result(extractor.extractTables, 60.seconds)

  "the Extractor" should "extract a single table from the Tony Abbott report" in {
    assert(extractionResult.getResults(abbottTonyDocument)(extractionConstraint).isDefined)
  }

  it should "extract a single table from the Andrew Leigh report" in {
    assert(extractionResult.getResults(leighAndrewDocument)(extractionConstraint).isDefined)
  }

  val expectedRowsTonyAbbott = 24
  it should s"extract a table from the Tony Abbott Report with $expectedRowsTonyAbbott rows" in {
    val table: Table = extractionResult(abbottTonyDocument)(extractionConstraint)

    assert(table.numberOfRows == expectedRowsTonyAbbott)
  }

  val expectedRowsAndrewLeigh = 26
  it should s"extract a table from the Andrew Leigh Report with $expectedRowsAndrewLeigh rows" in {
    val table: Table = extractionResult(leighAndrewDocument)(extractionConstraint)

    assert(table.numberOfRows == expectedRowsAndrewLeigh)
  }

  it should "extract the expected values from the Tony Abbott report" in {
    val table: Table = extractionResult(abbottTonyDocument)(extractionConstraint)

    assertValueAtCell(expectedText = "Expenses From", table = table, rowNumber = 1, columnNumber = 2)
    assertValueAtCell(expectedText = "$628,736.33", table = table, rowNumber = 23, columnNumber = 2)
  }

  it should "extract the expected values from the Andrew Leigh report" in {
    val table: Table = extractionResult(leighAndrewDocument)(extractionConstraint)

    assertValueAtCell(expectedText = "Expenses From", table = table, rowNumber = 1, columnNumber = 2)
    assertValueAtCell(expectedText = "$109,760.32", table = table, rowNumber = 26, columnNumber = 2)
  }

  private def assertValueAtCell(expectedText: String, table: Table, rowNumber: Int, columnNumber: Int) = {
    assert(table.getCell(rowNumber, columnNumber).text == expectedText)
  }

  override def afterEach(): Unit = {
    abbottTonyDocument.close()
    leighAndrewDocument.close()
  }
}

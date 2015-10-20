package tools.ambitious.pdfextractiontoolkit.library.expensereports

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.library.extraction.extractionconstraints.FirstOccurrenceOfStringExtractionConstraint
import tools.ambitious.pdfextractiontoolkit.library.extraction.tableextractors.RegionBasedTableExtractor
import tools.ambitious.pdfextractiontoolkit.library.extraction.{ExtractionResult, Extractor}
import tools.ambitious.pdfextractiontoolkit.library.model.geometry.{PositivePoint, Rectangle, Size}
import tools.ambitious.pdfextractiontoolkit.library.model.{Document, Table}

import scala.concurrent.Await
import scala.concurrent.duration._

class SummaryOfParliamentaryExpenditureByPeriodExtractionSpec extends FreeSpec {

  "the tony abbott and andrew leigh reports" - {
    val abbottTonyReport = getClass.getResource("/expenseReports/P34_ABBOTT_Tony.pdf")
    val abbottTonyDocument = Document.fromPDFPath(abbottTonyReport)

    val leighAndrewReport = getClass.getResource("/expenseReports/P34_LEIGH_Andrew.pdf")
    val leighAndrewDocument = Document.fromPDFPath(leighAndrewReport)

    val textToFind = "Summary of Parliamentary Expenditure by Period"
    s"when provided to an extractor with the following regions looking for '$textToFind'" - {
      val tableRegion = Rectangle.fromCornerAndSize(PositivePoint.at(34, 138), Size.fromWidthAndHeight(540, 608))
      val tableExtractor = RegionBasedTableExtractor.forRegion(tableRegion)

      val textRegion = Rectangle.fromCornerAndSize(PositivePoint.at(165, 90), Size.fromWidthAndHeight(280, 25))

      val extractionConstraint = FirstOccurrenceOfStringExtractionConstraint.withTextAndTableExtractor(textToFind, textRegion, tableExtractor)

      val extractor = Extractor.fromDocumentsAndConstraints(List(abbottTonyDocument, leighAndrewDocument), extractionConstraint)

      val extractionResult: ExtractionResult = Await.result(extractor.extractTables, 60.seconds)

      "the tony abbott document" - {
        "should return a single table" in {
          assert(extractionResult.getResults(abbottTonyDocument)(extractionConstraint).isDefined)
        }

        val table: Table = extractionResult(abbottTonyDocument)(extractionConstraint)

        val expectedRows = 24
        s"should have $expectedRows rows in the returned table" in {
          assert(table.numberOfRows == expectedRows)
        }

        shouldHaveTextInTableAtRowAndColumn(expectedText = "Expenses From", table = table, rowNumber = 1, columnNumber = 2)
        shouldHaveTextInTableAtRowAndColumn(expectedText = "$628,736.33", table = table, rowNumber = 23, columnNumber = 2)
      }

      "the andrew leigh document" - {
        "should return a single table" in {
          assert(extractionResult.getResults(leighAndrewDocument)(extractionConstraint).isDefined)
        }

        val table: Table = extractionResult(leighAndrewDocument)(extractionConstraint)

        val expectedRows = 26
        s"should have $expectedRows rows in the returned table" in {
          assert(table.numberOfRows == expectedRows)
        }

        shouldHaveTextInTableAtRowAndColumn(expectedText = "Expenses From", table = table, rowNumber = 1, columnNumber = 2)
        shouldHaveTextInTableAtRowAndColumn(expectedText = "$109,760.32", table = table, rowNumber = 26, columnNumber = 2)
      }
    }
  }

  private def shouldHaveTextInTableAtRowAndColumn(expectedText: String, table: Table, rowNumber: Int, columnNumber: Int) = {
    s"should return a table with the value '$expectedText' at row $rowNumber and column $columnNumber" in {
      assert(table.getCell(rowNumber, columnNumber).text == expectedText)
    }
  }


}

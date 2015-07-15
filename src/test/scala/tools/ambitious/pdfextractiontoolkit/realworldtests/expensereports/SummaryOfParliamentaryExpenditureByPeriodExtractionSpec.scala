package tools.ambitious.pdfextractiontoolkit.realworldtests.expensereports

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.extraction.Extractor
import tools.ambitious.pdfextractiontoolkit.extraction.tableextractors.{TableExtractor, FirstOccurrenceOfStringTableExtractor}
import tools.ambitious.pdfextractiontoolkit.model.{Table, Document}
import tools.ambitious.pdfextractiontoolkit.model.geometry.{Size, PositivePoint, Rectangle}
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
      val textRegion = Rectangle.fromCornerAndSize(PositivePoint.at(165, 90), Size.fromWidthAndHeight(280, 25))
      val tableRegion = Rectangle.fromCornerAndSize(PositivePoint.at(34, 138), Size.fromWidthAndHeight(540, 608))
      val tableExtractor = FirstOccurrenceOfStringTableExtractor.withTextAndRegion(textToFind, textRegion, tableRegion)

      val extractor = Extractor.fromDocumentsAndExtractors(List(abbottTonyDocument, leighAndrewDocument), tableExtractor)

      val tables: Map[Document, Map[TableExtractor, Table]] = Await.result(extractor.extractTables, 60.seconds)

      "the tony abbott document" - {
        "should return a single table" in {
          assert(tables(abbottTonyDocument).get(tableExtractor).isDefined)
        }

        val table: Table = tables(abbottTonyDocument)(tableExtractor)

        val expectedRows = 24
        s"should have $expectedRows rows in the returned table" in {
          assert(table.numberOfRows == expectedRows)
        }

        shouldHaveTextInTableAtRowAndColumn(expectedText = "Expenses From", table = table, rowNumber = 1, columnNumber = 2)
        shouldHaveTextInTableAtRowAndColumn(expectedText = "$628,736.33", table = table, rowNumber = 23, columnNumber = 2)
      }

      "the andrew leigh document" - {
        "should return a single table" in {
          assert(tables(leighAndrewDocument).get(tableExtractor).isDefined)
        }

        val table: Table = tables(leighAndrewDocument)(tableExtractor)

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

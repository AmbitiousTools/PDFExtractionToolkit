package tools.ambitious.pdfextractiontoolkit.realworldtests.expensereports

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.extraction.Extractor
import tools.ambitious.pdfextractiontoolkit.extraction.tableextractors.FirstOccurrenceOfStringTableExtractor
import tools.ambitious.pdfextractiontoolkit.model.{Table, Document}
import tools.ambitious.pdfextractiontoolkit.model.geometry.{Size, PositivePoint, Rectangle}

class SummaryOfParliamentaryExpenditureByPeriodExtractionSpec extends FreeSpec {

  "the tony abbott report" - {
    val abbottTonyReport = getClass.getResource("/expensereports/P34_ABBOTT_Tony.pdf")
    val document = Document.fromPDFPath(abbottTonyReport)

    val textToFind = "Summary of Parliamentary Expenditure by Period"
    s"when provided to an extractor with the following regions looking for '$textToFind'" - {
      val textRegion = Rectangle.fromCornerAndSize(PositivePoint.at(165, 90), Size.fromWidthAndHeight(280, 25))
      val tableRegion = Rectangle.fromCornerAndSize(PositivePoint.at(34, 138), Size.fromWidthAndHeight(540, 608))
      val tableExtractor = FirstOccurrenceOfStringTableExtractor.withTextAndRegion(textToFind, textRegion, tableRegion)

      val extractor = Extractor.fromDocumentAndExtractors(document, tableExtractor)

      val tables: List[Table] = extractor.extractTables

      "should return a single table" in {
        assert(tables.length == 1)
      }

      val table: Table = tables.head

      val expectedRows = 24
      s"should have $expectedRows rows in the returned table" in {
        assert(table.numberOfRows == expectedRows)
      }

      shouldHaveTextInTableAtRowAndColumn(expectedText = "Expenses From", table = table, rowNumber = 1, columnNumber = 2)
      shouldHaveTextInTableAtRowAndColumn(expectedText = "$628,736.33", table = table, rowNumber = 23, columnNumber = 2)
    }
  }

  private def shouldHaveTextInTableAtRowAndColumn(expectedText: String, table: Table, rowNumber: Int, columnNumber: Int) = {
    s"should return a table with the value '$expectedText' at row $rowNumber and column $columnNumber" in {
      assert(table.getCell(rowNumber, columnNumber).text == expectedText)
    }
  }


}

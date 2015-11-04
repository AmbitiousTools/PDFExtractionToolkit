package tools.ambitious.pdfextractiontoolkit.library.extraction.tablemergers

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.Resources
import tools.ambitious.pdfextractiontoolkit.library.model.Table
import tools.ambitious.pdfextractiontoolkit.library.util.CSVUtil

class SimpleTableMergerSpec extends FreeSpec {

  private val table1: Table = CSVUtil.tableFromURL(Resources.simpleTest2Tables2TitlePage1CSVURL)
  private val table2: Table = CSVUtil.tableFromURL(Resources.simpleTest2Tables2TitlePage2CSVURL)
  private val tables: List[Table] = List(table1, table2)

  s"A ${SimpleTableMerger.getClass.getSimpleName}" - {
    "that does not ignore header rows" - {
      val simpleTableMerger: SimpleTableMerger = SimpleTableMerger.create

      "when it merges the two tables" - {
        val merged: Table = simpleTableMerger.mergeTables(tables).get

        shouldReturnTableWith(table = merged, row = 1, column = 3, expected = "4")
        shouldReturnTableWith(table = merged, row = 6, column = 1, expected = "4")
        shouldReturnTableWith(table = merged, row = 7, column = 4, expected = "6")
        shouldReturnTableWith(table = merged, row = 9, column = 2, expected = "9")
        shouldReturnTableWith(table = merged, row = 20, column = 3, expected = "5")
      }
    }

    "created to ignore header rows" - {
      val simpleTableMerger: SimpleTableMerger = SimpleTableMerger.createIgnoringHeaderRows

      "when it merges the two tables" - {
        val merged: Table = simpleTableMerger.mergeTables(tables).get

        shouldReturnTableWith(table = merged, row = 1, column = 3, expected = "5")
        shouldReturnTableWith(table = merged, row = 6, column = 1, expected = "2")
        shouldReturnTableWith(table = merged, row = 7, column = 4, expected = "1")
        shouldReturnTableWith(table = merged, row = 9, column = 2, expected = "8")
        shouldReturnTableWith(table = merged, row = 20, column = 3, expected = "1")
      }
    }
  }

  private def shouldReturnTableWith(table: Table, row: Int, column: Int, expected: String):Unit = {
    s"should return a table with the value $expected at row $row and column $column" in {
      assert(table.getCell(row, column).text == expected)
    }
  }
}

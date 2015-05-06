package tools.ambitious.pdfextractiontoolkit.model

import org.scalatest.FreeSpec

class TableSpec extends FreeSpec {

  "A table" - {
    val table: Table = new Table

    "should be able to add rows" in {
      table.addRow(new Row)
    }

    "with a cell containing the text 'test' added to it" - {
      val row: Row = new Row
      row.addCell(new Cell("test"))

      table.addRow(row)

      "should have that cell in it's 1,1 position" in {
        assert(table.getCell(1,1).text == "test")
      }
    }
  }

  "A table with 2 rows, each containing three entries" - {
    val table: Table = new Table

    var row: Row = new Row
    row.addCell("1")
    row.addCell("2")
    row.addCell("3")
    table.addRow(row)

    row = new Row
    row.addCell("4")
    row.addCell("5")
    row.addCell("6")
    table.addRow(row)

    "should return '1,2,3\n4,5,6' when converted to String" in {
      assert(table.toString == "1,2,3\n4,5,6")
    }
  }
}

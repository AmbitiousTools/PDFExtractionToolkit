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
}

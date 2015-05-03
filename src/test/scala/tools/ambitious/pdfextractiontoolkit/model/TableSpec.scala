package tools.ambitious.pdfextractiontoolkit.model

import org.scalatest.FreeSpec

class TableSpec extends FreeSpec {

  "A table" - {
    var table: Table = new Table

    "should be able to add rows" in {
      table.addRow(new Row)
    }
  }
}

package tools.ambitious.pdfextractiontoolkit.model

import org.scalatest.FreeSpec

class RowSpec extends FreeSpec {

  "A row" - {
    var row: Row = new Row

    "should be able to add cells" in {
      row.addCell(new Cell)
    }
  }
}

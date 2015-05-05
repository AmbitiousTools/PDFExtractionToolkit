package tools.ambitious.pdfextractiontoolkit.model

import org.scalatest.FreeSpec

class RowSpec extends FreeSpec {

  "A row" - {
    var row: Row = new Row

    "with a cell containing the text 'test' added to it" - {
      row.addCell(new Cell("test"))

      "should have the first cell contain the text 'test" in {
        assert(row.getCell(1).text == "test")
      }
    }
  }
}

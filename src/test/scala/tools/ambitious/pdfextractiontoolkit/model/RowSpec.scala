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

  "A row" - {
    var row: Row = new Row

    "that adds a cell using text" - {
      row.addCell("test")

      "should have the first cell contain the text 'test" in {
        assert(row.getCell(1).text == "test")
      }
    }
  }

  "A row containing the entries 1, 2 and 3" - {
    var row: Row = new Row
    row.addCell("1")
    row.addCell("2")
    row.addCell("3")

    "should return '1,2,3' when converted to String" in {
      assert(row.toString == "1,2,3")
    }
  }
}

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

  "A row instantiated from three Cells containing 1, 2 and 3 consecutively" - {
    val row: Row = Row.fromCells(List(new Cell("1"), new Cell("2"), new Cell("3")))

    "should have first cell with text 1" in {
      assert(row.getCell(1).text == "1")
    }

    "should have second cell with text 2" in {
      assert(row.getCell(2).text == "2")
    }

    "should have third cell with text 3" in {
      assert(row.getCell(3).text == "3")
    }
  }

  "A row instantiated from three Strings containing 1, 2 and 3 consecutively" - {
    val cellContents: List[String] = List("1", "2", "3")
    val row: Row = Row.fromStrings(cellContents)

    "should have first cell with text 1" in {
      assert(row.getCell(1).text == "1")
    }

    "should have second cell with text 2" in {
      assert(row.getCell(2).text == "2")
    }

    "should have third cell with text 3" in {
      assert(row.getCell(3).text == "3")
    }
  }

  "A row instantiated from a single cell" - {
    val row: Row = Row.fromCell(new Cell("1"))

    "should have first cell with text 1" in {
      assert(row.getCell(1).text == "1")
    }
  }

  "A row instantiated from a single string" - {
    val row: Row = Row.fromString("1")

    "should have first cell with text 1" in {
      assert(row.getCell(1).text == "1")
    }
  }
}

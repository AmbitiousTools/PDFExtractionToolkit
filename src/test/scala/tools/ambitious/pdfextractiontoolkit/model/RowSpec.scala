package tools.ambitious.pdfextractiontoolkit.model

import org.scalatest.FreeSpec

class RowSpec extends FreeSpec {

  "A row that is instantiated without any arguments" - {
    val row: Row = new Row

    "should be empty" in {
      assert(row.isEmpty)
    }

    "should have no cells" in {
      assert(row.numberOfCells == 0)
    }
  }

  "A row that is instantiated with a cell containing the text 'test'" - {
    val row: Row = new Row(List(new Cell("test")))

    "should not be empty" in {
      assert(!row.isEmpty)
    }

    "should have the first cell contain the text 'test" in {
      assert(row.getCell(1).text == "test")
    }

    "should have one cell" in {
      assert(row.numberOfCells == 1)
    }
  }

  "A row containing the entries 1, 2 and 3" - {
    val row: Row = Row.fromStrings(List("1", "2", "3"))

    "should return '1,2,3' when converted to String" in {
      assert(row.toString == "1,2,3")
    }

    "should have three cells" in {
      assert(row.numberOfCells == 3)
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

  "Two rows with the same cells" - {
    val cellA: Cell = new Cell("test")
    val cellB: Cell = new Cell("test")

    val rowA: Row = Row.fromCells(List(cellA, cellB))
    val rowB: Row = Row.fromCells(List(cellA, cellB))

    "should be equal" in {
      assert(rowA == rowB)
    }

    "should not have the same reference" in {
      assert(rowA ne rowB)
    }
  }

  "Two rows with different cells" - {
    val rowA: Row = Row.fromStrings(List("a", "b"))
    val rowB: Row = Row.fromStrings(List("c", "d"))

    "should not be equal" in {
      assert(rowA != rowB)
    }
  }

  "Two rows with the first row having more cells than the second row" - {
    val rowA: Row = Row.fromStrings(List("a", "b"))
    val rowB: Row = Row.fromStrings(List("a"))

    "should not be equal" in {
      assert(rowA != rowB)
    }
  }

  "Two rows with the first row having less cells than the second row" - {
    val rowA: Row = Row.fromStrings(List("a"))
    val rowB: Row = Row.fromStrings(List("a", "b"))

    "should not be equal" in {
      assert(rowA != rowB)
    }
  }

  "A row with three cells" - {
    val row: Row = Row.fromStrings(List("a", "b", "c"))

    "should have three cells" in {
      assert(row.numberOfCells == 3)
    }
  }
}

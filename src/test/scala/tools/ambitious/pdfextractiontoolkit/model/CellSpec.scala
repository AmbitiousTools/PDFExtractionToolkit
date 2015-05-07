package tools.ambitious.pdfextractiontoolkit.model

import org.scalatest.FreeSpec

class CellSpec extends FreeSpec {
  "A cell that is instantiated without any arguments" - {
    val cell: Cell = new Cell

    "should have text set to an empty string" in {
      assert(cell.text == "")
    }

    "should return true when asked if empty" in {
      assert(cell.isEmpty)
    }
  }

  "A cell that is instantiated with the string 'blah'" - {
    val cell: Cell = new Cell("blah")

    "should have text set to 'blah'" in {
      assert(cell.text == "blah")
    }

    "should return false when asked if empty" in {
      assert(!cell.isEmpty)
    }
  }

  "Two cells with the same text" - {
    val cellA: Cell = new Cell("test")
    val cellB: Cell = new Cell("test")

    "should be equal" in {
      assert(cellA == cellB)
    }

    "should not have the same reference" in {
      assert(cellA ne cellB)
    }
  }

  "Two cells with different text" - {
    val cellA: Cell = new Cell("test")
    val cellB: Cell = new Cell("test2")

    "should not be equal" in {
      assert(cellA != cellB)
    }
  }
}

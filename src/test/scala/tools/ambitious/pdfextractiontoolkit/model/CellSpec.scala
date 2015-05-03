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

    "should be able to set the text" in {
      cell.text = "blah"
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
}

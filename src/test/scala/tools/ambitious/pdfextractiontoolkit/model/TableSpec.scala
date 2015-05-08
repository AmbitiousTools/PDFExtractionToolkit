package tools.ambitious.pdfextractiontoolkit.model

import org.scalatest.FreeSpec

class TableSpec extends FreeSpec {

  "A table with a single row containing a single cell with the text 'test'" - {
    val row: Row = Row.fromString("test")
    val table: Table = Table.fromRow(row)

    "should have that cell in it's 1,1 position" in {
      assert(table.getCell(1,1).text == "test")
    }

    "should have one row" in {
      assert(table.numberOfRows == 1)
    }

    "should have it's first row equal to the row we set" in {
      assert(table.getRow(1) == row)
    }
  }

  "A table with 2 rows, each containing three distinct entries" - {
    val rowA: Row = Row.fromStrings(List("1", "2", "3"))
    val rowB: Row = Row.fromStrings(List("4", "5", "6"))
    val table: Table = Table.fromRows(List(rowA, rowB))

    "should return '1,2,3\n4,5,6' when converted to String" in {
      assert(table.toString == "1,2,3\n4,5,6")
    }

    "should have two rows" in {
      assert(table.numberOfRows == 2)
    }

    "should have it's first row equal to the first row we set" in {
      assert(table.getRow(1) == rowA)
    }

    "should have it's second row equal to the second row we set" in {
      assert(table.getRow(2) == rowB)
    }
  }

  "A table instantiated from three rows each containing one cell with text 1, 2 and 3 consecutively" - {
    val cellContents = List("1", "2", "3")
    val rows: List[Row] = cellContents.map(i => Row.fromString(i))

    val table: Table = Table.fromRows(rows)

    "should have string '1\n2\n3" in {
      assert(table.toString == "1\n2\n3")
    }

    "should have three rows" in {
      assert(table.numberOfRows == 3)
    }

    "should have it's first row equal to the first row we set" in {
      assert(table.getRow(1) == rows.head)
    }

    "should have it's second row equal to the second row we set" in {
      assert(table.getRow(2) == rows(1))
    }

    "should have it's third row equal to the third row we set" in {
      assert(table.getRow(3) == rows(2))
    }
  }

  "A table instantiated from a single row containing a cell with the text 'test'" - {
    val table: Table = Table.fromRow(Row.fromString("test"))

    "should have string 'test'" in {
      assert(table.toString == "test")
    }
  }

  "Two tables with the same rows" - {
    val cellA: Cell = new Cell("test")
    val cellB: Cell = new Cell("test")

    val rowA: Row = Row.fromCells(List(cellA, cellB))
    val rowB: Row = Row.fromCells(List(cellA, cellB))

    val tableA: Table = Table.fromRows(List(rowA, rowB))
    val tableB: Table = Table.fromRows(List(rowA, rowB))

    "should be equal" in {
      assert(tableA == tableB)
    }

    "should not have the same reference" in {
      assert(tableA ne tableB)
    }
  }

  "Two tables with different rows" - {
    val tableA: Table = Table.fromRows(List(Row.fromString("a"), Row.fromString("b")))
    val tableB: Table = Table.fromRows(List(Row.fromString("c"), Row.fromString("d")))

    "should not be equal" in {
      assert(tableA != tableB)
    }
  }

  "Two tables with the first table having more rows than the second table" - {
    val tableA: Table = Table.fromRows(List(Row.fromString("a"), Row.fromString("b")))
    val tableB: Table = Table.fromRow(Row.fromString("c"))

    "should not be equal" in {
      assert(tableA != tableB)
    }
  }

  "Two rows with the first row having less cells than the second row" - {
    val tableA: Table = Table.fromRow(Row.fromString("a"))
    val tableB: Table = Table.fromRows(List(Row.fromString("b"), Row.fromString("c")))

    "should not be equal" in {
      assert(tableA != tableB)
    }
  }

  "A list of two tables that are merged" - {
    val tableA: Table = Table.fromRows(List(Row.fromString("a"), Row.fromString("b")))
    val tableB: Table = Table.fromRows(List(Row.fromString("c"), Row.fromString("d")))

    val mergedTable: Table = Table.merge(List(tableA, tableB))

    "should have first row equal to the first row in tableA" in {
      assert(mergedTable.getRow(1) == tableA.getRow(1))
    }

    "should have second row equal to the second row in tableA" in {
      assert(mergedTable.getRow(2) == tableA.getRow(2))
    }

    "should have third row equal to the first row in tableB" in {
      assert(mergedTable.getRow(3) == tableB.getRow(1))
    }

    "should have fourth row equal to the second row in tableB" in {
      assert(mergedTable.getRow(4) == tableB.getRow(2))
    }

    "should have four rows" in {
      assert(mergedTable.numberOfRows == 4)
    }
  }

  "One table that is merged into another" - {
    val tableA: Table = Table.fromRows(List(Row.fromString("a"), Row.fromString("b")))
    val tableB: Table = Table.fromRows(List(Row.fromString("c"), Row.fromString("d")))

    val mergedTable: Table = tableA.mergedWith(tableB)

    "should have first row equal to the first row in tableA" in {
      assert(mergedTable.getRow(1) == tableA.getRow(1))
    }

    "should have second row equal to the second row in tableA" in {
      assert(mergedTable.getRow(2) == tableA.getRow(2))
    }

    "should have third row equal to the first row in tableB" in {
      assert(mergedTable.getRow(3) == tableB.getRow(1))
    }

    "should have fourth row equal to the second row in tableB" in {
      assert(mergedTable.getRow(4) == tableB.getRow(2))
    }

    "should have four rows" in {
      assert(mergedTable.numberOfRows == 4)
    }
  }

  "Two tables that are merged" - {
    val tableA: Table = Table.fromRows(List(Row.fromString("a"), Row.fromString("b")))
    val tableB: Table = Table.fromRows(List(Row.fromString("c"), Row.fromString("d")))

    val mergedTable: Table = Table.merge(tableA, tableB)

    "should have first row equal to the first row in tableA" in {
      assert(mergedTable.getRow(1) == tableA.getRow(1))
    }

    "should have second row equal to the second row in tableA" in {
      assert(mergedTable.getRow(2) == tableA.getRow(2))
    }

    "should have third row equal to the first row in tableB" in {
      assert(mergedTable.getRow(3) == tableB.getRow(1))
    }

    "should have fourth row equal to the second row in tableB" in {
      assert(mergedTable.getRow(4) == tableB.getRow(2))
    }

    "should have four rows" in {
      assert(mergedTable.numberOfRows == 4)
    }
  }
}

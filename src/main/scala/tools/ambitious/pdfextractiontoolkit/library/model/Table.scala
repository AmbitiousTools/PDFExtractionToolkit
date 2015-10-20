package tools.ambitious.pdfextractiontoolkit.library.model

class Table(val rows: List[Row] = Nil) {
  def getCell(row: Int, column: Int): Cell = getRow(row).getCell(column)

  def getRow(number: Int): Row = {
    if (number <= numberOfRows && number > 0)
      rows(number-1)
    else
      throw new IllegalArgumentException("Invalid row number.")
  }

  lazy val numberOfRows: Int = rows.length

  lazy val numberOfColumns: Int = rows.map(_.numberOfCells).max

  override def toString: String =
    rows.map(row => row.toString).mkString("\n")

  override def equals(obj: Any): Boolean = {
    obj match {
      case table: Table =>
        var rowComparison: Boolean = this.numberOfRows == table.numberOfRows
        for (i <- 1 to this.numberOfRows) {
          try {
            rowComparison = rowComparison && (this.getRow(i) == table.getRow(i))
          } catch {
            case _: Throwable => rowComparison = false
          }
        }
        rowComparison
      case _ => false
    }
  }

  def mergedWith(table: Table): Table =
    Table.merge(this, table)
}

object Table {
  def fromRows(rows: List[Row]): Table =
    new Table(rows)

  def fromRow(row: Row): Table =
    fromRows(List(row))

  def merge(tables: List[Table]): Table =
    fromRows(tables.flatMap(table => table.rows))

  def merge(tableA: Table, tableB: Table): Table =
    merge(List(tableA, tableB))
}
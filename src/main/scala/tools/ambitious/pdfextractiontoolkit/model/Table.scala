package tools.ambitious.pdfextractiontoolkit.model

class Table(private val rows: List[Row] = Nil) {
  def getCell(i: Int, j: Int): Cell = getRow(j).getCell(i)

  def getRow(number: Int): Row = {
    if (number <= numberOfRows && number > 0)
      rows(number-1)
    else
      throw new IllegalArgumentException("Invalid row number.")
  }

  def numberOfRows: Int = rows.length

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
}

object Table {
  def fromRows(rows: List[Row]): Table =
    new Table(rows)

  def fromRow(row: Row): Table =
    fromRows(List(row))

  def merge(tables: List[Table]): Table =
    fromRows(tables.map(table => table.rows).flatten)
}
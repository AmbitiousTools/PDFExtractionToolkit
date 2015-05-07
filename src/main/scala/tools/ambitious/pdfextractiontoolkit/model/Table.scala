package tools.ambitious.pdfextractiontoolkit.model

class Table(private val rows: List[Row] = Nil) {
  def getCell(i: Int, j: Int): Cell = rows(j-1).getCell(i)

  def getRow(i: Int): Row = rows(i-1)

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
}
package tools.ambitious.pdfextractiontoolkit.library.model

class Row(private val cells: List[Cell] = Nil) {
  def getCell(number: Int) = {
    if (number <= numberOfCells && number > 0)
      cells(number-1)
    else
      throw new IllegalArgumentException(s"Invalid cell number $number")
  }

  def numberOfCells: Int = cells.length

  def isEmpty: Boolean = numberOfCells == 0

  override def toString: String =
    cells.map(cell => cell.text).mkString(",")

  override def equals(obj: Any): Boolean = {
    obj match {
      case row: Row =>
        var cellComparison: Boolean = this.numberOfCells == row.numberOfCells
        for (i <- 1 to cells.length) {
          try {
            cellComparison = cellComparison && (this.getCell(i) == row.getCell(i))
          } catch {
            case _: Throwable => cellComparison = false
          }
        }
        cellComparison
      case _ => false
    }
  }
}

object Row {
  def fromCells(cells: List[Cell]): Row =
    new Row(cells)

  def fromCell(cell: Cell): Row =
    fromCells(List(cell))

  def fromStrings(strings: List[String]): Row =
    fromCells(strings.map(string => new Cell(string)))

  def fromString(string: String): Row =
    fromStrings(List(string))
}
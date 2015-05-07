package tools.ambitious.pdfextractiontoolkit.model

class Row {
  private var cells: List[Cell] = Nil

  def addCell(cell: Cell) =
    cells = cells ++ List(cell)

  def addCell(cellText: String): Unit =
    addCell(new Cell(cellText))

  def getCell(i: Int) =
    cells(i-1)

  override def toString: String =
    cells.map(cell => cell.text).mkString(",")

}

object Row {
  def fromCells(cells: List[Cell]): Row = {
    val row: Row = new Row
    cells.foreach(cell => row.addCell(cell))
    row
  }

  def fromCell(cell: Cell): Row =
    fromCells(List(cell))

  def fromStrings(strings: List[String]): Row =
    fromCells(strings.map(string => new Cell(string)))

  def fromString(string: String): Row =
    fromStrings(List(string))
}
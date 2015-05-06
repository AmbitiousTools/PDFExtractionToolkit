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

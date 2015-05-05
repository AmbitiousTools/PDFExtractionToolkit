package tools.ambitious.pdfextractiontoolkit.model

class Row {
  private var cells: List[Cell] = Nil

  def addCell(cell: Cell) = {
    cells = cells ++ List(cell)
  }

  def getCell(i: Int) = {
    cells(i-1)
  }
}

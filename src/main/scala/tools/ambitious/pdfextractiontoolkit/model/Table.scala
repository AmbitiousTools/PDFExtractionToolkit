package tools.ambitious.pdfextractiontoolkit.model

class Table {
  private var rows: List[Row] = Nil

  def addRow(row: Row) =
    rows = rows ++ List(row)

  def getCell(i: Int, j: Int): Cell =
    rows(j-1).getCell(i)

  override def toString: String = {
    rows.map(row => row.toString).mkString("\n")
  }
}

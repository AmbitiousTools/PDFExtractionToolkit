package tools.ambitious.pdfextractiontoolkit.model

class Table {
  private var rows: List[Row] = Nil

  def addRow(row: Row) = {
    rows = rows ++ List(row)
  }
}

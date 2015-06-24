package tools.ambitious.pdfextractiontoolkit.extraction.tablemergers

import tools.ambitious.pdfextractiontoolkit.model.{Row, Table}

class SimpleTableMerger(val ignoreHeaders: Boolean) extends TableMerger {
  override def mergeTables(toMerge: List[Table]): Option[Table] = {
    if (ignoreHeaders) {
      val rowsMinusHeaders: List[Row] = toMerge.flatMap(_.rows.drop(1))
      if (rowsMinusHeaders.isEmpty) {
        Option.empty
      } else {
        Option.apply(Table.fromRows(rowsMinusHeaders))
      }
    } else {
      toMerge.reduceOption(Table.merge)
    }
  }
}

object SimpleTableMerger {
  def create: SimpleTableMerger = new SimpleTableMerger(false)
  def createIgnoringHeaderRows: SimpleTableMerger = new SimpleTableMerger(true)
}

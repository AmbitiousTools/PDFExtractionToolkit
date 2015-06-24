package tools.ambitious.pdfextractiontoolkit.extraction.tablemergers

import tools.ambitious.pdfextractiontoolkit.model.Table

trait TableMerger {
  def mergeTables(toMerge: List[Table]): Option[Table]
}

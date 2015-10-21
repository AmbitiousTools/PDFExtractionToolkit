package tools.ambitious.pdfextractiontoolkit.library.extraction.tablemergers

import tools.ambitious.pdfextractiontoolkit.library.model.Table

trait TableMerger {
  def mergeTables(toMerge: List[Table]): Option[Table]
}

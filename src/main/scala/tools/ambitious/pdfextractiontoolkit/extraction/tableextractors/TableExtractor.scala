package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import tools.ambitious.pdfextractiontoolkit.extraction.DocumentWalkerListener
import tools.ambitious.pdfextractiontoolkit.model.Table

trait TableExtractor extends DocumentWalkerListener {
  def getTable: Option[Table]
}

package tools.ambitious.pdfextractiontoolkit.library.extraction.tableextractors

import tools.ambitious.pdfextractiontoolkit.library.model.{Page, Table}

trait TableExtractor {
  def getTable(page: Page): Table
}

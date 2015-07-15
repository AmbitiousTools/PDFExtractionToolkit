package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import tools.ambitious.pdfextractiontoolkit.model.{Page, Table}

trait PageToTableTranslator {
  def getTable(page: Page): Table
}

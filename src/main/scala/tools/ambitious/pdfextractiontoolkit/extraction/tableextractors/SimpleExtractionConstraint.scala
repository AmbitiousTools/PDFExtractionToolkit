package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import tools.ambitious.pdfextractiontoolkit.extraction.StateBundle
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page, Table}

trait SimpleExtractionConstraint extends ExtractionConstraint {
  protected val pageToTableTranslator: PageToTableTranslator

  protected var table: Option[Table] = None

  def shouldExtractOnPage(page: Page, document: Document, stateBundle: StateBundle): Boolean
  
  protected def performExtraction(page: Page) = {
    pageToTableTranslator.getTable(page)
  }
}

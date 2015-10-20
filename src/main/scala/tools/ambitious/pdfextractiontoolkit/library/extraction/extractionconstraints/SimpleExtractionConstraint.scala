package tools.ambitious.pdfextractiontoolkit.library.extraction.extractionconstraints

import tools.ambitious.pdfextractiontoolkit.library.extraction.StateBundle
import tools.ambitious.pdfextractiontoolkit.library.extraction.tableextractors.TableExtractor
import tools.ambitious.pdfextractiontoolkit.library.model.{Document, Page, Table}

trait SimpleExtractionConstraint extends ExtractionConstraint {
  protected val tableExtractor: TableExtractor

  protected var table: Option[Table] = None

  def shouldExtractOnPage(page: Page, document: Document, stateBundle: StateBundle): Boolean
  
  protected def performExtraction(page: Page) =
    tableExtractor.getTable(page)
}

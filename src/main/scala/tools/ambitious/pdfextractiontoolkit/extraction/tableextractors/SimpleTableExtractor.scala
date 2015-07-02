package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import technology.tabula
import tools.ambitious.pdfextractiontoolkit.extraction.{ExtractionUtils, StateBundle}
import tools.ambitious.pdfextractiontoolkit.model.geometry.Rectangle
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page, Table}
import tools.ambitious.pdfextractiontoolkit.util.TabulaConverter

trait SimpleTableExtractor extends TableExtractor {
  protected var table: Option[Table] = None

  val region: Rectangle

  def shouldExtractOnPage(page: Page, document: Document, stateBundle: StateBundle): Boolean
  
  protected def performExtraction(page: Page, region: Rectangle) = {
    val tabulaTable: tabula.Table = ExtractionUtils.extractTabulaTableFromPage(page, region)
    TabulaConverter.tableFromTabulaTable(tabulaTable)
  }
}

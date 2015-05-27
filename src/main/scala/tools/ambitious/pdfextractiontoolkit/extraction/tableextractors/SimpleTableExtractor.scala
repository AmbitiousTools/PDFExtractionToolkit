package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import technology.tabula
import tools.ambitious.pdfextractiontoolkit.extraction.ExtractionUtils
import tools.ambitious.pdfextractiontoolkit.model.geometry.Rectangle
import tools.ambitious.pdfextractiontoolkit.model.{Table, Document, Page}
import tools.ambitious.pdfextractiontoolkit.util.TabulaConverter

trait SimpleTableExtractor extends TableExtractor {
  protected var table: Option[Table] = None

  override def getTable = table

  val region: Rectangle

  override def onPage(page:Page, document:Document) = {
    if (shouldExtractOnPage(page, document)) table = Option.apply(performExtraction(page, region))
  }

  def shouldExtractOnPage(page: Page, document: Document): Boolean
  
  protected def performExtraction(page: Page, region: Rectangle) = {
    val tabulaTable: tabula.Table = ExtractionUtils.extractTabulaTableFromPage(page, region)
    TabulaConverter.tableFromTabulaTable(tabulaTable)
  }
}

package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import technology.tabula
import tools.ambitious.pdfextractiontoolkit.extraction.ExtractionUtils
import tools.ambitious.pdfextractiontoolkit.model.geometry.Rectangle
import tools.ambitious.pdfextractiontoolkit.model.{Page, Table}
import tools.ambitious.pdfextractiontoolkit.util.TabulaConverter

class RegionBasedPageToTableTranslator protected (val region: Rectangle) extends PageToTableTranslator {
  override def getTable(page: Page): Table = {
    val tabulaTable: tabula.Table = ExtractionUtils.extractTabulaTableFromPage(page, region)
    TabulaConverter.tableFromTabulaTable(tabulaTable)
  }
}

object RegionBasedPageToTableTranslator {
  def forRegion(region: Rectangle) = new RegionBasedPageToTableTranslator(region)
}
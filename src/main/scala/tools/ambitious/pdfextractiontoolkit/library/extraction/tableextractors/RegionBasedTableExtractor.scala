package tools.ambitious.pdfextractiontoolkit.library.extraction.tableextractors

import technology.tabula
import tools.ambitious.pdfextractiontoolkit.library.extraction.ExtractionUtils
import tools.ambitious.pdfextractiontoolkit.library.model.geometry.Rectangle
import tools.ambitious.pdfextractiontoolkit.library.model.{Page, Table}
import tools.ambitious.pdfextractiontoolkit.library.util.TabulaConverter

class RegionBasedTableExtractor protected (val region: Rectangle) extends TableExtractor {
  override def getTable(page: Page): Table = {
    val tabulaTable: tabula.Table = ExtractionUtils.extractTabulaTableFromPage(page, region)
    TabulaConverter.tableFromTabulaTable(tabulaTable)
  }
}

object RegionBasedTableExtractor {
  def forRegion(region: Rectangle) = new RegionBasedTableExtractor(region)
}
package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import technology.tabula
import tools.ambitious.pdfextractiontoolkit.extraction.ExtractionUtils
import tools.ambitious.pdfextractiontoolkit.model.geometry.Rectangle
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page}

case class FirstOccurrenceOfStringTableExtractor protected (text: String, textRegion: Rectangle, region: Rectangle) extends SimpleTableExtractor {
  override def shouldExtractOnPage(page: Page, document: Document): Boolean = {
    val table: tabula.Table = ExtractionUtils.extractTabulaTableFromPage(page, textRegion)
    val foundText = table.getCell(0, 0).getText

    text == foundText
  }
}

object FirstOccurrenceOfStringTableExtractor {
  def withTextAndRegion(text: String, textRegion: Rectangle, region: Rectangle) = new FirstOccurrenceOfStringTableExtractor(text, textRegion, region)
}


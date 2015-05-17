package tools.ambitious.pdfextractiontoolkit.model.constraints

import technology.tabula
import tools.ambitious.pdfextractiontoolkit.extraction.ExtractionUtils
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page}
import tools.ambitious.pdfextractiontoolkit.model.geometry.Rectangle

class FirstOccurrenceOfStringExtractionCondition protected (val window: Rectangle, val text: String) extends ExtractionCondition {
  private var currentPage: Option[Page] = None

  override def onPage(page: Page, document: Document): Unit = currentPage = Option.apply(page)

  override def onEnd(): Unit = currentPage = None

  override def shouldPerformExtraction(): Boolean = {
    currentPage.exists(presentCurrentPage => {
      val table: tabula.Table = ExtractionUtils.extractTabulaTableFromPage(presentCurrentPage, window)
      val foundText = table.getCell(0, 0).getText

      text == foundText
    })
  }
}

object FirstOccurrenceOfStringExtractionCondition {
  def usingWindowForString(window:Rectangle, text:String): FirstOccurrenceOfStringExtractionCondition =
    new FirstOccurrenceOfStringExtractionCondition(window, text)
}

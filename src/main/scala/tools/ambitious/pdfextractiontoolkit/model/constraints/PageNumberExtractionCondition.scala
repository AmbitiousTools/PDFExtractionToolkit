package tools.ambitious.pdfextractiontoolkit.model.constraints

import tools.ambitious.pdfextractiontoolkit.model.{Document, Page}

class PageNumberExtractionCondition protected (val pageNumber: Int) extends ExtractionCondition {

  if (pageNumber <= 0) {
    throw new IllegalArgumentException("Page numbers can only be positive numbers.")
  }

  private var currentPageNum: Option[Int] = None

  override def onPage(page: Page, document: Document): Unit = {
    val temp: Option[Int] = document.pageNumOf(page)
    currentPageNum = temp
  }

  override def onEnd(): Unit = currentPageNum = None

  override def shouldPerformExtraction(): Boolean = currentPageNum.contains(pageNumber)
}

object PageNumberExtractionCondition {
  def atPage(pageNum: Int): PageNumberExtractionCondition = new PageNumberExtractionCondition(pageNum)
}

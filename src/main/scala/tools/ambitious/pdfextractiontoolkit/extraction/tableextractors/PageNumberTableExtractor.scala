package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import tools.ambitious.pdfextractiontoolkit.model.geometry.Rectangle
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page}

case class PageNumberTableExtractor protected (pageNumber: Int, region: Rectangle) extends SimpleTableExtractor {
  if (pageNumber <= 0) {
    throw new IllegalArgumentException("Page numbers can only be positive numbers.")
  }

  override def shouldExtractOnPage(page: Page, document: Document): Boolean =
    document.pageNumberOf(page).contains(pageNumber)

}
object PageNumberTableExtractor {
  def withPageNumberAndRegion(pageNumber: Int, region: Rectangle) = new PageNumberTableExtractor(pageNumber, region)
}


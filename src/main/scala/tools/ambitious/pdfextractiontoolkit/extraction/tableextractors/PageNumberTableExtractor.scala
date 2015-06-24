package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import tools.ambitious.pdfextractiontoolkit.extraction.tablemergers.{SimpleTableMerger, TableMerger}
import tools.ambitious.pdfextractiontoolkit.model.geometry.Rectangle
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page}

import scala.collection.immutable.Range.Inclusive

case class PageNumberTableExtractor protected (pages: Set[Int], region: Rectangle) extends MergingSimpleTableExtractor {
  if (pages.exists(_ <= 0)) {
    throw new IllegalArgumentException("Page numbers can only be positive numbers.")
  }

  override def shouldExtractOnPage(page: Page, document: Document): Boolean =
    document.pageNumberOf(page).exists(pages.contains)

  override val tableMerger: TableMerger = SimpleTableMerger.create
}
object PageNumberTableExtractor {
  def withPageNumberAndRegion(pageNumber: Int, region: Rectangle) =
    new PageNumberTableExtractor(Set(pageNumber), region)

  def withPageRangeAndRegion(range: Inclusive, region: Rectangle) = new PageNumberTableExtractor(Set(range: _*), region)
}


package tools.ambitious.pdfextractiontoolkit.extraction.extractionconstraints

import tools.ambitious.pdfextractiontoolkit.extraction.StateBundle
import tools.ambitious.pdfextractiontoolkit.extraction.tableextractors.TableExtractor
import tools.ambitious.pdfextractiontoolkit.extraction.tablemergers.{SimpleTableMerger, TableMerger}
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page}

import scala.collection.immutable.Range.Inclusive

case class PageNumberExtractionConstraint protected (pages: Set[Int], tableExtractor: TableExtractor) extends MergingSimpleExtractionConstraint {
  if (pages.exists(_ <= 0)) {
    throw new IllegalArgumentException("Page numbers can only be positive numbers.")
  }

  override def shouldExtractOnPage(page: Page, document: Document, stateBundle: StateBundle): Boolean =
    document.pageNumberOf(page).exists(pages.contains)

  override val tableMerger: TableMerger = SimpleTableMerger.create
}
object PageNumberExtractionConstraint {
  def withPageNumberAndTableExtractor(pageNumber: Int, tableExtractor: TableExtractor) =
    new PageNumberExtractionConstraint(Set(pageNumber), tableExtractor)

  def withPageRangeAndTableExtractor(range: Inclusive, tableExtractor: TableExtractor) =
    new PageNumberExtractionConstraint(Set(range: _*), tableExtractor)
}


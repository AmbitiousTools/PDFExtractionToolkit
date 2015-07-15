package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import tools.ambitious.pdfextractiontoolkit.extraction.StateBundle
import tools.ambitious.pdfextractiontoolkit.extraction.tablemergers.{SimpleTableMerger, TableMerger}
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page}

import scala.collection.immutable.Range.Inclusive

case class PageNumberExtractionConstraint protected (pages: Set[Int], pageToTableTranslator: PageToTableTranslator) extends MergingSimpleExtractionConstraint {
  if (pages.exists(_ <= 0)) {
    throw new IllegalArgumentException("Page numbers can only be positive numbers.")
  }

  override def shouldExtractOnPage(page: Page, document: Document, stateBundle: StateBundle): Boolean =
    document.pageNumberOf(page).exists(pages.contains)

  override val tableMerger: TableMerger = SimpleTableMerger.create
}
object PageNumberExtractionConstraint {
  def withPageNumberAndTranslator(pageNumber: Int, pageToTableTranslator: PageToTableTranslator) =
    new PageNumberExtractionConstraint(Set(pageNumber), pageToTableTranslator)

  def withPageRangeAndTranslator(range: Inclusive, pageToTableTranslator: PageToTableTranslator) =
    new PageNumberExtractionConstraint(Set(range: _*), pageToTableTranslator)
}


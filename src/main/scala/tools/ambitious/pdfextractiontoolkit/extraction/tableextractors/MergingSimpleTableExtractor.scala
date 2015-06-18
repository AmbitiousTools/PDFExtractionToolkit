package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import tools.ambitious.pdfextractiontoolkit.extraction.tablemergers.TableMerger
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page, Table}

trait MergingSimpleTableExtractor extends SimpleTableExtractor {

  val tableMerger: TableMerger

  protected var extractedTables: List[Table] = List()

  override def onPage(page: Page, document: Document): Unit = {
    if (shouldExtractOnPage(page, document)) {
      extractedTables = extractedTables :+ performExtraction(page, region)
    }
  }

  override def getTable: Option[Table] = tableMerger.mergeTables(extractedTables)
}

package tools.ambitious.pdfextractiontoolkit.extraction

import technology.tabula
import tools.ambitious.pdfextractiontoolkit.model.constraints.ExtractionCondition
import tools.ambitious.pdfextractiontoolkit.model.geometry.Rectangle
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page, Table}
import tools.ambitious.pdfextractiontoolkit.util.TabulaConverter

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class DatumExtractor protected (val portal: Rectangle, val extractionCondition: ExtractionCondition)
  extends DocumentWalkerListener {

  private var extractedTablesBuffer: mutable.ListBuffer[Table] = ListBuffer[Table]()
  private var extractedTablesOption: Option[List[Table]] = None

  override def onStart() = {
    extractionCondition.onStart()
  }

  override def onPage(page: Page, document: Document) = {
    extractionCondition.onPage(page, document)

    if (extractionCondition.shouldPerformExtraction()) {
      val extractedTable = extractTableFromPage(page)
      extractedTablesBuffer = extractedTablesBuffer :+ extractedTable
    }
  }

  def extractTableFromPage(page: Page) = {
    TabulaConverter.tableFromTabulaTable(extractTabulaTableFromPage(page))
  }

  def extractTabulaTableFromPage(page: Page) :tabula.Table = {
    ExtractionUtils.extractTabulaTableFromPage(page, portal)
  }

  override def onEnd() = {
    extractionCondition.onEnd()
    extractedTablesOption = Option.apply(extractedTablesBuffer.toList)
  }

  def extractedTables(): List[Table] = {
    extractedTablesOption.getOrElse(
      throw new IllegalStateException("Attempted to retrieve tables before completing the extraction")
    )
  }
}

object DatumExtractor {
  def using(rectangle: Rectangle, extractionCondition: ExtractionCondition): DatumExtractor = {
    new DatumExtractor(rectangle, extractionCondition)
  }
}
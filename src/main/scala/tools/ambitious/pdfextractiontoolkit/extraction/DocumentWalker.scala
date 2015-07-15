package tools.ambitious.pdfextractiontoolkit.extraction

import scala.concurrent.{Promise, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import tools.ambitious.pdfextractiontoolkit.extraction.tableextractors.TableExtractor
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page, Table}

class DocumentWalker protected (val document:Document, val tableExtractors: Set[TableExtractor]) {
  private val stateBundles: Map[TableExtractor, StateBundle] = tableExtractors.map(_ -> StateBundle.create).toMap
  private val promise: Promise[Map[TableExtractor, Table]] = Promise()

  private def run() =
    Future {
      traverseDocument()
      promise.success(getCompletedTables)
    }

  private def traverseDocument() = {
      callOnStartOnTableExtractors()

      document.pages.foreach(page => callOnPageOnTableExtractors(page))

      callOnEndOnTableExtractors()
  }

  private def getCompletedTables: Map[TableExtractor, Table] =
    tableExtractors
      .map(tableExtractor => tableExtractor -> tableExtractor.tableFromState(stateBundles(tableExtractor)))
      .filter((tuple: (TableExtractor, Option[Table])) => tuple._2.isDefined)
      .map((tuple: (TableExtractor, Option[Table])) => tuple._1 -> tuple._2.get)
      .toMap

  def getTables: Future[Map[TableExtractor, Table]] =
    promise.future

  private def callOnStartOnTableExtractors() =
    tableExtractors.foreach(tableExtractor => tableExtractor.onStart(stateBundles(tableExtractor)))

  private def callOnPageOnTableExtractors(page: Page) =
    tableExtractors.foreach(tableExtractor => tableExtractor.onPage(page, document, stateBundles(tableExtractor)))

  private def callOnEndOnTableExtractors() =
    tableExtractors.foreach(tableExtractor => tableExtractor.onEnd(stateBundles(tableExtractor)))
}

object DocumentWalker {
  def toWalkWithTableExtractors(document: Document, tableExtractors: Set[TableExtractor]): DocumentWalker = {
    val walker = new DocumentWalker(document, tableExtractors)
    walker.run()
    walker
  }

  def toWalkWithTableExtractors(document: Document, tableExtractors: Seq[TableExtractor]): DocumentWalker =
    toWalkWithTableExtractors(document, tableExtractors.toSet)

  def toWalkWithTableExtractor(document: Document, tableExtractor: TableExtractor): DocumentWalker =
    toWalkWithTableExtractors(document, Seq(tableExtractor))
}
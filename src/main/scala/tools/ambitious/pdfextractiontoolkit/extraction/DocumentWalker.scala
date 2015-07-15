package tools.ambitious.pdfextractiontoolkit.extraction

import tools.ambitious.pdfextractiontoolkit.extraction.tableextractors.ExtractionConstraint
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page, Table}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

class DocumentWalker protected (val document:Document, val tableExtractors: Set[ExtractionConstraint]) {
  private val stateBundles: Map[ExtractionConstraint, StateBundle] = tableExtractors.map(_ -> StateBundle.create).toMap
  private val promise: Promise[Map[ExtractionConstraint, Table]] = Promise()

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

  private def getCompletedTables: Map[ExtractionConstraint, Table] =
    tableExtractors
      .map(tableExtractor => tableExtractor -> tableExtractor.tableFromState(stateBundles(tableExtractor)))
      .filter((tuple: (ExtractionConstraint, Option[Table])) => tuple._2.isDefined)
      .map((tuple: (ExtractionConstraint, Option[Table])) => tuple._1 -> tuple._2.get)
      .toMap

  def getTables: Future[Map[ExtractionConstraint, Table]] =
    promise.future

  private def callOnStartOnTableExtractors() =
    tableExtractors.foreach(tableExtractor => tableExtractor.onStart(stateBundles(tableExtractor)))

  private def callOnPageOnTableExtractors(page: Page) =
    tableExtractors.foreach(tableExtractor => tableExtractor.onPage(page, document, stateBundles(tableExtractor)))

  private def callOnEndOnTableExtractors() =
    tableExtractors.foreach(tableExtractor => tableExtractor.onEnd(stateBundles(tableExtractor)))
}

object DocumentWalker {
  def toWalkWithTableExtractors(document: Document, tableExtractors: Set[ExtractionConstraint]): DocumentWalker = {
    val walker = new DocumentWalker(document, tableExtractors)
    walker.run()
    walker
  }

  def toWalkWithTableExtractors(document: Document, tableExtractors: Seq[ExtractionConstraint]): DocumentWalker =
    toWalkWithTableExtractors(document, tableExtractors.toSet)

  def toWalkWithTableExtractor(document: Document, tableExtractor: ExtractionConstraint): DocumentWalker =
    toWalkWithTableExtractors(document, Seq(tableExtractor))
}
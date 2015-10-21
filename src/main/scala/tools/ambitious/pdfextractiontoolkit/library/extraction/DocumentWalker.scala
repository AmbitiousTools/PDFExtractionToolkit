package tools.ambitious.pdfextractiontoolkit.library.extraction

import tools.ambitious.pdfextractiontoolkit.library.extraction.extractionconstraints.ExtractionConstraint
import tools.ambitious.pdfextractiontoolkit.library.model.{Document, Page, Table}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

class DocumentWalker protected (val document:Document, val extractionConstraints: Set[ExtractionConstraint]) {
  private val stateBundles: Map[ExtractionConstraint, StateBundle] = extractionConstraints.map(_ -> StateBundle.create).toMap
  private val promise: Promise[Map[ExtractionConstraint, Table]] = Promise()

  private def run() =
    Future {
      traverseDocument()
      promise.success(getCompletedTables)
    }

  private def traverseDocument() = {
      callOnStartOnExtractionConstraints()

      document.pages.foreach(page => callOnPageOnExtractionConstraints(page))

      callOnEndOnExtractionConstraints()
  }

  private def getCompletedTables: Map[ExtractionConstraint, Table] =
    extractionConstraints
      .map(extractionConstraint => extractionConstraint -> extractionConstraint.tableFromState(stateBundles(extractionConstraint)))
      .filter((tuple: (ExtractionConstraint, Option[Table])) => tuple._2.isDefined)
      .map((tuple: (ExtractionConstraint, Option[Table])) => tuple._1 -> tuple._2.get)
      .toMap

  def getTables: Future[Map[ExtractionConstraint, Table]] =
    promise.future

  private def callOnStartOnExtractionConstraints() =
    extractionConstraints.foreach(extractionConstraint => extractionConstraint.onStart(stateBundles(extractionConstraint)))

  private def callOnPageOnExtractionConstraints(page: Page) =
    extractionConstraints.foreach(extractionConstraint => extractionConstraint.onPage(page, document, stateBundles(extractionConstraint)))

  private def callOnEndOnExtractionConstraints() =
    extractionConstraints.foreach(extractionConstraint => extractionConstraint.onEnd(stateBundles(extractionConstraint)))
}

object DocumentWalker {
  def toWalkWithExtractionConstraint(document: Document, extractionConstraints: Set[ExtractionConstraint]): DocumentWalker = {
    val walker = new DocumentWalker(document, extractionConstraints)
    walker.run()
    walker
  }

  def toWalkWithExtractionConstraint(document: Document, extractionConstraints: Seq[ExtractionConstraint]): DocumentWalker =
    toWalkWithExtractionConstraint(document, extractionConstraints.toSet)

  def toWalkWithExtractionConstraint(document: Document, extractionConstraint: ExtractionConstraint): DocumentWalker =
    toWalkWithExtractionConstraint(document, Seq(extractionConstraint))
}
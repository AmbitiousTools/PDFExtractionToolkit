package tools.ambitious.pdfextractiontoolkit.library.extraction

import tools.ambitious.pdfextractiontoolkit.library.extraction.extractionconstraints.ExtractionConstraint
import tools.ambitious.pdfextractiontoolkit.library.model._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

class Extractor protected(private val documents: List[Document], private val extractors: List[ExtractionConstraint]) {

  private def extractTablesFromDocument(document: Document): Future[Map[ExtractionConstraint, Table]] = {
    val walker: DocumentWalker = DocumentWalker.toWalkWithExtractionConstraint(document, extractors)

    val promise: Promise[Map[ExtractionConstraint, Table]] = Promise()
    walker.getTables.onSuccess {
      case tables => promise.success(tables)
    }
    promise.future
  }

  def extractTables: Future[ExtractionResult] = {
    val documentMap: Map[Document, Future[Map[ExtractionConstraint, Table]]] =
      documents.map(document => document -> extractTablesFromDocument(document)).toMap

    Future.sequence(documentMap.map(entry => entry._2.map(i => (entry._1, i))))
      .map(_.toMap)
      .map(ExtractionResult.withResultsMap)
  }
}

object Extractor {
  def fromDocumentsAndConstraints(documents: List[Document], extractors: List[ExtractionConstraint]): Extractor =
    new Extractor(documents, extractors)

  def fromDocumentsAndConstraints(documents: List[Document], extractors: ExtractionConstraint*): Extractor =
    fromDocumentsAndConstraints(documents, extractors.toList)

  def fromDocumentAndConstraints(document: Document, extractors: List[ExtractionConstraint]): Extractor =
    fromDocumentsAndConstraints(List(document), extractors)

  def fromDocumentAndConstraints(document: Document, extractors: ExtractionConstraint*): Extractor =
    fromDocumentAndConstraints(document, extractors.toList)
}
package tools.ambitious.pdfextractiontoolkit.extraction

import tools.ambitious.pdfextractiontoolkit.extraction.tableextractors.ExtractionConstraint
import tools.ambitious.pdfextractiontoolkit.model._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

class Extractor protected(private val documents: List[Document], private val extractors: List[ExtractionConstraint]) {

  private def extractTablesFromDocument(document: Document): Future[Map[ExtractionConstraint, Table]] = {
    val walker: DocumentWalker = DocumentWalker.toWalkWithTableExtractors(document, extractors)

    val promise: Promise[Map[ExtractionConstraint, Table]] = Promise()
    walker.getTables.onSuccess {
      case tables => promise.success(tables)
    }
    promise.future
  }

  def extractTables: Future[Map[Document, Map[ExtractionConstraint, Table]]] = {
    val documentMap: Map[Document, Future[Map[ExtractionConstraint, Table]]] =
      documents.map(document => document -> extractTablesFromDocument(document)).toMap

    Future.sequence(documentMap.map(entry => entry._2.map(i => (entry._1, i)))).map(_.toMap)
  }
}

object Extractor {
  def fromDocumentsAndExtractors(documents: List[Document], extractors: List[ExtractionConstraint]): Extractor =
    new Extractor(documents, extractors)

  def fromDocumentsAndExtractors(documents: List[Document], extractors: ExtractionConstraint*): Extractor =
    fromDocumentsAndExtractors(documents, extractors.toList)

  def fromDocumentAndExtractors(document: Document, extractors: List[ExtractionConstraint]): Extractor =
    fromDocumentsAndExtractors(List(document), extractors)

  def fromDocumentAndExtractors(document: Document, extractors: ExtractionConstraint*): Extractor =
    fromDocumentAndExtractors(document, extractors.toList)
}
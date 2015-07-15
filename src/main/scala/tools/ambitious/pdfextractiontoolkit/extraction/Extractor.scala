package tools.ambitious.pdfextractiontoolkit.extraction

import tools.ambitious.pdfextractiontoolkit.extraction.tableextractors.TableExtractor
import tools.ambitious.pdfextractiontoolkit.model._
import scala.concurrent.{Promise, Future}
import scala.concurrent.ExecutionContext.Implicits.global

class Extractor protected(private val documents: List[Document], private val extractors: List[TableExtractor]) {

  private def extractTablesFromDocument(document: Document): Future[Map[TableExtractor, Table]] = {
    val walker: DocumentWalker = DocumentWalker.toWalkWithTableExtractors(document, extractors)

    val promise: Promise[Map[TableExtractor, Table]] = Promise()
    walker.getTables.onSuccess {
      case tables => promise.success(tables)
    }
    promise.future
  }

  def extractTables: Future[Map[Document, Map[TableExtractor, Table]]] = {
    val documentMap: Map[Document, Future[Map[TableExtractor, Table]]] =
      documents.map(document => document -> extractTablesFromDocument(document)).toMap

    Future.sequence(documentMap.map(entry => entry._2.map(i => (entry._1, i)))).map(_.toMap)
  }
}

object Extractor {
  def fromDocumentsAndExtractors(documents: List[Document], extractors: List[TableExtractor]): Extractor =
    new Extractor(documents, extractors)

  def fromDocumentsAndExtractors(documents: List[Document], extractors: TableExtractor*): Extractor =
    fromDocumentsAndExtractors(documents, extractors.toList)

  def fromDocumentAndExtractors(document: Document, extractors: List[TableExtractor]): Extractor =
    fromDocumentsAndExtractors(List(document), extractors)

  def fromDocumentAndExtractors(document: Document, extractors: TableExtractor*): Extractor =
    fromDocumentAndExtractors(document, extractors.toList)
}
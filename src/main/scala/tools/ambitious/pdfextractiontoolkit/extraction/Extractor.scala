package tools.ambitious.pdfextractiontoolkit.extraction

import tools.ambitious.pdfextractiontoolkit.extraction.tableextractors.TableExtractor
import tools.ambitious.pdfextractiontoolkit.model._
import scala.concurrent.{Promise, Future}
import scala.concurrent.ExecutionContext.Implicits.global

class Extractor protected(private val document: Document, private val extractors: List[TableExtractor]) {

  def extractTables: Future[List[Table]] = {
    val walker: DocumentWalker = DocumentWalker.toWalkWithTableExtractors(document, extractors)

    val promise: Promise[List[Table]] = Promise()
    walker.getTables.onSuccess {
      case tables => promise.success(tables.values.toList)
    }
    promise.future
  }
}

object Extractor {
  def fromDocumentAndExtractors(document: Document, extractors: List[TableExtractor]) =
    new Extractor(document, extractors)

  def fromDocumentAndExtractors(document: Document, extractors: TableExtractor*) =
    new Extractor(document, extractors.toList)
}
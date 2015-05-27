package tools.ambitious.pdfextractiontoolkit.extraction

import tools.ambitious.pdfextractiontoolkit.extraction.tableextractors.TableExtractor
import tools.ambitious.pdfextractiontoolkit.model._

class Extractor protected(private val document: Document, private val extractors: List[TableExtractor]) {

  def extractTables: List[Table] = {
    val walker: DocumentWalker = DocumentWalker.toWalk(document)

    extractors.foreach(walker.addListener(_))

    walker.walk()

    extractors.flatMap(_.getTable.orElse(None))
  }
}

object Extractor {
  def fromDocumentAndExtractors(document: Document, extractors: List[TableExtractor]) =
    new Extractor(document, extractors)

  def fromDocumentAndExtractors(document: Document, extractors: TableExtractor*) =
    new Extractor(document, extractors.toList)
}
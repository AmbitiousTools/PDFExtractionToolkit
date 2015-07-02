package tools.ambitious.pdfextractiontoolkit.extraction

import tools.ambitious.pdfextractiontoolkit.extraction.tableextractors.TableExtractor
import tools.ambitious.pdfextractiontoolkit.model._

class Extractor protected(private val document: Document, private val extractors: List[TableExtractor]) {

  def extractTables: List[Table] = {
    val walker: DocumentWalker = DocumentWalker.toWalkWithTableExtractors(document, extractors)

    walker.walk()

    walker.getTables.values.flatten.toList
  }
}

object Extractor {
  def fromDocumentAndExtractors(document: Document, extractors: List[TableExtractor]) =
    new Extractor(document, extractors)

  def fromDocumentAndExtractors(document: Document, extractors: TableExtractor*) =
    new Extractor(document, extractors.toList)
}
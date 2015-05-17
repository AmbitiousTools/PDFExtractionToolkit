package tools.ambitious.pdfextractiontoolkit.extraction

import tools.ambitious.pdfextractiontoolkit.model._

class Extractor protected(private val document: Document, private val extractors: List[DatumExtractor]) {

  def run: List[Table] = {
    val walker: DocumentWalker = DocumentWalker.toWalk(document)

    extractors.foreach(walker.addListener(_))

    walker.walk()

    val extractedTables: List[Table] = extractors.flatMap(_.extractedTables())
    extractedTables
  }
}

object Extractor {
  def fromDocumentAndExtractors(document: Document, extractors: List[DatumExtractor]) =
    new Extractor(document, extractors)

  def fromDocumentAndExtractors(document: Document, extractors: DatumExtractor*) =
    new Extractor(document, extractors.toList)
}
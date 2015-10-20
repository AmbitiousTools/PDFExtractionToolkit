package tools.ambitious.pdfextractiontoolkit.library.extraction

import tools.ambitious.pdfextractiontoolkit.library.extraction.extractionconstraints.ExtractionConstraint
import tools.ambitious.pdfextractiontoolkit.library.model.{Document, Table}

class ExtractionResult protected (private val resultsMap: Map[Document, Map[ExtractionConstraint, Table]]) {

  def getResults(document: Document)(extractionConstraint: ExtractionConstraint): Option[Table] =
    resultsMap(document).get(extractionConstraint)

  def apply(document: Document)(extractionConstraint: ExtractionConstraint): Table =
    resultsMap(document)(extractionConstraint)

}

object ExtractionResult {

  def withResultsMap(resultsMap: Map[Document, Map[ExtractionConstraint, Table]]) = new ExtractionResult(resultsMap)

}

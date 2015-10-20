package tools.ambitious.pdfextractiontoolkit.library.extraction.extractionconstraints

import tools.ambitious.pdfextractiontoolkit.library.extraction.StateBundle
import tools.ambitious.pdfextractiontoolkit.library.model.{Document, Page, Table}

trait ExtractionConstraint {

  def onStart(stateBundle: StateBundle) = {}
  def onPage(page:Page, document:Document, stateBundle: StateBundle)
  def onEnd(stateBundle: StateBundle) = {}

  def tableFromState(stateBundle: StateBundle): Option[Table]

}

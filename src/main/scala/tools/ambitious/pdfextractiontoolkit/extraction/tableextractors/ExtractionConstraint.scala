package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import tools.ambitious.pdfextractiontoolkit.extraction.StateBundle
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page, Table}

trait ExtractionConstraint {

  def onStart(stateBundle: StateBundle) = {}
  def onPage(page:Page, document:Document, stateBundle: StateBundle)
  def onEnd(stateBundle: StateBundle) = {}

  def tableFromState(stateBundle: StateBundle): Option[Table]

}

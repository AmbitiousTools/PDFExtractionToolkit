package tools.ambitious.pdfextractiontoolkit.model.constraints

import tools.ambitious.pdfextractiontoolkit.extraction.DocumentWalkerListener

trait ExtractionCondition extends DocumentWalkerListener {
  def shouldPerformExtraction() :Boolean
}
package tools.ambitious.pdfextractiontoolkit.extraction

import tools.ambitious.pdfextractiontoolkit.model.{Document, Page}

trait DocumentWalkerListener {
  def onStart() = {}
  def onPage(page:Page, document:Document) = {}
  def onEnd() = {}
}

package tools.ambitious.pdfextractiontoolkit.extraction

import tools.ambitious.pdfextractiontoolkit.model.Document

class DocumentWalker protected (val document:Document) {

  var listeners: Set[DocumentWalkerListener] = Set()

  def addListener(listener: DocumentWalkerListener) = listeners += listener

  def walk() = {
    listeners.foreach(_.onStart())
    document.pages.foreach(page => listeners.foreach(_.onPage(page, document)))
    listeners.foreach(_.onEnd())
  }

}

object DocumentWalker {
  def toWalk(document:Document): DocumentWalker = {
    new DocumentWalker(document)
  }
}
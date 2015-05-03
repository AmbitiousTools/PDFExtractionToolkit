package tools.ambitious.pdfextractiontoolkit.model

class Cell(var text: String) {

  def this() = {
    this("")
  }

  def isEmpty: Boolean = text match {
    case "" => true
    case _ => false
  }
}

package tools.ambitious.pdfextractiontoolkit.library.model

class Cell(val text: String = "") {

  def isEmpty: Boolean = text == ""

  override def equals(obj: Any): Boolean =
    obj.isInstanceOf[Cell] && (text == obj.asInstanceOf[Cell].text)
}

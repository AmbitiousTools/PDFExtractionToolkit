package tools.ambitious.pdfextractiontoolkit.library.model.geometry

case class Size protected (width: Double, height: Double) {
  if (width < 0)
    throw new IllegalArgumentException("Width must not be negative")

  if (height < 0)
    throw new IllegalArgumentException("Height must not be negative")

  val area = math.abs(width * height)
}

object Size {
  def fromWidthAndHeight(width:Double, height:Double) = new Size(width, height)
}

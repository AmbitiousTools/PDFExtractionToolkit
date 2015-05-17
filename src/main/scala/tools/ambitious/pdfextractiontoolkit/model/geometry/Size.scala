package tools.ambitious.pdfextractiontoolkit.model.geometry

class Size protected (val width: Double, val height: Double) {
  if (width < 0)
    throw new IllegalArgumentException("Width must not be negative")

  if (height < 0)
    throw new IllegalArgumentException("Height must not be negative")

  val area = math.abs(width * height)
}

object Size {
  def widthAndHeight(width:Double, height:Double) = new Size(width, height)
}

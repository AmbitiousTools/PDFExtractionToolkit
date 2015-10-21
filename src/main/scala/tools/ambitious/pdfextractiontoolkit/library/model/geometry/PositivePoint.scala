package tools.ambitious.pdfextractiontoolkit.library.model.geometry

class PositivePoint protected (override val x: Double, override val y: Double) extends Point(x, y) {
  if (x < 0)
    throw new IllegalArgumentException("x must not be negative")
  if (y < 0)
    throw new IllegalArgumentException("y must not be negative")
}

object PositivePoint {
  def at(x:Double, y:Double) = new PositivePoint(x, y)
}

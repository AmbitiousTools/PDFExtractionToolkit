package tools.ambitious.pdfextractiontoolkit.model.geometry

class PositivePoint(override val x: Double, override val y: Double) extends Point(x, y) {
  if (x < 0)
    throw new IllegalArgumentException("x must not be negative")
  if (y < 0)
    throw new IllegalArgumentException("y must not be negative")
}

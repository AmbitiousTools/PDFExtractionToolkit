package tools.ambitious.pdfextractiontoolkit.model

import tools.ambitious.pdfextractiontoolkit.model.geometry._

class Window(val origin: PositivePoint, val size: Size) {
  def leftCoordinate: Double = origin.x
  def topCoordinate: Double = origin.y
  def rightCoordinate: Double = origin.x + size.width
  def bottomCoordinate: Double = origin.y + size.height
}

object Window {
  def fromAbsoluteCoordinates(x1: Double, y1: Double, x2: Double, y2: Double): Window = {
    val origin: PositivePoint = new PositivePoint(x1, y1)
    val size: Size = new Size(x2-x1, y2-y1)
    new Window(origin, size)
  }
}

package tools.ambitious.pdfextractiontoolkit.model

import tools.ambitious.pdfextractiontoolkit.model.constraints.Constraint
import tools.ambitious.pdfextractiontoolkit.model.geometry._

class Window(val location: PositivePoint, val size: Size) {
  private var _constraints: List[Constraint] = Nil

  def constraints: List[Constraint] = _constraints

  def addConstraint(constraint: Constraint) =
    _constraints = _constraints ++ List(constraint)

  def leftCoordinate: Double = location.x
  def topCoordinate: Double = location.y
  def rightCoordinate: Double = location.x + size.width
  def bottomCoordinate: Double = location.y + size.height
}

object Window {
  def fromAbsoluteCoordinates(x1: Double, y1: Double, x2: Double, y2: Double): Window = {
    val location: PositivePoint = new PositivePoint(x1, y1)
    val size: Size = new Size(x2-x1, y2-y1)
    new Window(location, size)
  }
}

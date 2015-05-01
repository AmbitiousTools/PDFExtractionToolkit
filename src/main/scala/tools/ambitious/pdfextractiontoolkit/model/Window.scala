package tools.ambitious.pdfextractiontoolkit.model

import tools.ambitious.pdfextractiontoolkit.model.constraints.Constraint
import tools.ambitious.pdfextractiontoolkit.model.geometry._

class Window(val location: PositivePoint, val size: Size) {
  private var _constraints: List[Constraint] = Nil

  def constraints: List[Constraint] = _constraints

  def addConstraint(constraint: Constraint) = {
    _constraints = _constraints ++ List(constraint)
  }
}

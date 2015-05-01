package tools.ambitious.pdfextractiontoolkit.model.constraints

import tools.ambitious.pdfextractiontoolkit.model.constraints.types.ConstraintType

abstract class Constraint {
  val constraintType: ConstraintType
}
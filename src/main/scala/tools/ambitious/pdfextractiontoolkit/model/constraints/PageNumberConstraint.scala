package tools.ambitious.pdfextractiontoolkit.model.constraints

import tools.ambitious.pdfextractiontoolkit.model.constraints.types.AnchorConstraintType

class PageNumberConstraint(val pageNumber: Int) extends Constraint {
  if (pageNumber < 1)
    throw new IllegalArgumentException("Page numbers can only be positive numbers.")

  val constraintType = new AnchorConstraintType
}
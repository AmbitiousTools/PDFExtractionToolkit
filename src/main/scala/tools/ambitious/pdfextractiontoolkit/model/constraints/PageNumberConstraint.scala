package tools.ambitious.pdfextractiontoolkit.model.constraints

class PageNumberConstraint(val pageNumber: Int) extends Constraint {
  if (pageNumber < 1)
    throw new IllegalArgumentException("Page numbers can only be positive numbers.")
}
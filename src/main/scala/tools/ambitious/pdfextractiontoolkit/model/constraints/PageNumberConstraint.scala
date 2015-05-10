package tools.ambitious.pdfextractiontoolkit.model.constraints

import tools.ambitious.pdfextractiontoolkit.model.{Page, Document}

class PageNumberConstraint(val pageNumber: Int) extends Constraint with Anchor {
  if (pageNumber < 1)
    throw new IllegalArgumentException("Page numbers can only be positive numbers.")

  def pageFromDocumentAndPreviousPages(document: Document, pages: List[Page]): Page =
    document.getPage(pageNumber)
}
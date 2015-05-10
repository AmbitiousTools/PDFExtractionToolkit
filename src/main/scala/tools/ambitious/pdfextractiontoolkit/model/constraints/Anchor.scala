package tools.ambitious.pdfextractiontoolkit.model.constraints

import tools.ambitious.pdfextractiontoolkit.model.{Document, Page}

trait Anchor {
  def pageFromDocumentAndPreviousPages(document: Document, pages: List[Page]): Page
}

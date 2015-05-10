package tools.ambitious.pdfextractiontoolkit.model.constraints

import tools.ambitious.pdfextractiontoolkit.extraction.Extractor
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page, Window}

class FirstOccurrenceOfStringInWindowConstraint(val text: String, val window: Window) extends Constraint with Anchor {

  def pageFromDocumentAndPreviousPages(document: Document, pages: List[Page]): Page = {
    val pageMaybe: Option[Page] = document.pages.find(page => Extractor.extractTableFromPageUsingWindow(page, window).getCell(1,1).text == text)
    pageMaybe match {
      case Some(page) => page
      case None => throw new Exception("Invalid Constraint: Couldn't find text '" + text + "' in Window " + window + " on any page of document " + document + ".")
    }
  }

}
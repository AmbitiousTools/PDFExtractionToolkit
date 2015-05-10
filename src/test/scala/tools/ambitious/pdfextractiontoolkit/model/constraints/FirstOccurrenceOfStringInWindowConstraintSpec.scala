package tools.ambitious.pdfextractiontoolkit.model.constraints

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.model.geometry.{Size, PositivePoint}
import tools.ambitious.pdfextractiontoolkit.model.{Document, Window}

class FirstOccurrenceOfStringInWindowConstraintSpec extends FreeSpec {
  val simpleTest2Tables2TitleURL = getClass.getResource("/simplePDFs/SimpleTest2Tables1Title.pdf")

  "A FirstOccurrenceOfStringInWindowConstraint with string 'An example Title' and appropriate Window for SimpleTest2Tables1Title.pdf" - {
    val document: Document = Document.fromPDFPath(simpleTest2Tables2TitleURL)

    val constraintWindow: Window = new Window(new PositivePoint(185.38, 165.62), new Size(112.64, 16.16))
    val constraint = new FirstOccurrenceOfStringInWindowConstraint("An example Title", constraintWindow)

    "should return the second page when asked for it" in {
      assert(constraint.pageFromDocumentAndPreviousPages(document, Nil) == document.getPage(2))
    }
  }
}

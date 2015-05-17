package tools.ambitious.pdfextractiontoolkit.model.constraints

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.model.Document
import tools.ambitious.pdfextractiontoolkit.model.geometry.{PositivePoint, Rectangle, Size}

class FirstOccurrenceOfStringExtractionConditionSpec extends FreeSpec {
  val simpleTest2Tables2TitleURL = getClass.getResource("/simplePDFs/SimpleTest2Tables1Title.pdf")

  s"A ${FirstOccurrenceOfStringExtractionCondition.getClass.getSimpleName} with string 'An example Title' and " +
    s"appropriate Window for SimpleTest2Tables1Title.pdf" - {
    val document: Document = Document.fromPDFPath(simpleTest2Tables2TitleURL)

    val constraintWindow = Rectangle.fromCornerAndSize(PositivePoint.at(185.38, 165.62), Size.widthAndHeight(112.64, 16.16))
    val constraint = FirstOccurrenceOfStringExtractionCondition.usingWindowForString(constraintWindow, "An example Title")

    "should suggest extraction for the second page" in {
      constraint.onPage(document.getPage(2), document)
      assert(constraint.shouldPerformExtraction())
    }
  }
}

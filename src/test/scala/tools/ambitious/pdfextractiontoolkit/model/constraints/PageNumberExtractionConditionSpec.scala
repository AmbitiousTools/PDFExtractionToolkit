package tools.ambitious.pdfextractiontoolkit.model.constraints

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.model.Document

class PageNumberExtractionConditionSpec extends FreeSpec {
  val twoPagedDocumentPath = getClass.getResource("/simplePDFs/TwoPagedBlankDocument.pdf")

  s"A ${PageNumberExtractionCondition.getClass.getSimpleName} that is instantiated with page number 1" - {
    val pageNumberConstraint = PageNumberExtractionCondition.atPage(1)

    "should have page number 1" in {
      assert(pageNumberConstraint.pageNumber == 1)
    }
  }

  s"A ${PageNumberExtractionCondition.getClass.getSimpleName} that is instantiated with a page number less than 1" - {
    val instantiatePageNumberConstraint = intercept[IllegalArgumentException] {
      PageNumberExtractionCondition.atPage(0)
    }

    "should throw an IllegalArgumentException" in {
      assert(instantiatePageNumberConstraint.getMessage === "Page numbers can only be positive numbers.")
    }
  }

  s"A ${PageNumberExtractionCondition.getClass.getSimpleName} that is instantiated with page 2 for a two paged document" - {
    val number = 2
    val constraint = PageNumberExtractionCondition.atPage(number)
    val document = Document.fromPDFPath(twoPagedDocumentPath)

    "should suggest extraction for the second page" in {
      constraint.onPage(document.getPage(2), document)
      assert(constraint.shouldPerformExtraction())
    }
  }
}

package tools.ambitious.pdfextractiontoolkit.model.constraints

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.model.Document

class PageNumberConstraintSpec extends FreeSpec {
  val twoPagedDocumentPath = getClass.getResource("/simplePDFs/TwoPagedBlankDocument.pdf")

  "A PageNumberConstraint that is instantiated with page number 1" - {
    val pageNumberConstraint: PageNumberConstraint = new PageNumberConstraint(1)

    "should have page number 1" in {
      assert(pageNumberConstraint.pageNumber == 1)
    }
  }

  "A PageNumberConstraint that is instantiated with a page number less than 1" - {
    val instantiatePageNumberConstraint = intercept[IllegalArgumentException] {
      new PageNumberConstraint(0)
    }

    "should throw an IllegalArgumentException" in {
      assert(instantiatePageNumberConstraint.getMessage === "Page numbers can only be positive numbers.")
    }
  }

  "A PageNumberConstraint that is instantiated with page 2 for a two paged document" - {
    val number = 2
    val constraint = new PageNumberConstraint(number)
    val document = Document.fromPDFPath(twoPagedDocumentPath)

    "should return the second page when asked for it" in {
      assert(constraint.pageFromDocumentAndPreviousPages(document, Nil) == document.getPage(number))
    }
  }
}

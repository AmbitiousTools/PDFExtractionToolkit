package tools.ambitious.pdfextractiontoolkit.model.constraints

import org.scalatest.FreeSpec

class PageNumberConstraintSpec extends FreeSpec {
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
}

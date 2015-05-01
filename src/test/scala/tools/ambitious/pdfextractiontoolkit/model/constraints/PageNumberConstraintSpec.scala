package tools.ambitious.pdfextractiontoolkit.model.constraints

import org.scalatest.FreeSpec
//import tools.ambitious.pdfextractiontoolkit.exceptions.WindowLimitReachedException
//import tools.ambitious.pdfextractiontoolkit.model.Window
import tools.ambitious.pdfextractiontoolkit.model.constraints.types.AnchorConstraintType
import tools.ambitious.pdfextractiontoolkit.model.geometry.{Size, PositivePoint}

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

  "A PageNumberConstraint" - {
    val pageNumberConstraint: PageNumberConstraint = new PageNumberConstraint(1)

    "should be a standalone constraint" in {
      assert(pageNumberConstraint.constraintType.isInstanceOf[AnchorConstraintType])
    }
  }
}

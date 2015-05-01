package tools.ambitious.pdfextractiontoolkit.model

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.model.constraints.PageNumberConstraint
import tools.ambitious.pdfextractiontoolkit.model.geometry.{PositivePoint, Size}

class WindowSpec extends FreeSpec {
  "A Window instantiated with location equal to (0,10) and size equal to (100, 50)" - {
    val location: PositivePoint = new PositivePoint(0, 10)
    val size: Size = new Size(100, 50)

    val window: Window = new Window(location, size)

    "should have location" - {
      ".x equal to 0" in {
        assert(window.location.x == 0)
      }

      ".y equal to 10" in {
        assert(window.location.y == 10)
      }
    }

    "should have size" - {
      ".width equal to 100" in {
        assert(window.size.width == 100)
      }

      ".height equal to 50" in {
        assert(window.size.height == 50)
      }
    }

    "should have a constraints property which is an empty List" in {
      assert(window.constraints.isEmpty)
    }

    "should be able to add constraints" in {
      val pageNumberConstraint: PageNumberConstraint = new PageNumberConstraint(1)
      window.addConstraint(pageNumberConstraint)
    }
  }
}

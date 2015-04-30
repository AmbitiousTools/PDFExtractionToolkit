package tools.ambitious.pdfextractiontoolkit.model.geometry

import org.scalatest.FreeSpec

class SizeSpec extends FreeSpec {

  "A Size instantiated with width equal to 100.0 and height equal to 50.0" - {
    val size: Size = new Size(100.0, 50.0)

    "should have width equal to 100.0" in {
      assert(size.width == 100.0)
    }

    "should have height equal to 50.0" in {
      assert(size.height == 50.0)
    }
  }

}

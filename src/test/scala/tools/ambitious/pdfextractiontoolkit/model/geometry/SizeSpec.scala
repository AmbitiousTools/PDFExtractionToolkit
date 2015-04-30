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

  "A Size instantiated with negative width and positive height should throw an InvalidWidthException" in {
    val instantiateSize = intercept[IllegalArgumentException] {
      new Size(-100, 50)
    }
    assert(instantiateSize.getMessage === "Width must not be negative")
  }

  "A Size instantiated with negative height and positive width should throw an InvalidWidthException" in {
    val instantiateSize = intercept[IllegalArgumentException] {
      new Size(100, -50)
    }
    assert(instantiateSize.getMessage === "Height must not be negative")
  }

}

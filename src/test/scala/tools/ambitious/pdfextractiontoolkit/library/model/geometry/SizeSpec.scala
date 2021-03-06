package tools.ambitious.pdfextractiontoolkit.library.model.geometry

import org.scalatest.FreeSpec

class SizeSpec extends FreeSpec {

  "A Size instantiated with width equal to 100.0 and height equal to 50.0" - {
    val size: Size = Size.fromWidthAndHeight(100.0, 50.0)

    "should have width equal to 100.0" in {
      assert(size.width == 100.0)
    }

    "should have height equal to 50.0" in {
      assert(size.height == 50.0)
    }

    "should have area equal to 5000.0" in {
      assert(size.area == 5000.0)
    }
  }

  "A Size instantiated with negative width and positive height should throw an IllegalArgumentException" in {
    val instantiateSize = intercept[IllegalArgumentException] {
      Size.fromWidthAndHeight(-100, 50)
    }
    assert(instantiateSize.getMessage === "Width must not be negative")
  }

  "A Size instantiated with negative height and positive width should throw an IllegalArgumentException" in {
    val instantiateSize = intercept[IllegalArgumentException] {
      Size.fromWidthAndHeight(100, -50)
    }
    assert(instantiateSize.getMessage === "Height must not be negative")
  }

}

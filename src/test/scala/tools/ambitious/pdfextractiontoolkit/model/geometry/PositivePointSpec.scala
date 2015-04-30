package tools.ambitious.pdfextractiontoolkit.model.geometry

import org.scalatest.FreeSpec

class PositivePointSpec extends FreeSpec {

  "A PositivePoint instantiated with negative x and positive y should throw an IllegalArgumentException" in {
    val instantiatePositivePoint = intercept[IllegalArgumentException] {
      new PositivePoint(-100, 50)
    }
    assert(instantiatePositivePoint.getMessage === "x must not be negative")
  }

  "A PositivePoint instantiated with positive x and negative y should throw an IllegalArgumentException" in {
    val instantiatePositivePoint = intercept[IllegalArgumentException] {
      new PositivePoint(100, -50)
    }
    assert(instantiatePositivePoint.getMessage === "y must not be negative")
  }

}

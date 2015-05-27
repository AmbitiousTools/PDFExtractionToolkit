package tools.ambitious.pdfextractiontoolkit.model.geometry

import org.scalatest.FreeSpec

class PointSpec extends FreeSpec {

  "A Point instantiated with x equal to 100.0 and y equal to 50.0" - {
    val point: Point = Point.at(100.0, 50.0)

    "should have x equal to 100.0" in {
      assert(point.x == 100.0)
    }

    "should have y equal to 50.0" in {
      assert(point.y == 50.0)
    }
  }
 }

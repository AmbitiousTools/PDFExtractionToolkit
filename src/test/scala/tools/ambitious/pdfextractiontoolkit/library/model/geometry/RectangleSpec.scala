package tools.ambitious.pdfextractiontoolkit.library.model.geometry

import org.scalatest.FreeSpec

class RectangleSpec extends FreeSpec {

  "A Rectangle" - {
    "defined by the points [1, 2] and [5, 4]" - {

      val theRectangle = Rectangle.fromCornerCoords(1, 2, 5, 4)

      standardRectangleTest(theRectangle,
        expectedArea = 8,
        expectedWidth = 4,
        expectedHeight = 2,
        expectedTop = 2,
        expectedBottom = 4,
        expectedLeft = 1,
        expectedRight = 5)
    }

    "defined by the points [4, 3] and [1, 2]" - {

      val theRectangle = Rectangle.fromCornerCoords(4, 3, 1, 2)

      standardRectangleTest(theRectangle,
        expectedArea = 3,
        expectedWidth = 3,
        expectedHeight = 1,
        expectedTop = 2,
        expectedBottom = 3,
        expectedLeft = 1,
        expectedRight = 4)

    }
  }

  def standardRectangleTest(rectangle:Rectangle,
                            expectedArea:Double,
                            expectedWidth:Double,
                            expectedHeight:Double,
                            expectedLeft:Double,
                            expectedRight:Double,
                            expectedTop:Double,
                            expectedBottom:Double) = {
    s"should have an area of $expectedArea" in {
      assert(rectangle.size.area == expectedArea)
    }

    s"should have a width of $expectedWidth" in {
      assert(rectangle.size.width == expectedWidth)
    }

    s"should have a height of $expectedHeight" in {
      assert(rectangle.size.height == expectedHeight)
    }

    s"should have a left bound of $expectedLeft" in {
      assert(rectangle.left == expectedLeft)
    }

    s"should have a right bound of $expectedRight" in {
      assert(rectangle.right == expectedRight)
    }

    s"should have a top bound of $expectedTop" in {
      assert(rectangle.top == expectedTop)
    }

    s"should have a bottom bound of $expectedBottom" in {
      assert(rectangle.bottom == expectedBottom)
    }
  }
}

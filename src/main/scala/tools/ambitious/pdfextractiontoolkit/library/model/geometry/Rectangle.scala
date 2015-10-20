package tools.ambitious.pdfextractiontoolkit.library.model.geometry

case class Rectangle protected (topLeft: PositivePoint, bottomRight: PositivePoint) {

  lazy val size:Size = Size.fromWidthAndHeight(math.abs(bottomRight.x - topLeft.x), math.abs(bottomRight.y - topLeft.y))

  lazy val left :Double = math.min(topLeft.x, bottomRight.x)
  lazy val right :Double = math.max(topLeft.x, bottomRight.x)
  lazy val top :Double = math.min(topLeft.y, bottomRight.y)
  lazy val bottom :Double = math.max(topLeft.y, bottomRight.y)
}

object Rectangle {
  def fromCornerCoords(x1:Double, y1:Double, x2:Double, y2:Double): Rectangle = {
    new Rectangle(PositivePoint.at(x1, y1), PositivePoint.at(x2, y2))
  }

  def fromCorners(topLeft: PositivePoint, bottomRight: PositivePoint): Rectangle = {
    new Rectangle(topLeft, bottomRight)
  }

  def fromCornerAndSize(corner: PositivePoint, size: Size): Rectangle = {
    new Rectangle(corner, PositivePoint.at(corner.x + size.width, corner.y + size.height))
  }
}

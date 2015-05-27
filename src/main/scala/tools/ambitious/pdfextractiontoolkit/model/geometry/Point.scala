package tools.ambitious.pdfextractiontoolkit.model.geometry

case class Point protected (x: Double, y: Double)

object Point {
  def at(x:Double, y:Double) = new Point(x, y)
}
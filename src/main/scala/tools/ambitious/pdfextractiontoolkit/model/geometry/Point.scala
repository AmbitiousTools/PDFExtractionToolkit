package tools.ambitious.pdfextractiontoolkit.model.geometry

class Point protected (val x: Double, val y: Double)

object Point {
  def at(x:Double, y:Double) = new Point(x, y)
}
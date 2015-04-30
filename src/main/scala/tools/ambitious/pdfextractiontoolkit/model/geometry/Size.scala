package tools.ambitious.pdfextractiontoolkit.model.geometry

class Size(val width: Double, val height: Double) {
  if (width < 0)
    throw new IllegalArgumentException("Width must not be negative")

  if (height < 0)
    throw new IllegalArgumentException("Height must not be negative")
}

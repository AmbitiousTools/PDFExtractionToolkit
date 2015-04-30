package tools.ambitious.pdfextractiontoolkit.model

class Stencil {
  var windows: List[Window] = Nil

  def addWindow(window: Window) = {
    windows = windows ++ List(window)
  }
}

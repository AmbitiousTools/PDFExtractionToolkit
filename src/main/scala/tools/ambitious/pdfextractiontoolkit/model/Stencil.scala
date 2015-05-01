package tools.ambitious.pdfextractiontoolkit.model

class Stencil {
  private var _windows: List[Window] = Nil

  def windows: List[Window] = _windows

  def addWindow(window: Window) = {
    _windows = _windows ++ List(window)
  }
}

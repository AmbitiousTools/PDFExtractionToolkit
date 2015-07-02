package tools.ambitious.pdfextractiontoolkit.extraction

class StateBundle {
  var state: Option[Any] = None
}

object StateBundle {
  def create: StateBundle = new StateBundle
}
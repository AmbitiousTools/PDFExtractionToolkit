package tools.ambitious.pdfextractiontoolkit.model

import scala.collection.immutable.ListMap

class Stencil(private val windowMap: ListMap[Window, ConstraintTracker]) {
  def windows: List[Window] = windowMap.keysIterator.toList
  def trackerForWindow(window: Window): ConstraintTracker = windowMap(window)
}

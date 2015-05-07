package tools.ambitious.pdfextractiontoolkit.model.constraints

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.model.Window

class FirstOccurrenceOfStringInWindowConstraintSpec extends FreeSpec {
  "A FirstOccurrenceOfStringInWindowConstraint should take as arguments a string and window" in {
    val constraint = new FirstOccurrenceOfStringInWindowConstraint("test", Window.fromAbsoluteCoordinates(0,0,100,100))
  }
}

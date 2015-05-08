package tools.ambitious.pdfextractiontoolkit.model

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.model.constraints.PageNumberConstraint

import scala.collection.immutable.ListMap

class StencilSpec extends FreeSpec {

  "A Stencil with a single window and constraint tracker" - {
    val window: Window = Window.fromAbsoluteCoordinates(0, 0, 100, 100)
    val tracker: ConstraintTracker = new ConstraintTracker(new PageNumberConstraint(1))
    val stencil: Stencil = new Stencil(ListMap(window -> tracker))

    "should have that window as the first element in it's windows property" in {
      assert(stencil.windows.head == window)
    }

    "should have a matching tracker for the window" - {
      assert(stencil.trackerForWindow(window) == tracker)
    }
  }

  "A Stencil with three windows" - {
    val windowA: Window = Window.fromAbsoluteCoordinates(0, 0, 100, 100)
    val windowB: Window = Window.fromAbsoluteCoordinates(0, 0, 150, 150)
    val windowC: Window = Window.fromAbsoluteCoordinates(0, 0, 200, 200)
    val tracker: ConstraintTracker = new ConstraintTracker(new PageNumberConstraint(1))

    val stencil: Stencil = new Stencil(ListMap(windowA -> tracker, windowB -> tracker, windowC -> tracker))

    "should have first window equal to window A" in {
      assert(stencil.windows.head == windowA)
    }

    "should have second window equal to window B" in {
      assert(stencil.windows(1) == windowB)
    }

    "should have third window equal to window C" in {
      assert(stencil.windows(2) == windowC)
    }
  }

  "A Stencil with the same three windows as above, but added in a different order" - {
    val windowA: Window = Window.fromAbsoluteCoordinates(0, 0, 100, 100)
    val windowB: Window = Window.fromAbsoluteCoordinates(0, 0, 150, 150)
    val windowC: Window = Window.fromAbsoluteCoordinates(0, 0, 200, 200)
    val tracker: ConstraintTracker = new ConstraintTracker(new PageNumberConstraint(1))

    val stencil: Stencil = new Stencil(ListMap(windowC -> tracker, windowA -> tracker, windowB -> tracker))

    "should have first window equal to window C" in {
      assert(stencil.windows.head == windowC)
    }

    "should have second window equal to window A" in {
      assert(stencil.windows(1) == windowA)
    }

    "should have third window equal to window B" in {
      assert(stencil.windows(2) == windowB)
    }
  }
}

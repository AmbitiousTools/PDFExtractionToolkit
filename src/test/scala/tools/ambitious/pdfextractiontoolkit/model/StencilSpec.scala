package tools.ambitious.pdfextractiontoolkit.model

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.model.geometry._

class StencilSpec extends FreeSpec {

  "A newly instantiated Stencil" - {
    val stencil: Stencil = new Stencil

    "should have a windows property which is an empty List" in {
      assert(stencil.windows.isEmpty)
    }
  }

  "A Stencil with an added Window" - {
    val stencil: Stencil = new Stencil
    val window: Window = new Window(new PositivePoint(0,0), new Size(100,100))
    stencil.addWindow(window)

    "should have that window as the first element in it's windows property" in {
      assert(stencil.windows.head == window)
    }
  }
}

package tools.ambitious.pdfextractiontoolkit.extraction

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.model.{Page, Document}

import scala.collection.mutable

class DocumentWalkerSpec extends FreeSpec {
  s"A ${DocumentWalker.getClass.getSimpleName} for a two page document" - {
    val document: Document = Document.fromPDFPath(simpleTest2Tables2TitleURL)
    val documentWalker: DocumentWalker = DocumentWalker.toWalk(document)

    "With a listener attached" - {

      object listener extends DocumentWalkerListener {
        val eventsReceived: mutable.MutableList[String] = mutable.MutableList()

        override def onStart(): Unit = eventsReceived += "Start"

        override def onPage(page: Page, document: Document): Unit = eventsReceived += "" + document.pageNumberOf(page).get

        override def onEnd(): Unit = eventsReceived += "End"
      }

      documentWalker.addListener(listener)

      "Should fire the correct events when walk is called" - {
        documentWalker.walk()

        assert(listener.eventsReceived.head === "Start")
        assert(listener.eventsReceived(1) === "1")
        assert(listener.eventsReceived(2) === "2")
        assert(listener.eventsReceived.last === "End")
      }
    }
  }
}

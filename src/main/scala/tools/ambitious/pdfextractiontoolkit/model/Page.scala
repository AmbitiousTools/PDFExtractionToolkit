package tools.ambitious.pdfextractiontoolkit.model

import java.util
import tools.ambitious.pdfextractiontoolkit.model.geometry.Size
import scala.collection.JavaConversions._
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage}

class Page() {
  private var _PDPage: PDPage = new PDPage
  private var _size: Size = new Size(0,0)

  def pDPage: PDPage = _PDPage
  def size: Size = _size

  def asPDDocument: PDDocument = {
    var document: PDDocument = new PDDocument
    document.addPage(this.pDPage)
    document
  }
}

object Page {
  def fromPDPage(pDPage: PDPage): Page = {
    val page = new Page
    page._PDPage = pDPage

    val mediaBox = pDPage.getMediaBox
    page._size = new Size(mediaBox.getWidth, mediaBox.getHeight)

    page
  }

  def listFromPDPageList(pDPages: util.List[_]): List[Page] =
    pDPages.map(pDPage => Page.fromPDPage(pDPage.asInstanceOf[PDPage]))(collection.breakOut)
}
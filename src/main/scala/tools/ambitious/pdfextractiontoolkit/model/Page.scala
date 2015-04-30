package tools.ambitious.pdfextractiontoolkit.model

import java.util
import scala.collection.JavaConversions._

import org.apache.pdfbox.pdmodel.PDPage

class Page() {
  private var rawPDPage: PDPage = new PDPage

  def pDPage: PDPage = rawPDPage
}

object Page {
  def fromPDPage(pDPage: PDPage): Page = {
    val page = new Page
    page.rawPDPage = pDPage
    page
  }

  def listFromPDPageList(pDPages: util.List[_]): List[Page] =
    pDPages.map(pDPage => Page.fromPDPage(pDPage.asInstanceOf[PDPage]))(collection.breakOut)
}
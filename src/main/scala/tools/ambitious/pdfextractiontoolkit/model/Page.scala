package tools.ambitious.pdfextractiontoolkit.model

import org.apache.pdfbox.pdmodel.PDPage

class Page() {
  private var rawPDPage: PDPage = new PDPage
}

object Page {
  def fromPDPage(pDPage: PDPage): Page = {
    var page = new Page
    page.rawPDPage = pDPage
    page
  }
}
package tools.ambitious.pdfextractiontoolkit.model

import java.net.URL
import java.util
import org.apache.pdfbox.pdmodel.PDDocument

class Document {
  private var pages: List[Page] = Nil

  def numberOfPages: Int = pages.length

  def getPage(number: Int): Page = {
    if (number <= numberOfPages && number > 0)
      pages(number-1)
    else
      throw new IllegalArgumentException("Invalid page number.")
  }
}

object Document {
  def fromPDFPath(path: URL): Document = {
    val pDDocument: PDDocument = PDDocument.load(path)
    val allPDPages: util.List[_] = pDDocument.getDocumentCatalog.getAllPages

    val document: Document = new Document
    document.pages = Page.listFromPDPageList(allPDPages)
    document
  }
}

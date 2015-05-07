package tools.ambitious.pdfextractiontoolkit.model

import tools.ambitious.pdfextractiontoolkit.model.geometry.Size
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage}

class Page {
  private var _PDDocument: PDDocument = null
  private var _size: Size = new Size(0,0)

  def size: Size = _size
  def asPDDocument: PDDocument = _PDDocument
  def asPDPage: PDPage = this.asPDDocument.getDocumentCatalog.getAllPages.get(0).asInstanceOf[PDPage]
}

object Page {
  def fromPDDocument(document: PDDocument): Page = {
    if (numberOfPagesInPDDocument(document) != 1)
      throw new IllegalArgumentException("Page constructor fromPDDocument must supply a PDDocument with one page only.")

    val page = new Page
    page._PDDocument = document

    val mediaBox = page.asPDPage.getMediaBox
    page._size = new Size(mediaBox.getWidth, mediaBox.getHeight)

    page
  }

  def listFromSinglePagePDDocuments(documents: List[PDDocument]): List[Page] =
    documents.map(document => Page.fromPDDocument(document))

  private def numberOfPagesInPDDocument(document: PDDocument): Int =
    document.getDocumentCatalog.getAllPages.size
}
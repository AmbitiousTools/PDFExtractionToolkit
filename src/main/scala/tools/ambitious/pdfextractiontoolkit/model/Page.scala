package tools.ambitious.pdfextractiontoolkit.model

import tools.ambitious.pdfextractiontoolkit.model.geometry.Size
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage}

class Page protected (private val document: PDDocument) {

  val asPDPage: PDPage = this.asPDDocument.getDocumentCatalog.getAllPages.get(0).asInstanceOf[PDPage]

  private val mediaBox = asPDPage.getMediaBox
  val size: Size = Size.fromWidthAndHeight(mediaBox.getWidth, mediaBox.getHeight)

  def asPDDocument: PDDocument = document
}

object Page {
  def fromPDDocument(document: PDDocument): Page = {
    if (numberOfPagesInPDDocument(document) != 1)
      throw new IllegalArgumentException("Page constructor fromPDDocument must supply a PDDocument with one page only.")

    new Page(document)
  }

  def listFromSinglePagePDDocuments(documents: List[PDDocument]): List[Page] =
    documents.map(document => Page.fromPDDocument(document))

  private def numberOfPagesInPDDocument(document: PDDocument): Int =
    document.getDocumentCatalog.getAllPages.size
}
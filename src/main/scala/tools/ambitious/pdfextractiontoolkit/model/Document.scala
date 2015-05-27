package tools.ambitious.pdfextractiontoolkit.model

import java.net.URL
import java.util
import org.apache.pdfbox.pdmodel.font._
import org.apache.pdfbox.pdmodel.{PDPage, PDDocument}
import org.apache.pdfbox.util.Splitter

import scala.collection.JavaConverters._

class Document private (private val pDDocument: PDDocument, val pages: List[Page]) {
  def numberOfPages: Int = pages.length

  def getPage(number: Int): Page = {
    if (number <= numberOfPages && number > 0)
      pages(number-1)
    else
      throw new IllegalArgumentException("Invalid page number.")
  }

  def close() = pDDocument.close()

  def pageNumberOf(page: Page): Option[Int] =
    Option.apply(pages.indexOf(page))
      .filter(_ >= 0)
      .map(_ + 1)
}

object Document {
  def fromPDFPath(path: URL): Document = {
    val pDDocument: PDDocument = PDDocument.load(path)

    val splitPDDocuments: List[PDDocument] = Document.splitPDDocumentIntoPDDocumentForEachPage(pDDocument)
    val pages = Page.listFromSinglePagePDDocuments(splitPDDocuments)

    new Document(pDDocument, pages)
  }

  def splitPDDocumentIntoPDDocumentForEachPage(document: PDDocument): List[PDDocument] = {
    val splitter: Splitter = new Splitter
    val splitPages: List[PDDocument] = splitter.split(document).asScala.toList

    // There is a bug in PDFBox Splitter that doesn't set fonts from the old
    // document to each new document. This manifests itself as a NullPointerException
    // when Tabula tries to extract a page. The following fixes that bug.
    setFontsFromMasterPDDocumentToSplitPDDocuments(document, splitPages)

    splitPages
  }

  private def setFontsFromMasterPDDocumentToSplitPDDocuments(masterPDDocument: PDDocument, splitPDDocuments: List[PDDocument]) = {
    for (i <- 0 until masterPDDocument.getNumberOfPages) {
      val masterPage: PDPage = masterPDDocument.getDocumentCatalog.getAllPages.get(i).asInstanceOf[PDPage]
      val splitPage: PDPage = splitPDDocuments(i).getDocumentCatalog.getAllPages.get(0).asInstanceOf[PDPage]

      val fontsFromMasterPage: util.Map[String, PDFont] = masterPage.getResources.getFonts

      splitPage.getResources.setFonts(fontsFromMasterPage)
    }
  }
}

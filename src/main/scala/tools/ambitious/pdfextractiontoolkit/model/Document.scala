package tools.ambitious.pdfextractiontoolkit.model

import java.net.URL
import java.util
import org.apache.pdfbox.pdmodel.font._
import org.apache.pdfbox.pdmodel.{PDPage, PDDocument}
import org.apache.pdfbox.util.Splitter

import scala.collection.JavaConverters._

class Document {
  private var pages: List[Page] = Nil
  private var _tables: List[Table] = Nil
  private var _PDDocument: PDDocument = null

  def numberOfPages: Int = pages.length

  def getPage(number: Int): Page = {
    if (number <= numberOfPages && number > 0)
      pages(number-1)
    else
      throw new IllegalArgumentException("Invalid page number.")
  }

  def addTable(table: Table) = {
    _tables = _tables ++ List(table)
  }

  def tables: List[Table] = _tables

  def close() = {
    _PDDocument.close()
    _PDDocument = null
  }
}

object Document {
  def fromPDFPath(path: URL): Document = {
    val pDDocument: PDDocument = PDDocument.load(path)
    val splitPDDocuments: List[PDDocument] = Document.splitPDDocumentIntoPDDocumentForEachPage(pDDocument)

    val document: Document = new Document
    document._PDDocument = pDDocument
    document.pages = Page.listFromSinglePagePDDocuments(splitPDDocuments)
    document
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

package tools.ambitious.pdfextractiontoolkit.model

import java.net.URL
import java.util
import org.apache.pdfbox.pdmodel.{PDPage, PDDocument}
import scala.collection.JavaConversions._

class Document {
  private var pages: List[Page] = Nil

  def numberOfPages: Int = pages.length

}

object Document {
  def fromPDFPath(path: URL): Document = {
    val pDDocument: PDDocument = PDDocument.load(path)
    val allPDPages: util.List[_] = pDDocument.getDocumentCatalog.getAllPages

    var document: Document = new Document
    document.pages = convertPDPageListToPageList(allPDPages)
    document
  }

  private def convertPDPageListToPageList(pDPages: util.List[_]): List[Page] =
    pDPages.map(pDPage => Page.fromPDPage(pDPage.asInstanceOf[PDPage]))(collection.breakOut)
}

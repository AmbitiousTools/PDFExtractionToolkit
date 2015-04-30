package tools.ambitious.pdfextractiontoolkit.model

import java.util

import org.apache.pdfbox.pdmodel.{PDPage, PDDocument}
import org.scalatest.FlatSpec

class PageSpec extends FlatSpec {
  val samplePDFPath = getClass.getResource("/simplePDFs/SimpleTest1Table.pdf")

  "Page" should "instantiate from PDPage" in {
    val pdDocument: PDDocument = PDDocument.load(samplePDFPath)
    val allPages: util.List[_] = pdDocument.getDocumentCatalog.getAllPages
    val firstPage: PDPage = allPages.get(0).asInstanceOf[PDPage]

    var page: Page = Page.fromPDPage(firstPage)
  }
}

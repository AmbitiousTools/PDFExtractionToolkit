package tools.ambitious.pdfextractiontoolkit.model

import org.apache.pdfbox.pdmodel.{PDPage, PDDocument}
import org.scalatest.FreeSpec

class PageSpec extends FreeSpec {
  val samplePDFPath = getClass.getResource("/simplePDFs/SimpleTest1Table.pdf")
  val twoPagedBlankDocumentPath = getClass.getResource("/simplePDFs/TwoPagedBlankDocument.pdf")

  "In the context of a PDDocument with two pages (TwoPagedBlankDocument.pdf)" - {
    val document: PDDocument = PDDocument.load(twoPagedBlankDocumentPath)

    "An IllegalArgumentException should be thrown when trying to call the constructor Page.fromPDDocument" in {
      val interceptException = intercept[IllegalArgumentException] {
        val page: Page = Page.fromPDDocument(document)
      }

      assert(interceptException.getMessage === "Page constructor fromPDDocument must supply a PDDocument with one page only.")
    }
  }

  "A Page should instantiate from a PDDocument with one page" in {
    val document: PDDocument = PDDocument.load(samplePDFPath)
    val page: Page = Page.fromPDDocument(document)
  }

  "A Page" - {
    val document: PDDocument = PDDocument.load(samplePDFPath)
    val page: Page = Page.fromPDDocument(document)

    "should have width equal to the document's PDPage width" in {
      val pDPage: PDPage = document.getDocumentCatalog.getAllPages.get(0).asInstanceOf[PDPage]
      assert(page.size.width == pDPage.getMediaBox.getWidth)
    }

    "should have height equal to the document's PDPage height" in {
      val pDPage: PDPage = document.getDocumentCatalog.getAllPages.get(0).asInstanceOf[PDPage]
      assert(page.size.height == pDPage.getMediaBox.getHeight)
    }
  }

  "A Page instantiated from a PDDocument with one page" - {
    val document: PDDocument = PDDocument.load(samplePDFPath)
    val page: Page = Page.fromPDDocument(document)

    "should return the same PDDocument" in {
      assert(page.asPDDocument == document)
    }
  }
}

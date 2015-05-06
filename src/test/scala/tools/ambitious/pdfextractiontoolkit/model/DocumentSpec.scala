package tools.ambitious.pdfextractiontoolkit.model

import org.apache.pdfbox.pdmodel.PDDocument
import org.scalatest.FreeSpec

class DocumentSpec extends FreeSpec {
  val samplePDFPath = getClass.getResource("/simplePDFs/SimpleTest1Table.pdf")
  val twoPagedDocumentPath = getClass.getResource("/simplePDFs/TwoPagedBlankDocument.pdf")

  "A Document should instantiate from PDF" in {
    val document = Document.fromPDFPath(samplePDFPath)
  }

  "A Document instantiated with SimpleTest1Table.pdf" - {
    val document = Document.fromPDFPath(samplePDFPath)

    "should have one page" in {
      assert(document.numberOfPages == 1)
    }
  }

  "A Document instantiated with TwoPagedBlankDocument.pdf" - {
    val document = Document.fromPDFPath(twoPagedDocumentPath)

    "should have two pages" in {
      assert(document.numberOfPages == 2)
    }

    "should be able to get the first page" in {
      val page: Page = document.getPage(1)
    }

    "should be able to get the second page" in {
      val page: Page = document.getPage(2)
    }

    "should throw an IllegalArgumentException when trying to get the third page" in {
      val interceptException = intercept[IllegalArgumentException] {
        val page: Page = document.getPage(3)
      }

      assert(interceptException.getMessage === "Invalid page number.")
    }

    "should be able to add a table and have it in it's tables property" in {
      val table: Table = new Table
      document.addTable(table)
      assert(document.tables.head == table)
    }
  }

  "A List of PDDocuments split from TwoPagedBlankDocument.pdf" - {
    val document: PDDocument = PDDocument.load(twoPagedDocumentPath)
    val pages: List[PDDocument] = Document.splitPDDocumentIntoPDDocumentForEachPage(document)

    "should have only one page in each PDDocument" in {
      for (pDDocument: PDDocument <- pages)
        assert(pDDocument.getDocumentCatalog.getAllPages.size == 1)
    }
  }
}

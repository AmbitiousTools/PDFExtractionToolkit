package tools.ambitious.pdfextractiontoolkit.model

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
  }
}

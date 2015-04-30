package tools.ambitious.pdfextractiontoolkit.model

import org.scalatest.FlatSpec

class DocumentSpec extends FlatSpec {
  val samplePDFPath = getClass.getResource("/simplePDFs/SimpleTest1Table.pdf")

  "Document" should "instantiate from PDF" in {
    val document = Document.fromPDFPath(samplePDFPath)
  }

  "A Document instantiated with SimpleTest1Table.pdf" should "have one page" in {
    val document = Document.fromPDFPath(samplePDFPath)
    assert(document.numberOfPages == 1)
  }

  "A Document instantiated with TwoPagedBlankDocument.pdf" should "have two pages" in {
    val twoPagedDocumentPath = getClass.getResource("/simplePDFs/TwoPagedBlankDocument.pdf")
    val document = Document.fromPDFPath(twoPagedDocumentPath)
    assert(document.numberOfPages == 2)
  }
}

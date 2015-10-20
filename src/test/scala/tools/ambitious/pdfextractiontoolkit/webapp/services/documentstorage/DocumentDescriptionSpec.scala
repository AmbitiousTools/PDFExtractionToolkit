package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import org.scalatest.FreeSpec

class DocumentDescriptionSpec extends FreeSpec {

  s"a ${DocumentDescription.getClass.getSimpleName}" - {

    "can be created with a title 'testDoc'" in {
      val docDescription: DocumentDescription = DocumentDescription.withTitle("testDoc")

      assert(docDescription.title == "testDoc")
    }

    "can be created with a title 'testDoc' and a description 'a test document'" in {
      val docDescription: DocumentDescription =
        DocumentDescription.withTitleAndDescription("testDoc", "a test document")

      assert(docDescription.title == "testDoc")
    }
  }
}

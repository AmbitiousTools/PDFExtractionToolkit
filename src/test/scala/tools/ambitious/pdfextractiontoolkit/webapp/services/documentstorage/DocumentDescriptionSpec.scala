package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import org.scalatest.FreeSpec
import spray.http.MediaType

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
      assert(docDescription.description.contains("a test document"))
    }

    "can be created with a title 'testDoc' and media type 'application/pdf'" in {
      val mediaType: MediaType = MediaType.custom("application/pdf")

      val docDescription: DocumentDescription =
        DocumentDescription.withTitleAndMediaType("testDoc", mediaType)

      assert(docDescription.title == "testDoc")
      assert(docDescription.mediaType == mediaType)
    }

    "can be created with a title 'testDoc' and a description 'a test document' and media type 'application/pdf'" in {
      val mediaType: MediaType = MediaType.custom("application/pdf")

      val docDescription: DocumentDescription =
        DocumentDescription.withTitleAndDescriptionAndMediaType("testDoc", "a test document", mediaType)

      assert(docDescription.title == "testDoc")
      assert(docDescription.description.contains("a test document"))
      assert(docDescription.mediaType == mediaType)
    }
  }
}

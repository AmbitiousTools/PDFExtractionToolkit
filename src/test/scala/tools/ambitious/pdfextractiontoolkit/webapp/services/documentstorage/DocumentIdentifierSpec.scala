package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import org.scalatest.FreeSpec

class DocumentIdentifierSpec extends FreeSpec {

  s"a ${DocumentIdentifier.getClass.getSimpleName}" - {

    "created with a hash and a description" - {
      val hash: List[Byte] = List(0x42, 0x5a).map(_.toByte)
      val description: DocumentDescription = DocumentDescription.withTitle("testDoc")
      
      val docID: DocumentIdentifier = DocumentIdentifier.withHashAndDescription(hash, description)
      
      "has that hash and description" in {
        assert(docID.hash == hash)
        assert(docID.description == description)
      }
      
    }

  }

}

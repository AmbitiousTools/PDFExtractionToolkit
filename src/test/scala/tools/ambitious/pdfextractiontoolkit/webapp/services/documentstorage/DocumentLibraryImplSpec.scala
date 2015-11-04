package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.net.URL

import org.scalamock.scalatest.MockFactory
import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.Resources

class DocumentLibraryImplSpec extends FreeSpec with MockFactory {

  s"a ${DocumentLibraryImpl.getClass.getSimpleName}" - {
    val mockFileStore: DocumentFileStore = mock[DocumentFileStore]
    val stubDao: DocumentInformationDao = stub[DocumentInformationDao]

    val documentLibrary: DocumentLibraryImpl = new DocumentLibraryImpl(mockFileStore, stubDao)

    val description: DocumentDescription = DocumentDescription.withTitle("testTitle")
    val source: URL = Resources.quickBrownFoxTxt

    val expectedHash: String = "58B433FA7E8B0F94B2FF02178E7768F5A329EF346D908C7B917824E5A4CA9575"

    val expectedID: DocumentIdentifier = DocumentIdentifier.withHashAndDescription(expectedHash, description)

    "will store a document when store is called" in {

      (mockFileStore.storeFileFor _).expects(expectedID, source)

      documentLibrary.store(description, source)

      (stubDao.storeDocumentID _).verify(expectedID)
    }

    "will retrieve a document when retrieve is called" in {
      (mockFileStore.retrieveFileFor _).expects(expectedID).returns(source)

      assert(documentLibrary.retrieve(expectedID) == source)
    }

    "will delete a document when delete is called" in {
      (mockFileStore.deleteFileFor _).expects(expectedID)

      documentLibrary.delete(expectedID)

      (stubDao.deleteDocumentID _).verify(expectedID)
    }

    "will list all documents when list is called" in {

      documentLibrary.list()

      (stubDao.retrieveAllIDs _).verify()
    }
  }

}

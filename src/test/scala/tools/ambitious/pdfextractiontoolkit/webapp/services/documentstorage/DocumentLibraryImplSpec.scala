package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import org.scalamock.scalatest.MockFactory
import org.scalatest.FreeSpec

import scala.io.Source

class DocumentLibraryImplSpec extends FreeSpec with MockFactory {

  s"a ${DocumentLibraryImpl.getClass.getSimpleName}" - {
    val mockFileStore: DocumentFileStore = mock[DocumentFileStore]
    val stubDao: DocumentInformationDao = stub[DocumentInformationDao]

    val documentLibrary: DocumentLibraryImpl = new DocumentLibraryImpl(mockFileStore, stubDao)

    val description: DocumentDescription = DocumentDescription.withTitle("testTitle")
    val source: Source = Source.fromChar('x')

    val expectedHash: String = "2D711642B726B04401627CA9FBAC32F5C8530FB1903CC4DB02258717921A4881"

    val expectedID: DocumentIdentifier = DocumentIdentifier.withHashAndDescription(expectedHash, description)

    "will store a document when put is called" in {

      (mockFileStore.storeFileFor _).expects(expectedID, source)

      documentLibrary.store(description, source)

      (stubDao.storeDocumentID _).verify(expectedID)
    }

    "will retrieve a document when get is called" in {
      (mockFileStore.retrieveFileFor _).expects(expectedID).returns(source)

      assert(documentLibrary.retrieve(expectedID) sameElements source)
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

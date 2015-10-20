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

    val expectedHash: List[Byte] = List(
      45, 113, 22, 66, -73, 38, -80, 68, 1, 98, 124, -87, -5, -84, 50,
      -11, -56, 83, 15, -79, -112, 60, -60, -37, 2, 37, -121, 23, -110,
      26, 72, -127
    ).map(_.toByte)

    val expectedID: DocumentIdentifier = DocumentIdentifier.withHashAndDescription(expectedHash, description)

    "will store a document when put is called" in {

      (mockFileStore.put _).expects(expectedID, source)

      documentLibrary.put(description, source)

      (stubDao.recordDocumentStored _).verify(expectedID)
    }

    "will retrieve a document when get is called" in {
      (mockFileStore.get _).expects(expectedID).returns(source)

      documentLibrary.get(expectedID)
    }

    "will delete a document when delete is called" in {
      (mockFileStore.delete _).expects(expectedID)

      documentLibrary.delete(expectedID)

      (stubDao.recordDocumentDeleted _).verify(expectedID)
    }

    "will list all documents when list is called" in {

      documentLibrary.list()

      (stubDao.retrieveAllIDs _).verify()
    }
  }

}

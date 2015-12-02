package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.net.URL

import org.scalamock.scalatest.MockFactory
import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.Resources
import tools.ambitious.pdfextractiontoolkit.webapp.data.DocumentInformationDao

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class DocumentLibraryImplSpec extends FreeSpec with MockFactory {

  s"a ${DocumentLibraryImpl.getClass.getSimpleName}" - {
    val mockFileStore: DocumentFileStore = mock[DocumentFileStore]
    val mockDao: DocumentInformationDao = mock[DocumentInformationDao]

    val documentLibrary: DocumentLibraryImpl = new DocumentLibraryImpl(mockFileStore, mockDao)

    val description: DocumentDescription = DocumentDescription.withTitle("testTitle")
    val source: URL = Resources.quickBrownFoxTxt

    val expectedHash: String = "58B433FA7E8B0F94B2FF02178E7768F5A329EF346D908C7B917824E5A4CA9575"

    val expectedID: DocumentIdentifier = DocumentIdentifier.withHashAndDescription(expectedHash, description)

    "will store a document when store is called" in {

      (mockFileStore.storeFileFor _).expects(expectedID, source)
      (mockDao.storeDocumentID _).expects(expectedID).returns(Future(Unit))

      Await.result(documentLibrary.store(description, source), 30.seconds)
    }

    "will retrieve a document when retrieve is called" in {
      (mockFileStore.retrieveFileFor _).expects(expectedID).returns(Future(source))

      val retrievedDocument = Await.result(documentLibrary.retrieve(expectedID), 30.seconds)
      assert(retrievedDocument == source)
    }

    "will delete a document when delete is called" in {
      (mockFileStore.deleteFileFor _).expects(expectedID)
      (mockDao.deleteDocumentID _).expects(expectedID).returns(Future(Unit))

      Await.result(documentLibrary.delete(expectedID), 30.seconds)
    }

    "will list all documents when list is called" in {
      (mockDao.retrieveAllIDs _).expects().returns(Future(Seq()))
      Await.result(documentLibrary.list(), 30.seconds)
    }
  }

}

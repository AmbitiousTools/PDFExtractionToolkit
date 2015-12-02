package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.net.URL

import tools.ambitious.pdfextractiontoolkit.utils.AmbitiousIoUtils._
import tools.ambitious.pdfextractiontoolkit.webapp.data.DocumentInformationDao

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

private class DocumentLibraryImpl (val fileStore: DocumentFileStore,
                                   val dao: DocumentInformationDao) extends DocumentLibrary {

  override def store(description: DocumentDescription, documentSource: URL): Future[DocumentIdentifier] = {
    val bytes: Array[Byte] = documentSource.toBytes

    val docID: DocumentIdentifier = DocumentIdentifier.computeFor(description, bytes)

    fileStore.storeFileFor(docID, documentSource)

    dao.storeDocumentID(docID)
      .flatMap(_ => Future(docID))
  }

  override def retrieve(docID: DocumentIdentifier): Future[URL] = fileStore.retrieveFileFor(docID)

  override def delete(docID: DocumentIdentifier): Future[Unit] = {
    fileStore.deleteFileFor(docID)
    dao.deleteDocumentID(docID)
  }

  override def list(): Future[Seq[DocumentIdentifier]] = dao.retrieveAllIDs()

}

object DocumentLibraryImpl {
}
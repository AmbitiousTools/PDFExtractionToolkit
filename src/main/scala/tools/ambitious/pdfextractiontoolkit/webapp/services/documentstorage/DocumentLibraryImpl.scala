package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.net.URL

import tools.ambitious.pdfextractiontoolkit.utils.AmbitiousIoUtils._

private class DocumentLibraryImpl (val fileStore: DocumentFileStore,
                                   val dao: DocumentInformationDao) extends DocumentLibrary {

  override def store(description: DocumentDescription, documentSource: URL): DocumentIdentifier = {
    val bytes: Array[Byte] = documentSource.toBytes

    val docID: DocumentIdentifier = DocumentIdentifier.computeFor(description, bytes)

    fileStore.storeFileFor(docID, documentSource)
    dao.storeDocumentID(docID)

    docID
  }

  override def retrieve(docID: DocumentIdentifier): URL = fileStore.retrieveFileFor(docID)

  override def delete(docID: DocumentIdentifier): Unit = {
    fileStore.deleteFileFor(docID)
    dao.deleteDocumentID(docID)
  }

  override def list(): Seq[DocumentIdentifier] = dao.retrieveAllIDs()

}

object DocumentLibraryImpl {
}
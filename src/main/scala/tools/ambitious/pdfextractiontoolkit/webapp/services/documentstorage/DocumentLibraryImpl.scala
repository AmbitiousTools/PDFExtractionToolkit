package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import scala.io.Source

private class DocumentLibraryImpl (val fileStore: DocumentFileStore,
                                   val dao: DocumentInformationDao) extends DocumentLibrary {

  override def store(description: DocumentDescription, documentSource: Source): DocumentIdentifier = {
    val docID: DocumentIdentifier = DocumentIdentifier.computeFor(description, documentSource)

    fileStore.storeFileFor(docID, documentSource)
    dao.storeDocumentID(docID)

    docID
  }

  override def retrieve(docID: DocumentIdentifier): Source = fileStore.retrieveFileFor(docID)

  override def delete(docID: DocumentIdentifier): Unit = {
    fileStore.deleteFileFor(docID)
    dao.deleteDocumentID(docID)
  }

  override def list(): Seq[DocumentIdentifier] = dao.retrieveAllIDs()

}

object DocumentLibraryImpl {
}
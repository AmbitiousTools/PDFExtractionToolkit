package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import scala.io.Source

private class DocumentLibraryImpl (val fileStore: DocumentFileStore,
                                   val dao: DocumentInformationDao) extends DocumentLibrary {

  override def put(description: DocumentDescription, documentSource: Source): DocumentIdentifier = {
    val docID: DocumentIdentifier = DocumentIdentifier.computeFor(description, documentSource)

    fileStore.put(docID, documentSource)
    dao.recordDocumentStored(docID)

    docID
  }

  override def get(docID: DocumentIdentifier): Source = fileStore.get(docID)

  override def delete(docID: DocumentIdentifier): Unit = {
    fileStore.delete(docID)
    dao.recordDocumentDeleted(docID)
  }

  override def list(): Seq[DocumentIdentifier] = dao.retrieveAllIDs()

}

object DocumentLibraryImpl {
}
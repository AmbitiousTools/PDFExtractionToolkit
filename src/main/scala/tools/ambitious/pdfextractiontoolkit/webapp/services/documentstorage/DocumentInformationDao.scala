package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

private trait DocumentInformationDao {
  def recordDocumentStored(docID: DocumentIdentifier): Unit

  def recordDocumentDeleted(docID: DocumentIdentifier): Unit

  def retrieveAllIDs(): Seq[DocumentIdentifier]
}

package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

private trait DocumentInformationDao {
  def storeDocumentID(docID: DocumentIdentifier): Unit

  def deleteDocumentID(docID: DocumentIdentifier): Unit

  def retrieveAllIDs(): Seq[DocumentIdentifier]
}

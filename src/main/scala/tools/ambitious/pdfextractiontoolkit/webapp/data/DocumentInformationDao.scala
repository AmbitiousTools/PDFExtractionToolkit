package tools.ambitious.pdfextractiontoolkit.webapp.data

import tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage.DocumentIdentifier

import scala.concurrent.Future

private[webapp] trait DocumentInformationDao {
  def storeDocumentID(docID: DocumentIdentifier): Future[Unit]

  def deleteDocumentID(docID: DocumentIdentifier): Future[Unit]

  def retrieveAllIDs(): Future[Seq[DocumentIdentifier]]
}

private[webapp] object DocumentInformationDao {
  def forRootDao(rootDao: RootDAO): DocumentInformationDao = new DocumentInformationDaoImpl(rootDao)
}
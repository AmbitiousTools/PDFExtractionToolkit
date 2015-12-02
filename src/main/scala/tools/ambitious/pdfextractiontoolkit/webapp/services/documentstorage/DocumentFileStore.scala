package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.net.URL

import scala.concurrent.Future

private trait DocumentFileStore {

  def storeFileFor(docID: DocumentIdentifier, documentSource: URL): Future[Unit]

  def retrieveFileFor(docID: DocumentIdentifier): Future[URL]

  def deleteFileFor(docID: DocumentIdentifier): Future[Unit]

}

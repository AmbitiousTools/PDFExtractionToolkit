package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.net.URL

import scala.concurrent.Future

/**
 * A globally available interface for manipulating the applications stored set of Documents.
 */
trait DocumentLibrary {

  def store(description: DocumentDescription, documentSource: URL): Future[DocumentIdentifier]

  def retrieve(docID: DocumentIdentifier): Future[URL]

  def delete(docID: DocumentIdentifier): Future[Unit]

  def list(): Future[Seq[DocumentIdentifier]]

}

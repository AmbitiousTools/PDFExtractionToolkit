package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import scala.io.Source

private trait DocumentFileStore {

  def storeFileFor(docID: DocumentIdentifier, source: Source)

  def retrieveFileFor(docID: DocumentIdentifier): Source

  def deleteFileFor(docID: DocumentIdentifier)

}

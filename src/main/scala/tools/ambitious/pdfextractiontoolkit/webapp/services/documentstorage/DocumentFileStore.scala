package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.net.URL

private trait DocumentFileStore {

  def storeFileFor(docID: DocumentIdentifier, documentSource: URL)

  def retrieveFileFor(docID: DocumentIdentifier): URL

  def deleteFileFor(docID: DocumentIdentifier)

}

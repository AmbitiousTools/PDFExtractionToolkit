package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.net.URL

/**
 * A globally available interface for manipulating the applications stored set of Documents.
 */
trait DocumentLibrary {

  def store(description: DocumentDescription, documentSource: URL): DocumentIdentifier

  def retrieve(docID: DocumentIdentifier): URL

  def delete(docID: DocumentIdentifier)

  def list(): Seq[DocumentIdentifier]

}

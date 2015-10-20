package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import scala.io.Source

/**
 * A globally available interface for manipulating the applications stored set of Documents.
 */
trait DocumentLibrary {

  def put(description: DocumentDescription, documentSource: Source): DocumentIdentifier

  def get(docID: DocumentIdentifier): Source

  def delete(docID: DocumentIdentifier)

  def list(): Seq[DocumentIdentifier]

}

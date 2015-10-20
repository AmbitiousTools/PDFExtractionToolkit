package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import scala.io.Source

private trait DocumentFileStore {

  def put(docID: DocumentIdentifier, source: Source)

  def get(docID: DocumentIdentifier): Source

  def delete(docID: DocumentIdentifier)

}

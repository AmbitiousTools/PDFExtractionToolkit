package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import tools.ambitious.pdfextractiontoolkit.utils.AmbitiousIoUtils.ByteArrayUtils

case class DocumentIdentifier protected(hash: String,
                                        description: DocumentDescription) {
}

object DocumentIdentifier {

  def computeFor(documentDescription: DocumentDescription, rawBytes: Array[Byte]): DocumentIdentifier = {
    val hash: String = rawBytes.computeHashAsHex

    withHashAndDescription(hash, documentDescription)
  }

  def withHashAndDescription(hash: String, description: DocumentDescription): DocumentIdentifier =
    new DocumentIdentifier(hash, description)

}
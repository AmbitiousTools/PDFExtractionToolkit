package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.security.MessageDigest

import scala.io.Source

case class DocumentIdentifier protected(hash: String,
                                        description: DocumentDescription) {
}

object DocumentIdentifier {

  def computeFor(documentDescription: DocumentDescription, documentSource: Source): DocumentIdentifier = {
    val hash: String = computeHashAsHex(documentSource)

    withHashAndDescription(hash, documentDescription)
  }

  // TODO make utility method
  def computeHashAsHex(source: Source): String = {
    digest.digest(source.reset().map(_.toByte).toArray)
      .map("%02X" format _)
      .mkString
  }

  private val digest: MessageDigest = MessageDigest.getInstance("SHA-256")

  def withHashAndDescription(hash: String, description: DocumentDescription): DocumentIdentifier =
    new DocumentIdentifier(hash, description)

}
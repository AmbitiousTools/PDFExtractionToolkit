package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.security.MessageDigest

import scala.io.Source

case class DocumentIdentifier protected(hash: List[Byte],
                                        description: DocumentDescription) {
}

object DocumentIdentifier {

  def computeFor(documentDescription: DocumentDescription, documentSource: Source): DocumentIdentifier = {
    val hash: Array[Byte] = computeHash(documentSource)

    withHashAndDescription(hash, documentDescription)
  }

  // TODO make utility method
  def computeHash(source: Source): Array[Byte] = digest.digest(source.map(_.toByte).toArray)

  private val digest: MessageDigest = MessageDigest.getInstance("SHA-256")

  def withHashAndDescription(hash: Iterable[Byte], description: DocumentDescription): DocumentIdentifier =
    new DocumentIdentifier(hash.toList, description)
}
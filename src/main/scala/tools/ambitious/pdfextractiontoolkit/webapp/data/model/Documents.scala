package tools.ambitious.pdfextractiontoolkit.webapp.data.model

import slick.driver.SQLiteDriver.api._
import spray.http.MediaType
import tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage.{DocumentDescription, DocumentIdentifier}

case class Document(documentID: Option[Long], hash: String, title: String, description: String, mediaType: String) {

  lazy val asDocumentIdentifier: DocumentIdentifier = {
    val parsedMediaType: MediaType = MediaType.custom(mediaType)
    val documentDescription = DocumentDescription.withTitleAndDescriptionAndMediaType(title, description, parsedMediaType)

    DocumentIdentifier.withHashAndDescription(hash, documentDescription)
  }
}

class Documents(tag: Tag) extends Table[Document](tag, "Documents") {
  def documentID = column[Long]("documentID", O.PrimaryKey, O.AutoInc)
  def hash = column[String]("hash")
  def title = column[String]("title")
  def description = column[String]("description")
  def mediaType = column[String]("mediaType")

  def * = (documentID.?, hash, title, description, mediaType) <> (Document.tupled, Document.unapply)
}

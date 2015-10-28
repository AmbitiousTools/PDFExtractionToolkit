package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import spray.http.MediaType

/**
 * A description of a document in the [[DocumentLibrary]], containing information that would be useful to a user.
 */
case class DocumentDescription protected (title: String,
                                          description: Option[String],
                                          mediaType: MediaType = MediaType.custom("application/octet-stream")) {
}

object DocumentDescription {
  def withTitleAndDescriptionAndMediaType(title: String, description: String, mediaType: MediaType): DocumentDescription =
    new DocumentDescription(title, Some(description), mediaType)

  def withTitleAndMediaType(title: String, mediaType: MediaType): DocumentDescription =
    new DocumentDescription(title, None, mediaType)

  def withTitleAndDescription(title: String, description: String): DocumentDescription =
    new DocumentDescription(title, Some(description))

  def withTitle(title: String): DocumentDescription =
    new DocumentDescription(title, None)
}

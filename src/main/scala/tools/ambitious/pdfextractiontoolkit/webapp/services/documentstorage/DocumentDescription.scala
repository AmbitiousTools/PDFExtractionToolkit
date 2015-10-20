package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

/**
 * A description of a document in the [[DocumentLibrary]], containing information that would be useful to a user.
 */
case class DocumentDescription protected (title: String,
                                          description: Option[String]) {
}

object DocumentDescription {
  def withTitleAndDescription(title: String, description: String): DocumentDescription =
    new DocumentDescription(title, Some(description))

  def withTitle(title: String): DocumentDescription = new DocumentDescription(title, None)
}

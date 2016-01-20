package tools.ambitious.pdfextractiontoolkit.webapp.data

import tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage.DocumentIdentifier

class DocumentIdentifierMissingException(documentID: DocumentIdentifier) extends Exception(s"${documentID.toString()} could not be found.")

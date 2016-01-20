package tools.ambitious.pdfextractiontoolkit.webapp.data

import slick.driver.SQLiteDriver.api._
import slick.lifted.{Query, TableQuery}
import tools.ambitious.pdfextractiontoolkit.webapp.data.model.{Document, Documents}
import tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage.DocumentIdentifier

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

private class DocumentInformationDaoImpl(val rootDAO: RootDAO) extends DocumentInformationDao {
  override def storeDocumentID(documentID: DocumentIdentifier): Future[Unit] = {
    val newRow = Document(None,
      documentID.hash,
      documentID.description.title,
      documentID.description.description.getOrElse(""),
      documentID.description.mediaType.toString())

    val tableQuery: TableQuery[Documents] = new TableQuery(new Documents(_))
    val insertAction = tableQuery += newRow

    rootDAO.database.run(insertAction).map(_ => Unit)
  }

  override def deleteDocumentID(documentID: DocumentIdentifier): Future[Unit] = {
    val tableQuery: TableQuery[Documents] = new TableQuery(new Documents(_))
    val deleteAction = tableQuery.filter(document => document.hash === documentID.hash).delete

    rootDAO.database.run(deleteAction).map(rowsAffected => {
      if (rowsAffected == 0)
        throw new DocumentIdentifierMissingException(documentID)
      else
        Unit
    })
  }

  override def retrieveAllIDs(): Future[Seq[DocumentIdentifier]] = {
    val tableQuery: TableQuery[Documents] = new TableQuery(new Documents(_))

    val query: Query[Documents, Document, Seq] = tableQuery.map(row => row)

    val resultsFuture: Future[Seq[Document]] = rootDAO.database.run(query.result)

    resultsFuture.map(documents => documents.map(_.asDocumentIdentifier))
  }
}
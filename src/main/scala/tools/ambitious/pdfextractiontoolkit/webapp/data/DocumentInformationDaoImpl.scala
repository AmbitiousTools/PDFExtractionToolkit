package tools.ambitious.pdfextractiontoolkit.webapp.data

import slick.driver.SQLiteDriver.api._
import slick.lifted.TableQuery
import tools.ambitious.pdfextractiontoolkit.webapp.data.model.{Document, Documents}
import tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage.DocumentIdentifier

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

private class DocumentInformationDaoImpl(val rootDAO: RootDAO) extends DocumentInformationDao {
  override def storeDocumentID(docID: DocumentIdentifier): Future[Unit] = {
    val newRow = Document(None,
      docID.hash,
      docID.description.title,
      docID.description.description.getOrElse(""),
      docID.description.mediaType.toString())

    val tableQuery: TableQuery[Documents] = new TableQuery(new Documents(_))
    val insertAction = tableQuery += newRow

    rootDAO.database.run(insertAction).map(_ => Unit)
  }

  override def deleteDocumentID(docID: DocumentIdentifier): Future[Unit] = ???

  override def retrieveAllIDs(): Future[Seq[DocumentIdentifier]] = ???
}
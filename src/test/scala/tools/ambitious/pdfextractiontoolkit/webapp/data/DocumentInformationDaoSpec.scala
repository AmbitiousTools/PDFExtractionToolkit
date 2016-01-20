package tools.ambitious.pdfextractiontoolkit.webapp.data

import slick.driver.SQLiteDriver.api._
import slick.lifted.{Query, TableQuery}
import spray.http.MediaTypes
import tools.ambitious.pdfextractiontoolkit.AmbitiousToolsSpec
import tools.ambitious.pdfextractiontoolkit.webapp.data.model.{Document, Documents}
import tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage.{DocumentDescription, DocumentIdentifier}

import scala.concurrent.Await
import scala.concurrent.duration._

class DocumentInformationDaoSpec extends AmbitiousToolsSpec {

  val rootDao: RootDAO = DAOTestUtils.constructCleanRootDAO
  val dao = DocumentInformationDao.forRootDao(rootDao)

  val documentID: DocumentIdentifier = constructDocumentId

  storeDocumentInDatabase(documentID)

  val allDocuments = retrieveAllDocsForID(documentID)

  "the document information dao" should "write one database row per stored document ID" in {
    assert(allDocuments.length == 1)
  }

  it should "store the document's hash" in {
    assert(allDocuments.head.hash == documentID.hash)
  }

  it should "store the document's title" in {
    assert(allDocuments.head.title == documentID.description.title)
  }

  it should "store the document's description" in {
    assert(documentID.description.description.contains(allDocuments.head.description))
  }

  it should "store the document's media type" in {
    assert(allDocuments.head.mediaType == documentID.description.mediaType.toString())
  }

  def constructDocumentId: DocumentIdentifier = {
    val testHash: String = "425A"

    val description: DocumentDescription = DocumentDescription
      .withTitleAndDescriptionAndMediaType("testDoc", "A test document", MediaTypes.`application/pdf`)

    DocumentIdentifier.withHashAndDescription(testHash, description)
  }

  def storeDocumentInDatabase(documentID: DocumentIdentifier): Unit = {
    Await.result(dao.storeDocumentID(documentID), 30.seconds)
  }

  def retrieveAllDocsForID(documentID: DocumentIdentifier): Seq[Document] = {
    val tableQuery: TableQuery[Documents] = new TableQuery(new Documents(_))

    val query: Query[Documents, Document, Seq] = tableQuery.map(row => row)
    Await.result(rootDao.database.run(query.result), 30.seconds)
  }
}

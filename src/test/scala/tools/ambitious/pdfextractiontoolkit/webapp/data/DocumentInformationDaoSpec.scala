package tools.ambitious.pdfextractiontoolkit.webapp.data

import slick.driver.SQLiteDriver.api._
import slick.lifted.{Query, TableQuery}
import spray.http.{MediaType, MediaTypes}
import tools.ambitious.pdfextractiontoolkit.AmbitiousToolsSpec
import tools.ambitious.pdfextractiontoolkit.webapp.data.model.{Document, Documents}
import tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage.{DocumentDescription, DocumentIdentifier}

import scala.concurrent.Await
import scala.concurrent.duration._

class DocumentInformationDaoSpec extends AmbitiousToolsSpec {

  val rootDao: RootDAO = DAOTestUtils.constructCleanRootDAO
  val dao = DocumentInformationDao.forRootDao(rootDao)

  val documentID: DocumentIdentifier = {
    val testDocumentHash: String = "425A"
    val testDocumentTitle: String = "testDoc"
    val testDocumentDescription: String = "A test document"
    val testDocumentMediaType: MediaType = MediaTypes.`application/pdf`

    val description: DocumentDescription = DocumentDescription
      .withTitleAndDescriptionAndMediaType(testDocumentTitle, testDocumentDescription, testDocumentMediaType)

    DocumentIdentifier.withHashAndDescription(testDocumentHash, description)
  }

  "the document information dao" should "write one database row per stored document ID" in {
    storeDocumentUsingDao(documentID)

    val allDocuments = retrieveAllDocsForIDFromDatabase(documentID)

    assert(allDocuments.length == 1)
  }

  it should "store a stored document'sthe document's hash" in {
    storeDocumentUsingDao(documentID)

    val allDocuments = retrieveAllDocsForIDFromDatabase(documentID)

    assert(allDocuments.head.hash == documentID.hash)
  }

  it should "store the document's title" in {
    storeDocumentUsingDao(documentID)

    val allDocuments = retrieveAllDocsForIDFromDatabase(documentID)

    assert(allDocuments.head.title == documentID.description.title)
  }

  it should "store the document's description" in {
    storeDocumentUsingDao(documentID)

    val allDocuments = retrieveAllDocsForIDFromDatabase(documentID)

    assert(documentID.description.description.contains(allDocuments.head.description))
  }

  it should "store the document's media type" in {
    storeDocumentUsingDao(documentID)

    val allDocuments = retrieveAllDocsForIDFromDatabase(documentID)

    assert(allDocuments.head.mediaType == documentID.description.mediaType.toString())
  }

  it should "retrieve no identifiers from an empty identifier table" in {
    val allDocumentIDs: Seq[DocumentIdentifier] = retrieveAllDocumentIDsFromDao()

    assert(allDocumentIDs.isEmpty)
  }

  it should "retrieve one identifier from an identifer table with one record" in {
    Given("one document has been stored in the database")
    storeDocumentUsingDao(documentID)

    When("the document IDs are retrieved from the dao")
    val allDocIDs: Seq[DocumentIdentifier] = retrieveAllDocumentIDsFromDao()

    Then("there should be one document ID")
    assert(allDocIDs.size == 1)
  }

  it should "retrieve the correct document ID" in {
    Given("one document has been stored in the database")
    storeDocumentUsingDao(documentID)

    When("the document IDs are retrieved from the dao")
    val allDocIDs: Seq[DocumentIdentifier] = retrieveAllDocumentIDsFromDao()

    Then("the retrieved document ID should be the same as the stored one")
    val retrievedDocID = allDocIDs.head

    assert(retrievedDocID == documentID)
  }

  def storeDocumentUsingDao(documentID: DocumentIdentifier): Unit = {
    Await.result(dao.storeDocumentID(documentID), 30.seconds)
  }

  def retrieveAllDocsForIDFromDatabase(documentID: DocumentIdentifier): Seq[Document] = {
    val tableQuery: TableQuery[Documents] = new TableQuery(new Documents(_))

    val query: Query[Documents, Document, Seq] = tableQuery.map(row => row)
    Await.result(rootDao.database.run(query.result), 30.seconds)
  }

  def retrieveAllDocumentIDsFromDao(): Seq[DocumentIdentifier] = {
    val allDocumentIDs: Seq[DocumentIdentifier] = Await.result(dao.retrieveAllIDs(), 30.seconds)
    allDocumentIDs
  }
}

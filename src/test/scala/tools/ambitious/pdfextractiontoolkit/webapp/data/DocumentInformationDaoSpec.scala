package tools.ambitious.pdfextractiontoolkit.webapp.data

import org.scalatest.FreeSpec
import slick.driver.SQLiteDriver.api._
import slick.lifted.{Query, TableQuery}
import spray.http.MediaTypes
import tools.ambitious.pdfextractiontoolkit.webapp.data.model.{Document, Documents}
import tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage.{DocumentDescription, DocumentIdentifier}

import scala.concurrent.Await
import scala.concurrent.duration._

class DocumentInformationDaoSpec extends FreeSpec {

  val rootDao: RootDAO = DAOTestUtils.constructCleanRootDAO
  val dao = DocumentInformationDao.forRootDao(rootDao)

  "when a document is stored" - {

    val hash: String = "425A"
    val description: DocumentDescription = DocumentDescription
      .withTitleAndDescriptionAndMediaType("testDoc", "A test document", MediaTypes.`application/pdf`)

    val documentID: DocumentIdentifier = DocumentIdentifier.withHashAndDescription(hash, description)
    Await.result(dao.storeDocumentID(documentID), 30.seconds)

    "the table" - {
      val tableQuery: TableQuery[Documents] = new TableQuery(new Documents(_))
      val query: Query[Documents, Document, Seq] = tableQuery.map(row => row)

      val allDocuments: Seq[Document] = Await.result(rootDao.database.run(query.result), 30.seconds)

      "has one row" in {
        assert(allDocuments.length == 1)
      }

      "row" - {
        val theRow: Document = allDocuments.head

        "has the same hash" in {
          assert(theRow.hash == documentID.hash)
        }

        "has the same title" in {
          assert(theRow.title == documentID.description.title)
        }

        "has the same description" in {
          assert(documentID.description.description.contains(theRow.description))
        }

        "has the same media type" in {
          assert(theRow.mediaType == documentID.description.mediaType.toString())
        }
      }
    }
  }
}

package tools.ambitious.pdfextractiontoolkit.webapp.data

import java.net.URL
import java.nio.file.Files

import org.scalatest.FreeSpec
import slick.jdbc.meta.MTable
import tools.ambitious.pdfextractiontoolkit.utils.AmbitiousIoUtils.{ByteArrayUtils, URLUtils}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class RootDAOImplSpec extends FreeSpec {

  "the root DAO" - {
    val dao: RootDAO = RootDAO.forConfigName(DAOTestUtils.testConfigName)
    DAOTestUtils.testDBFile.getParent.toFile.mkdirs()

    "if the database doesn't exist" - {

      Files.deleteIfExists(DAOTestUtils.testDBFile)

      "a call to initialiseIfNeeded" - {

        Await.result(dao.initialiseIfNeeded(), 30.seconds)

        "will create a database" in {
          assert(Files.exists(DAOTestUtils.testDBFile))
        }

        "will create the correct tables" in {
          val listTables: Future[Vector[MTable]] = dao.database.run(MTable.getTables)

          val tablesMetaData: Vector[MTable] = Await.result(listTables, 30.seconds)

          val actualTableNames = tablesMetaData
            .map(_.name.name)
            .toSet
            .filterNot(_ == "sqlite_sequence") // Metadata table that's always created. We don't give a shit.

          val expectedTableNames = Set("Documents")

          assert(expectedTableNames == actualTableNames)
        }
      }
    }

    "if the database does exist" - {

      dao.initialiseIfNeeded()

      "does not change the database" in {
        val testDBUrl: URL = DAOTestUtils.testDBFile.toUri.toURL

        val originalHash = testDBUrl
          .toBytes
          .computeHashAsHex

        dao.initialiseIfNeeded()

        val newHash = testDBUrl
          .toBytes
          .computeHashAsHex

        assert(originalHash == newHash)
      }
    }
  }
}

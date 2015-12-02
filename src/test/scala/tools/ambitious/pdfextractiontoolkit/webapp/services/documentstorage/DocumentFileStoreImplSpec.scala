package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.io.File
import java.net.URL
import java.nio.file.{Files, Path}

import org.scalatest._
import spray.http.MediaType
import _root_.tools.ambitious.pdfextractiontoolkit.Resources
import _root_.tools.ambitious.pdfextractiontoolkit.utils.AmbitiousIoUtils._
import _root_.tools.ambitious.pdfextractiontoolkit.webapp.data.DAOTestUtils

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.Source

class DocumentFileStoreImplSpec extends FlatSpec with GivenWhenThen with BeforeAndAfter with OneInstancePerTest {
  private val workingDirectory: Path = DAOTestUtils.createAndGetWorkingDirectory()
  private val documentFileStore: DocumentFileStore = new DocumentFileStoreImpl(workingDirectory)

  private val mediaType: MediaType = MediaType.custom("text/plain")
  private val documentDescription: DocumentDescription = DocumentDescription.withTitleAndMediaType("Test", mediaType)
  private val source: URL = Resources.quickBrownFoxTxt

  private val documentID: DocumentIdentifier = DocumentIdentifier.computeFor(documentDescription, source.toBytes)

  private val expectedSourceContents: String = "The quick brown fox jumped over the lazy dogs"
  private val expectedOutputFile: File = workingDirectory.resolve(documentID.hash.toString).toFile

  def storeTestFileInFileStore(): Unit = {
    Await.result(documentFileStore.storeFileFor(documentID, source), 30.seconds)
  }

  after {
    DAOTestUtils.cleanWorkingDirectory()
  }

  "A file store" should "store files with the expected name" in {
    When("a file is stored in the file store")
    storeTestFileInFileStore()

    Then("the file should exist on disk")
    assert(expectedOutputFile.isFile)
  }

  it should "store files with the expected content" in {
    When("a file is stored in the file store")
    storeTestFileInFileStore()

    Then("the file stored on disk should contain the contents of the source file")
    assert(Source.fromFile(expectedOutputFile).mkString == expectedSourceContents)
  }

  it should "delete stored files" in {
    Given("a file is in the file store")
    storeTestFileInFileStore()

    When("the file is deleted")
    Await.result(documentFileStore.deleteFileFor(documentID), 30.seconds)

    Then("the file should not exist on disk")
    assert(!expectedOutputFile.exists())
  }

  it should "return quietly if asked to delete a missing file" in {
    Given("no file has been stored in the file store")

    When("the file is deleted")
    Await.result(documentFileStore.deleteFileFor(documentID), 30.seconds)

    Then("the file store should ignore the missing file")
  }
}

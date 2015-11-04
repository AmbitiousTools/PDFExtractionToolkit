package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.io.File
import java.net.URL
import java.nio.file.{Path, Paths}

import org.scalatest.FreeSpec
import spray.http.MediaType
import tools.ambitious.pdfextractiontoolkit.Resources
import tools.ambitious.pdfextractiontoolkit.utils.AmbitiousIoUtils._

import scala.io.Source

class DocumentFileStoreImplSpec extends FreeSpec {
  val workingDirectory: Path = Paths.get("working")

  "a file store created for the 'working' directory" - {
    val documentFileStore: DocumentFileStore = new DocumentFileStoreImpl(workingDirectory)

    "when you store a file in the document file store" - {
      val mediaType: MediaType = MediaType.custom("text/plain")
      val documentDescription: DocumentDescription = DocumentDescription.withTitleAndMediaType("Test", mediaType)
      val source: URL = Resources.quickBrownFoxTxt

      val documentID: DocumentIdentifier = DocumentIdentifier.computeFor(documentDescription, source.toBytes)

      documentFileStore.storeFileFor(documentID, source)

      val expectedSourceContents: String = "The quick brown fox jumped over the lazy dogs"
      val expectedOutputFile: File = workingDirectory.resolve(documentID.hash.toString).toFile

      "the document file store should contain a file with that name" in {
        assert(expectedOutputFile.isFile)
      }

      "the document file store should contain the file" in {
        assert(Source.fromFile(expectedOutputFile).mkString == expectedSourceContents)
      }
    }
  }
}

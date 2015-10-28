package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.io.File
import java.nio.file.{Path, Paths}

import org.scalatest.FreeSpec
import spray.http.MediaType

import scala.io.Source

class DocumentFileStoreImplSpec extends FreeSpec {
  val workingDirectory: Path = Paths.get("working")

  "a file store created for the 'working' directory" - {
    val documentFileStore: DocumentFileStore = new DocumentFileStoreImpl(workingDirectory)

    "when you store a file in the document file store" - {
      val mediaType: MediaType = MediaType.custom("text/plain")
      val documentDescription: DocumentDescription = DocumentDescription.withTitleAndMediaType("Test", mediaType)
      val sourceContents: String = "The quick brown fox jumped over the lazy dogs"
      val source: Source = Source.fromString(sourceContents)

      val documentID: DocumentIdentifier = DocumentIdentifier.computeFor(documentDescription, source)

      documentFileStore.storeFileFor(documentID, source)

      val expectedOutputFile: File = workingDirectory.resolve(documentID.hash.toString).toFile

      "the document file store should contain a file with that name" in {
        assert(expectedOutputFile.isFile)
      }

      "the document file store should contain the file" in {
        assert(Source.fromFile(expectedOutputFile).mkString == sourceContents)
      }
    }
  }
}

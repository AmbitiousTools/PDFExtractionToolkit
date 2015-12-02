package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.net.URL
import java.nio.file.{Files, Path}

import org.apache.commons.io.FileUtils

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

private class DocumentFileStoreImpl(val workingDirectory: Path) extends DocumentFileStore {

  private def computeExpectedPath(docID: DocumentIdentifier): Path = {
    val fileName: String = docID.hash
    val outputFile: Path = workingDirectory.resolve(fileName)
    outputFile
  }

  override def storeFileFor(docID: DocumentIdentifier, source: URL): Future[Unit] = {
    Future {
      val outputFile: Path = computeExpectedPath(docID)

      FileUtils.copyURLToFile(source, outputFile.toFile)
    }
  }

  override def deleteFileFor(docID: DocumentIdentifier): Future[Unit] = {
    Future {
      Files.deleteIfExists(computeExpectedPath(docID))
    }
  }

  override def retrieveFileFor(docID: DocumentIdentifier): Future[URL] = ???
}
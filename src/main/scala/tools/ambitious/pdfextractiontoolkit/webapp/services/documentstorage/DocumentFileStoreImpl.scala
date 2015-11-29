package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.net.URL
import java.nio.file.Path

import org.apache.commons.io.FileUtils

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

private class DocumentFileStoreImpl(val workingDirectory: Path) extends DocumentFileStore {

  override def storeFileFor(docID: DocumentIdentifier, source: URL): Future[Unit] = {
    Future {
      val fileName: String = docID.hash
      val outputFile: Path = workingDirectory.resolve(fileName)

      FileUtils.copyURLToFile(source, outputFile.toFile)
    }
  }

  override def deleteFileFor(docID: DocumentIdentifier): Future[Unit] = ???

  override def retrieveFileFor(docID: DocumentIdentifier): Future[URL] = ???
}
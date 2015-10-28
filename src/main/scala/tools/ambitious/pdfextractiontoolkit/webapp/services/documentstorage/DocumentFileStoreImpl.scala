package tools.ambitious.pdfextractiontoolkit.webapp.services.documentstorage

import java.nio.file.Path

import org.apache.commons.io.FileUtils

import scala.io.Source

private class DocumentFileStoreImpl(val workingDirectory: Path) extends DocumentFileStore {

  override def storeFileFor(docID: DocumentIdentifier, source: Source): Unit = {
    val fileName: String = docID.hash
    val outputFile: Path = workingDirectory.resolve(fileName)
    FileUtils.writeByteArrayToFile(outputFile.toFile, source.reset().map(_.toByte).toArray)
  }

  override def deleteFileFor(docID: DocumentIdentifier): Unit = {}

  override def retrieveFileFor(docID: DocumentIdentifier): Source = ???
}
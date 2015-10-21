package tools.ambitious.pdfextractiontoolkit.library.util

import java.io.File
import java.net.URL

import com.github.tototoshi.csv._
import tools.ambitious.pdfextractiontoolkit.library.model.{Row, Table}

object CSVUtil {
  def tableFromFile(file: File): Table = {
    val reader: CSVReader = CSVReader.open(file)
    val lines: List[List[String]] = reader.all()
    Table.fromRows(lines.map(line => Row.fromStrings(line)))
  }

  def tableFromURL(url: URL): Table =
    tableFromFile(new File(url.toURI))
}

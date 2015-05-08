package tools.ambitious.pdfextractiontoolkit.util

import java.io.File
import java.net.URL

import tools.ambitious.pdfextractiontoolkit.model.{Table, Row}

import com.github.tototoshi.csv._

object CSVUtil {
  def tableFromFile(file: File): Table = {
    val reader: CSVReader = CSVReader.open(file)
    val lines: List[List[String]] = reader.all()
    Table.fromRows(lines.map(line => Row.fromStrings(line)))
  }

  def tableFromURL(url: URL): Table =
    tableFromFile(new File(url.toURI))
}

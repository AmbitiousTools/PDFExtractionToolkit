package tools.ambitious.pdfextractiontoolkit.library.util

import java.io.File

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.library.model.{Row, Table}

class CSVUtilSpec extends FreeSpec {
  val simple3x3DigitsURL = getClass.getResource("/simpleCSVs/simple3x3Digits.csv")

  "A Table from CSV File" - {
    val table: Table = CSVUtil.tableFromFile(new File(simple3x3DigitsURL.toURI))

    "should be equal to table: 1,2,3\n4,5,6\n7,8,9" in {
      val rowA: Row = Row.fromStrings(List("1", "2", "3"))
      val rowB: Row = Row.fromStrings(List("4", "5", "6"))
      val rowC: Row = Row.fromStrings(List("7", "8", "9"))

      val tableB: Table = Table.fromRows(List(rowA, rowB, rowC))

      assert(table == tableB)
    }
  }

  "A Table from CSV URL" - {
    val table: Table = CSVUtil.tableFromURL(simple3x3DigitsURL)

    "should be equal to table: 1,2,3\n4,5,6\n7,8,9" in {
      val rowA: Row = Row.fromStrings(List("1", "2", "3"))
      val rowB: Row = Row.fromStrings(List("4", "5", "6"))
      val rowC: Row = Row.fromStrings(List("7", "8", "9"))

      val tableB: Table = Table.fromRows(List(rowA, rowB, rowC))

      assert(table == tableB)
    }
  }
}

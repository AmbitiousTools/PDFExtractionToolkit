package tools.ambitious.pdfextractiontoolkit.extraction.tableextractors

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.extraction._
import tools.ambitious.pdfextractiontoolkit.extraction.tablemergers.SimpleTableMerger
import tools.ambitious.pdfextractiontoolkit.model.geometry.{PositivePoint, Rectangle, Size}
import tools.ambitious.pdfextractiontoolkit.model.{Document, Table}
import tools.ambitious.pdfextractiontoolkit.util.CSVUtil

class PageNumberTableExtractorSpec extends FreeSpec {
  val region: Rectangle = Rectangle.fromCornerAndSize(PositivePoint.at(168.48, 240), Size.fromWidthAndHeight(213.54, 340))

  s"A ${PageNumberTableExtractor.getClass.getSimpleName}" - {
    "for page 2" - {
      val tableExtractor: PageNumberTableExtractor = PageNumberTableExtractor.withPageNumberAndRegion(2, region)

      "when put through a walker with test document 2" - {
        val document: Document = Document.fromPDFPath(simpleTest2Tables2TitleURL)
        val walker: DocumentWalker = DocumentWalker.toWalk(document)
        walker.addListener(tableExtractor)
        walker.walk()

        "should return the table at page 2" in {
          val table: Table = tableExtractor.getTable.get
          val tableFromCSV: Table = CSVUtil.tableFromURL(simpleTest2Tables2TitlePage2CSVURL)

          assert(table == tableFromCSV)
        }
      }
    }

    "for a page range from 1 to 2" - {
      val tableExtractor: PageNumberTableExtractor =
        PageNumberTableExtractor.withPageRangeAndRegion(Range.inclusive(1, 2), region)

      "when put through a walker with test document 2" - {
        val document: Document = Document.fromPDFPath(simpleTest2Tables2TitleURL)
        val walker: DocumentWalker = DocumentWalker.toWalk(document)
        walker.addListener(tableExtractor)
        walker.walk()

        "should return the two tables merged" in {
          val table: Table = tableExtractor.getTable.get

          val tableMerger: SimpleTableMerger = SimpleTableMerger.create

          val table1: Table = CSVUtil.tableFromURL(simpleTest2Tables2TitlePage1CSVURL)
          val table2: Table = CSVUtil.tableFromURL(simpleTest2Tables2TitlePage2CSVURL)
          val tables: List[Table] = List(table1, table2)

          val tableFromCSV: Table = tableMerger.mergeTables(tables).get

          assert(table == tableFromCSV)
        }
      }
    }

    s"instantiated for a page number less than 1 should throw an IllegalArgumentException" - {
      val instantiatePageNumberTableExtractor = intercept[IllegalArgumentException] {
        PageNumberTableExtractor.withPageNumberAndRegion(0, Rectangle.fromCornerCoords(0, 0, 0, 0))
      }
      assert(instantiatePageNumberTableExtractor.getMessage === "Page numbers can only be positive numbers.")
    }
  }
}

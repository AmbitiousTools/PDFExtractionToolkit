package tools.ambitious.pdfextractiontoolkit.library.extraction

import org.scalatest.FreeSpec
import technology.tabula
import tools.ambitious.pdfextractiontoolkit.Resources
import tools.ambitious.pdfextractiontoolkit.library.model.geometry.Rectangle
import tools.ambitious.pdfextractiontoolkit.library.model.{Document, Page, Table}
import tools.ambitious.pdfextractiontoolkit.library.util.{CSVUtil, TabulaConverter}

class ExtractionUtilsSpec extends FreeSpec {
  "The method extractTabulaTableFromPage() should be able to extract the same values as those in the corresponding" +
    "csv file for simpleTest1Table.pdf file" in {
    val window: Rectangle = Rectangle.fromCornerCoords(108, 81, 312, 305)
    val page: Page = Document.fromPDFPath(Resources.simpleTest1TableURL).getPage(1)

    val extractedTabulaTable: tabula.Table = ExtractionUtils.extractTabulaTableFromPage(page, window)
    val extractedTable: Table = TabulaConverter.tableFromTabulaTable(extractedTabulaTable)
    val tableFromCSV: Table = CSVUtil.tableFromURL(Resources.simpleTest1TableCSVURL)

    assert(extractedTable == tableFromCSV)
  }
}

package tools.ambitious.pdfextractiontoolkit.extraction

import java.util.NoSuchElementException

import technology.tabula
import technology.tabula.ObjectExtractor
import technology.tabula.extractors.BasicExtractionAlgorithm
import tools.ambitious.pdfextractiontoolkit.model.Page
import tools.ambitious.pdfextractiontoolkit.model.geometry.Rectangle

object ExtractionUtils {

  def extractTabulaTableFromPage(page: Page, portal: Rectangle) :tabula.Table = {
    val objectExtractor = new ObjectExtractor(page.asPDDocument)
    val wholePage: tabula.Page = objectExtractor.extract(1)

    try {
      val tablePageArea = wholePage.getArea(
        portal.top.toFloat,
        portal.left.toFloat,
        portal.bottom.toFloat,
        portal.right.toFloat)

      (new BasicExtractionAlgorithm).extract(tablePageArea).get(0)
    } catch {
      case e: NoSuchElementException => new tabula.Table
    }
  }
}

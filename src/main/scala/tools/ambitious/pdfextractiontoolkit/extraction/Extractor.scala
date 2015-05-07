package tools.ambitious.pdfextractiontoolkit.extraction

import technology.tabula.ObjectExtractor
import technology.tabula.extractors.BasicExtractionAlgorithm
import technology.tabula
import tools.ambitious.pdfextractiontoolkit.model.constraints.{PageNumberConstraint, Constraint}
import tools.ambitious.pdfextractiontoolkit.model._
import tools.ambitious.pdfextractiontoolkit.util.TabulaConverter

class Extractor private (private val stencil: Stencil, private val documents: List[Document]) {
  def extractTables: Map[Document, Table] =
    documents.map(document => document -> extractStencilFromDocument(document))(collection.breakOut)

  private def extractStencilFromDocument(document: Document): Table = {
    /**
     * We have a Stencil that contains a bunch of windows and constraints with some
     * sort of relation between them.
     *
     * The flow should be:
     *  - Use Constraints to find all of the Windows and corresponding Pages in the Document
     *  - Extract the Table from each Page using Window (in page number order)
     *  - Merge these Tables together as we're going
     *  - Return the Table
     */

    // TODO: Make the following follow the above outlined flow

    var table: Table = new Table
    val window: Window = stencil.windows.head

    for (constraint: Constraint <- window.constraints) {
      constraint match {
        case pageNumberConstraint: PageNumberConstraint =>
          val page: Page = document.getPage(pageNumberConstraint.pageNumber)
          table = extractTableFromPageUsingWindow(page, window)
        case _ =>
          throw new Exception("Unknown constraint type!")
      }
    }

    table
  }

  private def extractTableFromPageUsingWindow(page: Page, window: Window): Table = {
    val tabulaTable: tabula.Table = extractTabulaTableFromPageUsingWindow(page, window)
    TabulaConverter.tableFromTabulaTable(tabulaTable)
  }

  private def extractTabulaTableFromPageUsingWindow(page: Page, window: Window): tabula.Table = {
    val objectExtractor = new ObjectExtractor(page.asPDDocument)
    val wholePage: tabula.Page = objectExtractor.extract(1)

    val tablePageArea = wholePage.getArea(
      window.topCoordinate.toFloat,
      window.leftCoordinate.toFloat,
      window.bottomCoordinate.toFloat,
      window.rightCoordinate.toFloat)

    (new BasicExtractionAlgorithm).extract(tablePageArea).get(0)
  }
}

object Extractor {
  def fromStencilAndDocuments(stencil: Stencil, documents: List[Document]): Extractor =
    new Extractor(stencil, documents)

  def fromStencilAndDocument(stencil: Stencil, document: Document): Extractor =
    fromStencilAndDocuments(stencil, List(document))
}
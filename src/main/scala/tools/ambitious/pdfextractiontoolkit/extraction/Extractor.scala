package tools.ambitious.pdfextractiontoolkit.extraction

import org.apache.pdfbox.pdmodel.PDDocument
import technology.tabula.ObjectExtractor
import technology.tabula.extractors.BasicExtractionAlgorithm
import technology.tabula
import tools.ambitious.pdfextractiontoolkit.model.constraints.{PageNumberConstraint, Constraint}
import tools.ambitious.pdfextractiontoolkit.model._

import scala.collection.JavaConverters._

class Extractor {
  private var documents: List[Document] = Nil
  private var stencil: Stencil = null

  def addDocument(document: Document) =
    addDocuments(List(document))

  def addDocuments(documents: List[Document]) =
    this.documents = this.documents ++ documents

  def applyStencil(stencil: Stencil) =
    this.stencil = stencil

  def extractTables() = {
    for (window: Window <- stencil.windows)
      extractTableFromWindow(window)
  }

  private def extractTableFromWindow(window: Window) = {
    for (constraint: Constraint <- window.constraints) {

      /**
       * The following is not 'clean code' and should be refactored at will.
       */

      constraint match {
        case pageNumberConstraint: PageNumberConstraint =>
          val pageNumber: Int = pageNumberConstraint.pageNumber

          for (document: Document <- documents) {
            val page: Page = document.getPage(pageNumber)
            val pageAsPDDocument: PDDocument = page.asPDDocument

            val objectExtractor = new ObjectExtractor(pageAsPDDocument)
            val wholePage: tabula.Page = objectExtractor.extract(1)
            val tablePageArea = wholePage.getArea(
              window.topCoordinate.toFloat,
              window.leftCoordinate.toFloat,
              window.bottomCoordinate.toFloat,
              window.rightCoordinate.toFloat)

            val extractionAlgorithm = new BasicExtractionAlgorithm
            val tabulaTable = extractionAlgorithm.extract(tablePageArea).get(0)

            val table: Table = new Table

            for (tabulaRow <- tabulaTable.getRows.asScala) {
              val row: Row = new Row
              for (tabulaCell <- tabulaRow.asScala) {
                val cell: Cell = new Cell(tabulaCell.getText.trim)
                row.addCell(cell)
              }
              table.addRow(row)
            }

            document.addTable(table)
          }
        case _ =>
          throw new Exception("Unknown constraint type!")
      }
    }

  }
}

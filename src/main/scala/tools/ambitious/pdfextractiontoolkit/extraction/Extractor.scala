package tools.ambitious.pdfextractiontoolkit.extraction

import java.util.NoSuchElementException

import technology.tabula.ObjectExtractor
import technology.tabula.extractors.BasicExtractionAlgorithm
import technology.tabula
import tools.ambitious.pdfextractiontoolkit.model.constraints.{FirstOccurrenceOfStringInWindowConstraint, PageNumberConstraint}
import tools.ambitious.pdfextractiontoolkit.model._
import tools.ambitious.pdfextractiontoolkit.util.TabulaConverter

class Extractor private (private val stencil: Stencil, private val documents: List[Document]) {
  def extractTables: Map[Document, Table] =
    documents.map(document => document -> extractStencilFromDocument(document))(collection.breakOut)

  private def extractStencilFromDocument(document: Document): Table = {
    var table: Table = new Table

    for (window: Window <- stencil.windows) {
      val tracker: ConstraintTracker = stencil.trackerForWindow(window)
      tracker.anchor match {
        case constraint: PageNumberConstraint =>
          val page: Page = document.getPage(constraint.pageNumber)
          table = table.mergedWith(extractTableFromPageUsingWindow(page, window))

        case constraint: FirstOccurrenceOfStringInWindowConstraint =>
          val pageMaybe: Option[Page] = document.pages.find(page => extractTableFromPageUsingWindow(page, constraint.window).getCell(1,1).text == constraint.text)
          pageMaybe match {
            case Some(page) => table = table.mergedWith(extractTableFromPageUsingWindow(page, window))
            case None => throw new Exception("Invalid Constraint: Couldn't find text '" + constraint.text + "' in Window " + constraint + " on any page of document " + document + ".")
          }

        case _ =>
          throw new Exception("Unknown constraint type for constraint tracker anchor.")
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

    try {
      val tablePageArea = wholePage.getArea(
        window.topCoordinate.toFloat,
        window.leftCoordinate.toFloat,
        window.bottomCoordinate.toFloat,
        window.rightCoordinate.toFloat)

      (new BasicExtractionAlgorithm).extract(tablePageArea).get(0)
    } catch {
      case e: NoSuchElementException => new tabula.Table
    }
  }
}

object Extractor {
  def fromStencilAndDocuments(stencil: Stencil, documents: List[Document]): Extractor =
    new Extractor(stencil, documents)

  def fromStencilAndDocument(stencil: Stencil, document: Document): Extractor =
    fromStencilAndDocuments(stencil, List(document))
}
package tools.ambitious.pdfextractiontoolkit.extraction

import tools.ambitious.pdfextractiontoolkit.extraction.tableextractors.TableExtractor
import tools.ambitious.pdfextractiontoolkit.model.{Document, Page, Table}

class DocumentWalker protected (val document:Document, val tableExtractors: Set[TableExtractor]) {
  val stateBundles: Map[TableExtractor, StateBundle] = tableExtractors.map(_ -> StateBundle.create).toMap

  def walk() = {
    callOnStartOnTableExtractors(stateBundles)

    eachPageOnTableExtractors(stateBundles)

    callOnEndOnTableExtractors(stateBundles)
  }

  def getTables: Map[TableExtractor, Option[Table]] = tableExtractors
    .map(tableExtractor => tableExtractor -> tableExtractor.tableFromState(stateBundles(tableExtractor)))
    .toMap

  private def callOnStartOnTableExtractors(stateBundles: Map[TableExtractor, StateBundle]) =
    tableExtractors.foreach(tableExtractor => tableExtractor.onStart(stateBundles(tableExtractor)))

  private def eachPageOnTableExtractors(stateBundles: Map[TableExtractor, StateBundle]) =
    document.pages.foreach(callOnPageOnTableExtractors(stateBundles, _))

  private def callOnPageOnTableExtractors(stateBundles: Map[TableExtractor, StateBundle], page: Page) =
    tableExtractors.foreach(tableExtractor => tableExtractor.onPage(page, document, stateBundles(tableExtractor)))

  private def callOnEndOnTableExtractors(stateBundles: Map[TableExtractor, StateBundle]) =
    tableExtractors.foreach(tableExtractor => tableExtractor.onEnd(stateBundles(tableExtractor)))
}

object DocumentWalker {
  def toWalkWithTableExtractors(document: Document, tableExtractors: Set[TableExtractor]): DocumentWalker =
    new DocumentWalker(document, tableExtractors)

  def toWalkWithTableExtractors(document: Document, tableExtractors: Seq[TableExtractor]): DocumentWalker =
    toWalkWithTableExtractors(document, tableExtractors.toSet)

  def toWalkWithTableExtractor(document: Document, tableExtractor: TableExtractor): DocumentWalker =
    toWalkWithTableExtractors(document, Seq(tableExtractor))
}
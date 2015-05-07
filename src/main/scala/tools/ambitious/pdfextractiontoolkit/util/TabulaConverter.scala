package tools.ambitious.pdfextractiontoolkit.util

import java.util

import technology.tabula
import technology.tabula.{HasText, RectangularTextContainer}
import tools.ambitious.pdfextractiontoolkit.model.{Row, Table}

import scala.collection.JavaConverters._

object TabulaConverter {
  def tableFromTabulaTable(table: tabula.Table): Table =
    Table.fromRows(rowsFromTabulaRows(table.getRows.asScala.toList))

  def rowsFromTabulaRows(rows: List[util.List[RectangularTextContainer[_ <: HasText]]]): List[Row] =
    rows.map(row => rowFromTabulaRow(row))

  def rowFromTabulaRow(row: util.List[RectangularTextContainer[_ <: HasText]]): Row =
    Row.fromStrings(tabulaRowAsListOfTrimmedStrings(row))

  def tabulaRowAsListOfTrimmedStrings(row: util.List[RectangularTextContainer[_ <: HasText]]): List[String] =
    row.asScala.map(container => container.getText.trim).toList
}

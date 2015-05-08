package tools.ambitious.pdfextractiontoolkit.extractor

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.extraction.Extractor
import tools.ambitious.pdfextractiontoolkit.model.constraints.{FirstOccurrenceOfStringInWindowConstraint, PageNumberConstraint}
import tools.ambitious.pdfextractiontoolkit.model._
import tools.ambitious.pdfextractiontoolkit.model.geometry.{Size, PositivePoint}

import scala.collection.immutable.ListMap

class ExtractorSpec extends FreeSpec {
  val simpleTest1TablePath = getClass.getResource("/simplePDFs/SimpleTest1Table.pdf")
  val simpleTest2Tables2TitlePath = getClass.getResource("/simplePDFs/SimpleTest2Tables1Title.pdf")

  "An Extractor with a document and valid stencil" - {
    val document: Document = Document.fromPDFPath(simpleTest1TablePath)

    val window: Window = Window.fromAbsoluteCoordinates(108, 81, 312, 305)
    val tracker: ConstraintTracker = new ConstraintTracker(new PageNumberConstraint(1))

    val stencil: Stencil = new Stencil(ListMap(window -> tracker))

    val extractor: Extractor = Extractor.fromStencilAndDocument(stencil, document)

    "should be able to extract the table and have the value 10 in its top left cell" in {
      val tableMap: Map[Document, Table] = extractor.extractTables
      document.close()

      val table: Table = tableMap(document)
      val cell: Cell = table.getCell(1,1)

      assert(cell.text == "10")
    }
  }

  "An Extractor with one document and a stencil using the FirstOccurrenceOfStringInWindow constraint" - {
    val document: Document = Document.fromPDFPath(simpleTest2Tables2TitlePath)

    val constraintWindow: Window = new Window(new PositivePoint(185.38, 165.62), new Size(112.64, 16.16))
    val constraint = new FirstOccurrenceOfStringInWindowConstraint("An example Title", constraintWindow)

    val window: Window = new Window(new PositivePoint(168.48, 273.95), new Size(213.54, 303.5))
    val tracker: ConstraintTracker = new ConstraintTracker(constraint)

    val stencil: Stencil = new Stencil(ListMap(window -> tracker))

    val extractor: Extractor = Extractor.fromStencilAndDocument(stencil, document)

    "should be able to extract the table and have the value 4 in its top left cell" in {
      val tableMap: Map[Document, Table] = extractor.extractTables
      document.close()

      val table: Table = tableMap(document)
      val cell: Cell = table.getCell(1,1)

      assert(cell.text == "4")
    }
  }
}

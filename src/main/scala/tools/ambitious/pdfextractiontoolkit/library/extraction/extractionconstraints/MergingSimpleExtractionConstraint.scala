package tools.ambitious.pdfextractiontoolkit.library.extraction.extractionconstraints

import tools.ambitious.pdfextractiontoolkit.library.extraction.StateBundle
import tools.ambitious.pdfextractiontoolkit.library.extraction.tablemergers.TableMerger
import tools.ambitious.pdfextractiontoolkit.library.model.{Document, Page, Table}

trait MergingSimpleExtractionConstraint extends SimpleExtractionConstraint {

  val tableMerger: TableMerger

  override def onStart(stateBundle: StateBundle): Unit = stateBundle.state = Option.apply(List())

  override def onPage(page: Page, document: Document, stateBundle: StateBundle): Unit = {
    if (shouldExtractOnPage(page, document, stateBundle)) {

      val newList: List[Table] = stateBundle.state.asInstanceOf[Option[List[Table]]].get :+ performExtraction(page)

      stateBundle.state = Option.apply(newList)
    }
  }

  override def tableFromState(stateBundle: StateBundle): Option[Table] =
    tableMerger.mergeTables(stateBundle.state.asInstanceOf[Option[List[Table]]].get)

}

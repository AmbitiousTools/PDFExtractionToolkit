package tools.ambitious.pdfextractiontoolkit.extraction

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.model.geometry.Rectangle

class DatumExtractorSpec extends FreeSpec {

  s"A ${DatumExtractor.getClass.getSimpleName}" - {
    "that has not walked a document" - {
      val windowExtractor: DatumExtractor = DatumExtractor.using(Rectangle.fromCornerCoords(0, 0, 0, 0), null)

      s"should throw an IllegalStateException when retrieving the extracted tables" - {
        val exception = intercept[IllegalStateException] {
          windowExtractor.extractedTables()
        }
      }

    }
  }

}

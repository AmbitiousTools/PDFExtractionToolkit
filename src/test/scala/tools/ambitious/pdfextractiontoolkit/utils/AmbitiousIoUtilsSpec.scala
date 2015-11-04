package tools.ambitious.pdfextractiontoolkit.utils

import org.scalatest.FreeSpec
import tools.ambitious.pdfextractiontoolkit.Resources
import tools.ambitious.pdfextractiontoolkit.utils.AmbitiousIoUtils.{ByteArrayUtils, URLUtils}

class AmbitiousIoUtilsSpec extends FreeSpec {
  "the toBytes method will return the bytes of the quick brown fox text file" in {
    val actualBytes: Array[Byte] = Resources.quickBrownFoxTxt.toBytes
    val expectedBytes: Array[Byte] = "The quick brown fox jumped over the lazy dogs".getBytes("UTF-8")

    assert(expectedBytes sameElements actualBytes)
  }

  "the compute hex method should return the correct hash of an array of bytes" in {
    val input = Array(42, 16, 43).map(_.toByte)

    val actualHash = input.computeHashAsHex
    val expectedHash = "220ED007F88E894C0AA52A193C826EED7B37AD84A2C4EA69FD538991550F8C46"

    assert(expectedHash == actualHash)
  }
}

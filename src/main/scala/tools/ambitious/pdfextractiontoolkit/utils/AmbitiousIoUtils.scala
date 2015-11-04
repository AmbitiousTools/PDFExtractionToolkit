package tools.ambitious.pdfextractiontoolkit.utils

import java.io.InputStream
import java.net.URL
import java.security.MessageDigest

import org.apache.commons.io.IOUtils

object AmbitiousIoUtils {

  private val digest: MessageDigest = MessageDigest.getInstance("SHA-256")

  implicit class URLUtils(url: URL) {
    def toBytes: Array[Byte] = {
      var inputStream: InputStream = null

      try {
        inputStream = url.openStream()

        IOUtils.toByteArray(inputStream)
      } finally {
        if (inputStream != null) {
          inputStream.close()
        }
      }
    }
  }

  implicit class ByteArrayUtils(bytes: Array[Byte]) {
    def computeHashAsHex: String = {
      digest.digest(bytes)
        .map("%02X" format _)
        .mkString
    }
  }

}

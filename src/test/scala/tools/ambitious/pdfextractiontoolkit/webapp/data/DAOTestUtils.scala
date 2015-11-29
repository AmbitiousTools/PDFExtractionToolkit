package tools.ambitious.pdfextractiontoolkit.webapp.data

import java.nio.file.{Files, Path, Paths}

import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.Await
import scala.concurrent.duration._

object DAOTestUtils {
  private val config: Config = ConfigFactory.load()

  private val testDBPath: String = config.getString("testDB.path")

  val testConfigName: String = "testDB"

  val testDBFile: Path = Paths.get(testDBPath)

  def constructCleanRootDAO: RootDAO = {
    Files.deleteIfExists(testDBFile)

    val rootDAO: RootDAO = RootDAO.forConfigName(testConfigName)

    Await.result(rootDAO.initialiseIfNeeded(), 30.seconds)

    rootDAO
  }
}

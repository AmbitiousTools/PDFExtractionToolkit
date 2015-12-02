package tools.ambitious.pdfextractiontoolkit.webapp.data

import java.nio.file.{Files, Path, Paths}

import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.Await
import scala.concurrent.duration._

object DAOTestUtils {
  private val applicationConfig: Config = ConfigFactory.load()

  private val workingDirPathFromConfig: String = applicationConfig.getString("workingDir")

  def createAndGetWorkingDirectory(): Path = {
    val workingDir: Path = Paths.get(workingDirPathFromConfig)

    workingDir.toFile.mkdirs()

    workingDir
  }

  private val testDBPathFromConfig: String = applicationConfig.getString("testDB.path")

  val testDBFile: Path = Paths.get(testDBPathFromConfig)

  val testDBConfigName: String = "testDB"

  def constructCleanRootDAO: RootDAO = {
    Files.deleteIfExists(testDBFile)
    DAOTestUtils.testDBFile.getParent.toFile.mkdirs()

    val rootDAO: RootDAO = RootDAO.forConfigName(testDBConfigName)

    Await.result(rootDAO.initialiseIfNeeded(), 30.seconds)

    rootDAO
  }
}

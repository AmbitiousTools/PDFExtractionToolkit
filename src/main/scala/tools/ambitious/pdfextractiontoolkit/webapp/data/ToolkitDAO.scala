package tools.ambitious.pdfextractiontoolkit.webapp.data

import scala.concurrent.Future
import slick.driver.SQLiteDriver.api.Database

private[data] trait ToolkitDAO {
  def database: Database

  def initialiseIfNeeded(): Future[Unit]
}

private[data] object ToolkitDAO {
  def forConfigName(configName: String): ToolkitDAO = new ToolkitDAOImpl(configName)
}
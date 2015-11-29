package tools.ambitious.pdfextractiontoolkit.webapp.data

import slick.driver.SQLiteDriver.api.Database

import scala.concurrent.Future

private[data] trait RootDAO {
  def database: Database

  def initialiseIfNeeded(): Future[Unit]
}

private[data] object RootDAO {
  def forConfigName(configName: String): RootDAO = new RootDAOImpl(configName)
}
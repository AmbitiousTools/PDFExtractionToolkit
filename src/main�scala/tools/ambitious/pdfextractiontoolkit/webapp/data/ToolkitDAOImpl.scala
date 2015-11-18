package tools.ambitious.pdfextractiontoolkit.webapp.data

import slick.driver.SQLiteDriver.api._
import slick.jdbc.meta.MTable
import tools.ambitious.pdfextractiontoolkit.webapp.data.model.Documents

import scala.concurrent.Future

private[data] class ToolkitDAOImpl(databaseConfigName: String) extends ToolkitDAO {
  val database = Database.forConfig(databaseConfigName)

  private def isInitialised: Future[Boolean] =
    database.run(MTable.getTables).map(tables => tables.nonEmpty)

  def initialiseIfNeeded(): Future[Unit] =
    isInitialised.flatMap(alreadyInitialised => if (!alreadyInitialised) initialise() else Future())

  private def initialise(): Future[Unit] =
    database.run(createTablesAction)

  private lazy val createTablesAction: DBIO[Unit] =
    TableQuery[Documents].schema.create
}

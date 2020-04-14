import java.sql.{Connection, SQLException, Timestamp}

import scalikejdbc._

class FetchData(databaseConnection: DatabaseConnection) {
  implicit val connection: Connection = databaseConnection.connection

  def fetchMaxBlogPublicationDate: Option[Timestamp] = {
    implicit val session = AutoSession


    try {


      sql" SELECT MAX(published_on) FROM blog".map(rs => rs.timestamp(1)).single().apply()

    }
    catch {
      case ex: SQLException => throw new SQLException(ex)
      case ex: Exception => throw new Exception(ex)
    }
    finally {
      session.close()
    }

  }


}

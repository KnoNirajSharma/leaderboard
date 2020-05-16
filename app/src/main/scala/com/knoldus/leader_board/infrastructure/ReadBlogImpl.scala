package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}
import java.time.{ZoneId, ZonedDateTime}

import com.knoldus.leader_board.{BlogCount, DatabaseConnection, GetMonthlyCount}
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import scalikejdbc.{DB, DBSession, SQL}

class ReadBlogImpl(config: Config) extends ReadBlog with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetches knolders who have written blogs.
   *
   * @return List of knolder with its wordpress id.
   */
  override def fetchKnoldersWithBlogs: List[BlogCount] = {
    logger.info("Fetching details of knolders with blogs.")
    SQL("SELECT knolder.id, knolder.wordpress_id, knolder.full_name, COUNT(blog.id) AS count FROM blog INNER JOIN " +
      "knolder ON knolder.wordpress_id = blog.wordpress_id GROUP BY knolder.id, knolder.wordpress_id, " +
      "knolder.full_name")
      .map(rs => BlogCount(rs.int("id"), rs.string("wordpress_id"),
        rs.string("full_name"), rs.int("count"))).list().apply()
  }

  /**
   * Fetches number of blogs of each knolder in current month from blog table.
   *
   * @return List of monthly data of each knolder.
   */
  override def fetchKnoldersWithMonthlyBlogs: List[GetMonthlyCount] = {
    val currentMonth = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Calcutta"))
      .withDayOfMonth(1).toLocalDate.atStartOfDay())

    val nextMonth = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Calcutta"))
      .withDayOfMonth(1).toLocalDate.plusMonths(1).atStartOfDay())

    SQL("SELECT knolder.id, knolder.full_name, COUNT(blog.id) AS count FROM blog RIGHT JOIN knolder ON " +
      "knolder.wordpress_id = blog.wordpress_id AND published_on >= ? AND published_on < ? " +
      "GROUP BY knolder.id, knolder.wordpress_id, knolder.full_name;").bind(currentMonth, nextMonth)
      .map(rs => GetMonthlyCount(rs.int("id"), rs.string("full_name"),
        rs.int("count"))).list.apply()
  }
}

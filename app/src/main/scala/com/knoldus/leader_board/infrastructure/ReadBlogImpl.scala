package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}

import com.knoldus.leader_board.{Constant, DatabaseConnection, GetCount}
import com.typesafe.config.Config
import com.typesafe.scalalogging._
import scalikejdbc.{DB, DBSession, SQL}

class ReadBlogImpl(config: Config) extends ReadBlog with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetches number of blogs of each knolder from blog table.
   *
   * @return List of all time data of each knolder.
   */
  override def fetchKnoldersWithBlogs: List[GetCount] = {
    logger.info("Fetching details of knolders with blogs.")
    SQL("SELECT knolder.id, knolder.full_name, COUNT(blog.id) AS count FROM blog RIGHT JOIN knolder ON " +
      "knolder.wordpress_id = blog.wordpress_id GROUP BY knolder.id, knolder.wordpress_id, knolder.full_name")
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("count"))).list().apply()
  }

  /**
   * Fetches number of blogs of each knolder in current month from blog table.
   *
   * @return List of monthly data of each knolder.
   */
  override def fetchKnoldersWithMonthlyBlogs: List[GetCount] = {
    logger.info("Fetching details of knolders with blogs of current month.")
    val currentMonth = Timestamp.valueOf(Constant.CURRENT_TIME
      .withDayOfMonth(1).toLocalDate.atStartOfDay())
    val nextMonth = Timestamp.valueOf(Constant.CURRENT_TIME
      .withDayOfMonth(1).toLocalDate.plusMonths(1).atStartOfDay())
    SQL("SELECT knolder.id, knolder.full_name, COUNT(blog.id) AS count FROM blog RIGHT JOIN knolder ON " +
      "knolder.wordpress_id = blog.wordpress_id AND published_on >= ? AND published_on < ? " +
      "GROUP BY knolder.id, knolder.wordpress_id, knolder.full_name;").bind(currentMonth, nextMonth)
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("count"))).list.apply()
  }

  /**
   * Fetches number of blogs of each knolder in first month of quarter from blog table.
   *
   * @return List of quarterly data of each knolder.
   */
  override def fetchKnoldersWithQuarterFirstMonthBlogs: List[GetCount] = {
    logger.info("Fetching details of knolders with blogs of first month of quarter.")
    val firstMonth = Timestamp.valueOf(Constant.CURRENT_TIME
      .withDayOfMonth(1).toLocalDate.minusMonths(3).atStartOfDay())
    val nextMonth = Timestamp.valueOf(Constant.CURRENT_TIME
      .withDayOfMonth(1).toLocalDate.minusMonths(2).atStartOfDay())
    SQL("SELECT knolder.id, knolder.full_name, COUNT(blog.id) AS count FROM blog RIGHT JOIN knolder ON " +
      "knolder.wordpress_id = blog.wordpress_id AND published_on >= ? AND published_on < ? " +
      "GROUP BY knolder.id, knolder.wordpress_id, knolder.full_name;").bind(firstMonth, nextMonth)
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("count"))).list.apply()
  }

  /**
   * Fetches number of blogs of each knolder in second month of quarter from blog table.
   *
   * @return List of quarterly data of each knolder.
   */
  override def fetchKnoldersWithQuarterSecondMonthBlogs: List[GetCount] = {
    logger.info("Fetching details of knolders with blogs of second month of quarter.")
    val secondMonth = Timestamp.valueOf(Constant.CURRENT_TIME
      .withDayOfMonth(1).toLocalDate.minusMonths(2).atStartOfDay())
    val nextMonth = Timestamp.valueOf(Constant.CURRENT_TIME
      .withDayOfMonth(1).toLocalDate.minusMonths(1).atStartOfDay())
    SQL("SELECT knolder.id, knolder.full_name, COUNT(blog.id) AS count FROM blog RIGHT JOIN knolder ON " +
      "knolder.wordpress_id = blog.wordpress_id AND published_on >= ? AND published_on < ? " +
      "GROUP BY knolder.id, knolder.wordpress_id, knolder.full_name;").bind(secondMonth, nextMonth)
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("count"))).list.apply()
  }

  /**
   * Fetches number of blogs of each knolder in third month of quarter from blog table.
   *
   * @return List of monthly data of each knolder.
   */
  override def fetchKnoldersWithQuarterThirdMonthBlogs: List[GetCount] = {
    logger.info("Fetching details of knolders with blogs of third month of quarter.")
    val thirdMonth = Timestamp.valueOf(Constant.CURRENT_TIME
      .withDayOfMonth(1).toLocalDate.minusMonths(1).atStartOfDay())
    val nextMonth = Timestamp.valueOf(Constant.CURRENT_TIME
      .withDayOfMonth(1).toLocalDate.atStartOfDay())
    SQL("SELECT knolder.id, knolder.full_name, COUNT(blog.id) AS count FROM blog RIGHT JOIN knolder ON " +
      "knolder.wordpress_id = blog.wordpress_id AND published_on >= ? AND published_on < ? " +
      "GROUP BY knolder.id, knolder.wordpress_id, knolder.full_name;").bind(thirdMonth, nextMonth)
      .map(rs => GetCount(rs.int("id"), rs.string("full_name"),
        rs.int("count"))).list.apply()
  }
}

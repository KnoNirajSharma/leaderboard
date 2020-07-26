package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board._
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class FetchKnolderDetailsImpl(config: Config) extends FetchKnolderDetails with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetching monthly details of specific knolder.
   *
   * @return List of details of knolders.
   */
  override def fetchKnolderMonthlyDetails(knolderId: Int, month: Int, year: Int): Option[KnolderDetails] = {
    logger.info("Fetching monthly details of specific knolder.")

    val contributions = List(fetchKnolderBlogMonthlyDetails(month, year, knolderId), fetchKnolderKnolxMonthlyDetails(month, year, knolderId),
      fetchKnolderWebinarMonthlyDetails(month, year, knolderId))

    SQL(
      s"""SELECT
      knolder.full_name,
      COUNT(DISTINCT blog.title) * ${config.getInt("scorePerBlog")} + COUNT(DISTINCT knolx.title) * ${config.getInt("scorePerKnolx")}
      + COUNT(DISTINCT webinar.title) * ${config.getInt("scorePerWebinar")} AS monthly_score
    FROM
    knolder
    LEFT JOIN
      blog
    ON knolder.wordpress_id = blog.wordpress_id
    AND EXTRACT(month
      FROM
      blog.published_on) = ?
    AND EXTRACT(year
      FROM
     blog.published_on) = ?
    LEFT JOIN
      knolx
    ON knolder.email_id = knolx.email_id
    AND EXTRACT(month
      FROM
      knolx.delivered_on) = ?
    AND EXTRACT(year
      FROM
      knolx.delivered_on) = ?
    LEFT JOIN
      webinar
    ON knolder.email_id = webinar.email_id
    AND EXTRACT(month
      FROM
      webinar.delivered_on) = ?
    AND EXTRACT(year
      FROM
      webinar.delivered_on) = ?
    WHERE
    knolder.id = ?
    GROUP BY
      knolder.full_name""")
      .bind(month, year, month, year, month, year, knolderId)
      .map(rs => KnolderDetails(rs.string("full_name"), rs.int("monthly_score"), contributions))
      .single().apply()
  }

  def fetchKnolderWebinarMonthlyDetails(month: Int, year: Int, knolderId: Int): Option[Contribution] = {

    val webinarTitles = SQL(
      """
     SELECT
      webinar.title,
      webinar.delivered_on
        FROM
        knolder
        RIGHT JOIN
        webinar
        ON knolder.email_id = webinar.email_id
    WHERE
    EXTRACT(month
      FROM
      delivered_on) = ?
    AND EXTRACT(year
      FROM
      delivered_on) = ?
    AND knolder.id = ? """)
      .bind(month, year, knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("delivered_on")))
      .list().apply()

    SQL(
      s"""
      SELECT
      COUNT(webinar.title) AS webinarCount,
      COUNT(webinar.title) * ${config.getInt("scorePerWebinar")} AS WebinarScore
        FROM
        webinar
        RIGHT JOIN
        knolder
        ON knolder.email_id = webinar.email_id
    AND EXTRACT(month
      FROM
      delivered_on) = ?
    AND EXTRACT(year
      FROM
      delivered_on) = ?
    WHERE
    knolder.id = ? """)
      .bind(month, year, knolderId)
      .map(rs => Contribution("Webinar", rs.int("webinarCount"), rs.int("webinarScore"), webinarTitles))
      .single().apply()
  }

  def fetchKnolderBlogMonthlyDetails(month: Int, year: Int, knolderId: Int): Option[Contribution] = {

    val blogTitles = SQL(
      """SELECT
    blog.title,
    blog.published_on
    FROM
    knolder
    RIGHT JOIN
      blog
    ON knolder.wordpress_id = blog.wordpress_id
    WHERE
    EXTRACT(month
      FROM
      published_on) = ?
    AND EXTRACT(year
      FROM
      published_on) = ?
    AND knolder.id = ? """)
      .bind(month, year, knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("published_on")))
      .list().apply()

    SQL(
      s"""
      SELECT
      COUNT(blog.title) AS blogCount,
      COUNT(blog.title) * ${config.getInt("scorePerBlog")} AS blogScore
        FROM
        blog
        RIGHT JOIN
        knolder
        ON knolder.wordpress_id = blog.wordpress_id
    AND EXTRACT(month
      FROM
      published_on) = ?
    AND EXTRACT(year
      FROM
      published_on) = ?
    WHERE
    knolder.id = ? """)
      .bind(month, year, knolderId)
      .map(rs => Contribution("Blogs", rs.int("blogCount"), rs.int("blogScore"), blogTitles))
      .single().apply()
  }

  def fetchKnolderKnolxMonthlyDetails(month: Int, year: Int, knolderId: Int): Option[Contribution] = {

    val knolxTitles = SQL(
      """SELECT
      knolx.title,
      knolx.delivered_on
        FROM
        knolder
        RIGHT JOIN
        knolx
        ON knolder.email_id = knolx.email_id
    WHERE
    EXTRACT(month
      FROM
      delivered_on) = ?
    AND EXTRACT(year
      FROM
      delivered_on) = ?
    AND knolder.id = ? """)
      .bind(month, year, knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("delivered_on")))
      .list().apply()


    SQL(
      s"""SELECT
      COUNT(knolx.title) AS knolxCount,
      COUNT(knolx.title) *  ${config.getInt("scorePerKnolx")} AS knolxScore
        FROM
        knolx
        RIGHT JOIN
        knolder
        ON knolder.email_id =knolx.email_id
    AND EXTRACT(month
      FROM
      delivered_on) = ?
    AND EXTRACT(year
      FROM
      delivered_on) = ?
    WHERE
    knolder.id = ? """)
      .bind(month, year, knolderId)
      .map(rs => Contribution("Knolx", rs.int("knolxCount"), rs.int("knolxScore"), knolxTitles))
      .single().apply()
  }

  /**
   * Fetching all time details of specific knolder.
   *
   * @return List of details of knolders.
   */
  override def fetchKnolderAllTimeDetails(knolderId: Int): Option[KnolderDetails] = {
    logger.info("Fetching all time details of specific knolder.")

    val contributions = List(fetchAllTimeBlogDetails(knolderId), fetchAllTimeknolxDetails(knolderId), fetchAllTimeWebinarDetails(knolderId))

    SQL(
      s"""
      SELECT
      knolder.full_name,
      COUNT(DISTINCT blog.title) * ${config.getInt("scorePerBlog")} + COUNT(DISTINCT knolx.title) * ${config.getInt("scorePerKnolx")}
      + COUNT(DISTINCT webinar.title) * ${config.getInt("scorePerWebinar")} AS score
    FROM
    knolder
    LEFT JOIN
      blog
    ON knolder.wordpress_id = blog.wordpress_id
    LEFT JOIN
      knolx
    ON knolder.email_id = knolx.email_id
    LEFT JOIN
      webinar
    ON knolder.email_id = webinar.email_id
    WHERE
    knolder.id = ?
    GROUP BY
      knolder.full_name""")
      .bind(knolderId)
      .map(rs => KnolderDetails(rs.string("full_name"), rs.int("score"), contributions))
      .single().apply()
  }

  def fetchAllTimeknolxDetails(knolderId: Int): Option[Contribution] = {
    val knolxTitles = SQL(
      """SELECT
      knolx.title,
      knolx.delivered_on
        FROM
        knolder
        RIGHT JOIN
        knolx
        ON knolder.email_id = knolx.email_id
    WHERE
    knolder.id = ? """)
      .bind(knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("delivered_on")))
      .list().apply()

    SQL(
      s"""
      SELECT
      COUNT(knolx.title) AS knolxCount,
      COUNT(knolx.title) * ${config.getInt("scorePerKnolx")} AS KnolxScore
        FROM
        knolx
        LEFT JOIN
        knolder
        ON knolder.email_id = knolx.email_id
    WHERE
    knolder.id = ? """)
      .bind(knolderId)
      .map(rs => Contribution("Knolx", rs.int("knolxCount"), rs.int("knolxScore"), knolxTitles))
      .single().apply()
  }

  def fetchAllTimeWebinarDetails(knolderId: Int): Option[Contribution] = {
    val webinarTitles = SQL(
      """
      SELECT
      webinar.title,
      webinar.delivered_on
        FROM
        knolder
        RIGHT JOIN
        webinar
        ON knolder.email_id = webinar.email_id
    WHERE
    knolder.id = ? """)
      .bind(knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("delivered_on")))
      .list().apply()

    SQL(
      s"""
      SELECT
      COUNT(webinar.title) AS webinarCount,
      COUNT(webinar.title) *  ${config.getInt("scorePerWebinar")} AS webinarScore
        FROM
        webinar
        LEFT JOIN
        knolder
        ON knolder.email_id = webinar.email_id
    WHERE
    knolder.id = ? """)
      .bind(knolderId)
      .map(rs => Contribution("Webinar", rs.int("webinarCount"), rs.int("webinarScore"), webinarTitles))
      .single().apply()
  }

  def fetchAllTimeBlogDetails(knolderId: Int): Option[Contribution] = {
    val blogTitles = SQL(
    """
    SELECT
      blog.title,
    blog.published_on
    FROM
    knolder
    RIGHT JOIN
      blog
    ON knolder.wordpress_id = blog.wordpress_id
    WHERE
    knolder.id = ? """)
      .bind(knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("published_on")))
      .list().apply()

    SQL(s"""
      SELECT
      COUNT(blog.title) AS blogCount,
      COUNT(blog.title) * ${config.getInt("scorePerBlog")} AS blogScore
        FROM
        blog
        RIGHT JOIN
        knolder
        ON knolder.wordpress_id =blog.wordpress_id
    WHERE
    knolder.id = ? """)
      .bind(knolderId)
      .map(rs => Contribution("Blogs", rs.int("blogCount"), rs.int("blogScore"), blogTitles))
      .single().apply()
  }
}

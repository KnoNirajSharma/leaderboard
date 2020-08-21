package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board._
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class FetchKnolderContributionDetailsImpl(config: Config) extends FetchKnolderContributionDetails with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetching monthly details of specific knolder.
   *
   * @return List of details of knolders.
   */
  override def fetchKnolderMonthlyDetails(knolderId: Int, month: Int, year: Int): Option[KnolderDetails] = {
    logger.info("Fetching monthly details of specific knolder.")

    val contributions = List(fetchKnolderMonthlyBlogDetails(month, year, knolderId),
      fetchKnolderMonthlyKnolxDetails(month, year, knolderId), fetchKnolderMonthlyWebinarDetails(month, year, knolderId),
      fetchKnolderMonthlyTechHubDetails(month, year, knolderId), fetchKnolderMonthlyOsContributionDetails(month, year, knolderId))

    SQL(
      s"""SELECT
      knolder.full_name,
      COUNT(DISTINCT blog.id) * ${config.getInt("scorePerBlog")} + COUNT(DISTINCT knolx.id) * ${config.getInt("scorePerKnolx")}
      + COUNT(DISTINCT webinar.id) * ${config.getInt("scorePerWebinar")} + COUNT(DISTINCT techhub.id)
       * ${config.getInt("scorePerTechHub")} + COUNT(DISTINCT oscontribution.id) * ${config.getInt("scorePerOsContribution")} AS monthly_score
    FROM knolder
    LEFT JOIN blog ON knolder.wordpress_id = blog.wordpress_id
    AND EXTRACT(month FROM blog.published_on) = ?
    AND EXTRACT(year FROM blog.published_on) = ?
    LEFT JOIN knolx ON knolder.email_id = knolx.email_id
    AND EXTRACT(month FROM knolx.delivered_on) = ?
    AND EXTRACT(year FROM knolx.delivered_on) = ?
    LEFT JOIN webinar ON knolder.email_id = webinar.email_id
    AND EXTRACT(month FROM webinar.delivered_on) = ?
    AND EXTRACT(year FROM webinar.delivered_on) = ?
    LEFT JOIN techhub ON knolder.email_id = techhub.email_id
    AND EXTRACT(month FROM techhub.uploaded_on) = ?
    AND EXTRACT(year FROM techhub.uploaded_on) = ?
    LEFT JOIN oscontribution ON knolder.email_id = oscontribution.email_id
    AND EXTRACT(month FROM oscontribution.contributed_on) = ?
    AND EXTRACT(year FROM oscontribution.contributed_on) = ?
    WHERE knolder.id = ?
    GROUP BY knolder.full_name""")
      .bind(month, year, month, year, month, year, month, year, month, year, knolderId)
      .map(rs => KnolderDetails(rs.string("full_name"), rs.int("monthly_score"), contributions))
      .single().apply()
  }

  def fetchKnolderMonthlyWebinarDetails(month: Int, year: Int, knolderId: Int): Option[Contribution] = {

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
    AND knolder.id = ? ORDER BY delivered_on DESC""")
      .bind(month, year, knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("delivered_on")))
      .list().apply()

    val webinarCount = webinarTitles.length
    val webinarScore = webinarTitles.length * config.getInt("scorePerWebinar")

    Option(Contribution("Webinar", webinarCount, webinarScore, webinarTitles))
  }

  def fetchKnolderMonthlyBlogDetails(month: Int, year: Int, knolderId: Int): Option[Contribution] = {

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
    AND knolder.id = ?  ORDER BY published_on DESC""")
      .bind(month, year, knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("published_on")))
      .list().apply()

    val blogCount = blogTitles.length
    val blogScore = blogTitles.length * config.getInt("scorePerBlog")

    Option(Contribution("Blogs", blogCount, blogScore, blogTitles))
  }

  def fetchKnolderMonthlyKnolxDetails(month: Int, year: Int, knolderId: Int): Option[Contribution] = {

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
    AND knolder.id = ?  ORDER BY delivered_on DESC""")
      .bind(month, year, knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("delivered_on")))
      .list().apply()

    val knolxCount = knolxTitles.length
    val knolxScore = knolxTitles.length * config.getInt("scorePerKnolx")

    Option(Contribution("Knolx", knolxCount, knolxScore, knolxTitles))
  }


  def fetchKnolderMonthlyTechHubDetails(month: Int, year: Int, knolderId: Int): Option[Contribution] = {

    val techHubTitles = SQL(
      """ SELECT
      techhub.title,
      techhub.uploaded_on
        FROM
        knolder
        RIGHT JOIN
        techhub
        ON knolder.email_id = techhub.email_id
    WHERE
    EXTRACT(month
      FROM
      uploaded_on) = ?
    AND EXTRACT(year
      FROM
      uploaded_on) = ?
    AND knolder.id = ? ORDER BY uploaded_on DESC """)
      .bind(month, year, knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("uploaded_on")))
      .list().apply()

    val techHubCount = techHubTitles.length
    val techHubScore = techHubTitles.length * config.getInt("scorePerTechHub")

    Option(Contribution("TechHub", techHubCount, techHubScore, techHubTitles))
  }

  def fetchKnolderMonthlyOsContributionDetails(month: Int, year: Int, knolderId: Int): Option[Contribution] = {

    val osContributionTitles = SQL(
      """ SELECT
      oscontribution.title,
      oscontribution.contributed_on
        FROM
        knolder
        RIGHT JOIN
        oscontribution
        ON knolder.email_id = oscontribution.email_id
    WHERE
    EXTRACT(month
      FROM
      contributed_on) = ?
    AND EXTRACT(year
      FROM
      contributed_on) = ?
    AND knolder.id = ? ORDER BY contributed_on desc """)
      .bind(month, year, knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("contributed_on")))
      .list().apply()

    val osContributionCount = osContributionTitles.length
    val osContributionScore = osContributionTitles.length * config.getInt("scorePerOsContribution")

    Option(Contribution("OSContribution", osContributionCount, osContributionScore, osContributionTitles))
  }


  /**
   * Fetching all time details of specific knolder.
   *
   * @return List of details of knolders.
   */
  override def fetchKnolderAllTimeDetails(knolderId: Int): Option[KnolderDetails] = {
    logger.info("Fetching all time details of specific knolder.")

    val contributions = List(fetchAllTimeBlogDetails(knolderId), fetchAllTimeknolxDetails(knolderId), fetchAllTimeWebinarDetails(knolderId)
      , fetchAllTimeTechHubDetails(knolderId), fetchAllTimeOsContributionDetails(knolderId))

    SQL(
      s"""
      SELECT
      knolder.full_name,
     COUNT(DISTINCT blog.id) * ${config.getInt("scorePerBlog")} + COUNT(DISTINCT knolx.id) * ${config.getInt("scorePerKnolx")}
      + COUNT(DISTINCT webinar.id) * ${config.getInt("scorePerWebinar")} + COUNT(DISTINCT techhub.id)
       * ${config.getInt("scorePerTechHub")} + COUNT(DISTINCT oscontribution.id) * ${config.getInt("scorePerOsContribution")} AS score
    FROM
    knolder
    LEFT JOIN
      blog
    ON knolder.wordpress_id = blog.wordpress_id
    LEFT JOIN
      techhub
    ON knolder.email_id = techhub.email_id
    LEFT JOIN
      knolx
    ON knolder.email_id = knolx.email_id
    LEFT JOIN
      webinar
    ON knolder.email_id = webinar.email_id
     LEFT JOIN
      oscontribution
    ON knolder.email_id = oscontribution.email_id
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
    knolder.id = ?  ORDER BY delivered_on DESC""")
      .bind(knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("delivered_on")))
      .list().apply()

    val knolxCount = knolxTitles.length
    val knolxScore = knolxTitles.length * config.getInt("scorePerKnolx")

    Option(Contribution("Knolx", knolxCount, knolxScore, knolxTitles))
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
    knolder.id = ? ORDER BY delivered_on DESC """)
      .bind(knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("delivered_on")))
      .list().apply()

    val webinarCount = webinarTitles.length
    val webinarScore = webinarTitles.length * config.getInt("scorePerWebinar")

    Option(Contribution("Webinar", webinarCount, webinarScore, webinarTitles))

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
    knolder.id = ?  ORDER BY published_on DESC""")
      .bind(knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("published_on")))
      .list().apply()

    val blogCount = blogTitles.length
    val blogScore = blogTitles.length * config.getInt("scorePerBlog")

    Option(Contribution("Blogs", blogCount, blogScore, blogTitles))
  }

  def fetchAllTimeTechHubDetails(knolderId: Int): Option[Contribution] = {
    val techHubTitles = SQL(
      """SELECT
      techhub.title,
      techhub.uploaded_on
        FROM
        knolder
        RIGHT JOIN
        techhub
        ON knolder.email_id = techhub.email_id
    WHERE
    knolder.id = ? ORDER BY uploaded_on DESC""")
      .bind(knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("uploaded_on")))
      .list().apply()

    val techHubCount = techHubTitles.length
    val techHubScore = techHubTitles.length * config.getInt("scorePerTechHub")

    Option(Contribution("TechHub", techHubCount, techHubScore, techHubTitles))

  }

  def fetchAllTimeOsContributionDetails(knolderId: Int): Option[Contribution] = {
    val osContributionTitles = SQL(
      """SELECT
      oscontribution.title,
      oscontribution.contributed_on
        FROM
        knolder
        RIGHT JOIN
        oscontribution
        ON knolder.email_id = oscontribution.email_id
    WHERE
    knolder.id = ? ORDER BY contributed_on DESC """)
      .bind(knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("contributed_on")))
      .list().apply()
    val osContributionCount = osContributionTitles.length
    val osContributionScore = osContributionTitles.length * config.getInt("scorePerOsContribution")

    Option(Contribution("OSContribution", osContributionCount, osContributionScore, osContributionTitles))
  }
}

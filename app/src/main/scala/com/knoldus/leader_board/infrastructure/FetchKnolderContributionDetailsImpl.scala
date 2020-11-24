package com.knoldus.leader_board.infrastructure

import java.sql.Connection
import java.time.Month

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
    val knolderBlogDetails = fetchKnolderMonthlyBlogDetails(month, year, knolderId)
    val knolderKnolxDetails = fetchKnolderMonthlyKnolxDetails(month, year, knolderId)
    val knolderWebinarDetails = fetchKnolderMonthlyWebinarDetails(month, year, knolderId)
    val knolderTechHubDetails = fetchKnolderMonthlyTechHubDetails(month, year, knolderId)
    val knolderOSContributionDetails = fetchKnolderMonthlyOsContributionDetails(month, year, knolderId)
    val knolderConferenceDetails = fetchKnolderMonthlyConferenceDetails(month, year, knolderId)
    val knolderBookDetails = fetchKnolderMonthlyBookDetails(month, year, knolderId)
    val knolderResearchPaperDetails = fetchKnolderMonthlyResearchPaperDetails(month, year, knolderId)
    val knolderMeetupDetails = fetchKnolderMonthlyMeetupDetails(month, year, knolderId)
    val score = knolderBlogDetails.contributionScore + knolderKnolxDetails.contributionScore + knolderWebinarDetails.contributionScore +
      knolderTechHubDetails.contributionScore + knolderOSContributionDetails.contributionScore + knolderConferenceDetails.contributionScore +
      knolderBookDetails.contributionScore + knolderResearchPaperDetails.contributionScore + knolderMeetupDetails.contributionScore

    val contributions = List(knolderBlogDetails, knolderKnolxDetails, knolderWebinarDetails, knolderTechHubDetails,
      knolderOSContributionDetails, knolderConferenceDetails, knolderBookDetails, knolderResearchPaperDetails, knolderMeetupDetails)

    SQL(
      """select knolder.full_name from knolder where knolder.id = ? """.stripMargin)
      .bind(knolderId)
      .map(rs => KnolderDetails(rs.string("full_name"), score, contributions))
      .single().apply()

  }

  def fetchKnolderMonthlyWebinarDetails(month: Int, year: Int, knolderId: Int): Contribution = {

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
    val webinarScore = SQL(
      """
        |select webinar_score from monthlycontribution where knolder_id= ? and month = ? and year=?
        |""".stripMargin).bind(knolderId, Month.of(month).toString, year).map(rs => rs.int("webinar_score")).single().apply()

    val webinarCount = webinarTitles.length

    webinarScore match {
      case Some(score) => Contribution("Webinar", webinarCount, score, webinarTitles)
      case None => Contribution("Webinar", webinarCount, 0, webinarTitles)
    }
  }

  def fetchKnolderMonthlyBlogDetails(month: Int, year: Int, knolderId: Int): Contribution = {

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

    val blogScore = SQL(
      """
        |select blog_score from monthlycontribution where knolder_id= ? and month = ? and year=?
        |""".stripMargin).bind(knolderId, Month.of(month).toString, year).map(rs => rs.int("blog_score")).single().apply()

    val blogCount = blogTitles.length

    blogScore match {
      case Some(score) => Contribution("Blogs", blogCount, score, blogTitles)
      case None => Contribution("Blogs", blogCount, 0, blogTitles)
    }
  }

  def fetchKnolderMonthlyKnolxDetails(month: Int, year: Int, knolderId: Int): Contribution = {

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

    val knolxScore = SQL(
      """
        |select knolx_score from monthlycontribution where knolder_id= ? and month = ? and year=?
        |""".stripMargin).bind(knolderId, Month.of(month).toString, year).map(rs => rs.int("knolx_score")).single().apply()

    val knolxCount = knolxTitles.length

    knolxScore match {
      case Some(score) => Contribution("Knolx", knolxCount, score, knolxTitles)
      case None => Contribution("Knolx", knolxCount, 0, knolxTitles)
    }
  }


  def fetchKnolderMonthlyTechHubDetails(month: Int, year: Int, knolderId: Int): Contribution = {

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

    val techHubScore = SQL(
      """
        |select techhub_score from monthlycontribution where knolder_id= ? and month = ? and year=?
        |""".stripMargin).bind(knolderId, Month.of(month).toString, year).map(rs => rs.int("techhub_score")).single().apply()

    val techHubCount = techHubTitles.length

    techHubScore match {
      case Some(score) => Contribution("TechHub", techHubCount, score, techHubTitles)
      case None => Contribution("TechHub", techHubCount, 0, techHubTitles)
    }
  }

  def fetchKnolderMonthlyOsContributionDetails(month: Int, year: Int, knolderId: Int): Contribution = {

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

    val osContributionScore = SQL(
      """
        |select oscontribution_score from monthlycontribution where knolder_id= ? and month = ? and year=?
        |""".stripMargin).bind(knolderId, Month.of(month).toString, year).map(rs => rs.int("oscontribution_score")).single().apply()

    val osContributionCount = osContributionTitles.length

    osContributionScore match {
      case Some(score) => Contribution("OS Contribution", osContributionCount, score, osContributionTitles)
      case None => Contribution("OS Contribution", osContributionCount, 0, osContributionTitles)
    }
  }

  def fetchKnolderMonthlyConferenceDetails(month: Int, year: Int, knolderId: Int): Contribution = {

    val conferenceTitles = SQL(
      """ SELECT
      conference.title,
      conference.delivered_on
        FROM
        knolder
        RIGHT JOIN
        conference
        ON knolder.email_id = conference.email_id
    WHERE
    EXTRACT(month
      FROM
      delivered_on) = ?
    AND EXTRACT(year
      FROM
      delivered_on) = ?
    AND knolder.id = ? ORDER BY delivered_on desc """)
      .bind(month, year, knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("delivered_on")))
      .list().apply()

    val conferenceScore = SQL(
      """
        |select conference_score from monthlycontribution where knolder_id= ? and month = ? and year=?
        |""".stripMargin).bind(knolderId, Month.of(month).toString, year).map(rs => rs.int("conference_score")).single().apply()

    val conferenceCount = conferenceTitles.length

    conferenceScore match {
      case Some(score) => Contribution("Conferences", conferenceCount, score, conferenceTitles)
      case None => Contribution("Conferences", conferenceCount, 0, conferenceTitles)
    }
  }

  def fetchKnolderMonthlyBookDetails(month: Int, year: Int, knolderId: Int): Contribution = {

    val bookTitles = SQL(
      """ SELECT
      book.title,
      book.published_on
        FROM
        knolder
        RIGHT JOIN
        book
        ON knolder.email_id = book.email_id
    WHERE
    EXTRACT(month
      FROM
      published_on) = ?
    AND EXTRACT(year
      FROM
      published_on) = ?
    AND knolder.id = ? ORDER BY published_on desc """)
      .bind(month, year, knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("published_on")))
      .list().apply()

    val bookScore = SQL(
      """
        |select book_score from monthlycontribution where knolder_id= ? and month = ? and year=?
        |""".stripMargin).bind(knolderId, Month.of(month).toString, year).map(rs => rs.int("book_score")).single().apply()

    val bookCount = bookTitles.length

    bookScore match {
      case Some(score) => Contribution("Books", bookCount, score, bookTitles)
      case None => Contribution("Books", bookCount, 0, bookTitles)
    }
  }

  def fetchKnolderMonthlyResearchPaperDetails(month: Int, year: Int, knolderId: Int): Contribution = {

    val researchPaperTitles = SQL(
      """ SELECT
      researchpaper.title,
      researchpaper.published_on
        FROM
        knolder
        RIGHT JOIN
        researchpaper
        ON knolder.email_id = researchpaper.email_id
    WHERE
    EXTRACT(month
      FROM
      published_on) = ?
    AND EXTRACT(year
      FROM
      published_on) = ?
    AND knolder.id = ? ORDER BY published_on desc """)
      .bind(month, year, knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("published_on")))
      .list().apply()

    val researchPaperScore = SQL(
      """
        |select researchpaper_score from monthlycontribution where knolder_id= ? and month = ? and year=?
        |""".stripMargin).bind(knolderId, Month.of(month).toString, year).map(rs => rs.int("researchpaper_score")).single().apply()

    val researchPaperCount = researchPaperTitles.length

    researchPaperScore match {
      case Some(score) => Contribution("Research Paper", researchPaperCount, score, researchPaperTitles)
      case None => Contribution("Research Paper", researchPaperCount, 0, researchPaperTitles)
    }
  }

  def fetchKnolderMonthlyMeetupDetails(month: Int, year: Int, knolderId: Int): Contribution = {

    val meetupTitles = SQL(
      """
     SELECT
      meetup.title,
      meetup.delivered_on
        FROM
        knolder
        RIGHT JOIN
        meetup
        ON knolder.email_id = meetup.email_id
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
    val meetupScore = SQL(
      """
        |select meetup_score from monthlycontribution where knolder_id= ? and month = ? and year=?
        |""".stripMargin).bind(knolderId, Month.of(month).toString, year).map(rs => rs.int("meetup_score")).single().apply()

    val meetupCount = meetupTitles.length

    meetupScore match {
      case Some(score) => Contribution("Meetup", meetupCount, score, meetupTitles)
      case None => Contribution("Meetup", meetupCount, 0, meetupTitles)
    }
  }


  /**
   * Fetching all time details of specific knolder.
   *
   * @return List of details of knolders.
   */
  override def fetchKnolderAllTimeDetails(knolderId: Int): Option[KnolderDetails] = {
    logger.info("Fetching all time details of specific knolder.")

    val contributions = List(fetchAllTimeBlogDetails(knolderId), fetchAllTimeknolxDetails(knolderId), fetchAllTimeWebinarDetails(knolderId)
      , fetchAllTimeTechHubDetails(knolderId), fetchAllTimeOsContributionDetails(knolderId), fetchAllTimeConferenceDetails(knolderId)
      , fetchAllTimeBookDetails(knolderId), fetchAllTimeResearchPaperDetails(knolderId), fetchAllTimeMeetupDetails(knolderId))

    SQL(
      """SELECT
      knolder.full_name,all_time_reputation.score AS score
    FROM
    knolder
    LEFT JOIN
    all_time_reputation
    on knolder.id = all_time_reputation.knolder_id
    WHERE
    knolder.id = ?
    GROUP BY
      knolder.full_name ,all_time_reputation.score""")
      .bind(knolderId)
      .map(rs => KnolderDetails(rs.string("full_name"), rs.int("score"), contributions))
      .single().apply()
  }

  def fetchAllTimeknolxDetails(knolderId: Int): Contribution = {
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

    val knolxScore = SQL(
      """
        | select SUM(knolx_score) as knolx_score from monthlycontribution where knolder_id= ? group by knolder_id
        |""".stripMargin).bind(knolderId).map(rs => rs.int("knolx_score")).single().apply()

    val knolxCount = knolxTitles.length

    knolxScore match {
      case Some(score) => Contribution("Knolx", knolxCount, score, knolxTitles)
      case None => Contribution("Knolx", knolxCount, 0, knolxTitles)
    }
  }

  def fetchAllTimeWebinarDetails(knolderId: Int): Contribution = {
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

    val webinarScore = SQL(
      """
        | select SUM(webinar_score) as webinar_score from monthlycontribution where knolder_id= ? group by knolder_id
        |""".stripMargin).bind(knolderId).map(rs => rs.int("webinar_score")).single().apply()

    val webinarCount = webinarTitles.length

    webinarScore match {
      case Some(score) => Contribution("Webinar", webinarCount, score, webinarTitles)
      case None => Contribution("Webinar", webinarCount, 0, webinarTitles)
    }
  }

  def fetchAllTimeBlogDetails(knolderId: Int): Contribution = {
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

    val blogScore = SQL(
      """
        | select SUM(blog_score) as blog_score from monthlycontribution where knolder_id= ? group by knolder_id
        |""".stripMargin).bind(knolderId).map(rs => rs.int("blog_score")).single().apply()
    val blogCount = blogTitles.length

    blogScore match {
      case Some(score) => Contribution("Blogs", blogCount, score, blogTitles)
      case None => Contribution("Blogs", blogCount, 0, blogTitles)
    }
  }

  def fetchAllTimeTechHubDetails(knolderId: Int): Contribution = {
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
    val techHubScore = SQL(
      """
        | select SUM(techhub_score) as techhub_score from monthlycontribution where knolder_id= ? group by knolder_id
        |""".stripMargin).bind(knolderId).map(rs => rs.int("techhub_score")).single().apply()

    val techHubCount = techHubTitles.length

    techHubScore match {
      case Some(score) => Contribution("TechHub", techHubCount, score, techHubTitles)
      case None => Contribution("TechHub", techHubCount, 0, techHubTitles)
    }
  }

  def fetchAllTimeOsContributionDetails(knolderId: Int): Contribution = {
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

    val osContributionScore = SQL(
      """
        | select SUM(oscontribution_score) as oscontribution_score from monthlycontribution where knolder_id= ? group by knolder_id
        |""".stripMargin).bind(knolderId).map(rs => rs.int("oscontribution_score")).single().apply()
    val osContributionCount = osContributionTitles.length

    osContributionScore match {
      case Some(score) => Contribution("OS Contribution", osContributionCount, score, osContributionTitles)
      case None => Contribution("OS Contribution", osContributionCount, 0, osContributionTitles)
    }
  }

  def fetchAllTimeConferenceDetails(knolderId: Int): Contribution = {
    val conferenceTitles = SQL(
      """SELECT
      conference.title,
      conference.delivered_on
        FROM
        knolder
        RIGHT JOIN
        conference
        ON knolder.email_id = conference.email_id
    WHERE
    knolder.id = ? ORDER BY delivered_on DESC """)
      .bind(knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("delivered_on")))
      .list().apply()

    val conferenceScore = SQL(
      """
        | select SUM(conference_score) as conference_score from monthlycontribution where knolder_id= ? group by knolder_id
        |""".stripMargin).bind(knolderId).map(rs => rs.int("conference_score")).single().apply()
    val conferenceCount = conferenceTitles.length

    conferenceScore match {
      case Some(score) => Contribution("Conferences", conferenceCount, score, conferenceTitles)
      case None => Contribution("Conferences", conferenceCount, 0, conferenceTitles)
    }
  }

  def fetchAllTimeBookDetails(knolderId: Int): Contribution = {
    val bookTitles = SQL(
      """SELECT
      book.title,
      book.published_on
        FROM
        knolder
        RIGHT JOIN
        book
        ON knolder.email_id = book.email_id
    WHERE
    knolder.id = ? ORDER BY published_on DESC """)
      .bind(knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("published_on")))
      .list().apply()

    val bookScore = SQL(
      """
        | select SUM(book_score) as book_score from monthlycontribution where knolder_id= ? group by knolder_id
        |""".stripMargin).bind(knolderId).map(rs => rs.int("book_score")).single().apply()
    val bookCount = bookTitles.length

    bookScore match {
      case Some(score) => Contribution("Books", bookCount, score, bookTitles)
      case None => Contribution("Books", bookCount, 0, bookTitles)
    }
  }

  def fetchAllTimeResearchPaperDetails(knolderId: Int): Contribution = {
    val researchPaperTitles = SQL(
      """SELECT
      researchpaper.title,
      researchpaper.published_on
        FROM
        knolder
        RIGHT JOIN
        researchpaper
        ON knolder.email_id = researchpaper.email_id
    WHERE
    knolder.id = ? ORDER BY published_on DESC """)
      .bind(knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("published_on")))
      .list().apply()

    val researchPaperScore = SQL(
      """
        | select SUM(researchpaper_score) as researchpaper_score from monthlycontribution where knolder_id= ? group by knolder_id
        |""".stripMargin).bind(knolderId).map(rs => rs.int("researchpaper_score")).single().apply()
    val researchPaperCount = researchPaperTitles.length

    researchPaperScore match {
      case Some(score) => Contribution("Research Paper", researchPaperCount, score, researchPaperTitles)
      case None => Contribution("Research Paper", researchPaperCount, 0, researchPaperTitles)
    }
  }

  def fetchAllTimeMeetupDetails(knolderId: Int): Contribution = {
    val meetupTitles = SQL(
      """
      SELECT
      meetup.title,
      meetup.delivered_on
        FROM
        knolder
        RIGHT JOIN
        meetup
        ON knolder.email_id = meetup.email_id
    WHERE
    knolder.id = ? ORDER BY delivered_on DESC """)
      .bind(knolderId)
      .map(rs => ContributionDetails(rs.string("title"), rs.string("delivered_on")))
      .list().apply()

    val meetupScore = SQL(
      """
        | select SUM(meetup_score) as meetup_score from monthlycontribution where knolder_id= ? group by knolder_id
        |""".stripMargin).bind(knolderId).map(rs => rs.int("meetup_score")).single().apply()

    val meetupCount = meetupTitles.length

    meetupScore match {
      case Some(score) => Contribution("Meetup", meetupCount, score, meetupTitles)
      case None => Contribution("Meetup", meetupCount, 0, meetupTitles)
    }
  }
}

package com.knoldus.leader_board.infrastructure

import java.sql.{Connection, Timestamp}

import com.knoldus.leader_board._
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class FetchKnolderDetailsImpl(config: Config) extends FetchKnolderDetails with LazyLogging {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.readOnlySession()

  /**
   * Fetching details of each knolder.
   *
   * @return List of details of knolders.
   */
  override def fetchKnolderDetails(knolderId: Int): Option[KnolderDetails] = {
    logger.info("Fetching details of each knolder.")

    val currentMonth = Timestamp.valueOf(Constant.CURRENT_TIME
      .withDayOfMonth(1).toLocalDate.atStartOfDay())

    val nextMonth = Timestamp.valueOf(Constant.CURRENT_TIME
      .withDayOfMonth(1).toLocalDate.plusMonths(1).atStartOfDay())

    val currentMonthAndYear = s"${Constant.CURRENT_TIME.toLocalDate.getMonth} " +
      s"${Constant.CURRENT_TIME.toLocalDate.getYear}"

    val scores = SQL("SELECT knolder.full_name, all_time_reputation.score AS allTimeScore, monthly_reputation.score " +
      s"AS monthlyScore,COUNT(blog.title) * ${config.getInt("scorePerBlog")} As blogScore FROM knolder INNER JOIN " +
      s"all_time_reputation ON knolder.id = all_time_reputation.knolder_id " + "INNER JOIN monthly_reputation ON " +
      "knolder.id = monthly_reputation.knolder_id LEFT JOIN blog ON knolder.wordpress_id = blog.wordpress_id AND " +
      "published_on >= ? AND published_on < ? WHERE knolder.id = ? GROUP BY knolder.full_name, all_time_reputation.score, " +
      "monthly_reputation.score")
      .bind(currentMonth, nextMonth, knolderId)
      .map(rs => ContributionScores(rs.string("full_name"), currentMonthAndYear,
        rs.int("allTimeScore"), rs.int("monthlyScore"), rs.int("blogScore")))
      .single().apply()

    val titles = SQL("SELECT blog.title, blog.published_on FROM knolder LEFT JOIN blog ON knolder.wordpress_id = " +
      "blog.wordpress_id WHERE knolder.id = ? AND published_on >= ? AND published_on < ?")
      .bind(knolderId, currentMonth, nextMonth)
      .map(rs => ContributionTitles(rs.string("title"), rs.string("published_on")))
      .list().apply()

    scores.map(scores => KnolderDetails(scores.knolderName, currentMonthAndYear, scores.allTimeScore,
      scores.monthlyScore, scores.blogScore, titles))
  }
}

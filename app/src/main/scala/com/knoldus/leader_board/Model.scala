package com.knoldus.leader_board

import java.sql.Timestamp

final case class Blog(blogId: Option[Int], wordpressId: Option[String], publishedOn: Timestamp, title: Option[String])

final case class Knolx(knolxId: Option[String], emailId: Option[String], deliveredOn: Option[Timestamp], title: Option[String])

final case class GetCount(knolderId: Int, knolderName: String, numberOfBlogs: Int, numberOfKnolx: Int, numberOfWebinar: Int)

final case class GetScore(knolderId: Int, knolderName: String, score: Int)

final case class GetReputation(knolderId: Int, knolderName: String, score: Int, rank: Int)

final case class KnolderReputation(knolderId: Option[Int], reputation: GetReputation)

final case class GetStreak(knolderId: Int, knolderName: String, streak: String)

final case class KnolderStreak(knolderId: Option[Int], streak: GetStreak)

final case class Reputation(knolderId: Int, knolderName: String, allTimeScore: Int, allTimeRank: Int,
                            quarterlyStreak: String, monthlyScore: Int, monthlyRank: Int)

final case class Contribution(contributionType: String, contributionCount: Int, contributionScore: Int,
                              contributionDetails: List[ContributionDetails])

final case class ContributionDetails(title: String, date: String)

final case class KnolderDetails(knolderName: String, score: Int, scoreBreakDown: List[Option[Contribution]])

final case class TwelveMonthsScore(month: String, year: Int, score: Int)

final case class ReputationWithCount(monthlyBlogCount: Int, monthlyKnolxCount: Int, monthlyWebinarCount: Int,
                                     allTimeBlogCount: Int, allTimeKnolxCount: Int, allTimeWebinarCount: Int,
                                     reputation: List[Reputation])

final case class Webinar(id: String, deliveredOn: Option[Timestamp], name: String, title: String, emailId: String)

case object ExecuteBlogsScript

case object ExecuteWebinarScript

case object ExecuteKnolxScript

case object CalculateReputation

case object WriteAllTimeReputation

case object WriteMonthlyReputation

case object WriteQuarterlyReputation

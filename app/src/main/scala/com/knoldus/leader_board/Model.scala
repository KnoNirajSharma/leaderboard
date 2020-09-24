package com.knoldus.leader_board

import java.sql.Timestamp

final case class Blog(blogId: Option[Int], wordpressId: Option[String], publishedOn: Timestamp, title: Option[String])

final case class Knolx(knolxId: Option[String], emailId: Option[String], deliveredOn: Option[Timestamp], title: Option[String])

final case class TechHubTemplate(techHubId: Option[String], emailId: Option[String], uploadedOn: Option[Timestamp], title: Option[String])

final case class OtherContributionDetails(contributionId: String, emailId: String, name: String, contributedOn: Option[Timestamp]
                                          , title: String, typeOfContributon: String)

final case class GetContributionCount(knolderId: Int, knolderName: String, numberOfBlogs: Int, numberOfKnolx: Int, numberOfWebinar: Int, numberOfTechHub: Int
                                      , numberOfOSContribution: Int, numberOfConferences: Int, numberOfBooks: Int, numberOfResearchPapers: Int)

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

final case class TwelveMonthsScore(month: String, year: Int, blogScore: Int, knolxScore: Int, webinarScore: Int, techHubScore: Int,
                                   osContributionScore: Int, conferenceScore: Int, bookScore: Int, researchPaperScore: Int)

final case class ReputationWithCount(blogs: ContributionCount, knolx: ContributionCount, webinars: ContributionCount, techhubTemplates: ContributionCount,
                                     osContributions: ContributionCount, conferences: ContributionCount,
                                     books: ContributionCount, researchPapers: ContributionCount, reputation: List[Reputation])

final case class Webinar(id: String, deliveredOn: Option[Timestamp], name: String, title: String, emailId: String)

final case class ContributionScore(blogScore: Int, knolxScore: Int, webinarScore: Int, techHubScore: Int, osContributionScore: Int, conferenceScore: Int,
                                   bookScore: Int, researchPaperScore: Int)

final case class MonthlyTopFiveLeaders(month: String, year: Int, knolderId: Int, knolderName: String, monthlyScore: Int, monthlyRank: Int, allTimeScore: Int
                                       , allTimeRank: Int)

final case class MonthlyAllTimeReputation(knolderId: Int, knolderName: String, allTimeScore: Int, allTimeRank: Int, monthlyScore: Int, monthlyRank: Int)

final case class MonthYearWithTopFiveLeaders(month: String, year: Int, leaders: List[MonthlyTopFiveLeaders])

case object ExecuteBlogsScript

case object ExecuteWebinarScript

case object ExecuteKnolxScript

case object ExecuteTechHubScript

case object ExecuteOtherContributionScript

case object CalculateReputation

case object WriteAllTimeReputation

case object WriteMonthlyReputation

case object WriteQuarterlyReputation

package com.knoldus.leader_board

final case class GetContributionCount(knolderId: Int, knolderName: String, numberOfBlogs: Int, numberOfKnolx: Int, numberOfWebinar: Int, numberOfTechHub: Int
                                      , numberOfOSContribution: Int, numberOfConferences: Int, numberOfBooks: Int, numberOfResearchPapers: Int)

final case class GetScore(knolderId: Int, knolderName: String, score: Int)

final case class GetReputation(knolderId: Int, knolderName: String, score: Int, rank: Int)

final case class MonthlyAllTimeReputation(knolderId: Int, knolderName: String, allTimeScore: Int, allTimeRank: Int, monthlyScore: Int, monthlyRank: Int,
                                          month: String, year: Int)

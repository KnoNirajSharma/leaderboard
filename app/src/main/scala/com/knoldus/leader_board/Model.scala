package com.knoldus.leader_board

final case class GetCount(knolderId: Int, knolderName: String, numberOfBlogs: Int)

final case class GetScore(knolderId: Int, knolderName: String, score: Int)

final case class GetReputation(knolderId: Int, knolderName: String, score: Int, rank: Int)

final case class KnolderReputation(knolderId: Option[Int], reputation: GetReputation)

final case class GetStreak(knolderId: Int, knolderName: String, streak: String)

final case class KnolderStreak(knolderId: Option[Int], streak: GetStreak)

final case class Reputation(knolderName: String, allTimeScore: Int, allTimeRank: Int, quarterlyStreak: String,
                            monthlyScore: Int, monthlyRank: Int)

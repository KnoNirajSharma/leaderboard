package com.knoldus.leader_board

final case class GetCount(knolderId: Int, knolderName: String, numberOfBlogs: Int)

final case class GetScore(knolderId: Int, knolderName: String, score: Int)

final case class Reputation(knolderId: Int, knolderName: String, score: Int, rank: Int)

final case class KnolderReputation(knolderId: Option[Int], reputation: Reputation)

final case class Streak(knolderId: Int, knolderName: String, streak: String)

final case class KnolderStreak(knolderId: Option[Int], streak: Streak)

final case class GetReputation(knolderName: String, score: Int, rank: Int)

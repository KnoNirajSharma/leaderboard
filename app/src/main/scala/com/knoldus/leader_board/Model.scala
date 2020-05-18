package com.knoldus.leader_board

final case class BlogCount(knolderId: Int, wordpressId: String, authorName: String, numberOfBlogs: Int)

final case class KnolderBlogCount(knolderId: Option[Int], blogCount: BlogCount)

final case class GetScore(knolderId: Int, knolderName: String, score: Int)

final case class Reputation(knolderId: Int, knolderName: String, score: Int, rank: Int)

final case class GetAllTime(knolderId: Int, knolderName: String, numberOfBlogs: Option[Int])

final case class KnolderReputation(knolderId: Option[Int], reputation: Reputation)

final case class GetReputation(knolderName: String, score: Int, rank: Int)

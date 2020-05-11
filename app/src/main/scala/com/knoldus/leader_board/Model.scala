package com.knoldus.leader_board

final case class Knolder(knolderId: Int, fullName: String, wordPressId: String, email: String)

final case class KnolderBlog(blogId: Int, wordpressId: String)

final case class BlogCount(knolderId: Int, wordpressId: String, authorName: String, numberOfBlogs: Int)

final case class KnolderBlogCount(knolderId: Option[Int], blogCount: BlogCount)

final case class GetScore(knolderName: String, score: Int)

final case class Reputation(knolderName: String, score: Int, rank: Int)

final case class GetAllTime(knolderName: String, numberOfBlogs: Option[Int])

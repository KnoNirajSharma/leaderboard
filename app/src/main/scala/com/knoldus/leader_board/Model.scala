package com.knoldus.leader_board

import java.sql.Timestamp

final case class Author(authorName: Option[String], authorLogin: Option[String])

final case class Blog(blogId: Option[Int], authorLogin: Option[String], publishedOn: Timestamp, title: Option[String])

final case class BlogAuthor(blogs: List[Blog], authors: List[Author])

final case class BlogCount(authorLogin: Option[String], authorName: Option[String], numberOfBlogs: Int)

final case class AuthorScore(authorLogin: Option[String], authorName: Option[String], score: Int, rank: Int)

final case class GetScore(knolderId: Int, score: Int)

final case class GetRank(knolderId: Int, rank: Int)

final case class GetAuthorScore(authorName: String, score: Int, rank: Int)

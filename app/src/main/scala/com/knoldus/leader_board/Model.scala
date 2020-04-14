package com.knoldus.leader_board

import java.sql.Timestamp

case class Author(authorName: Option[String], authorLogin: Option[String])

case class Blog(blogId: Option[Int], authorLogin: Option[String], publishedOn: Timestamp, title: Option[String])

case class BlogAuthor(blogs: List[Blog], authors: List[Author])

case class BlogCount(authorLogin: Option[String], authorName: Option[String], numberOfBlogs: Int)

case class AuthorScore(authorLogin: Option[String], authorName: Option[String], score: Int, rank: Int)

case class GetScore(knolderId: Int, score: Int)

case class GetRank(knolderId: Int, rank: Int)

case class GetAuthorScore(authorName: String, score: Int, rank: Int)

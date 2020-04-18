package com.knoldus.leader_board.business

import com.knoldus.leader_board.{Author, Blog, BlogAuthor}

import scala.concurrent.Future

trait Blogs {
  def getAllBlogsAndAuthors: Future[BlogAuthor]

  def getTotalNoOfPosts: Future[Int]

  def getAllBlogs(lastPage: Int): Future[BlogAuthor]

  def getListOfBlogs(blogs: String): List[Blog]

  def getListOfAuthors(blogs: String): List[Author]
}

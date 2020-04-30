package com.knoldus.leader_board.business

import com.knoldus.leader_board.Blog

import scala.concurrent.Future

trait Blogs {
  def getAllPagesBlogsFromAPI: Future[List[Blog]]

  def getTotalNoOfPosts: Future[Int]

  def getFirstPageBlogsFromAPI: Future[List[Blog]]

  def getAllBlogs(lastPage: Int): Future[List[Blog]]

  def getListOfBlogs(blogs: String): List[Blog]
}

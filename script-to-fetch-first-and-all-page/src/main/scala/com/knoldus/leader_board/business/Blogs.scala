package com.knoldus.leader_board.business

import com.knoldus.leader_board.Blog

trait Blogs {
  def getResponse(url: String): String

  def getAllPagesBlogsFromAPI: List[Blog]

  def getTotalNoOfPosts: Int

  def getFirstPageBlogsFromAPI: List[Blog]

  def getAllBlogs(lastPage: Int): List[Blog]

  def getListOfBlogs(blogs: String): List[Blog]
}

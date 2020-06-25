package com.knoldus.leader_board.business

import com.knoldus.leader_board.Blog

trait Blogs {
  def getAllBlogsFromAPI: List[Blog]

  def getTotalNoOfPosts: Int

  def getAllBlogs(lastPage: Int): List[Blog]

  def getListOfBlogs(unparsedBlogs: String): List[Blog]
}

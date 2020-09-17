package com.knoldus.leader_board.business

import com.knoldus.leader_board.Blog

trait Blogs {
  def getLatestBlogsFromAPI: List[Blog]

  def getListOfLatestBlogs(unparsedBlogs: String): List[Blog]
}

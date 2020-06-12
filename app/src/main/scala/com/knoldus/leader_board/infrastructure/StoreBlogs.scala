package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.Blog

trait StoreBlogs {
  def insertBlog(listOfBlogsAndAuthors: List[Blog]): List[Int]
}

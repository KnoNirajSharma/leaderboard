package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.Blog

trait StoreData {
  def insertBlog(listOfBlogsAndAuthors: List[Blog]): List[Int]

  def updateBlog(listOfBlogs: List[Blog]): List[Int]
}

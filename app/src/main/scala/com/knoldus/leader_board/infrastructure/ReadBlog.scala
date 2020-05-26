package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.GetCount

trait ReadBlog {
  def fetchKnoldersWithBlogs: List[GetCount]

  def fetchKnoldersWithMonthlyBlogs: List[GetCount]

  def fetchKnoldersWithQuarterFirstMonthBlogs: List[GetCount]

  def fetchKnoldersWithQuarterSecondMonthBlogs: List[GetCount]

  def fetchKnoldersWithQuarterThirdMonthBlogs: List[GetCount]
}

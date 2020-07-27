package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.{Contribution, KnolderDetails}

trait FetchKnolderDetails {
  def fetchKnolderMonthlyDetails(knolderId: Int, month: Int, year: Int): Option[KnolderDetails]

  def fetchKnolderAllTimeDetails(knolderId: Int): Option[KnolderDetails]

  def fetchKnolderMonthlyWebinarDetails(month: Int, year: Int, knolderId: Int): Option[Contribution]

  def fetchKnolderMonthlyBlogDetails(month: Int, year: Int, knolderId: Int): Option[Contribution]

  def fetchKnolderMonthlyKnolxDetails(month: Int, year: Int, knolderId: Int): Option[Contribution]

  def fetchAllTimeknolxDetails(knolderId: Int): Option[Contribution]

  def fetchAllTimeWebinarDetails(knolderId: Int): Option[Contribution]

  def fetchAllTimeBlogDetails(knolderId: Int): Option[Contribution]
}

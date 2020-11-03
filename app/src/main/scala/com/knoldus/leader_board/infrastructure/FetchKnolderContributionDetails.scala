package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.{Contribution, KnolderDetails}

trait FetchKnolderContributionDetails {
  def fetchKnolderMonthlyDetails(knolderId: Int, month: Int, year: Int): Option[KnolderDetails]

  def fetchKnolderAllTimeDetails(knolderId: Int): Option[KnolderDetails]

  def fetchKnolderMonthlyWebinarDetails(month: Int, year: Int, knolderId: Int): Contribution

  def fetchKnolderMonthlyBlogDetails(month: Int, year: Int, knolderId: Int): Contribution

  def fetchKnolderMonthlyKnolxDetails(month: Int, year: Int, knolderId: Int): Contribution

  def fetchAllTimeknolxDetails(knolderId: Int): Contribution

  def fetchAllTimeWebinarDetails(knolderId: Int): Contribution

  def fetchAllTimeBlogDetails(knolderId: Int): Contribution

  def fetchKnolderMonthlyTechHubDetails(month: Int, year: Int, knolderId: Int): Contribution

  def fetchAllTimeTechHubDetails(knolderId: Int): Contribution

  def fetchKnolderMonthlyOsContributionDetails(month: Int, year: Int, knolderId: Int): Contribution

  def fetchAllTimeOsContributionDetails(knolderId: Int): Contribution

  def fetchKnolderMonthlyConferenceDetails(month: Int, year: Int, knolderId: Int): Contribution

  def fetchAllTimeConferenceDetails(knolderId: Int): Contribution

  def fetchKnolderMonthlyBookDetails(month: Int, year: Int, knolderId: Int): Contribution

  def fetchAllTimeBookDetails(knolderId: Int): Contribution

  def fetchKnolderMonthlyResearchPaperDetails(month: Int, year: Int, knolderId: Int): Contribution

  def fetchAllTimeResearchPaperDetails(knolderId: Int): Contribution

}

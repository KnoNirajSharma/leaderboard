package com.knoldus.leader_board.infrastructure

trait FetchMonthlyKnolderContribution {

  def fetchKnolderIdIfKnolderMonthlyContributionExist(knolderId: Int, month: String, year: Int): Option[Int]
}

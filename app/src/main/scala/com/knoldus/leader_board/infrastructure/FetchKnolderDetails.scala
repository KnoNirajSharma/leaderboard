package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.KnolderDetails

trait FetchKnolderDetails {
  def fetchKnolderMonthlyDetails(knolderId: Int, month: Int, year: Int): Option[KnolderDetails]

  def fetchKnolderAllTimeDetails(knolderId: Int): Option[KnolderDetails]
}

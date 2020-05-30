package com.knoldus.leader_board.infrastructure

import com.knoldus.leader_board.KnolderDetails

trait FetchKnolderDetails {
  def fetchKnolderDetails(knolderId: Int): Option[KnolderDetails]
}

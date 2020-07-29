package com.knoldus.leader_board.business

import com.google.api.services.sheets.v4.model.ValueRange
import com.knoldus.leader_board.Webinar

trait WebinarSpreadSheetData {
  def getWebinarData: List[Webinar]

}

package com.knoldus.leader_board.utils

import com.google.api.services.sheets.v4.model.ValueRange

trait SpreadSheetApi {

  def getResponse(spreadSheetId: String, spreadSheetRange: String): ValueRange

}

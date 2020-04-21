package com.knoldus.leader_board

import java.sql.Connection

import com.typesafe.config.Config
import scalikejdbc.{ConnectionPool, GlobalSettings, LoggingSQLAndTimeSettings}

class DatabaseConnection(config: Config) {
  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(enabled = true, singleLineMode = true, logLevel = 'DEBUG)

  def connection: Connection = {
    val url = config.getString("url")
    val driver = config.getString("driver")
    val username = config.getString("username")
    val password = config.getString("password")
    Class.forName(driver)
    ConnectionPool.singleton(url, username, password)
    ConnectionPool.borrow()
  }
}

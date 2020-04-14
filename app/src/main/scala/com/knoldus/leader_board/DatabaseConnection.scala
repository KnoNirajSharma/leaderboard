package com.knoldus.leader_board

import java.sql.Connection

import com.typesafe.config.{Config, ConfigFactory}
import scalikejdbc.ConnectionPool

class DatabaseConnection {
  val config: Config = ConfigFactory.load()

  def connection: Connection = {
    try {
      val url = config.getString("url")
      val driver = config.getString("driver")
      val username = config.getString("username")
      val password = config.getString("password")
      Class.forName(driver)
      ConnectionPool.singleton(url, username, password)
      ConnectionPool.borrow()
    }
    catch {
      case ex: Exception => throw new Exception(ex)
    }
  }
}

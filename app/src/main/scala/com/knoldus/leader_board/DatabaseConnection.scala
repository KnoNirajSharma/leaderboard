package com.knoldus.leader_board

import java.sql.{Connection, DriverManager}

import com.typesafe.config.{Config, ConfigFactory}

class DatabaseConnection {
  val config: Config = ConfigFactory.load()

  def connection: Connection = {
    try {
      val url = config.getString("url")
      val driver = config.getString("driver")
      val username = config.getString("username")
      val password = config.getString("password")
      Class.forName(driver)
      DriverManager.getConnection(url, username, password)
    }
    catch {
      case ex: Exception => throw new Exception(ex)
    }
  }
}

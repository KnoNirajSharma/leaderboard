package com.knoldus.leader_board.infrastructure

import java.io.FileReader
import java.sql.Connection

import org.h2.tools.RunScript
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike


class DBSpec extends AnyWordSpecLike with Matchers {

  def createTable(jdbcConnection: Connection): Unit = {
    val fileName = "src/main/resources/createTable.sql"
    RunScript.execute(jdbcConnection, new FileReader(fileName))
  }

  def cleanUpDatabase(jdbcConnection: Connection): Unit = {
    RunScript.execute(jdbcConnection, new FileReader("src/main/resources/dropTable.sql"))
  }
}

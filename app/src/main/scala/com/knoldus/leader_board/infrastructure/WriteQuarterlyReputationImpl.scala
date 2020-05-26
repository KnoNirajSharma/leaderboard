package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, KnolderStreak}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class WriteQuarterlyReputationImpl(config: Config) extends LazyLogging with WriteQuarterlyReputation {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Verifies whether knolder already exist in quarterly_reputation table or not, if not then it inserts it into the
   * table with its score and rank.
   *
   * @param reputationOfKnolders List of reputation of knolders along with their knolder id fetched from quarterly
   *                             reputation table.
   * @return List of Integer which displays the status of query execution.
   */
  override def insertQuarterlyReputationData(reputationOfKnolders: List[KnolderStreak]): List[Int] = {
    logger.info("Inserting reputation of knolder quarterly reputation table.")
    reputationOfKnolders.filter { reputationOfKnolder =>
      reputationOfKnolder.knolderId.isEmpty
    }.map { reputationOfKnolder =>
      SQL("INSERT INTO quarterly_reputation(knolder_id, full_name, streak) VALUES (?,?,?)")
        .bind(reputationOfKnolder.streak.knolderId, reputationOfKnolder.streak.knolderName,
          reputationOfKnolder.streak.streak).update().apply()
    }
  }

  /**
   * Verifies whether knolder already exist in quarterly_reputation table or not, if it does then it updates its score
   * and rank.
   *
   * @param reputationOfKnolders List of reputation of knolders along with their knolder id fetched from quarterly
   *                             reputation table.
   * @return List of Integer which displays the status of query execution.
   */
  override def updateQuarterlyReputationData(reputationOfKnolders: List[KnolderStreak]): List[Int] = {
    logger.info("Updating reputation of knolder in quarterly reputation table.")
    reputationOfKnolders.filter { reputationOfKnolder =>
      reputationOfKnolder.knolderId.isDefined
    }.map { reputationOfKnolder =>
      SQL("UPDATE quarterly_reputation SET streak = ? WHERE knolder_id = ?")
        .bind(reputationOfKnolder.streak.streak,
          reputationOfKnolder.streak.knolderId).update().apply()
    }
  }
}

package com.knoldus.leader_board.infrastructure

import java.sql.Connection

import com.knoldus.leader_board.{DatabaseConnection, KnolderReputation}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scalikejdbc.{DB, DBSession, SQL}

class WriteMonthlyReputationImpl(config: Config) extends LazyLogging with WriteMonthlyReputation {
  implicit val connection: Connection = DatabaseConnection.connection(config)
  implicit val session: DBSession = DB.autoCommitSession()

  /**
   * Verifies whether knolder already exist in monthly_reputation table or not, if not then it inserts it into the
   * table with its score and rank.
   *
   * @param reputationOfKnolders List of reputation of knolders along with their knolder id fetched from monthly
   *                             reputation table..
   * @return List of Integer which displays the status of query execution.
   */
  override def insertMonthlyReputationData(reputationOfKnolders: List[KnolderReputation]): List[Int] = {
    logger.info("Inserting reputation of knolder monthly reputation table.")
    reputationOfKnolders.filter { reputationOfKnolder =>
      reputationOfKnolder.knolderId.isEmpty
    }.map { reputationOfKnolder =>
      SQL("INSERT INTO monthly_reputation(knolder_id, score, rank) VALUES (?,?,?)")
        .bind(reputationOfKnolder.reputation.knolderId, reputationOfKnolder.reputation.score,
          reputationOfKnolder.reputation.rank).update().apply()
    }
  }

  /**
   * Verifies whether knolder already exist in monthly_reputation table or not, if it does then it updates its score
   * and rank.
   *
   * @param reputationOfKnolders List of reputation of knolders along with their knolder id fetched from monthly
   *                             reputation table.
   * @return List of Integer which displays the status of query execution.
   */
  override def updateMonthlyReputationData(reputationOfKnolders: List[KnolderReputation]): List[Int] = {
    logger.info("Updating reputation of knolder in monthly reputation table.")
    reputationOfKnolders.filter { reputationOfKnolder =>
      reputationOfKnolder.knolderId.isDefined
    }.map { reputationOfKnolder =>
      SQL("UPDATE monthly_reputation SET score = ?, rank = ? WHERE knolder_id = ?")
        .bind(reputationOfKnolder.reputation.score, reputationOfKnolder.reputation.rank,
          reputationOfKnolder.reputation.knolderId).update().apply()
    }
  }
}

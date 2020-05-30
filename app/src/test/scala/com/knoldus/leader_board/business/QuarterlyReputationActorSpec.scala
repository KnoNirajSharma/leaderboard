package com.knoldus.leader_board.business

import java.sql.Connection

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.knoldus.leader_board.infrastructure.{DBSpec, WriteQuarterlyReputation, WriteQuarterlyReputationImpl}
import com.knoldus.leader_board.{DatabaseConnection, GetStreak, KnolderStreak}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}

class QuarterlyReputationActorSpec extends TestKit(ActorSystem("QuarterlyActorSpec")) with ImplicitSender with AnyWordSpecLike
  with Matchers with BeforeAndAfterAll with BeforeAndAfterEach with MockitoSugar with DBSpec {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockQuarterlyReputation: QuarterlyReputation = mock[QuarterlyReputationImpl]
  val mockWriteQuarterlyReputation: WriteQuarterlyReputation = mock[WriteQuarterlyReputationImpl]

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  override def afterEach(): Unit = {
    cleanUpDatabase(connection)
  }

  override protected def beforeEach(): Unit = {
    cleanUpDatabase(connection)
    createTable(connection)
  }

  "Quarterly reputation actor" must {
    "insert and update quarterly reputation" in {
      val probe = TestProbe()
      val reputationOfKnolders = List(KnolderStreak(None, GetStreak(1, "Mukesh Gupta", "15-20-20")),
        KnolderStreak(None, GetStreak(2, "anjali", "10-10-15")))
      when(mockQuarterlyReputation.getKnolderQuarterlyReputation)
        .thenReturn(reputationOfKnolders)
      when(mockWriteQuarterlyReputation.insertQuarterlyReputationData(reputationOfKnolders))
        .thenReturn(List(2))
      when(mockWriteQuarterlyReputation.updateQuarterlyReputationData(reputationOfKnolders))
        .thenReturn(List(2))
      val quarterlyReputationActor = system.actorOf(Props(new QuarterlyReputationActor(mockQuarterlyReputation,
        mockWriteQuarterlyReputation)))
      probe watch quarterlyReputationActor
      probe.send(quarterlyReputationActor, "write quarterly reputation")
      probe.expectMsg("quarterly reputation saved")
    }
    "insert and update quarterly reputation with incorrect message" in {
      val probe = TestProbe()
      val reputationOfKnolders = List(KnolderStreak(None, GetStreak(1, "Mukesh Gupta", "15-20-20")),
        KnolderStreak(None, GetStreak(2, "anjali", "10-10-15")))
      when(mockQuarterlyReputation.getKnolderQuarterlyReputation)
        .thenReturn(reputationOfKnolders)
      when(mockWriteQuarterlyReputation.insertQuarterlyReputationData(reputationOfKnolders))
        .thenReturn(List(2))
      when(mockWriteQuarterlyReputation.updateQuarterlyReputationData(reputationOfKnolders))
        .thenReturn(List(2))
      val quarterlyReputationActor = system.actorOf(Props(new QuarterlyReputationActor(mockQuarterlyReputation,
        mockWriteQuarterlyReputation)))
      probe watch quarterlyReputationActor
      probe.send(quarterlyReputationActor, "read quarterly reputation")
      probe.expectMsg("invalid message")
    }
  }
}

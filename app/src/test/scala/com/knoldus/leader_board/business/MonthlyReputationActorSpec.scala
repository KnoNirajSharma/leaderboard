package com.knoldus.leader_board.business

import java.sql.Connection

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.knoldus.leader_board.infrastructure.{WriteMonthlyReputation, WriteMonthlyReputationImpl}
import com.knoldus.leader_board.{DatabaseConnection, GetReputation, KnolderReputation, WriteMonthlyReputation}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class MonthlyReputationActorSpec extends TestKit(ActorSystem("MonthlyActorSpec")) with ImplicitSender
  with AnyWordSpecLike with Matchers with BeforeAndAfterAll with MockitoSugar {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockMonthlyReputation: MonthlyReputation = mock[MonthlyReputationImpl]
  val mockWriteMonthlyReputation: WriteMonthlyReputation = mock[WriteMonthlyReputationImpl]

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "Monthly reputation actor" must {

    "insert and update monthly reputation" in {
      val probe = TestProbe()
      val reputationOfKnolders = List(KnolderReputation(None, GetReputation(1, "Mukesh Gupta", 10, 1)),
        KnolderReputation(None, GetReputation(2, "anjali", 5, 2)))
      when(mockMonthlyReputation.getKnolderMonthlyReputation)
        .thenReturn(reputationOfKnolders)
      when(mockWriteMonthlyReputation.insertMonthlyReputationData(reputationOfKnolders))
        .thenReturn(List(2))
      when(mockWriteMonthlyReputation.updateMonthlyReputationData(reputationOfKnolders))
        .thenReturn(List(2))
      val quarterlyReputationActor = system.actorOf(Props(new MonthlyReputationActor(mockMonthlyReputation,
        mockWriteMonthlyReputation)))
      probe watch quarterlyReputationActor
      probe.send(quarterlyReputationActor, WriteMonthlyReputation)
      probe.expectMsg("monthly reputation saved")
    }

    "insert and update monthly reputation with incorrect message" in {
      val probe = TestProbe()
      val reputationOfKnolders = List(KnolderReputation(None, GetReputation(1, "Mukesh Gupta", 10, 1)),
        KnolderReputation(None, GetReputation(2, "anjali", 5, 2)))
      when(mockMonthlyReputation.getKnolderMonthlyReputation)
        .thenReturn(reputationOfKnolders)
      when(mockWriteMonthlyReputation.insertMonthlyReputationData(reputationOfKnolders))
        .thenReturn(List(2))
      when(mockWriteMonthlyReputation.updateMonthlyReputationData(reputationOfKnolders))
        .thenReturn(List(2))
      val quarterlyReputationActor = system.actorOf(Props(new MonthlyReputationActor(mockMonthlyReputation,
        mockWriteMonthlyReputation)))
      probe watch quarterlyReputationActor
      probe.send(quarterlyReputationActor, "read monthly reputation")
      probe.expectMsg("invalid message")
    }
  }
}

package com.knoldus.leader_board.business

import java.sql.Connection

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.knoldus.leader_board.infrastructure.{WriteAllTimeReputation, WriteAllTimeReputationImpl}
import com.knoldus.leader_board.{DatabaseConnection, GetReputation, KnolderReputation, WriteAllTimeReputation}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class AllTimeReputationActorSpec extends TestKit(ActorSystem("AllTimeActorSpec")) with ImplicitSender
  with AnyWordSpecLike with Matchers with BeforeAndAfterAll with MockitoSugar {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockAllTimeReputation: AllTimeReputation = mock[AllTimeReputationImpl]
  val mockWriteAllTimeReputation: WriteAllTimeReputation = mock[WriteAllTimeReputationImpl]

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "All time reputation actor" must {

    "insert and update all time reputation" in {
      val probe = TestProbe()
      val reputationOfKnolders = List(KnolderReputation(None, GetReputation(1, "Mukesh Gupta", 10, 1)),
        KnolderReputation(None, GetReputation(2, "anjali", 5, 2)))
      when(mockAllTimeReputation.getKnolderReputation)
        .thenReturn(reputationOfKnolders)
      when(mockWriteAllTimeReputation.insertAllTimeReputationData(reputationOfKnolders))
        .thenReturn(List(2))
      when(mockWriteAllTimeReputation.updateAllTimeReputationData(reputationOfKnolders))
        .thenReturn(List(2))
      val quarterlyReputationActor = system.actorOf(Props(new AllTimeReputationActor(mockAllTimeReputation,
        mockWriteAllTimeReputation)))
      probe watch quarterlyReputationActor
      probe.send(quarterlyReputationActor, WriteAllTimeReputation)
      probe.expectMsg("all time reputation saved")
    }

    "insert and update all time reputation with incorrect message" in {
      val probe = TestProbe()
      val reputationOfKnolders = List(KnolderReputation(None, GetReputation(1, "Mukesh Gupta", 10, 1)),
        KnolderReputation(None, GetReputation(2, "anjali", 5, 2)))
      when(mockAllTimeReputation.getKnolderReputation)
        .thenReturn(reputationOfKnolders)
      when(mockWriteAllTimeReputation.insertAllTimeReputationData(reputationOfKnolders))
        .thenReturn(List(2))
      when(mockWriteAllTimeReputation.updateAllTimeReputationData(reputationOfKnolders))
        .thenReturn(List(2))
      val quarterlyReputationActor = system.actorOf(Props(new AllTimeReputationActor(mockAllTimeReputation,
        mockWriteAllTimeReputation)))
      probe watch quarterlyReputationActor
      probe.send(quarterlyReputationActor, "read all time reputation")
      probe.expectMsg("invalid message")
    }
  }
}

package com.knoldus.leader_board.business

import java.sql.Connection

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.knoldus.leader_board.infrastructure._
import com.knoldus.leader_board.{DatabaseConnection, ExecuteOSContributionScript}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class OSContributionActorSpec extends TestKit(ActorSystem("OSContributionActorSpec")) with ImplicitSender
  with AnyWordSpecLike with Matchers with BeforeAndAfterAll with MockitoSugar {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockOSContribution: OSContributionData = mock[OSContributionDataImpl]
  val mockStoreOSContribution: StoreOSContributionDetails = mock[StoreOSContributionDetailsImpl]
  val mockAllTimeReputation: AllTimeReputation = mock[AllTimeReputationImpl]
  val mockWriteAllTimeReputation: WriteAllTimeReputation = mock[WriteAllTimeReputationImpl]
  val mockMonthlyReputation: MonthlyReputation = mock[MonthlyReputationImpl]
  val mockWriteMonthlyReputation: WriteMonthlyReputation = mock[WriteMonthlyReputationImpl]
  val mockQuarterlyReputation: QuarterlyReputation = mock[QuarterlyReputationImpl]
  val mockWriteQuarterlyReputation: WriteQuarterlyReputation = mock[WriteQuarterlyReputationImpl]
  val allTimeReputationActorRef: ActorRef = system.actorOf(Props(new AllTimeReputationActor(mockAllTimeReputation,
    mockWriteAllTimeReputation)), "allTimeReputationActor")
  val monthlyReputationActorRef: ActorRef = system.actorOf(Props(new MonthlyReputationActor(mockMonthlyReputation,
    mockWriteMonthlyReputation)), "monthlyReputationActor")
  val quarterlyReputationActorRef: ActorRef = system.actorOf(Props(new QuarterlyReputationActor(mockQuarterlyReputation,
    mockWriteQuarterlyReputation)), "quarterlyReputationActor")

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "os contribution Actor" must {
    "not do anything with incorrect message" in {
      val probe = TestProbe()
      val scriptActor = system.actorOf(Props(new OSContributionActor(allTimeReputationActorRef, monthlyReputationActorRef,
        quarterlyReputationActorRef, mockStoreOSContribution, mockOSContribution)))
      probe watch scriptActor
      probe.send(scriptActor, "display reputation")
      probe.expectMsg("invalid message")
    }
    "execute os contribution script" in {
      val probe = TestProbe.apply()
      val mockActorRef = probe.ref
      val scriptActor = system.actorOf(Props(new OSContributionActor(mockActorRef, mockActorRef, mockActorRef,
        mockStoreOSContribution, mockOSContribution)))
      probe watch scriptActor
      probe.send(scriptActor, ExecuteOSContributionScript)
      probe.expectMsg("stored os contribution data")
    }
  }
}

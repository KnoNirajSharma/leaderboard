package com.knoldus.leader_board.business

import java.sql.Connection

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.knoldus.leader_board.infrastructure._
import com.knoldus.leader_board.{DatabaseConnection, ExecuteOtherContributionScript}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class OtherContributionActorSpec extends TestKit(ActorSystem("OtherContributionActorSpec")) with ImplicitSender
  with AnyWordSpecLike with Matchers with BeforeAndAfterAll with MockitoSugar {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockOtherContribution: OtherContributionData = mock[OtherContributionDataImpl]
  val mockStoreOSContribution: StoreOSContributionDetails = mock[StoreOSContributionDetailsImpl]
  val mockStoreBooksContribution: StoreBooksContribution = mock[StoreBooksContributionImpl]
  val mockStoreConferenceDetails: StoreConferenceDetails = mock[StoreConferenceDetailsImpl]
  val mockStoreResearchPaperContribution: StoreResearchPapersContribution = mock[StoreResearchPapersContributionImpl]
  val mockAllTimeReputation: AllTimeReputation = mock[AllTimeReputationImpl]
  val mockWriteAllTimeReputation: WriteAllTimeReputation = mock[WriteAllTimeReputationImpl]
  val mockMonthlyReputation: MonthlyReputation = mock[MonthlyReputationImpl]
  val mockWriteMonthlyReputation: WriteMonthlyReputation = mock[WriteMonthlyReputationImpl]
  val mockQuarterlyReputation: QuarterlyReputation = mock[QuarterlyReputationImpl]
  val mockWriteQuarterlyReputation: WriteQuarterlyReputation = mock[WriteQuarterlyReputationImpl]
  val mockKnolderMonthlyContribution: KnolderMonthlyContributionImpl = mock[KnolderMonthlyContributionImpl]
  val mockWriteMonthlyContribution: WriteMonthlyContribution = mock[WriteMonthlyContribution]
  val allTimeReputationActorRef: ActorRef = system.actorOf(Props(new AllTimeReputationActor(mockAllTimeReputation,
    mockWriteAllTimeReputation)), "allTimeReputationActor")
  val monthlyReputationActorRef: ActorRef = system.actorOf(Props(new MonthlyReputationActor(mockMonthlyReputation,
    mockWriteMonthlyReputation)), "monthlyReputationActor")
  val quarterlyReputationActorRef: ActorRef = system.actorOf(Props(new QuarterlyReputationActor(mockQuarterlyReputation,
    mockWriteQuarterlyReputation)), "quarterlyReputationActor")

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "other contribution Actor" must {
    "not do anything with incorrect message" in {
      val probe = TestProbe()
      val mockActorRef = probe.ref

      val scriptActor = system.actorOf(Props(new OtherContributionActor(mockStoreOSContribution, mockStoreConferenceDetails, mockStoreBooksContribution
        , mockStoreResearchPaperContribution, mockOtherContribution, mockActorRef)))
      probe watch scriptActor
      probe.send(scriptActor, "display reputation")
      probe.expectMsg(InvalidMessage)
    }
    "execute other contribution script" in {
      val probe = TestProbe.apply()
      val mockActorRef = probe.ref
      val scriptActor = system.actorOf(Props(new OtherContributionActor(
        mockStoreOSContribution, mockStoreConferenceDetails, mockStoreBooksContribution,
        mockStoreResearchPaperContribution, mockOtherContribution, mockActorRef)))
      probe watch scriptActor
      probe.send(scriptActor, ExecuteOtherContributionScript)
      probe.expectMsg(StoredOtherContributionData)
    }
  }
}

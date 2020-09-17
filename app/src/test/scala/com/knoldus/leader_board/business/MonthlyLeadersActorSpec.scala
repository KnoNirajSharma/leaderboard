package com.knoldus.leader_board.business

import java.sql.Connection

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.knoldus.leader_board.DatabaseConnection
import com.knoldus.leader_board.infrastructure.{StoreTopFiveLeaders, StoreTopFiveLeadersImpl}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class MonthlyLeadersActorSpec extends TestKit(ActorSystem("MonthlyLeadersActor")) with ImplicitSender
  with AnyWordSpecLike with Matchers with BeforeAndAfterAll with MockitoSugar {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockStoreMonthlyLeaders: StoreTopFiveLeaders = mock[StoreTopFiveLeadersImpl]


  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "Monthly Leaders actor" must {

    "store monthly top five leaders in hall of fame table" in {
      val probe = TestProbe()

      when(mockStoreMonthlyLeaders.insertTopFiveLeaders)
        .thenReturn(List(5))

      val monthlyLeadersActor = system.actorOf(Props(new MonthlyLeadersActor(mockStoreMonthlyLeaders)))
      probe watch monthlyLeadersActor
      probe.send(monthlyLeadersActor, StoreMonthlyLeaders)
      probe.expectMsg("stored top five leaders")
    }

    "store top five leaders with incorrect message" in {
      val probe = TestProbe()
      when(mockStoreMonthlyLeaders.insertTopFiveLeaders)
        .thenReturn(List(5))
      val monthlyLeadersActor = system.actorOf(Props(new MonthlyLeadersActor(mockStoreMonthlyLeaders)))
      probe watch monthlyLeadersActor
      probe.send(monthlyLeadersActor, "fetch top five leaders")
      probe.expectMsg("invalid message")
    }
  }
}

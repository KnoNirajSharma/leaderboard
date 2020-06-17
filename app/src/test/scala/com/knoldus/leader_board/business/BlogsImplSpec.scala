package com.knoldus.leader_board.business

import java.sql.{Connection, Timestamp}
import java.time.Instant

import com.knoldus.leader_board.infrastructure.FetchBlogsImpl
import com.knoldus.leader_board.{Blog, DatabaseConnection}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class BlogsImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockFetchData: FetchBlogsImpl = mock[FetchBlogsImpl]
  val mockURLResponse: URLResponse = mock[URLResponse]
  val blogs: Blogs = new BlogsImpl(mockFetchData, mockURLResponse, ConfigFactory.load())
  val spyBlogs: Blogs = spy(blogs)

  "Blogs" should {

    val blogData: String =
      """[{
        |  "id": 74399,
        |  "date": "2020-05-30T02:20:11",
        |  "date_gmt": "2020-05-29T20:50:11",
        |  "guid": {
        |   "rendered": "https:\/\/blog.knoldus.com\/?p=74399"
        |  },
        |  "modified": "2020-05-30T02:59:01",
        |  "modified_gmt": "2020-05-29T21:29:01",
        |  "slug": "realtime-supply-chains",
        |  "status": "publish",
        |  "type": "post",
        |  "link": "https:\/\/blog.knoldus.com\/realtime-supply-chains\/",
        |  "title": {
        |   "rendered": "Realtime Supply Chains"
        |  },
        |  "_embedded": {
        |   "author": [{
        |    "id": 66,
        |    "name": "Ram Indukuri",
        |    "url": "",
        |    "description": "As an Engineer, I help customers in architecting platforms using Spark, Mesos, Cassandra, Kafka (And their commercial versions).  As a partner, I guide customers in setting up the organization, processes and build top-notch teams that solve complex problems or deliver digital transformation.\r\n\r\nMy interests and expertise are in Mathematics, Machine learning, Microservices, Linked data, distributed cloud infrastructure, Real-time enterprise data integration.",
        |    "link": "https:\/\/blog.knoldus.com\/author\/ramindukuri\/",
        |    "slug": "ramindukuri"
        |   }]
        |  }
        |}]""".stripMargin

    "return latest blogs from API" in {
      when(mockFetchData.fetchMaxBlogPublicationDate)
        .thenReturn(Option(Timestamp.from(Instant.parse("2020-04-13T14:56:40Z"))))
      val date = "2020-05-30 02:20:11"
      when(mockURLResponse.getResponse(ConfigFactory.load.getString("urlForLatestBlogs"), date))
        .thenReturn(blogData)
      val publishedOn = Timestamp.valueOf(date)
      val listOfBlogs = List(Blog(
        Option(74399),
        Option("ramindukuri"),
        publishedOn,
        Option("Realtime Supply Chains")))
      assert(blogs.getLatestBlogsFromAPI == List())
    }

    "return list of latest blogs" in {
      val date = "2020-05-30 02:20:11"
      val publishedOn = Timestamp.valueOf(date)
      assert(blogs.getListOfLatestBlogs(blogData) == List(Blog(
        Option(74399),
        Option("ramindukuri"),
        publishedOn,
        Option("Realtime Supply Chains"))))
    }
  }
}

package com.knoldus.leader_board.business

import java.sql.{Connection, Timestamp}
import java.time.{Instant, ZoneOffset, ZonedDateTime}

import com.knoldus.leader_board.infrastructure.FetchDataImpl
import com.knoldus.leader_board.{Blog, DatabaseConnection}
import com.typesafe.config.ConfigFactory
import org.mockito.{Mockito, MockitoSugar}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class BlogsImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockFetchData: FetchDataImpl = mock[FetchDataImpl]
  val mockURLResponse: URLResponse = mock[URLResponse]
  val blogs: Blogs = new BlogsImpl(mockFetchData, mockURLResponse, ConfigFactory.load())
  val spyBlogs: Blogs = spy(blogs)

  "Blogs" should {

    val blogsData: String =
      """{
        |  "found": 2071,
        |  "posts": [{
        |   "ID": 70547,
        |   "site_ID": 24082517,
        |   "author": {
        |    "ID": 141,
        |    "login": "pankajchaudhary5",
        |    "email": false,
        |    "name": "Pankaj Chaudhary",
        |    "first_name": "Pankaj",
        |    "last_name": "Chaudhary",
        |    "nice_name": "pankajchaudhary5",
        |    "URL": "",
        |    "avatar_URL": "https:\/\/secure.gravatar.com\/avatar\/52bed28ffaa1c0cb6e78963e007841f3?s=96&d=monsterid&r=g",
        |    "profile_URL": "https:\/\/en.gravatar.com\/52bed28ffaa1c0cb6e78963e007841f3"
        |   },
        |   "date": "2020-04-14T12:35:30+05:30",
        |   "modified": "2020-04-14T12:35:31+05:30",
        |   "title": "Get a Look on Key Rust Crates for WebAssembly"
        |  }]
        |}""".stripMargin

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

    "return all blogs from API" in {
      MockitoSugar.doReturn(4).when(spyBlogs).getTotalNoOfPosts

      val date = "2020-04-14T12:35:30+05:30"
      val odtInstanceAtOffset = ZonedDateTime.parse(date)
      val odtInstanceAtUTC = odtInstanceAtOffset.withZoneSameInstant(ZoneOffset.UTC)
      val publishedOn = Timestamp.from(odtInstanceAtUTC.toInstant)

      val listOfBlogs = List(Blog(
        Option(70547),
        Option("pankajchaudhary5"),
        publishedOn,
        Option("Get a Look on Key Rust Crates for WebAssembly"))
      )

      Mockito.doAnswer(_ => listOfBlogs).when(spyBlogs).getAllBlogs(1)

      assert(spyBlogs.getAllBlogsFromAPI == listOfBlogs)
    }

    "return list of all blogs" in {
      val date = "2020-04-14T12:35:30+05:30"
      val odtInstanceAtOffset = ZonedDateTime.parse(date)
      val odtInstanceAtUTC = odtInstanceAtOffset.withZoneSameInstant(ZoneOffset.UTC)
      val publishedOn = Timestamp.from(odtInstanceAtUTC.toInstant)

      assert(blogs.getListOfAllBlogs(blogsData) == List(Blog(
        Option(70547),
        Option("pankajchaudhary5"),
        publishedOn,
        Option("Get a Look on Key Rust Crates for WebAssembly"))))
    }

    "return latest blogs from API" in {
      when(mockFetchData.fetchMaxBlogPublicationDate)
        .thenReturn(Option(Timestamp.from(Instant.parse("2020-04-13T14:56:40Z"))))

      when(mockURLResponse.getResponse(ConfigFactory.load.getString("urlForLatestBlogs")))
        .thenReturn(blogData)

      val date = "2020-05-30 02:20:11"
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

    "return list of blogs if last page is the first page" in {
      assert(blogs.getAllBlogs(1) == List())
    }

    "return list of blogs if last page is not first page" in {
      when(mockURLResponse.getResponse(ConfigFactory.load.getString("urlForLatestBlogs")))
        .thenReturn(blogsData)

      val date = "2020-04-14T12:35:30+05:30"
      val odtInstanceAtOffset = ZonedDateTime.parse(date)
      val odtInstanceAtUTC = odtInstanceAtOffset.withZoneSameInstant(ZoneOffset.UTC)
      val publishedOn = Timestamp.from(odtInstanceAtUTC.toInstant)

      val listOfBlogs = List(Blog(
        Option(70547),
        Option("pankajchaudhary5"),
        publishedOn,
        Option("Get a Look on Key Rust Crates for WebAssembly")))

      assert(blogs.getAllBlogs(2) == List())
    }
  }
}

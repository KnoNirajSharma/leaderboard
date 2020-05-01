package com.knoldus.leader_board.business

import java.sql.{Connection, Timestamp}
import java.time.{Instant, ZoneOffset, ZonedDateTime}

import akka.actor.ActorSystem
import com.knoldus.leader_board.infrastructure.FetchDataImpl
import com.knoldus.leader_board.{Blog, DatabaseConnection}
import com.typesafe.config.ConfigFactory
import org.mockito.{Mockito, MockitoSugar}
import org.scalatest.flatspec.AnyFlatSpec

import scala.concurrent.ExecutionContextExecutor

class BlogsImplSpec extends AnyFlatSpec with MockitoSugar {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockFetchData: FetchDataImpl = mock[FetchDataImpl]
  val blogs: Blogs = new BlogsImpl(mockFetchData, ConfigFactory.load())

  val spyBlogs: Blogs = spy(blogs)

  "get all pages all blogs from api" should "give blog author object" in {
    MockitoSugar.doReturn(4).when(spyBlogs).getTotalNoOfPosts

    val listOfBlogs = List(
      Blog(Option(1001), Option("mukesh01"),
        Timestamp.from(Instant.parse("2020-04-13T14:56:40Z")),
        Option("windows handling using selenium webdriver")),
      Blog(Option(1002), Option("abhishek02"),
        Timestamp.from(Instant.parse("2020-04-13T13:10:40Z")),
        Option("ChatOps : Make your life easy")),
      Blog(Option(1003), Option("komal03"),
        Timestamp.from(Instant.parse("2020-04-13T12:57:27Z")),
        Option("Automating Windows Controls in Selenium")),
      Blog(Option(1004), Option("mukesh01"),
        Timestamp.from(Instant.parse("2020-04-13T12:40:20Z")),
        Option("Java 9: Enhance your Jav…ptional API enhancement")))


    Mockito.doAnswer(_=>listOfBlogs).when(spyBlogs).getAllBlogs(1)

     assert(spyBlogs.getAllPagesBlogsFromAPI== listOfBlogs)
  }

  "get first page all blogs from api" should "give blog author object" in {

    val listOfBlogs = List(
      Blog(Option(1001), Option("mukesh01"),
        Timestamp.from(Instant.parse("2020-04-13T14:56:40Z")),
        Option("windows handling using selenium webdriver")),
      Blog(Option(1002), Option("abhishek02"),
        Timestamp.from(Instant.parse("2020-04-13T13:10:40Z")),
        Option("ChatOps : Make your life easy")),
      Blog(Option(1003), Option("komal03"),
        Timestamp.from(Instant.parse("2020-04-13T12:57:27Z")),
        Option("Automating Windows Controls in Selenium")),
      Blog(Option(1004), Option("mukesh01"),
        Timestamp.from(Instant.parse("2020-04-13T12:40:20Z")),
        Option("Java 9: Enhance your Jav…ptional API enhancement")))

    when(spyBlogs.getAllBlogs(1))
      .thenReturn(
        listOfBlogs
      )

    assert(spyBlogs.getFirstPageBlogsFromAPI == listOfBlogs)
  }

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

  "get list of blogs" should "give list of blogs if blog date is greater than maximum publication date of blog" in {
    when(mockFetchData.fetchMaxBlogPublicationDate)
      .thenReturn(Option(Timestamp.from(Instant.parse("2020-04-13T14:56:40Z"))))
    val date = "2020-04-14T12:35:30+05:30"
    val odtInstanceAtOffset = ZonedDateTime.parse(date)
    val odtInstanceAtUTC = odtInstanceAtOffset.withZoneSameInstant(ZoneOffset.UTC)
    val publishedOn = Timestamp.from(odtInstanceAtUTC.toInstant)

    assert(blogs.getListOfBlogs(blogsData) == List(Blog(
      Option(70547),
      Option("pankajchaudhary5"),
      publishedOn,
      Option("Get a Look on Key Rust Crates for WebAssembly"))))
  }

  "get list of blogs" should "not give list of blogs if blog date is smaller than maximum publication date of blog" in {
    when(mockFetchData.fetchMaxBlogPublicationDate)
      .thenReturn(Option(Timestamp.from(Instant.parse("2020-04-15T14:56:40Z"))))

    assert(blogs.getListOfBlogs(blogsData) == List())
  }

  "get list of blogs" should "give list of blogs if maximum publication date of blog is not present" in {
    when(mockFetchData.fetchMaxBlogPublicationDate)
      .thenReturn(None)
    val date = "2020-04-14T12:35:30+05:30"
    val odtInstanceAtOffset = ZonedDateTime.parse(date)
    val odtInstanceAtUTC = odtInstanceAtOffset.withZoneSameInstant(ZoneOffset.UTC)
    val publishedOn = Timestamp.from(odtInstanceAtUTC.toInstant)

    assert(blogs.getListOfBlogs(blogsData) == List(Blog(
      Option(70547),
      Option("pankajchaudhary5"),
      publishedOn,
      Option("Get a Look on Key Rust Crates for WebAssembly"))))
  }

  "get list of blogs" should "not give list of blogs if maximum publication date of blog is not present and blog " +
    "date is smaller than minimum date" in {
    val blogData: String =
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
        |   "date": "0000-03-01T09:37:10+05:30",
        |   "modified": "2020-04-14T12:35:31+05:30",
        |   "title": "Get a Look on Key Rust Crates for WebAssembly"
        |  }]
        |}""".stripMargin
    when(mockFetchData.fetchMaxBlogPublicationDate)
      .thenReturn(None)

    assert(blogs.getListOfBlogs(blogData) == List())
  }
  "get total no of posts" should "total no. of posts" in{
    val blogData: String =
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
        |   "date": "0000-03-01T09:37:10+05:30",
        |   "modified": "2020-04-14T12:35:31+05:30",
        |   "title": "Get a Look on Key Rust Crates for WebAssembly"
        |  }]
        |}""".stripMargin
    MockitoSugar.doReturn(blogData).when(spyBlogs).getResponse("url")
    assert(spyBlogs.getTotalNoOfPosts==4)
  }
  "get all blogs" should "return all blogs of page" in{
    val blogData: String =
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
        |   "date": "0000-03-01T09:37:10+05:30",
        |   "modified": "2020-04-14T12:35:31+05:30",
        |   "title": "Get a Look on Key Rust Crates for WebAssembly"
        |  }]
        |}""".stripMargin
    Mockito.doAnswer(_=>blogData).when(spyBlogs).getResponse("url")
    val date = "2020-04-14T12:35:30+05:30"
    val odtInstanceAtOffset = ZonedDateTime.parse(date)
    val odtInstanceAtUTC = odtInstanceAtOffset.withZoneSameInstant(ZoneOffset.UTC)
    val publishedOn = Timestamp.from(odtInstanceAtUTC.toInstant)
    val listOfBlogs = List(
      Blog(Option(1001), Option("mukesh01"),
        Timestamp.from(Instant.parse("2020-04-13T14:56:40Z")),
        Option("windows handling using selenium webdriver")),
      Blog(Option(1002), Option("abhishek02"),
        Timestamp.from(Instant.parse("2020-04-13T13:10:40Z")),
        Option("ChatOps : Make your life easy")),
      Blog(Option(1003), Option("komal03"),
        Timestamp.from(Instant.parse("2020-04-13T12:57:27Z")),
        Option("Automating Windows Controls in Selenium")),
      Blog(Option(1004), Option("mukesh01"),
        Timestamp.from(Instant.parse("2020-04-13T12:40:20Z")),
        Option("Java 9: Enhance your Jav…ptional API enhancement")))
    assert(spyBlogs.getAllBlogs(1)==listOfBlogs)
  }
}

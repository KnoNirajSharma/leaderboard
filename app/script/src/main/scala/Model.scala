import java.sql.Timestamp

case class Author(authorName: Option[String], authorLogin: Option[String])

case class Blog(blogId: Option[Int], authorLogin: Option[String], publishedOn: Timestamp, title: Option[String])

case class BlogAuthor(blogs: List[Blog], authors: List[Author])


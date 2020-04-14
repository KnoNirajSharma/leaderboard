import scala.concurrent.Await
import scala.concurrent.duration._

object DriverApp extends App {
  val databaseConnection = new DatabaseConnection
  val fetchData = new FetchData(databaseConnection)
  val storeData = new StoreData(databaseConnection)

  val blogs = new Blogs(fetchData)


  val getAllBlogsAndAuthors = blogs.getAllBlogsAndAuthors

  val listOfBlogsAndAuthors = Await.result(getAllBlogsAndAuthors, 3.minutes)

  val storedKnolders = storeData.insertKnolder(listOfBlogsAndAuthors)

  val storedBlogs = storeData.insertBlog(listOfBlogsAndAuthors)


}

import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.{BasicCookieStore, CloseableHttpClient, HttpClientBuilder}
import org.apache.http.impl.cookie.BasicClientCookie

val builder: URIBuilder = new URIBuilder("https://blog.knoldus.com/wp-json/wp/v2/posts")
  .setParameter("per_page", "100")
  .setParameter("_embed", "author")

val cookieStore = new BasicCookieStore()
val anotherOne =
  new BasicClientCookie("wordpress_logged_in_XXXXXXXXXXXXXXXx", "XXXXXXXXXXX")

anotherOne
  .setDomain("blog.knoldus.com")

anotherOne
  .setPath("/")

cookieStore
  .addCookies(Array(anotherOne))

val request = new HttpGet(builder.build())

val client: CloseableHttpClient = HttpClientBuilder
  .create()
  .setDefaultCookieStore(cookieStore)
  .build()

val result = client
  .execute(request)

println(
  IOUtils.toString(result
    .getEntity
    .getContent))



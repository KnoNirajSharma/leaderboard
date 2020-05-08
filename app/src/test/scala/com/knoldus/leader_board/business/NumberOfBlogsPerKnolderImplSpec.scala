package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class NumberOfBlogsPerKnolderImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockReadKnolder: ReadKnolder = mock[ReadKnolderImpl]
  val mockReadBlog: ReadBlog = mock[ReadBlogImpl]
  val mockReadAllTime: ReadAllTime = mock[ReadAllTimeImpl]
  val mockWriteAllTime: WriteAllTime = mock[WriteAllTimeImpl]
  val numberOfBlogs: NumberOfBlogsPerKnolder =
    new NumberOfBlogsPerKnolderImpl(mockReadBlog, mockReadAllTime)

  "get knolder blog count" should "return blog count of each knolder along with their knolder id" in {

    when(mockReadBlog.fetchKnoldersWithBlogs)
      .thenReturn(List(BlogCount(1, "mukesh01", "Mukesh Gupta", 2)))
    val knolderBlogCount = List(KnolderBlogCount(Some(1), BlogCount(1, "mukesh01", "Mukesh Gupta", 2)))

    when(mockReadAllTime.fetchKnolderIdFromAllTime(1))
      .thenReturn(Option(1))

    assert(numberOfBlogs.getKnolderBlogCount == knolderBlogCount)
  }
}

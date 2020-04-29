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
    new NumberOfBlogsPerKnolderImpl(mockReadKnolder, mockReadBlog, mockReadAllTime, mockWriteAllTime)

  "get number of blogs " should "return number of blogs of each knolder" in {
    when(mockReadKnolder.fetchKnolders)
      .thenReturn(List(Knolder(1, "Mukesh Gupta", "mukesh01", "mukesh.kumar@knoldus.com"),
        Knolder(2, "Abhishek Baranwal", "abhishek02", "abhishek.baranwal@knoldus.com"),
        Knolder(3, "Komal Rajpal", "komal03", "komal.rajpal@knoldus.com")))

    when(mockReadBlog.fetchKnoldersWithBlogs)
      .thenReturn(List(KnolderBlog(1001, "mukesh01"), KnolderBlog(1002, "abhishek02"),
        KnolderBlog(1003, "komal03"), KnolderBlog(1004, "mukesh01")))

    assert(numberOfBlogs.getNumberOfBlogsPerKnolder == List(BlogCount(1, "mukesh01", "Mukesh Gupta", 2),
      BlogCount(2, "abhishek02", "Abhishek Baranwal", 1), BlogCount(3, "komal03", "Komal Rajpal", 1)))
  }

  val blogCount = List(BlogCount(1, "mukesh01", "Mukesh Gupta", 2))

  "insert all time data" should "insert number of blogs in all_time table" in {
    when(mockReadAllTime.fetchKnolderIdFromAllTime(1))
      .thenReturn(None)

    when(mockWriteAllTime.insertAllTimeData(BlogCount(1, "mukesh01", "Mukesh Gupta", 2)))
      .thenReturn(1)

    assert(numberOfBlogs.insertBlogCount(blogCount).sum == 1)
  }

  "update all time " should "update number of blogs in all_time table" in {
    when(mockReadAllTime.fetchKnolderIdFromAllTime(1))
      .thenReturn(Option(1))

    when(mockWriteAllTime.updateAllTimeData(BlogCount(1, "mukesh01", "Mukesh Gupta", 2)))
      .thenReturn(1)

    assert(numberOfBlogs.updateBlogCount(blogCount).sum == 1)
  }
}

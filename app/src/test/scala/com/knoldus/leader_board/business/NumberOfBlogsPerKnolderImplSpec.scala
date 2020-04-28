package com.knoldus.leader_board.business

import com.knoldus.leader_board._
import com.knoldus.leader_board.infrastructure.{FetchDataImpl, StoreDataImpl, UpdateDataImpl}
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec

class NumberOfBlogsPerKnolderImplSpec extends AnyFlatSpec with MockitoSugar {
  val mockFetchData: FetchDataImpl = mock[FetchDataImpl]
  val mockStoreData: StoreDataImpl = mock[StoreDataImpl]
  val mockUpdateData: UpdateDataImpl = mock[UpdateDataImpl]
  val numberOfBlogs: NumberOfBlogsPerKnolder =
    new NumberOfBlogsPerKnolderImpl(mockFetchData, mockUpdateData, mockStoreData)

  "get number of blogs " should "return number of blogs of each knolder" in {
    when(mockFetchData.fetchKnolders)
      .thenReturn(List(Knolder(1, "Mukesh Gupta", "mukesh01", "mukesh.kumar@knoldus.com"),
        Knolder(2, "Abhishek Baranwal", "abhishek02", "abhishek.baranwal@knoldus.com"),
        Knolder(3, "Komal Rajpal", "komal03", "komal.rajpal@knoldus.com")))

    when(mockFetchData.fetchKnoldersWithBlogs)
      .thenReturn(List(KnolderBlog(1001, "mukesh01"), KnolderBlog(1002, "abhishek02"),
        KnolderBlog(1003, "komal03"), KnolderBlog(1004, "mukesh01")))

    assert(numberOfBlogs.getNumberOfBlogsPerKnolder == List(BlogCount(1, "mukesh01", "Mukesh Gupta", 2),
      BlogCount(2, "abhishek02", "Abhishek Baranwal", 1), BlogCount(3, "komal03", "Komal Rajpal", 1)))
  }

  val blogCount = List(BlogCount(1, "mukesh01", "Mukesh Gupta", 2))

  "manage all time " should "update number of blogs in all_time table" in {
    when(mockFetchData.fetchKnolderIdFromAllTime(1))
      .thenReturn(Option(1))

    when(mockUpdateData.updateAllTimeData(BlogCount(1, "mukesh01", "Mukesh Gupta", 2)))
      .thenReturn(1)

    assert(numberOfBlogs.manageAllTimeData(blogCount).sum == 1)
  }

  "manage all time data" should "insert number of blogs in all_time table" in {
    when(mockFetchData.fetchKnolderIdFromAllTime(1))
      .thenReturn(None)

    when(mockStoreData.insertAllTimeData(BlogCount(1, "mukesh01", "Mukesh Gupta", 2)))
      .thenReturn(1)

    assert(numberOfBlogs.manageAllTimeData(blogCount).sum == 1)
  }
}

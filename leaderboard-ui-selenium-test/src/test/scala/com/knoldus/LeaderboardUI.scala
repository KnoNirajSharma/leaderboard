package com.knoldus
import java.util.concurrent.TimeUnit
import com.typesafe.config.{Config, ConfigFactory}
import org.openqa.selenium.chrome.ChromeDriver
import org.scalatestplus.testng.TestNGSuite
import org.testng.{Assert, Reporter}
import org.testng.annotations.{Test}
class LeaderboardUI extends TestNGSuite {
  val config: Config = ConfigFactory.load("application.conf")
  val DriverPath = config.getString("webDriverPath")
  val Driver = config.getString("webDriver")
  val name = config.getString("knolderName")
  val url = config.getString("url")
  System.setProperty(Driver, DriverPath)
  val driver = new ChromeDriver()
  @Test(enabled = false)
  def Card(): Unit = {
    /**
     * method for extracting the cards template of Blogs, Knolx, Webinars and TechHub templates which may me added in the upcoming sprints
     */
    driver.manage().window().maximize()
    driver.get(url)
    Thread.sleep(2000)
    val cardName = driver.findElementsByCssSelector("span[class='title']")
    for (iterator <- 0 until cardName.size()) {
    }
    Reporter.log("Blog card is present and its value is " + cardName.get(1).getText)
    Reporter.log("Knolx card is present and its value is " + cardName.get(2).getText)
    Reporter.log("Webinars card is present and its value is " + cardName.get(3).getText)
    Reporter.log("TechHub Template card is present and its value is " + cardName.get(4).getText)
    Reporter.log("Os contibution card is present and its value is " + cardName.get(5).getText)
    Reporter.log("Conferences card is present and its value is " + cardName.get(6).getText)
  }
  @Test(priority = 1)
  def tableSorting(): Unit = {
    /**
     * Method to test the sorting functionality of the tables
     */
    driver.manage().window().maximize()
    driver.get(url)
    val title = driver.findElementByCssSelector("h1[class='col-sm-4 col-9 col-sm-10']")
    val titleText = title.getText.trim
    Assert.assertEquals(titleText, "Leaderboard")
    val sortButton = driver.findElementsByCssSelector("span[class='sort-btn']")
    for (iterator <- 0 until sortButton.size()) {
    }
    val nameColumn = driver.findElementByXPath("//*[@class='datatable-header-cell-label draggable' and contains(text(),'Name')]")
    val nameColumnText = nameColumn.getText.trim
    Assert.assertEquals(nameColumnText, "Name")
    Reporter.log("Name column is present")
    val allTimeScore = driver.findElementByXPath("//*[@class='datatable-header-cell-label draggable' and contains(text(),'Overall Score')]")
    val allTimeScoreText = allTimeScore.getText.trim
    Assert.assertEquals(allTimeScoreText, "Overall Score")
    Reporter.log("Overall score column is present")
    sortButton.get(1).click() //sorting functionality of Overall score tables by clicking the sorting button
    Reporter.log("OverAll Score Column is sortable")
    val sortedScore = driver.findElementByCssSelector("span[title='5']")
    val sortedScoreText = sortedScore.getText.trim
    Assert.assertEquals(sortedScoreText,"5")
    val allTimeRank = driver.findElementByXPath("//*[@class='datatable-header-cell-label draggable' and contains(text(),'Overall Rank')]")
    val allTimeRankText = allTimeRank.getText.trim
    Assert.assertEquals(allTimeRankText, "Overall Rank")
    Reporter.log("Overall rank column is present")
    sortButton.get(2).click()//sorting functionality of Overall Rank tables by clicking the sorting button
    Reporter.log("OverAll Rank Column is sortable")
    val sortedRank = driver.findElementByCssSelector("span[title='1']")
    val sortedRankText = sortedRank.getText.trim
    Assert.assertEquals(sortedRankText, "1")
    val monthScore = driver.findElementByXPath("//*[@class='datatable-header-cell-label draggable' and contains(text(),'Monthly Score')]")
    val monthScoreText = monthScore.getText.trim
    Assert.assertEquals(monthScoreText, "Monthly Score")
    Reporter.log("Monthly Score column is present")
    sortButton.get(3).click()//sorting functionality of Monthly score tables by clicking the sorting button
    Reporter.log("Monthly Score Column is sortable")
    val monthlyRank = driver.findElementByXPath("//*[@class='datatable-header-cell-label draggable' and contains(text(),'Monthly Rank')]")
    val monthlyRankText = monthlyRank.getText.trim
    Assert.assertEquals(monthlyRankText, "Monthly Rank")
    Reporter.log("Monthly rank column is present")
    sortButton.get(4).click()//sorting functionality of Monthly rank tables by clicking the sorting button
    Reporter.log("Monthly Rank Column is sortable")
    val sortedMonthlyRank = driver.findElementByCssSelector("span[title='1']")
    val sortedMonthlyRankText = sortedMonthlyRank.getText.trim
    Assert.assertEquals(sortedMonthlyRankText, "1")
    val threeMonthStreak = driver.findElementByXPath("//*[@class='datatable-header-cell-label draggable' and contains(text(),'3 Month Streak')]")
    val threeMonthStreakText = threeMonthStreak.getText.trim
    Assert.assertEquals(threeMonthStreakText, "3 Month Streak")
    Reporter.log("3 month Streak column is present")
  }
  @Test(priority = 2)
  def searchBar(): Unit = {
    /**
     * method for checking the search filter
     */
    driver.findElementByCssSelector("input[placeholder='Search...']").click()//To locate the search Bar
    Reporter.log("Search Bar is present")
    driver.findElementByCssSelector("input[placeholder='Search...']").sendKeys(name)//to search for a knolder through search bar
    Reporter.log("Knolder we searched for is " + name)
    val details = driver.findElementsByCssSelector("span[title]")
    for (iterator <- 0 until details.size()) { //for fetching the details of knolder whom we searched
    }
    Reporter.log("Overall score is " + details.get(1).getText)
    Reporter.log("Overall Rank is " + details.get(2).getText)
    Reporter.log("Monthly score is " + details.get(3).getText)
    Reporter.log("Monthly Rank is " + details.get(4).getText)
    Reporter.log("3 Month Streak is " + details.get(1).getText)
  }
  @Test(priority = 3)
  def knolderDetails(): Unit = {
    /**
     * method to check for the knolder details by clicking on the knolder's name
     */
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    driver.findElementByCssSelector("span[title='"+name+"']").click() //  to check whether the details of knolder appears on clicking on it
    Reporter.log("Knolder name is clickable")
    val currentMonth = driver.findElementByCssSelector("span[class='badge badge-pill badge-warning']")
    val currentMonthText = currentMonth.getText.trim// to fetch the current month score
    Reporter.log("This month score is " + currentMonthText)
    val thisMonthBlogs = driver.findElementByCssSelector("span[class='col-4 col-md-4 col-xl-3']")
    val thisMonthBlogstext = thisMonthBlogs.getText.trim // to get the current month blogs count
    Reporter.log("Total blogs for this month: " + thisMonthBlogstext)
    val totalScoreMonth = driver.findElementByCssSelector("span[class='col-3 col-md-3 col-xl-2']")
    val totalScoreMonthtext = totalScoreMonth.getText.trim // to get total score fot the current month
    Reporter.log("Total score for this month is: " + totalScoreMonthtext)
    driver.findElementByCssSelector(".all-time-btn").click() // to check whether the all time button is clickable or not
    Reporter.log("all time button is clickable")
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS)
    val totalBlogsAllTime = driver.findElementByCssSelector("span[class='col-4 col-md-4 col-xl-3']")
    val totalBlogsAllTimeText = totalBlogsAllTime.getText.trim // to get the total number of blogs
    Reporter.log("Total blogs(all time) : " + totalBlogsAllTimeText)
    val totalScoreAllTime = driver.findElementByCssSelector("span[class='col-3 col-md-3 col-xl-2']")
    val totalScoreAllTimeText = totalScoreAllTime.getText.trim // to fetch the all time score
    Reporter.log("Total Score(all time) : " + totalScoreAllTimeText)
    driver.findElementByCssSelector("div[class='col-8 col-md-8']")
    Reporter.log("Blogs Titles are present")
    driver.findElementByCssSelector("div[class='col-4 col-md-4 text-right text-xl-center']")
    Reporter.log("Date and time details of blogs are present")
    driver.findElementByCssSelector("input[name='date']").click() // to access the calender
    val month = driver.findElementByXPath("//*[contains(text(),'May')]").click() // to check for the details for month of may
    Reporter.log("Month can be selected from the dropDown table")
  }
}
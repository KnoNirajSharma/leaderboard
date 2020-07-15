package com.knoldus
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.YearMonth
import java.util.{Calendar, Date, Locale, TimeZone}
import java.util.concurrent.TimeUnit
import com.typesafe.config.{Config, ConfigFactory}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import org.scalatestplus.testng.TestNGSuite
import org.testng.{Assert, Reporter}
import org.testng.annotations.Test
class LeaderboardUI extends TestNGSuite {
  val config: Config = ConfigFactory.load("application.conf")
  val DriverPath = config.getString("webDriverPath")
  val Driver = config.getString("webDriver")
  val name = config.getString("knolderName")
  val url = config.getString("url")
  System.setProperty(Driver, DriverPath)
  val driver = new ChromeDriver()
  @Test(priority = 0)
  def totalCountHeader(): Unit = {
    /**
     * method for extracting the cards template of Blogs, Knolx, Webinars, TechHub templates, OS contibutions, Books/Papers and Conferences
     */
    driver.manage().window().maximize()
    driver.get(url)
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
    val cardName = driver.findElementsByCssSelector("div[class='my-1  mx-1 d-flex justify-content-between']")
    for (iterator <- 0 until cardName.size()) { // for fetching all the cards name
    }
    val allTimeStatus = driver.findElementsByCssSelector("span[class='contribution-count badge-secondary mr-1 shadow-sm badge-pill']")
    for (iterator <- 0 until allTimeStatus.size()) { // for fetching all time status of the cards
    }
    val currentMonthStatus = driver.findElementsByCssSelector("span[class='contribution-count badge-info shadow-sm badge-pill']")
    for (iterator <- 0 until currentMonthStatus.size()) { // for fetching current month status of the cards
    }
    Reporter.log("Blogs all time count is present: " + allTimeStatus.get(0).getText)
    Reporter.log("Blogs current month count is present: " + driver.findElementByCssSelector("span[class='badge-info contribution-count shadow-sm badge-pill']").getText)
    Reporter.log("Knolx all time count is present: " + allTimeStatus.get(1).getText)
    Reporter.log("Knolx current month count is present: " + currentMonthStatus.get(0).getText)
    Reporter.log("Webinar all time count is present: " + allTimeStatus.get(2).getText)
    Reporter.log("Webinar current month count is present: " + currentMonthStatus.get(1).getText)
    Reporter.log("TecHub Template all time count is present: " + allTimeStatus.get(3).getText)
    Reporter.log("TecHub Template month count is present: " + currentMonthStatus.get(2).getText)
    Reporter.log("OS contribution all time count is present: " + allTimeStatus.get(4).getText)
    Reporter.log("OS contribution month count is present: " + currentMonthStatus.get(3).getText)
    Reporter.log("Books/Papers all time count is present: " + allTimeStatus.get(5).getText)
    Reporter.log("Books/Papers month count is present: " + currentMonthStatus.get(4).getText)
    Reporter.log("Conferences all time count is present: " + allTimeStatus.get(6).getText)
    Reporter.log("Conferences month count is present: " + currentMonthStatus.get(5).getText)
  }
  @Test(priority = 1)
  def scoringWeightage(): Unit = {
    val scoringDetail = driver.findElementByCssSelector("h2[class='popover-title']")
    val actionBuilder = new Actions(driver)
    actionBuilder
      .moveToElement(scoringDetail) // to hover over the scoring section on the right side of the table
      .build()
      .perform()
    val event = driver.findElementByXPath("//*/table/tr[1]/th[1]")
    Assert.assertEquals(event.getText.trim, "Event")
    val weightage = driver.findElementByXPath("//*/table/tr[1]/th[2]")
    Assert.assertEquals(weightage.getText.trim, "Weightage")
    val integration = driver.findElementByXPath("//*/table/tr[1]/th[3]")
    Assert.assertEquals(integration.getText.trim, "Integration")
    Reporter.log("In the popover weightage message we have " + event.getText.trim + ", " + weightage.getText.trim + " and " + integration.getText.trim + " as criteria")
    val blogs = driver.findElementByXPath("//*/table/tr[2]/td[1]")
    Assert.assertEquals(blogs.getText.trim, "Blogs")// assertion on the events of blogs
    val blogWeightage = driver.findElementByXPath("//*/table/tr[2]/td[2]")
    Assert.assertEquals(blogWeightage.getText.trim, "5")// assertion on the weightage of blogs
    val blogsIntergration = driver.findElementByXPath("//*/table/tr[2]/td[3]")
    Assert.assertEquals(blogsIntergration.getText.trim, "Y")// assertion on the Integration of blogs
    Reporter.log("Event: " + blogs.getText.trim+ ", "+ "Weightage:" + blogWeightage.getText.trim + " " +"Integration: "+ blogsIntergration.getText.trim)
    val knolx = driver.findElementByXPath("//*/table/tr[3]/td[1]")
    Assert.assertEquals(knolx.getText.trim, "Knolx") // assertion on the event of knolx
    val knolxWeightage = driver.findElementByXPath("//*/table/tr[3]/td[2]")
    Assert.assertEquals(knolxWeightage.getText.trim, "10") // assertion on the weightage of knolx
    val knolxIntegration = driver.findElementByXPath("//*/table/tr[3]/td[3]")
    Assert.assertEquals(knolxIntegration.getText.trim, "N") // assertion on the Integration of knolx
    Reporter.log("Event: " +knolx.getText.trim+", Weightage: "+knolxWeightage.getText.trim+ " Integration: " + knolxIntegration.getText.trim)
    val webinar = driver.findElementByXPath("//*/table/tr[4]/td[1]")
    Assert.assertEquals(webinar.getText.trim, "Webinars") // assertion on the event of webinars
    val webinarWeightage = driver.findElementByXPath("//*/table/tr[4]/td[2]")
    Assert.assertEquals(webinarWeightage.getText.trim, "10")// assertion on the weightage of webinars
    val webinarIntegration = driver.findElementByXPath("//*/table/tr[4]/td[3]")
    Assert.assertEquals(webinarIntegration.getText.trim, "N") // assertion on the Integatios of webinars
    Reporter.log("Event: " +webinar.getText.trim+", Weightage: "+webinarWeightage.getText.trim+ " Integration: " + webinarIntegration.getText.trim)
    val osContribution = driver.findElementByXPath("//*/table/tr[5]/td[1]")
    Assert.assertEquals(osContribution.getText.trim, "OS Contributions") // assertion on the event of contibutions
    val osContributionWeightage = driver.findElementByXPath("//*/table/tr[5]/td[2]")
    Assert.assertEquals(osContributionWeightage.getText.trim, "10") // // assertion on the weightage of os contibutions
    val osContributionIntegration = driver.findElementByXPath("//*/table/tr[5]/td[3]")
    Assert.assertEquals(osContributionIntegration.getText.trim, "N") // assertion on the Integration of os contibutions
    Reporter.log("Event: " +osContribution.getText.trim+", Weightage: "+osContributionWeightage.getText.trim+ " Integration: " + osContributionIntegration.getText.trim)
    val techHub = driver.findElementByXPath("//*/table/tr[6]/td[1]")
    Assert.assertEquals(techHub.getText.trim, "TechHub Templates") // assertion on the events of techhub
    val techHubWeightage = driver.findElementByXPath("//*/table/tr[6]/td[2]")
    Assert.assertEquals(techHubWeightage.getText.trim, "5")// assertion on the weightage of techhub
    val techHubIntegration = driver.findElementByXPath("//*/table/tr[6]/td[3]")
    Assert.assertEquals(techHubIntegration.getText.trim, "N")// assertion on the Integrations of techhub
    Reporter.log("Event: " +techHub.getText.trim+", Weightage: "+techHubWeightage.getText.trim+ " Integration: " + techHubIntegration.getText.trim)
  }
  @Test(priority = 2)
  def tableSorting(): Unit = {
    /**
     * Method to test the sorting functionality of the tables and for testing the last updated detail.
     */
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
    val title = driver.findElementByCssSelector("div[class='title']")
    val titleText = title.getText.trim
    Assert.assertEquals(titleText, "LEADERBOARD")
    val calenderDate: Calendar = Calendar.getInstance(TimeZone.getDefault)
    val year = calenderDate.get(Calendar.YEAR) //to get the current year
    val date = calenderDate.get(Calendar.DATE) //to get the current date
    val monthName = calenderDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
    val lastUpdatedDetail = driver.findElementByCssSelector("div[class='float-right set-style']") //last updated detail on the top-right side of the page
    val lastUpdatedDetailText = lastUpdatedDetail.getText.trim
    Reporter.log(lastUpdatedDetailText)
    if (date <= 9) {
      Reporter.log("flow is fine")
      Assert.assertEquals(lastUpdatedDetailText, "Last updated: " + "0" + date + " " + monthName + " " + year + " " + "00:00:00") //to check time the format of Last updated detail
    }
    else {
      Assert.assertEquals(lastUpdatedDetailText, "Last updated: " + date + " " + monthName + " " + year + " " + "00:00:00") //to check time the format of Last updated detail
    }
    val currentMonth = driver.findElementByCssSelector("div[class='col-4 col-md-2 col-sm-2 col-lg-2 ml-auto my-auto text-center current-month']") // for the current month detail on the top of the page
    val currentMonthText = currentMonth.getText.trim
    Reporter.log("Current month is displayed on the top right side of the table")
    Reporter.log("Current month is " + currentMonthText)
    val month = new SimpleDateFormat("MMM") //Month format
    val strMonth = month.format(new Date())
    Assert.assertTrue(currentMonthText.matches("(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\-\\d{4}$")) //asserting on the format through regex pattern
    Assert.assertEquals(currentMonthText, strMonth + "-" + year) //asserting on the format and exact date and month of the current month detail
    val sortButton = driver.findElementsByCssSelector("span[class='sort-btn']")
    for (iterator <- 0 until sortButton.size()) {
    }
    val nameColumn = driver.findElementByXPath("//*[@class='datatable-header-cell-template-wrap' and contains(text(),'Name')]")
    val nameColumnText = nameColumn.getText.trim
    Assert.assertEquals(nameColumnText, "Name")
    Reporter.log("Name column is present")
    val monthlyRank = driver.findElementByXPath("//*[@class='datatable-header-cell-template-wrap' and contains(text(),'Monthly Rank')]")
    val monthlyRankText = monthlyRank.getText.trim
    Assert.assertEquals(monthlyRankText, "Monthly Rank")
    Reporter.log("Monthly rank column is present")
    sortButton.get(1).click() //sorting functionality of Monthly rank tables by clicking the sorting button
    Reporter.log("Monthly Rank Column is sortable")
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    val rank = driver.findElementsByCssSelector("div[class='datatable-body-cell-label']")
    for (iterator <- 0 until rank.size()) {
    }
    val sortedMonthlyRankText = rank.get(1).getText.trim
    Assert.assertEquals(sortedMonthlyRankText, "1")
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    val monthScore = driver.findElementByXPath("//*[@class='datatable-header-cell-template-wrap' and contains(text(),'Monthly Score')]")
    val monthScoreText = monthScore.getText.trim
    Assert.assertEquals(monthScoreText, "Monthly Score")
    Reporter.log("Monthly Score column is present")
    sortButton.get(2).click() //sorting functionality of Monthly score tables by clicking the sorting button
    Reporter.log("Monthly Score Column is sortable")
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    val allTimeRank = driver.findElementByXPath("//*[@class='datatable-header-cell-template-wrap' and contains(text(),'Overall Rank')]")
    val allTimeRankText = allTimeRank.getText.trim
    Assert.assertEquals(allTimeRankText, "Overall Rank")
    Reporter.log("Overall rank column is present")
    sortButton.get(3).click() //sorting functionality of Overall Rank tables by clicking the sorting button
    Reporter.log("OverAll Rank Column is sortable")
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    val sortedRank = driver.findElementByCssSelector("div[class='datatable-body-cell-label']")
    val sortedRankText = rank.get(3).getText.trim
    Assert.assertEquals(sortedRankText, "1")
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    val allTimeScore = driver.findElementByXPath("//*[@class='datatable-header-cell-template-wrap' and contains(text(),'Overall Score')]")
    val allTimeScoreText = allTimeScore.getText.trim
    Assert.assertEquals(allTimeScoreText, "Overall Score")
    Reporter.log("Overall score column is present")
    sortButton.get(4).click() //sorting functionality of Overall score tables by clicking the sorting button
    Reporter.log("OverAll Score Column is sortable")
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    val sortedScoreText = rank.get(4).getText.trim
    Assert.assertEquals(sortedScoreText, "5")
    val threeMonthStreak = driver.findElementByXPath("//*[@class='datatable-header-cell-template-wrap' and contains(text(),'3-month-streak')]")
    val threeMonthStreakText = threeMonthStreak.getText.trim
    Assert.assertEquals(threeMonthStreakText, "3-month-streak")
    Reporter.log("3 month Streak column is present")
  }
  @Test(priority = 3)
  def searchBar(): Unit = {
    /**
     * method for checking the search filter
     */
    driver.findElementByCssSelector("input[placeholder='Search...']").click() //To locate the search Bar
    Reporter.log("Search Bar is present")
    driver.findElementByCssSelector("input[placeholder='Search...']").sendKeys(name) //to search for a knolder through search bar
    Reporter.log("Knolder we searched for is "+name)
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    val details = driver.findElementsByCssSelector("div[class='datatable-body-cell-label']")
    for (iterator <- 0 until details.size()) { //for fetching the details of knolder whom we searched
    }
    Reporter.log("Monthly Rank is " + details.get(1).getText)
    Reporter.log("Monthly Score is " + details.get(2).getText)
    Reporter.log("Overall Rank is " + details.get(3).getText)
    Reporter.log("Overall Score is " + details.get(4).getText)
    Reporter.log("3 Month Streak is " + details.get(5).getText)
  }
  @Test(priority = 4)
  def knolderDetails(): Unit = {
    /**
     * method to check for the knolder details by clicking on the knolder's name and
     */
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
    driver.findElementByCssSelector("div[class='datatable-body-cell-label']").click() //  to check whether the details of knolder appears on clicking on it
    Reporter.log("Knolder name is clickable")
    Reporter.log("Loading Gif is present")
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.MINUTES)
    val currentMonth = driver.findElementByCssSelector("span[class='badge badge-pill badge-warning']")
    val currentMonthText = currentMonth.getText.trim // to fetch the current month score
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
    driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS)
    driver.findElementByCssSelector("div[class='col-8 col-md-8']")
    Reporter.log("Blogs Titles are present")
    val dateTime = driver.findElementByCssSelector("div[class='col-4 col-md-4 text-right text-xl-center']")
    val dateTimeText = dateTime.getText.trim
    Reporter.log("Date and time details of blogs are present")
    driver.findElementByCssSelector("svg[class='ngx-charts']") // bar graph
    Reporter.log("Bar graph representation is present")
    val thisMonth = YearMonth.now()
    val lastMonth = thisMonth.minusMonths(1) // to get the last month
    val last12thMonth = thisMonth.minusMonths(12) // to get the Last 12th month
    val monthYearFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM,yyyy", Locale.ENGLISH) // formatter to change the format of month for mm-yyyy to MMM,yyyy
    val lastMonthInWords = lastMonth.format(monthYearFormatter).toUpperCase // in order to get the month and year in the format from Jul,2020 to JUL,2020
    val last12thMonthInWords = last12thMonth.format(monthYearFormatter).toUpperCase // in order to get the month and year in the format from Jul,2020 JUL,2020
    val barGraph = driver.findElementsByCssSelector("path[class='bar']")
    for (iterator <- 0 until barGraph.size()) { //for moving to the bar Graph
    }
    val actionBuilder = new Actions(driver)
    actionBuilder
      .moveToElement(barGraph.get(0)) // to hover over the 12th month bar graph
      .build()
      .perform()
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    val graphToolTip = driver.findElementByCssSelector("span[class='tooltip-label']")
    val graphToolTipText = graphToolTip.getText.trim // to get the tool tip trxt
    Reporter.log("last 12th month is " + graphToolTipText)
    Assert.assertEquals(graphToolTipText, last12thMonthInWords)
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    actionBuilder
      .moveToElement(barGraph.get(11)) // to hover over the last month bar graph
      .build()
      .perform()
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    val lastMonthToolTip = driver.findElementByCssSelector("span[class='tooltip-label']")
    val lastMonthToolTipText = lastMonthToolTip.getText.trim // to get the lastmonth's tool tip text
    Reporter.log("last month was " + lastMonthToolTipText)
    Assert.assertEquals(lastMonthToolTipText, lastMonthInWords)
    actionBuilder
      .moveByOffset(7, 7) // to move the pointer away from the bar garph
      .build()
      .perform()
    Reporter.log("12 month performance is displayed in the bar graph")
    driver.findElementByCssSelector("path[class='arc']") // pie chart
    Reporter.log("Pie chart representation is present")
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    val tooltip = driver.findElementByCssSelector("path[class='arc']")
    val builder = new Actions(driver) // to hover over the pie chart and bar graph so that assertion can be made over tooltip text
    builder
      .moveToElement(tooltip)
      .build()
      .perform()
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    val tooltipLabel = driver.findElementByCssSelector("span[class='tooltip-label']")
    val tooltipLabelText = tooltipLabel.getText.trim // to get pie chart's tool tip text
    Assert.assertEquals(tooltipLabelText, "Blogs")
    val tooltipValue = driver.findElementByCssSelector("span[class='tooltip-val']")
    val tooltipValueText = tooltipValue.getText.trim
    Assert.assertTrue(tooltipValueText.matches("^[0-9]+$")) // to assert on the pattern of tooltip's value
    Reporter.log("Tool tip text of pie chart is " + tooltipLabelText + " " + tooltipValueText)
    driver.findElementByCssSelector("input[name='date']").click() // to access the calender
    driver.findElementByCssSelector("button[class='previous']").click() // to go to the previous year on the calender
    driver.findElementByCssSelector("button[class='next']").click() // to go to the next year on the calender
    Reporter.log("We can go forward and backward on the calender")
    val month = driver.findElementByXPath("//span[contains(text(),'May')]").click() // to check for the details for month of may
    Reporter.log("Month can be selected from the dropDown table")
  }
}

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
class LeaderboardUI extends TestNGSuite with LandingPage with DetailsPage with LoginPage{
  val config: Config = ConfigFactory.load("application.conf")
  val DriverPath = config.getString("webDriverPath")
  val Driver = config.getString("webDriver")
  val name = config.getString("knolderName")
  val url = config.getString("url")
  val email = config.getString("Email")
  val password = config.getString("Password")
  System.setProperty(Driver, DriverPath)
  val driver = new ChromeDriver()
  def windowSwitch(): Unit = {
    /**
     * method for switching to the main window to the google login window
     */
    val mainWindow = driver.getWindowHandle
    val allWindows = driver.getWindowHandles
    val i = allWindows.iterator()
    while (i.hasNext) {
      val childWindow: String = i.next()
      if (!mainWindow.equalsIgnoreCase(childWindow)) {
        driver.switchTo().window(childWindow)
      }
    }
  }
  @Test(enabled = true)
  def logIn(): Unit ={
    /**
     * method for login functionality
     */
    driver.manage().window().maximize()
    driver.get(url)
    val mainWindow = driver.getWindowHandle()
    val titleSignInPage = signInPageTitle(driver).getText.trim // title of the page
    Assert.assertEquals(titleSignInPage,"LEADERBOARD")
    Reporter.log("Title is present on the login page")
    signInButton(driver).click() // for clicking on the sign in button on login page
    Reporter.log("Sign in button is clickable")
    windowSwitch()
    inputEmail(driver).click()
    inputEmail(driver).sendKeys(email)
    nextButton(driver).click()// next button
    inputPassword(driver).click()
    inputPassword(driver).sendKeys(password)
    nextButton(driver).click()// next button
    driver.switchTo().window(mainWindow)
    Reporter.log("We can sign in successfully")
  }
  @Test(enabled = true)
  def totalCountHeader(): Unit = {
    /**
     * method for extracting the cards template of Blogs, Knolx, Webinars, TechHub templates, OS contibutions, Books/Papers and Conferences
     */
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
    val cardName = countHeaderName(driver)
    for (iterator <- 0 until cardName.size()) { // for fetching all the cards name
    }
    val allTimeStatus = allTimeCountInCountHeader(driver)
    for (iterator <- 0 until allTimeStatus.size()) { // for fetching all time status of the cards
    }
    val currentMonthStatus = currentMonthCountInCountHeader(driver)
    for (iterator <- 0 until currentMonthStatus.size()) { // for fetching current month status of the cards
    }
    Reporter.log("Blogs all time count is present: " + allTimeStatus.get(0).getText)
    Reporter.log("Blogs current month count is present: " + currentMonthStatus.get(0).getText)
    Reporter.log("Knolx all time count is present: " + allTimeStatus.get(1).getText)
    Reporter.log("Knolx current month count is present: " + currentMonthStatus.get(1).getText)
    Reporter.log("Webinar all time count is present: " + allTimeStatus.get(2).getText)
    Reporter.log("Webinar current month count is present: " + currentMonthStatus.get(2).getText)
    Reporter.log("TecHub Template all time count is present: " + allTimeStatus.get(3).getText)
    Reporter.log("TecHub Template month count is present: " + currentMonthStatus.get(3).getText)
    Reporter.log("OS contribution all time count is present: " + allTimeStatus.get(4).getText)
    Reporter.log("OS contribution month count is present: " + currentMonthStatus.get(4).getText)
    Reporter.log("Books/Papers all time count is present: " + allTimeStatus.get(5).getText)
    Reporter.log("Books/Papers month count is present: " + currentMonthStatus.get(5).getText)
    Reporter.log("Conferences all time count is present: " + allTimeStatus.get(6).getText)
    Reporter.log("Conferences month count is present: " + currentMonthStatus.get(6).getText)
  }
  @Test(enabled = true)
  def scoringWeightage(): Unit = {
    val scoringDetail = scoringTooltip(driver)
    val actionBuilder = new Actions(driver)
    actionBuilder
      .moveToElement(scoringDetail) // to hover over the scoring section on the right side of the table
      .build()
      .perform()
    val event = eventToolTip(driver)
    Assert.assertEquals(event.getText.trim, "Event")
    val weightage = weightageToolTip(driver)
    Assert.assertEquals(weightage.getText.trim, "Weightage")
    val integration = integrationToolTip(driver)
    Assert.assertEquals(integration.getText.trim, "Integration")
    Reporter.log("In the popover weightage message we have " + event.getText.trim + ", " + weightage.getText.trim + " and " + integration.getText.trim + " as criteria")
    val blogs = blogsToolTip(driver)
    Assert.assertEquals(blogs.getText.trim, "Blogs") // assertion on the events of blogs
    val blogWeightage = blogsWeightageToolTip(driver)
    Assert.assertEquals(blogWeightage.getText.trim, "5") // assertion on the weightage of blogs
    val blogsIntergration = blogsIntegrationToolTip(driver)
    Assert.assertEquals(blogsIntergration.getText.trim, "Y") // assertion on the Integration of blogs
    Reporter.log("Event: " + blogs.getText.trim + ", " + "Weightage:" + blogWeightage.getText.trim + " " + "Integration: " + blogsIntergration.getText.trim)
    val knolx = knolxToolTip(driver)
    Assert.assertEquals(knolx.getText.trim, "Knolx") // assertion on the event of knolx
    val knolxWeightage = knolxWeightageToolTip(driver)
    Assert.assertEquals(knolxWeightage.getText.trim, "20") // assertion on the weightage of knolx
    val knolxIntegration = knolxIntegrationToolTip(driver)
    Assert.assertEquals(knolxIntegration.getText.trim, "Y") // assertion on the Integration of knolx
    Reporter.log("Event: " + knolx.getText.trim + ", Weightage: " + knolxWeightage.getText.trim + " Integration: " + knolxIntegration.getText.trim)
    val webinar = webinarToolTip(driver)
    Assert.assertEquals(webinar.getText.trim, "Webinars") // assertion on the event of webinars
    val webinarWeightage =webinarWeightageToolTip(driver)
    Assert.assertEquals(webinarWeightage.getText.trim, "15") // assertion on the weightage of webinars
    val webinarIntegration = webinarIntegrationToolTip(driver)
    Assert.assertEquals(webinarIntegration.getText.trim, "N") // assertion on the Integatios of webinars
    Reporter.log("Event: " + webinar.getText.trim + ", Weightage: " + webinarWeightage.getText.trim + " Integration: " + webinarIntegration.getText.trim)
    val osContribution = osContributionToolTip(driver)
    Assert.assertEquals(osContribution.getText.trim, "OS Contributions") // assertion on the event of contibutions
    val osContributionWeightage = osContributionWeightageToolTip(driver)
    Assert.assertEquals(osContributionWeightage.getText.trim, "30") // // assertion on the weightage of os contibutions
    val osContributionIntegration = osContributionIntegrationToolTip(driver)
    Assert.assertEquals(osContributionIntegration.getText.trim, "N") // assertion on the Integration of os contibutions
    Reporter.log("Event: " + osContribution.getText.trim + ", Weightage: " + osContributionWeightage.getText.trim + " Integration: " + osContributionIntegration.getText.trim)
    val techHub = tecHubToolTip(driver)
    Assert.assertEquals(techHub.getText.trim, "TechHub Templates") // assertion on the events of techhub
    val techHubWeightage = tecHubWeightageToolTip(driver)
    Assert.assertEquals(techHubWeightage.getText.trim, "15") // assertion on the weightage of techhub
    val techHubIntegration = tecHubIntegrationToolTip(driver)
    Assert.assertEquals(techHubIntegration.getText.trim, "N") // assertion on the Integrations of techhub
  }
  @Test(enabled = true)
  def tableSorting(): Unit = {
    /**
     * Method to test the sorting functionality of the tables and for testing the last updated detail.
     */
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
    val titleText = title(driver).getText
    Assert.assertEquals(titleText, "LEADERBOARD")
    val calenderDate: Calendar = Calendar.getInstance(TimeZone.getDefault)
    val year = calenderDate.get(Calendar.YEAR) //to get the current year
    val date = calenderDate.get(Calendar.DATE) //to get the current date
    val monthName = calenderDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
    val lastUpdatedDetail = lastUpdated(driver) //last updated detail on the top-right side of the page
    val lastUpdatedDetailText = lastUpdatedDetail.getText.trim
    Reporter.log(lastUpdatedDetailText)
    if (date <= 9) {
      Assert.assertEquals(lastUpdatedDetailText, "Last updated: " + "0" + date + " " + monthName + " " + year) //to check time the format of Last updated detail
    }
    else {
      Assert.assertEquals(lastUpdatedDetailText, "Last updated: " + date + " " + monthName + " " + year) //to check time the format of Last updated detail
    }
    val currentMonth = monthDetail(driver) // for the current month detail on the top of the page
    val currentMonthText = currentMonth.getText.trim
    Reporter.log("Current month is displayed on the side of the table")
    Reporter.log("Current month is " + currentMonthText)
    val month = new SimpleDateFormat("MMMM") //Month format
    val strMonth = month.format(new Date())
    Assert.assertEquals(currentMonthText, "Summary of" + " " + strMonth + " " + year) //asserting on the format and exact date and month of the current month detail
    val sortButton = tableSortButton(driver)
    for (iterator <- 0 until sortButton.size()) {
    }
    val nameColumn = knolderName(driver)
    val nameColumnText = nameColumn.getText.trim
    Assert.assertEquals(nameColumnText, "NAME")
    Reporter.log("Name column is present")
    val monthlyRank = montlyRankColumn(driver)
    val monthlyRankText = monthlyRank.getText.trim
    Assert.assertEquals(monthlyRankText, "MONTHLY RANK")
    Reporter.log("Monthly rank column is present")
    sortButton.get(1).click() //sorting functionality of Monthly rank tables by clicking the sorting button
    Reporter.log("Monthly Rank Column is sortable")
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    val rank = searchedKnolderScore(driver)
    for (iterator <- 0 until rank.size()) {
    }
    val sortedMonthlyRankText = rank.get(1).getText.trim
    Assert.assertEquals(sortedMonthlyRankText, "5")
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    val monthScore = monthlyScoreColumn(driver)
    val monthScoreText = monthScore.getText.trim
    Assert.assertEquals(monthScoreText, "MONTHLY SCORE")
    Reporter.log("Monthly Score column is present")
    sortButton.get(2).click() //sorting functionality of Monthly score tables by clicking the sorting button
    Reporter.log("Monthly Score Column is sortable")
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    val allTimeRank = allTimeRankColumn(driver)
    val allTimeRankText = allTimeRank.getText.trim
    Assert.assertEquals(allTimeRankText, "OVERALL RANK")
    Reporter.log("Overall rank column is present")
    sortButton.get(3).click() //sorting functionality of Overall Rank tables by clicking the sorting button
    Reporter.log("OverAll Rank Column is sortable")
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    val sortedRank = searchedKnolderScore(driver))
    val sortedRankText = rank.get(3).getText.trim
    Assert.assertEquals(sortedRankText, "2")
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    val allTimeScore = allTimeScoreColumn(driver)
    val allTimeScoreText = allTimeScore.getText.trim
    Assert.assertEquals(allTimeScoreText, "OVERALL SCORE")
    Reporter.log("Overall score column is present")
    sortButton.get(4).click() //sorting functionality of Overall score tables by clicking the sorting button
    Reporter.log("OverAll Score Column is sortable")
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    val sortedScoreText = rank.get(4).getText.trim
    Thread.sleep(3000)
    val threeMonthStreak = threeMonthStreakColumn(driver)
    val threeMonthStreakText = threeMonthStreak.getText.trim
    Assert.assertEquals(threeMonthStreakText, "3-MONTH-STREAK")
    Reporter.log("3 month Streak column is present")
  }
  @Test(enabled = true)
  def searchBar(): Unit = {
    /**
     * method for checking the search filter
     */
    search(driver).click() //To locate the search Bar
    Reporter.log("Search Bar is present")
    search(driver).sendKeys(name) //to search for a knolder through search bar
    Reporter.log("Knolder we searched for is " + name)
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    val details = searchedKnolderScore(driver)
    for (iterator <- 0 until details.size()) { //for fetching the details of knolder whom we searched
    }
    Reporter.log("Monthly Rank is " + details.get(1).getText)
    Reporter.log("Monthly Score is " + details.get(2).getText)
    Reporter.log("Overall Rank is " + details.get(3).getText)
    Reporter.log("Overall Score is " + details.get(4).getText)
    Reporter.log("3 Month Streak is " + details.get(5).getText)
  }
  @Test(enabled = true)
  def knolderDetails(): Unit = {
    /**
     * method to check for the knolder details by clicking on the knolder's name and
     */
    knolderName(driver).click() //  to check whether the details of knolder appears on clicking on it
    Reporter.log("Knolder name is clickable")
    Reporter.log("Loading Gif is present")
    Thread.sleep(3000)
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.MINUTES)
    val currentMonth = currentMonthDetail(driver)
    val currentMonthText = currentMonth.getText.trim // to fetch the current month score
    Reporter.log("This month score is " + currentMonthText)
    val thisMonthBlogs = currentMonthBlogs(driver)
    val thisMonthBlogstext = thisMonthBlogs.getText.trim // to get the current month blogs count
    val totalScoreMonth = currentMonthTotalScore(driver)
    val totalScoreMonthtext = totalScoreMonth.getText.trim // to get total score fot the current month
    Reporter.log("Total score for this month is: " + totalScoreMonthtext)
    allTimeButton(driver).click()// to check whether the all time button is clickable or not
    Reporter.log("all time button is clickable")
    Reporter.log("all time button is clickable")
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS)
    val totalBlogsAllTime = allTimeBlogs(driver)
    val totalBlogsAllTimeText = totalBlogsAllTime.getText.trim // to get the total number of blogs
    Reporter.log("Total blogs(all time) : " + totalBlogsAllTimeText)
    val totalScoreAllTime = allTimeTotalScore(driver)
    val totalScoreAllTimeText = totalScoreAllTime.getText.trim // to fetch the all time score
    Reporter.log("Total Score(all time) : " + totalScoreAllTimeText)
    driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS)
    blogsTitle(driver)
    Reporter.log("Blogs Titles are present")
    Reporter.log("Blogs Titles are present")
    val dateTimeformat = dateTime(driver)
    val dateTimeText = dateTimeformat.getText.trim
    Reporter.log("Date and time details of blogs are present")
    barGraphDisplay(driver)//bar graph
    Reporter.log("Bar graph representation is present")
    val thisMonth = YearMonth.now()
    val lastMonth = thisMonth.minusMonths(1) // to get the last month
    val last12thMonth = thisMonth.minusMonths(12) // to get the Last 12th month
    val monthYearFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM,yyyy", Locale.ENGLISH) // formatter to change the format of month for mm-yyyy to MMM,yyyy
    val lastMonthInWords = lastMonth.format(monthYearFormatter).toUpperCase // in order to get the month and year in the format from Jul,2020 to JUL,2020
    val last12thMonthInWords = last12thMonth.format(monthYearFormatter).toUpperCase // in order to get the month and year in the format from Jul,2020 JUL,2020
    val barGraph = barGraphToolTip(driver)
    for (iterator <- 0 until barGraph.size()) { //for moving to the bar Graph
    }
    val actionBuilder = new Actions(driver)
    actionBuilder
      .moveToElement(barGraph.get(0)) // to hover over the 12th month bar graph
      .build()
      .perform()
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    val graphToolTip = toolTipScore(driver)
    val graphToolTipText = graphToolTip.getText.trim // to get the tool tip trxt
    Reporter.log("last 12th month is " + graphToolTipText)
    Assert.assertEquals(graphToolTipText, last12thMonthInWords)
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    actionBuilder
      .moveToElement(barGraph.get(11)) // to hover over the last month bar graph
      .build()
      .perform()
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    val lastMonthToolTip = toolTipScore(driver)
    val lastMonthToolTipText = lastMonthToolTip.getText.trim // to get the lastmonth's tool tip text
    Reporter.log("last month was " + lastMonthToolTipText)
    Assert.assertEquals(lastMonthToolTipText, lastMonthInWords)
    actionBuilder
      .moveByOffset(7, 7) // to move th pointer from the bar garph
      .build()
      .perform()
    Reporter.log("12 month performance is displayed in the bar graph")
    pieChartDisplay(driver) // pie chart
    Reporter.log("Pie chart representation is present")
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    val tooltip = pieChartDisplay(driver)
    val builder = new Actions(driver) // to hover over the pie chart and bar graph so that assertion can be made over tooltip text
    builder
      .moveToElement(tooltip)
      .build()
      .perform()
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
    val tooltipLabel = toolTipScore(driver)
    val tooltipLabelText = tooltipLabel.getText.trim // to get pie chart's tool tip text
    Assert.assertEquals(tooltipLabelText, "Blogs")
    val tooltipValue = toolTipValue(driver)
    val tooltipValueText = tooltipValue.getText.trim
    Assert.assertTrue(tooltipValueText.matches("^[0-9]+$")) // to assert on the pattern of tooltip's value
    Reporter.log("Tool tip text of pie chart is " + tooltipLabelText + " " + tooltipValueText)
    calenderButton(driver).click()// to access the calender
    calenderPreviousButton(driver).click()// to go to the previous year on the calender
    calenderNextButton(driver).click()// to go to the next year on the calender
    Reporter.log("We can go forward and backward on the calender")
    val month = selectMonthFromCalender(driver).click() // to check for the details for month of may
    Reporter.log("Month can be selected from the dropDown table")
  }
}

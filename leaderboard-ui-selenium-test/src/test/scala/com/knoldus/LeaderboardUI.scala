package com.knoldus
import com.typesafe.config.{Config, ConfigFactory}
import org.openqa.selenium.chrome.ChromeDriver
import org.scalatestplus.testng.TestNGSuite
import org.testng.{Assert, Reporter}
import org.testng.annotations.{DataProvider, Test}
class LeaderboardUI extends TestNGSuite {
  val config: Config = ConfigFactory.load("application.conf")
  val DriverPath = config.getString("webDriverPath")
  val Driver = config.getString("webDriver")
  val name = config.getString("knolderName")
  val url = config.getString("url")
  System.setProperty(Driver, DriverPath)
  val driver = new ChromeDriver()
  def Card(): Unit = {
    val cardName = driver.findElementsByXPath("//*[@class='title']")
    for (iterator <- 0 until b.size()) {
    }
    Reporter.log("Blog card is present and its value is " + cardName.get(0).getText)
    Reporter.log("Knolx card is present and its value is " + cardName.get(1).getText)
    Reporter.log("Webinars card is present and its value is " + cardName.get(2).getText)
    Reporter.log("TechHub Template card is present and its value is " + cardName.get(3).getText)
  }
  @DataProvider
  def knolder(name: String): Unit = {
    val NAME = driver.findElementByXPath("//ion-col[contains(text(),'" + name + "')]")
    val nameText = NAME.getText.trim
    val rank = driver.findElementByXPath("//ion-col[contains(text(),'" + name + "')]/following-sibling::ion-col/following-sibling::ion-col")
    val rankText = rank.getText.trim
    val score = driver.findElementByXPath("//ion-col[contains(text(),'" + name + "')]/following-sibling::ion-col")
    val scoreText = score.getText.trim
    Reporter.log("Knolder we searched for is "+nameText)
    Reporter.log("Rank is "+rankText)
    Reporter.log("Score is " + scoreText)
  }
  @Test(priority = 1)
  def allTimeTable(): Unit = {
    driver.manage().window().maximize()
    driver.get(url                                                )
    Thread.sleep(3000)
    Reporter.log("Site is accessible")
    Card()
    driver.findElementByXPath("//*[@class='title' and contains(text(),'Name')]")
    Reporter.log("Name column is present")
    driver.findElementByXPath("//*[@class='title' and contains(text(),'Score')]")
    Reporter.log("Score column is present")
    driver.findElementByXPath("//*[@class='title' and contains(text(),'Rank')]")
    Reporter.log("Rank column is present")
    knolder(name)
  }
  @Test(priority = 2)
  def monthlyTable(): Unit = {
    driver.findElementByCssSelector("#monthly").click()
    Card()
    Thread.sleep(2000)
    Reporter.log("Tab switched to monthly table")
    driver.findElementByXPath("//*[@class='title' and contains(text(),'Name')]")
    Reporter.log("Name column is present")
    driver.findElementByXPath("//*[@class='title' and contains(text(),'Monthly Score')]")
    Reporter.log("Monthly Score is present")
    driver.findElementByXPath("//*[@class='title' and contains(text(),'Monthly Rank')]")
    Reporter.log("Monthly Rank is present")
    knolder(name)
  }
  @Test(priority = 3)
  def threeMonthStreak(): Unit = {
    Card()
    driver.findElementByCssSelector("#streak").click()
    Reporter.log("Tab switched to 3 month Streak")
    Thread.sleep(2000)
    driver.findElementByXPath("//*[@class='title' and contains(text(),'Name')]")
    Reporter.log("Name column is present")
    driver.findElementByXPath("//*[@class='title' and contains(text(),'3 Month Streak')]")
    Reporter.log("3 Month Streak column is present")
    driver.findElementByXPath("//*[@class='title' and contains(text(),'Rank')]")
    Reporter.log("Rank column is present")
    Thread.sleep(1000)
    val name = driver.findElementByXPath("//ion-card/ion-card-content/ion-row/ion-col[1]")
    val text = name.getText.trim
    Assert.assertEquals("N/A", text)
    Reporter.log("In 3 month streak Name is " + text)
    val streak = driver.findElementByXPath("//ion-card/ion-card-content/ion-row/ion-col[2]")
    val streakText = streak.getText.trim
    Assert.assertEquals("N/A", streakText)
    Reporter.log("3 month streak is " + streakText)
    val rank = driver.findElementByXPath("//ion-card/ion-card-content/ion-row/ion-col[3]")
    val rankText = rank.getText.trim
    Assert.assertEquals("-1", rankText)
    Reporter.log("Rank is " + rankText)
  }
}

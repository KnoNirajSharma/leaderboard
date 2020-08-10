package com.knoldus
import java.util
import org.openqa.selenium.{By, WebDriver, WebElement}
trait LandingPage{
  def countHeaderName(driver: WebDriver): util.List[WebElement] = {
   val element = driver.findElements(By.cssSelector("div[class='my-1  mx-1 d-flex justify-content-between']"))
element
  }
  def currentMonthCountInCountHeader(driver: WebDriver): util.List[WebElement] = {
    val element = driver.findElements(By.cssSelector("span[class='mt-4 contribution-right shadow-sm']"))
    element
  }
  def allTimeCountInCountHeader(driver: WebDriver): util.List[WebElement] = {
    val element = driver.findElements(By.cssSelector("span[class='mt-4 contribution-left shadow-sm']"))
    element
  }
  def title(driver: WebDriver): WebElement = {
   val element = driver.findElement(By.cssSelector("div[class='title']"))
    element
  }
  def scoringTooltip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("div[class='popover-title']"))
    element
  }
  def eventToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[1]/th[1]"))
    element
  }
  def weightageToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[1]/th[2]"))
    element
  }
  def integrationToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[1]/th[3]"))
    element
  }
  def blogsToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[2]/td[1]"))
    element
  }
  def blogsWeightageToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[2]/td[2]"))
    element
  }
  def blogsIntegrationToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[2]/td[3]"))
    element
  }
  def knolxToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[3]/td[1]"))
    element
  }
  def knolxWeightageToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[3]/td[2]"))
    element
  }
  def knolxIntegrationToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[3]/td[3]"))
    element
  }
  def webinarToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[4]/td[1]"))
    element
  }
  def webinarWeightageToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[4]/td[2]"))
    element
  }
  def webinarIntegrationToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[4]/td[3]"))
    element
  }
  def osContributionToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[5]/td[1]"))
    element
  }
  def osContributionWeightageToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[5]/td[2]"))
    element
  }
  def osContributionIntegrationToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[5]/td[3]"))
    element
  }
  def tecHubToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[6]/td[1]"))
    element
  }
  def tecHubWeightageToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[6]/td[2]"))
    element
  }
  def tecHubIntegrationToolTip(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*/table/tr[6]/td[3]"))
    element
  }

  def lastUpdated(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//div[@class='float-right set-style']"))
    element
  }
  def monthDetail(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("div[class='month-style col-6 col-md-2 col-sm-2 col-lg-2 my-auto text-center current-month']"))
    element
  }
  def tableSortButton(driver: WebDriver): util.List[WebElement] = {
    val element = driver.findElements(By.cssSelector("span[class='sort-btn']"))
    element
  }
  def search(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("input[placeholder='Search...']"))
    element
  }
  def searchedKnolderScore(driver: WebDriver): util.List[WebElement] = {
    val element = driver.findElements(By.cssSelector("div[class='datatable-body-cell-label']"))
    element
  }
  def knolderName(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("div[class='datatable-row-center datatable-row-group']"))
    element
  }
  def knolderNameColumn(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*[@class='datatable-header-cell-label draggable' and contains(text(),'NAME')]"))
    element
  }
  def montlyRankColumn(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*[@class='datatable-header-cell-label draggable' and contains(text(),'MONTHLY RANK')]"))
    element
  }
  def monthlyScoreColumn(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*[@class='datatable-header-cell-label draggable' and contains(text(),'MONTHLY SCORE')]"))
    element
  }
  def allTimeRankColumn(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*[@class='datatable-header-cell-label draggable' and contains(text(),'OVERALL RANK')]"))
    element
  }
  def allTimeScoreColumn(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*[@class='datatable-header-cell-label draggable' and contains(text(),'OVERALL SCORE')]"))
    element
  }
  def threeMonthStreakColumn(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//*[@class='datatable-header-cell-label draggable' and contains(text(),'3-MONTH-STREAK')]"))
    element
  }
}

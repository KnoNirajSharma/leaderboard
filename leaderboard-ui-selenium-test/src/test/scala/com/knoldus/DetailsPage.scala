package com.knoldus
import java.util
import org.openqa.selenium.{By, WebDriver, WebElement}


trait DetailsPage {
  System.setProperty("webdriver.chrome.driver", "/home/knoldus/Downloads/chromedriver_linux64/chromedriver")

  def currentMonthDetail(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("span[class='badge badge-pill badge-warning']"))
    element
  }
  def currentMonthBlogs(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("span[class='col-4 col-md-4 col-xl-3']"))
    element
  }
  def currentMonthTotalScore(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("span[class='col-3 col-md-3 col-xl-2']"))
    element
  }
  def allTimeButton(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector(".all-time-btn"))
    element
  }
  def allTimeBlogs(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("span[class='col-4 col-md-4 col-xl-3']"))
    element
  }
  def allTimeTotalScore(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("span[class='col-3 col-md-3 col-xl-2']"))
    element
  }
  def blogsTitle(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("div[class='col-8 col-md-8']"))
    element
  }
  def dateTime(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("div[class='col-4 col-md-4 text-right text-xl-center']"))
    element
  }
  def barGraphDisplay(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("svg[class='ngx-charts']"))
    element
  }
  def barGraphToolTip(driver: WebDriver): util.List[WebElement] = {
    val element = driver.findElements(By.cssSelector("path[class='bar']"))
    element
  }
  def toolTipScore(driver: WebDriver): WebElement  = {
    val element = driver.findElement(By.cssSelector("span[class='tooltip-label']"))
    element
  }
  def toolTipValue(driver: WebDriver): WebElement  = {
    val element = driver.findElement(By.cssSelector("span[class='tooltip-val']"))
    element
  }
  def pieChartDisplay(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("path[class='arc']"))
    element
  }
  def calenderButton(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("input[name='date']"))
    element
  }
  def calenderPreviousButton(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("button[class='previous']"))
    element
  }
  def calenderNextButton(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("button[class='next']"))
    element
  }
  def selectMonthFromCalender(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.xpath("//span[contains(text(),'May')]"))
    element
  }
}
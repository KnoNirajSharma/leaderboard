package com.knoldus

import java.util
import org.openqa.selenium.{By, WebDriver, WebElement}

trait LoginPage{

  def signInButton(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("div[class='sign-in-btn card p-3 col-10 col-sm-12 mx-auto']"))
    element
  }
  def signInPageTitle(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("h1[class='title mx-auto ion-text-center font-weight-bolder']"))
    element
  }
  def inputEmail(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("input[type='email']"))
    element
  }
  def nextButton(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("div[class='VfPpkd-RLmnJb']"))
    element
  }
  def inputPassword(driver: WebDriver): WebElement = {
    val element = driver.findElement(By.cssSelector("input[class='whsOnd zHQkBf']"))
    element
  }


  def loginEmail(): Unit = {
  }
  def loginPassword(): Unit = {
  }
  def loginButton():Unit ={
  }


}
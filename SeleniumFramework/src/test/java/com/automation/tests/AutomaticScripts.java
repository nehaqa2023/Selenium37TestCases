package com.automation.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.base.BaseTest;
import com.automation.utilities.PropertyUtility;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AutomaticScripts extends BaseTest {

	@Test

	public void login_to_firebase_test_script() throws InterruptedException {
		log.info("login_to_firebase_test_script");
		PropertyUtility pro = new PropertyUtility();
		Properties appProp = pro.loadFile("applicationDataProperties");

		String userId = appProp.getProperty("login.valid.userid");
		String password = appProp.getProperty("login.valid.password");

		String expectedTitle = "Selenium";
		String actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

		WebDriverWait wait = new WebDriverWait(driver, 30);

		By userName = By.id("email_field");
		WebElement userNameElement = driver.findElement(userName);
		waitUntilElementVisible(userNameElement, "userNameField");
		enterText(userNameElement, userId, "username");

		By passwrd = By.id("password_field");
		WebElement passwordElement = driver.findElement(passwrd);
		enterText(passwordElement, password, "password");

		By button = By.tagName("button");
		WebElement buttonElement = driver.findElement(button);
		clickElement(buttonElement, "LoginButton");

	
	}

	@Test
	public void error_login_firebase() throws InterruptedException {

		String expectedError = "Error : The password is invalid or the user does not have a password.";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		// https://login.salesforce.com/
		driver.get("https://qa-tekarch.firebaseapp.com/");

		Thread.sleep(4000);

		WebElement label = driver.findElement(By.tagName("h3"));
		System.out.println("text of the label " + label.getText());

		By userName = By.id("email_field");
		WebElement userNameElement = driver.findElement(userName);
		if (userNameElement.isDisplayed()) {
			userNameElement.clear();
			userNameElement.sendKeys("admin123@gmail.com");
		} else {
			System.out.println("userNameElement is not displayed");
		}
		By password = By.id("password_field");
		WebElement passwordElement = driver.findElement(password);
		String value = passwordElement.getAttribute("type");
		System.out.println("type value ==" + value);
		System.out.println();

		if (passwordElement.isDisplayed()) {
			passwordElement.sendKeys("");
		} else {
			System.out.println("passwordElement is not displayed");
		}
		By button = By.tagName("button");
		WebElement buttonElement = driver.findElement(button);
		if (buttonElement.isDisplayed()) {
			buttonElement.click();
		} else {
			System.out.println("button element is not displayed");
		}
		Thread.sleep(4000);

		Alert alert = driver.switchTo().alert();
		String errorMsg = alert.getText();
		alert.accept();
		if (errorMsg.equals(expectedError)) {
			System.out.println("testcase passed");
		} else {
			System.out.println("testcase failed");
		}

		driver.close();
	}

	/*
	 * public static void main(String[] args) throws InterruptedException { // TODO
	 * Auto-generated method stub login_to_firebase_test_script();
	 * error_login_firebase(); }
	 */

}

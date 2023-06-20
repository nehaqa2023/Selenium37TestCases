package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.automation.base.BaseTest;
import com.automation.utilities.PropertyUtility;

public class SalesforceLogin extends BaseTest {

	String userId;
	String password;
	String invalidUserId;
	String invalidPassword;

	void propUtil() {
		PropertyUtility pro = new PropertyUtility();
		Properties appProp = pro.loadFile("applicationDataPropertiesSF");

		userId = appProp.getProperty("login.valid.userid");
		password = appProp.getProperty("login.valid.password");
		invalidUserId = appProp.getProperty("login.invalid.userid");
		invalidPassword = appProp.getProperty("login.invalid.password");
	}

	@Test

	// TestCase1: Login Error Message-1
	public void loginErrorMessage1() {

		log.info("loginErrorMessage1()");
		propUtil();

		String expectedTitle = "Login | Salesforce";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

		enterText("username", userId, "userName");

		clickElement("Login", "Login");

		WebElement errorMessage = driver.findElement(By.id("error"));
		String actualMessage = errorMessage.getText();
		
		String expectedMessage = "Please enter your password.";
		Assert.assertEquals(expectedMessage,actualMessage);
		

	}

	// TestCase2: Login to Salesforce-2
	@Test
	public void loginToSalesforce2() {
		log.info("loginToSalesforce2()");

		propUtil();

		String expectedTitle = "Login | Salesforce";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");

		expectedTitle = "Home Page ~ Salesforce - Developer Edition";
		actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}

	// TestCase3:Check Remember Me-3
	@Test
	public void checkRememberMe3() {
		log.info("checkRememberMe3()");

		propUtil();

		String expectedTitle = "Login | Salesforce";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		By rememberMe = By.xpath("//input[@name=\"rememberUn\"]");
		WebElement rememberMeCheckBox = driver.findElement(rememberMe);

		if (!rememberMeCheckBox.isSelected()) {
			clickElementByXpath("//input[@name=\"rememberUn\"]", "rememberMeCheckBox");
		}

		clickElement("Login", "Login");

		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		clickElementByXpath("//a[@title=\"Logout\"]", "logout");
		
		
		expectedTitle = "Login | Salesforce";
		waitUntilPageTitleContains(expectedTitle);
		actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}

	// TestCase: Forgot Password 4A
	@Test

	public void forgotPassword4A() {
		log.info("forgotPassword4A()");

		propUtil();

		String expectedTitle = "Login | Salesforce";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

		clickElementByXpath("//a[@id='forgot_password_link']", "forgot your password");

		WebElement userNameElement = driver.findElement(By.xpath("//input[@id='un']"));
		enterText(userNameElement, userId, "userName");

		clickElementByXpath("//input[@id='continue']", "Button");

		expectedTitle = "Check Your Email | Salesforce";
		actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}

	// TestCase: Forgot Password 4B
	@Test
	public void forgotPassword4B() {
		log.info("forgotPassword4B()");

		propUtil();
		String expectedTitle = "Login | Salesforce";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

		enterText("username", invalidUserId, "userName");

		enterText("password", invalidPassword, "password");

		clickElement("Login", "Login");

		WebElement errorMessage = driver.findElement(By.id("error"));
		String actualMessage = errorMessage.getText();
		
		String expectedMessage = "Please check your username and password";
		Assert.assertTrue(actualMessage.startsWith(expectedMessage));
		
	}
}

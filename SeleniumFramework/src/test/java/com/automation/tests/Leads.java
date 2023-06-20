package com.automation.tests;

import org.testng.annotations.Test;

import java.util.List;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.automation.base.BaseTest;
import com.automation.utilities.PropertyUtility;

public class Leads extends BaseTest {
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

	// TestCase20: leads tab
	@Test
	public void leadsTab20() {
		log.info("leads20()");

		propUtil();

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");

		// click leads tab
		clickElementByXpath("//a[@title='Leads Tab']", "Leads");

		String expectedTitle = "Leads: Home ~ Salesforce - Developer Edition";

		waitUntilPageTitleContains(expectedTitle);
		String actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);
		
		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		clickElementByXpath("//a[@title='Logout']", "Logout");


		expectedTitle = "Login | Salesforce";

		waitUntilPageTitleContains(expectedTitle);

		actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}

	// TestCase21:leadsSelectView
	@Test

	public void leadsSelectView21() {
		log.info("leadsSelectView21()");
 
		leadsLogin();
		// dropdown element
		clickElementByXpath("//select[@id='fcf']", "View dropdown");

		// popup closed
		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

		List<String> expectedList = new ArrayList<String>();
		expectedList.add("All Open Leads");
		expectedList.add("My Unread Leads");
		expectedList.add("Recently Viewed Leads");
		expectedList.add("Today's Leads");
		Collections.sort(expectedList);

		List<String> actualList = getListText(driver.findElement(By.xpath("//select[@id='fcf']")));
		if (actualList != null) {
			Collections.sort(actualList);
		}

		Assert.assertEquals(actualList, expectedList);
		
		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		clickElementByXpath("//a[@title='Logout']", "Logout");

		String expectedTitle = "Login | Salesforce";

		waitUntilPageTitleContains(expectedTitle);

		String actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);


	}

	// TestCase22:Default view
	@Test
	public void defaultView22() {
		log.info("default View22()");

		leadsLogin();
		selectByTextData(driver.findElement(By.xpath("//*[@id='fcf']")), "My Unread Leads", "My Unread Leads");

		// popup closed
		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		clickElementByXpath("//a[@title='Logout']", "Logout");

		leadsLogin();

		clickElementByXpath("//input[@value=' Go! ']", "Go");

		String expectedTitle = "Leads ~ Salesforce - Developer Edition";
		
		waitUntilPageTitleContains(expectedTitle);

		String actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);
		
		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		clickElementByXpath("//a[@title='Logout']", "Logout");


		expectedTitle = "Login | Salesforce";

		waitUntilPageTitleContains(expectedTitle);

		actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}

	@Test
	// TestCase23:List item "Todays Leads" work
	public void ViewTodaysLeadsItem23() {
		log.info("ViewTodaysLeadsItem23()");

		leadsLogin();
		selectByTextData(driver.findElement(By.xpath("//*[@id='fcf']")), "Today's Leads", "Today's Leads");

		// popup closed
		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		clickElementByXpath("//a[@title='Logout']", "Logout");

		String expectedTitle = "Login | Salesforce";

		waitUntilPageTitleContains(expectedTitle);

		String actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}

	@Test

	// TestCase24:Check "New" button on Leads Home
	public void checkNewBtnOnLeadsHm24() throws InterruptedException {
		log.info("checkNewBtnOnLeadsHm24()");
		leadsLogin();
		clickElementByXpath("//input[@value=' New ']", "Recent Leads New Btn");

		WebElement lastName = driver.findElement(By.xpath("//input[@id='name_lastlea2']"));
		enterText(lastName, "ABCD", "LastName: ABCD");

		WebElement lea3 = driver.findElement(By.xpath("//input[@id='name_lastlea2']"));
		enterText(lea3, "ABCD", "Company: ABCD");

		// popup closed
		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

		clickElementByXpath("//td[@class='pbButton']/input[@value=' Save ']", "Save Recent Leads");

		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		clickElementByXpath("//a[@title='Logout']", "Logout");

		String expectedTitle = "Login | Salesforce";

		waitUntilPageTitleContains(expectedTitle);

		String actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}
	
	private void leadsLogin() {
		log.info("leadsLogin()");
		
		propUtil();

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");

		// click leads tab
		clickElementByXpath("//a[@title='Leads Tab']", "Leads");

		String expectedTitle = "Leads: Home ~ Salesforce - Developer Edition";

		waitUntilPageTitleContains(expectedTitle);
		String actualTitle = driver.getTitle();
		
		Assert.assertEquals(actualTitle, expectedTitle);
	}


}

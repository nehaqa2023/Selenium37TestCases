package com.automation.tests;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.automation.base.BaseTest;
import com.automation.utilities.PropertyUtility;

public class Opportunities extends BaseTest {

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
	// TestCase15: Oppurtunities drop down
	public void oppurtunitiesDropdown15() {
		log.info("oppurtunitiesDropdown15()");

		propUtil();

		enterText("username", userId, "userName");
		enterText("password", password, "password");
		clickElement("Login", "Login");

		// click on opportunities link
		clickElementByXpath("//a[@title='Opportunities Tab']", "Oppurtunities");

		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

		// dropdown link
		clickElementByXpath("//select[@id='fcf']", "Dropdown");

		List<String> expectedList = new ArrayList<String>();
		expectedList.add("All Opportunities");
		expectedList.add("Closing Next Month");
		expectedList.add("Closing This Month");
		expectedList.add("My Opportunities");
		expectedList.add("New Last Week");
		expectedList.add("New This Week");
		expectedList.add("Opportunity Pipeline");
		expectedList.add("Private");
		expectedList.add("Recently Viewed Opportunities");
		expectedList.add("Won");

		Collections.sort(expectedList);

		List<String> actualList = getListText(driver.findElement(By.xpath("//select[@id='fcf']")));
		if (actualList != null) {
			Collections.sort(actualList);
		}

		AssertJUnit.assertEquals(actualList, expectedList);

	}

	// TestCase16:Create a new Opportunities
	@Test

	public void createNewOpp16() {
		log.info("createNewOpp16()");

		propUtil();

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");
		// click on opportunities link
		clickElementByXpath("//a[@title='Opportunities Tab']", "Oppurtunities");

		// click on new button
		clickElementByXpath("//input[@name='new']", "New");

		// Enter opportunity name
		WebElement userNameElement = driver.findElement(By.xpath("//input[@id='opp3']"));
		enterText(userNameElement, "Ganya", "Opp Name");

		// Enter account name
		WebElement userNameElement1 = driver.findElement(By.xpath("//input[@id='opp4']"));
		enterText(userNameElement1, "GG", "Account Name");

		// Enter close date
		WebElement userNameElement2 = driver.findElement(By.xpath("//input[@id='opp9']"));
		enterText(userNameElement2, "6/6/2023", "Date");

		// Enter probability
		WebElement userNameElement3 = driver.findElement(By.xpath("//input[@id='opp12']"));
		enterText(userNameElement3, "0", "Probability");

		// select lead source
		WebElement opp6 = driver.findElement(By.xpath("//*[@id='opp6']"));
		Select type = new Select(opp6);
		type.selectByValue("Web");

		// enter primary campagin
		WebElement userNameElement4 = driver.findElement(By.xpath("//input[@id='opp17']"));
		enterText(userNameElement4, "Aa", "Primary Campagin");
		
		//popup closed
		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");
						
		
		clickElementByXpath("//td[@class='pbButtonb']/input[@title='Save']","Save");

		String expectedTitle = "Opportunity: Job ~ Salesforce - Developer Edition";
		String actualTitle = driver.getTitle();
		
		Assert.assertEquals(actualTitle, expectedTitle);


	}

	@Test

	public void loginToSalesforce2() {
		log.info("loginToSalesforce2()");

		propUtil();

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");
	}

	// TestCase17:Test opportunity pipeline report
	@Test
	public void pipelineReport17() {
		log.info("pipelineReport17()");

		loginToSalesforce2();

		// click on opportunities link
		clickElementByXpath("//a[@title='Opportunities Tab']", "Oppurtunities");

		// click on pipeline link
		clickElementByXpath("//div[@class='lbBody']/ul/li/a[contains(text(), 'Opportunity Pipeline')]", "Pipeline");

		String expectedTitle = "Opportunity Pipeline ~ Salesforce - Developer Edition";
		String actualTitle = driver.getTitle();
		
		Assert.assertEquals(actualTitle, expectedTitle);

		}

	// TestCase18:Test Stuck Opportunities Report
	@Test

	public void stuckReport() {
		log.info("stuckReport()");
		loginToSalesforce2();

		// click on opportunities link
		clickElementByXpath("//a[@title='Opportunities Tab']", "Oppurtunities");

		// click on stuck report
		clickElementByXpath("//div[@class='lbBody']/ul/li/a[contains(text(), 'Stuck Opportunities')]",
				"Stuck Opportunities");
		
		String expectedTitle = "Stuck Opportunities ~ Salesforce - Developer Edition";
		String actualTitle = driver.getTitle();
		
		Assert.assertEquals(actualTitle, expectedTitle);
		

	}

	// TestCase19:Test Quarterly Summary Report
	@Test

	public void summaryReport() {
		log.info("summaryReport() ");

		loginToSalesforce2();

		// click on opportunities link
		clickElementByXpath("//a[@title='Opportunities Tab']", "Oppurtunities");

		// On quaterly summary click current FQ
		selectByValueData(driver.findElement(By.xpath("//select[@id='quarter_q']")), "current", "current FQ");

		clickElementByXpath("//input[@value='Run Report']","Run Report");
		
		String expectedTitle = "Opportunity Report ~ Salesforce - Developer Edition";
		String actualTitle = driver.getTitle();
		
		Assert.assertEquals(actualTitle, expectedTitle);
		
		
	}
}

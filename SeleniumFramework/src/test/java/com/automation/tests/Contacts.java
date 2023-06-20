package com.automation.tests;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.base.BaseTest;
import com.automation.utilities.PropertyUtility;

public class Contacts extends BaseTest {
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

	public void loginToSalesforce2() {
		log.info("loginToSalesforce2()");

		propUtil();

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");

	}

	// TestCase25:Create new contact
	@Test
	public void createNewContact25() {
		log.info("createNewContact25()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Contacts Tab']", "Contacts");

		clickElementByXpath("//input[@value=' New ']", "New");

		WebElement lastName = driver.findElement(By.xpath("//input[@id='name_lastcon2']"));
		enterText(lastName, "Rehan", "lastName");

		WebElement accName = driver.findElement(By.xpath("//input[@id='con4']"));
		enterText(accName, "Neha", "Account Name");

		// popup closed
		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

		clickElementByXpath("//td[@class='pbButton']/input[@name='save']", "Save");

	}

	// TestCase26: Create New View
	@Test
	public void createNewView26() {
		log.info("createNewView26()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Contacts Tab']", "Contacts");

		clickElementByXpath("//span[@class='fFooter']/a", "Create new view");

		WebElement viewName = driver.findElement(By.xpath("//input[@id='fname']"));
		enterText(viewName, "Anil", "Account Name");

		WebElement uniqueName = driver.findElement(By.xpath("//input[@id='devname']"));
		enterText(uniqueName, "AA", "Account Name");

		clickElementByXpath("//td[@class='pbButtonb']/input[@value=' Save ']", "save");

		// popup closed
		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

		String expectedTitle = "Contacts ~ Salesforce - Developer Edition";

		waitUntilPageTitleContains(expectedTitle);

		String actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}

	// TestCase27:Check recently created contact in the Contact Page
	@Test

	public void validateRecentlyCreatedContact27() {
		log.info("validateRecentlyCreatedContact27()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Contacts Tab']", "Contacts");

		clickElementByXpath("//select[@id='hotlist_mode']", "Recently created");

	}

	// TestCase28:Check 'My contacts' view in the Contact Page

	@Test
	public void checkMyContacts28() {
		log.info("checkMyContacts28()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Contacts Tab']", "Contacts");

		WebElement date = driver.findElement(By.xpath("//select[@id='fcf']"));
		Select type = new Select(date);
		type.selectByVisibleText("My Contacts");

		// popup closed
		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

	}

	// TestCase29:View a contact in the contact Page

	public void viewContact29() {
		log.info("viewContact29()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Contacts Tab']", "Contacts");

	}

	// TestCase30:Check the Error message if, the required information
	// is not entered while creating a New view in Contacts
	@Test
	public void checkErrorMessage30() {
		log.info("checkErrorMessage30()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Contacts Tab']", "Contacts");

		clickElementByXpath("//span[@class='fFooter']/a[contains(text(),'Create New View')]", "Create New View");

		WebElement uniqueName = driver.findElement(By.xpath("//input[@id='devname']"));
		enterText(uniqueName, "EFGH", "View Unique Name");

		clickElementByXpath("//td[@class='pbButtonb']/input[@data-uidsfdc='3']", "Save");

		String errMsg = getTextFromWebElement(driver.findElement(By.xpath("//div[@class='errorMsg']")),
				"Error Message");
		System.out.println(errMsg);

		String expectedErr = "Error: You must enter a value";

		Assert.assertEquals(errMsg, expectedErr);
	}

	// TestCase31:Check the Cancel button works fine in Create New View
	@Test

	public void validateCancelButton31() {
		log.info("validateCancelButton31()");
		loginToSalesforce2();
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Contacts Tab']", "Contacts");

		clickElementByXpath("//span[@class='fFooter']/a[contains(text(),'Create New View')]", "Create New View");

		WebElement viewName = driver.findElement(By.xpath("//input[@id='fname']"));
		enterText(viewName, "ABCD", "View Unique Name");

		WebElement uniqueName = driver.findElement(By.xpath("//input[@id='devname']"));
		enterText(uniqueName, "EFGH", "View Unique Name");

		clickElementByXpath("//td[@class='pbButtonb']/input[@value='Cancel']", "cancel");

	}

	// TestCase32:Check the Save and New button works in New Contact page
	@Test

	public void checkSaveButton32() {
		log.info("checkSaveButton32()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Contacts Tab']", "Contacts");

		clickElementByXpath("//input[@value=' New ']", "new");

		WebElement lastName = driver.findElement(By.xpath("//input[@id='name_lastcon2']"));
		enterText(lastName, "Indian", "lastName");

		WebElement accName = driver.findElement(By.xpath("//input[@id='con4']"));
		enterText(accName, "Global Media", "Account Name");

		// popup closed
		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

		clickElementByXpath("//td[@class='pbButton']/input[@name='save_new']", "Save");

		String actualMsg = driver.findElement(By.xpath("//div[@id='errorDiv_ep']")).getText();
		System.out.println(actualMsg);

		String expectedMsg = "Error: Invalid Data.";

		Assert.assertEquals(actualMsg, expectedMsg);

	}

}

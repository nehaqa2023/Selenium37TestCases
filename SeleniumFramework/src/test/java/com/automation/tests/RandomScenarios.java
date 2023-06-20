package com.automation.tests;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.automation.base.BaseTest;
import com.automation.utilities.PropertyUtility;

public class RandomScenarios extends BaseTest {
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

	// TestCase33:Verify if the firstname and lastname of the loggedin user is
	// displayed
	@Test

	public void validateLoggedInUser33() {
		log.info("validateLoggedInUser33()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Home Tab']", "Home");

		clickElementByXpath("//h1[@class='currentStatusUserName']/a", "Name");

	}

	// TestCase34:Verify the edited lastname is updated at various places
	@Test

	public void validateEditedLastName34() {
		log.info("validateEditedLastName34()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Home Tab']", "Home");

		clickElementByXpath("//h1[@class='currentStatusUserName']/a", "Name");

	}

	// TestCase35:Verify the tab customization
	@Test

	public void validateCustomization35() {
		log.info("validateCustomization35()");
		loginToSalesforce2();

		clickElementByXpath("//img[@title='All Tabs']", "tab");

		clickElementByXpath("//input[@value='Customize My Tabs']", "Customize tab");

		clickElementByXpath("//*[@value='Chatter']", "chatter");

		clickElementByXpath("//img[@title='Remove']", "Remove");

		clickElementByXpath("//input[@title='Save']", "Save");

		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		clickElementByXpath("//a[@title='Logout']", "Logout");

		loginToSalesforce2();

	}

	// TestCase36:Blocking an event in the calender
	@Test

	public void blockingEvent36() {
		log.info("blockingEvent36()()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Home Tab']", "Home");

		// popup closed
		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

		clickElementByXpath("//span[@class='pageDescription']/a", "Date");

		clickElementByXpath("//div[@id='p:f:j_id25:j_id61:4:j_id64']/a", "click 8:00");

		clickElementByXpath("//img[@class='comboboxIcon']", "Subject combo");

		String mainWindow = driver.getWindowHandle();

		switchToWindowOpened(mainWindow);

		clickElementByXpath("//li[@class='listItem4']/a", "Other");

		driver.switchTo().window(mainWindow);

		clickElementByXpath("//input[@id='EndDateTime_time']", "end time");

		clickElementByXpath("//div[@id='timePickerItem_42']", "time 9:00pm");

		clickElementByXpath("//td[@id='topButtonRow']/input[@value=' Save ']", "Save");

	}
	
	@Test
	//TestCase37:
	public void blockingEventInCalender37() {
		log.info("blockingEventInCalender37()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Home Tab']", "Home");
		
		clickElementByXpath("//span[@class='pageDescription']/a", "Current date");
		
		clickElementByXpath("//div[@id='p:f:j_id25:j_id61:20:j_id64']/a", "click 4:00PM");
		
		clickElementByXpath("//img[@class='comboboxIcon']","Subject icon");
		
		String mainWindow = driver.getWindowHandle();

		switchToWindowOpened(mainWindow);

		clickElementByXpath("//li[@class='listItem4']/a", "Other");

		driver.switchTo().window(mainWindow);
		
		// popup closed
		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");
		
		clickElementByXpath("//input[@id='EndDateTime_time']", "end time");
		
		clickElementByXpath("//div[@id='timePickerItem_38']", "time 7:00pm");
		

		By recurrence = By.xpath("//input[@id='IsRecurrence']");
		WebElement recurrenceCheckBox = driver.findElement(recurrence);

		if (!recurrenceCheckBox.isSelected()) {
			clickElementByXpath("//input[@id='IsRecurrence']", "recurrence CheckBox");
		}

		By weekly = By.xpath("//input[@id='rectypeftw']");
		WebElement weeklyRadioButton = driver.findElement(weekly);

		if (!weeklyRadioButton.isSelected()) {
			clickElementByXpath("//input[@id='rectypeftw']", "weeklyRadioButton");
		}
		
		clickElementByXpath("//input[@id='RecurrenceEndDateOnly']","End date");
		
		clickElementByXpath("//td[@class='pbButton']/input[@value=' Save ']","Save");
		
		getLaterDate(15);
}
}

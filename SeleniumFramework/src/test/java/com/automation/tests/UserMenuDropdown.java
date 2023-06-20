package com.automation.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.base.BaseTest;
import com.automation.utilities.PropertyUtility;

public class UserMenuDropdown extends BaseTest {

	String userId;
	String password;
	String invalidUserId;
	String invalidPassword;
	String senderName;
	String senderEmail;
	String downloadPath;

	void propUtil() {
		PropertyUtility pro = new PropertyUtility();
		Properties appProp = pro.loadFile("applicationDataPropertiesSF");

		userId = appProp.getProperty("login.valid.userid");
		password = appProp.getProperty("login.valid.password");
		invalidUserId = appProp.getProperty("login.invalid.userid");
		invalidPassword = appProp.getProperty("login.invalid.password");
		senderName = appProp.getProperty("email.sender.name");
		senderEmail = appProp.getProperty("email.sender.email");
		downloadPath = appProp.getProperty("file.download.path");
		
	}

	// TestCase2: Login to Salesforce-2
	@Test
	public void loginToSalesforce2() {
		log.info("loginToSalesforce2()");

		propUtil();

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");
	}

	// TestCase5: Select user menu for <username> drop down
	@Test
	public void selectUserMenu5() throws InterruptedException {
		log.info("selectUserMenu5()");

		propUtil();

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");

		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		List<String> expectedList = new ArrayList<String>();
		expectedList.add("My Profile");
		expectedList.add("My Settings");
		expectedList.add("Developer Console");
		expectedList.add("Switch to Lightning Experience");
		expectedList.add("Logout");
		Collections.sort(expectedList);

		List<WebElement> op = driver.findElements(By.xpath("//div[@id='userNav-menuItems']/a"));

		List<String> actualList = new ArrayList<String>(op.size());

		for (int i = 0; i < op.size(); i++) {
			actualList.add(op.get(i).getText());
		}

		if (actualList != null) {
			Collections.sort(actualList);
		}

		Assert.assertEquals(actualList, expectedList);

	}

//TestCase6: Select user menu for <username> drop down
	@Test

	public void editProfile6() throws InterruptedException {
		log.info("editProfile6()");
		propUtil();

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");

		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		clickElementByXpath("//a[@title='My Profile']", "myProfile");

		Thread.sleep(5000);

		clickElementByXpath("//a[@class='contactInfoLaunch editLink']", "profileEdit");

		Thread.sleep(5000);
		//findElementUntilVisibileById("contactInfoContentId");
		
		driver.switchTo().frame(driver.findElement(By.id("contactInfoContentId")));

		clickElementByXpath("//li[@id=\"aboutTab\"]/a[contains(@aria-controls,\"Body:1\")]", "aboutTab");

		WebElement lastName = driver.findElement(By.xpath("//input[@id=\"lastName\"]"));
		enterText(lastName, "Test", "lastName");

		clickElementByXpath("//input[@value=\"Save All\"]", "saveAll");

		driver.navigate().refresh();

		clickElementByXpath("//*[@id=\"publisherAttachTextPost\"]/span[1]", "postLink");

		clickElementByXpath("//*[@id='cke_43_contents']/iframe", "iframe");

		Actions actions = new Actions(driver);
		actions.sendKeys("Hi everyone!").build().perform();

		clickElement("publishersharebutton", "publishersharebutton");

		//Thread.sleep(5000);

		clickElementByXpath("//*[@id='publisherAttachContentPost']/span[1]", "file");

		clickElementByXpath("//*[@id='chatterUploadFileAction']", "upload file");
		
		Thread.sleep(5000);

		findElementAndSendKeys(driver.findElement(By.xpath("//input[@id='chatterFile']")),
				"/Users/ganyarehan/Desktop/HelloPost.txt");

//		driver.findElement(By.xpath("//input[@id='chatterFile']")).sendKeys("/Users/ganyarehan/Desktop/HelloPost.txt");
//		actions.moveToElement(driver.findElement(By.xpath("//input[@id='chatterFile']"))).build().perform();

		clickElementByXpath("//input[@id='publishersharebutton']", "publish file");
		Thread.sleep(4000);
		//findElementUntilVisibile("//span[@id='displayBadge']");
		actions.moveToElement(driver.findElement(By.xpath("//span[@id='displayBadge']"))).perform();

		clickElementByXpath("//a[@id='uploadLink']", "publish file");
		Thread.sleep(4000);
		
		//findElementUntilVisibileById("uploadPhotoContentId");
		driver.switchTo().frame(driver.findElement(By.id("uploadPhotoContentId")));

		Thread.sleep(2000);

		//findElementUntilVisibile("//input[@id='j_id0:uploadFileForm:uploadInputFile']");

		findElementAndSendKeys(driver.findElement(By.xpath("//input[@id='j_id0:uploadFileForm:uploadInputFile']")),
				"/Users/ganyarehan/Desktop/flower.png");

		clickElementByXpath("//input[@id='j_id0:uploadFileForm:uploadBtn']", "Save file");
		Thread.sleep(6000);

	}

//TestCase7:Select "My settings" option from user menu for <username> drop down
	@Test

	public void mySetting7() throws InterruptedException {
		log.info("mySetting7()");
		propUtil();

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");

		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		clickElementByXpath("//a[@title='My Settings']", "My Settings");

		clickElementByXpath("//span[@id='PersonalInfo_font']", "Personal");

		clickElementByXpath("//span[@id='LoginHistory_font']", "login history");

		clickElementByXpath("//div[@id='RelatedUserLoginHistoryList_body']/div/a", "Download Login History");

		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		
		Assert.assertTrue(isFileDownloaded(downloadPath, "LoginHistory"), "Failed to download LoginHistory.csv");   

		clickElementByXpath("//span[@id='DisplayAndLayout_font']", "Display");
		clickElementByXpath("//span[@id='CustomizeTabs_font']", "Customize tab");
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		// select salesforce
		Select salesforce = new Select(driver.findElement(By.xpath("//*[@id='p4']")));
		salesforce.selectByIndex(8);
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		
		Select selectedTab = new Select(driver.findElement(By.xpath("//*[@id='duel_select_1']")));
		List<WebElement> elements = selectedTab.getOptions();
		for(WebElement wElem : elements) {
			if(wElem.getText().equals("Reports")) {
				log.info("Reports already selected. Remove it first.");
				selectedTab.selectByVisibleText("Reports");
				clickElementByXpath("//a[@id='duel_select_0_left']", "Remove Button");
				clickElementByXpath("//input[@value=' Save ']", "Save Available tab");
				
				clickElementByXpath("//span[@id='DisplayAndLayout_font']", "Display");
				clickElementByXpath("//span[@id='CustomizeTabs_font']", "Customize tab");

				driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
				// select salesforce
				salesforce = new Select(driver.findElement(By.xpath("//*[@id = 'p4']")));
				salesforce.selectByIndex(8);
			}
		}
		
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);

		Select reports = new Select(driver.findElement(By.xpath("//*[@id='duel_select_0']")));
		reports.selectByVisibleText("Reports");
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);

		clickElementByXpath("//a[@id='duel_select_0_right']", "Add Button");
		clickElementByXpath("//input[@value=' Save ']", "Save Selected tab");
		
		clickElementByXpath("//div[@id='EmailSetup']/a", "Email");
		clickElementByXpath("//a[@id='EmailSettings_font']", "My Email Settings");
		enterText("sender_name", senderName, "Email Name");
		enterText("sender_email", senderEmail, "Email Address");
		clickElementByXpath("//input[@value=' Save ']", "Save My Email Settings");
		
		driver.switchTo().alert().accept();

	}

//TestCase8:Select "Developers Console" option from user menu for <username> drop down
	@Test
	public void developerConsole8() throws InterruptedException {
		log.info("developerConsole8()");
		propUtil();

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");

		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		String baseWindowHandle = driver.getWindowHandle();

		clickElementByXpath("//a[@title='Developer Console (New Window)']", "Developer Console");

		switchToWindowOpened(baseWindowHandle);

		driver.close();
		driver.switchTo().window(baseWindowHandle);

		String expectedTitle = "Home Page ~ Salesforce - Developer Edition";
		String actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}

//TestCase9:Select "Logout" option from user menu for <username> drop down
	@Test
	public void logout9() throws InterruptedException {
		log.info("logout9()");
		propUtil();

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");

		clickElementByXpath("//*[@id=\"userNav\"]", "userDropdown");

		clickElementByXpath("//a[@title='Logout']", "Logout");

		String expectedTitle = "Login | Salesforce";
		String actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}

//TestCase10:Create Account
	@Test
	public void createAccount10() {
		log.info("createAccount10()");
		loginToSalesforce2();
		clickElementByXpath("//a[@title='Accounts Tab']", "Accounts");

		clickElementByXpath("//input[@title='New']", "New");

		WebElement userNameElement = driver.findElement(By.xpath("//input[@id='acc2']"));
		enterText(userNameElement, "Ganya", "AccountName");

		waitImplicitly(5000);

		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

		waitImplicitly(2000);

		WebElement acc6 = driver.findElement(By.xpath("//*[@id='acc6']"));
		Select type = new Select(acc6);
		type.selectByValue("Technology Partner");

		WebElement cp = driver.findElement(By.xpath("//*[@id='00NDp00000CUOmH']"));
		Select cptype = new Select(cp);
		cptype.selectByValue("High");

		clickElementByXpath("//td[@id='topButtonRow']/input[@name='save']", "Save");

		String expectedTitle = "Account Edit: New Account ~ Salesforce - Developer Edition";
		String actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}

//TestCase11:Createnewview
	@Test
	public void createNewView11() {
		log.info("createNewView11()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Accounts Tab']", "Accounts");

		clickElementByXpath("//span[@class='fFooter']/a", "Create New View");

		WebElement userNameElement = driver.findElement(By.xpath("//input[@id='fname']"));
		enterText(userNameElement, "Anil", "View Name");

		WebElement userNameElement1 = driver.findElement(By.xpath("//input[@id='devname']"));
		enterText(userNameElement1, "AA", "Unique View Name");

		clickElementByXpath("//td[@class='pbButtonb']/input[@name='save']", "Save");

		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

		String expectedTitle = "Accounts ~ Salesforce - Developer Edition";
		String actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}

//TestCase12: EditView
	@Test
	public void EditView12() {
		log.info("EditView12()");

		propUtil();

		enterText("username", userId, "userName");

		enterText("password", password, "password");

		clickElement("Login", "Login");

		clickElementByXpath("//a[@title='Accounts Tab']", "Accounts");

		waitImplicitly(2000);

		clickElementByXpath("//a[@id='tryLexDialogX']", "No Thanks");

		waitImplicitly(2000);

		boolean hasView = false;
		Select select = new Select(driver.findElement(By.xpath("//*[@id='fcf']")));
		List<WebElement> view = select.getOptions();

		for (WebElement elem : view) {
			if (elem.getText().equals("Ganvi")) {
				hasView = true;
				break;
			}
		}

		if (!hasView) {
			clickElementByXpath("//span[@class='fFooter']/a", "Create New View");

			WebElement userNameElement = driver.findElement(By.xpath("//input[@id='fname']"));
			enterText(userNameElement, "Ganvi", "View Name");

			WebElement userNameElement1 = driver.findElement(By.xpath("//input[@id='devname']"));
			enterText(userNameElement1, "GanviUN", "Unique View Name");

			clickElementByXpath("//td[@class='pbButtonb']/input[@name='save']", "Save");
		}

		selectByTextData(driver.findElement(By.xpath("//*[@id='fcf']")), "Ganvi", "Ganvi");

		clickElementByXpath("//span[@class='fFooter']/a", "Edit");

		WebElement userNameElement = driver.findElement(By.xpath("//input[@id='fname']"));
		enterText(userNameElement, "Ganya", "View Name");

		selectByValueData(driver.findElement(By.xpath("//*[@id='fcol1']")), "ACCOUNT.NAME", "ACCOUNT.NAME");

		selectByValueData(driver.findElement(By.xpath("//*[@id='fop1']")), "c", "c");

		WebElement userNameElement1 = driver.findElement(By.xpath("//input[@id='fval1']"));
		enterText(userNameElement1, "a", "Value");

		clickElementByXpath("//td[@class='pbButtonb']/input[@name='save']", "Save");

	}

	// TestCase14:Create Account Report
	@Test
	public void createAccountReport14() {
		log.info("createAccountReport14()");
		loginToSalesforce2();

		clickElementByXpath("//a[@title='Accounts Tab']", "Accounts");

		clickElementByXpath("//div[@class='lbBody']/ul/li/a[contains(text(), 'Accounts with last')]",
				"Accounts with last");

		WebElement date = driver.findElement(By.xpath("//input[@id='ext-gen20']"));
		Select type = new Select(date);
		type.selectByVisibleText("Created Date");
	}

}
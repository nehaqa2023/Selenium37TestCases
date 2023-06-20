package com.automation.base;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

import com.automation.utilities.*;

public class BaseTest {
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Logger log;
	protected ExtentReportsUtility report = ExtentReportsUtility.getInstance();

	@BeforeTest
	public void setUpForBeforeTest() {
		log = LogManager.getLogger(BaseTest.class.getName());

	}

	@BeforeMethod
	@Parameters("browserName")
	public void setUpBeforeTestMethod(@Optional("chrome") String browName) {
		PropertyUtility pro = new PropertyUtility();
		Properties appProp = pro.loadFile("applicationDataPropertiesSF");
		String url = appProp.getProperty("url");
		launchBrowser(browName);
		goToUrl(url);
	}

	@AfterMethod
	public void TearDownAfterTestMethod() {
		driver.close();
	}

	public void launchBrowser(String browserName) {
		switch (browserName) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver,10);

			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver,10);

			break;
		case "safari":
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver,10);

			break;
		}
		log.info(browserName + " browser opened");
	}

	public void enterText(WebElement element, String data, String objectName) {
		if (element.isDisplayed()) {
			element.clear();
			element.sendKeys(data);
			log.info(objectName + " is entered to the " + objectName + " field");
			report.logTestInfo(objectName + " is entered to the " + objectName + " field");
		} else {
			log.error("userNameElement is not displayed");
		}

	}

	public void enterText(String id, String data, String objectName) {
		By elemId = By.id(id);
		WebElement element = driver.findElement(elemId);

		if (element.isDisplayed()) {
			element.clear();
			element.sendKeys(data);
			log.info(objectName + " is entered to the " + objectName + " field");
			report.logTestInfo(objectName + " is entered to the " + objectName + " field");
		} else {
			log.error("userNameElement is not displayed");
		}

	}

	public void clickElement(WebElement element, String objectName) {
		if (element.isDisplayed()) {
			element.click();
			log.info(objectName + " element clicked");
			report.logTestInfo(objectName + " element clicked");
		} else {
			log.error(objectName + " element not displayed");
		}
	}

	public void clickElement(String id, String objectName) {
		waitImplicitly(2000);

		By login = By.id(id);
		WebElement element = driver.findElement(login);

		if (element.isDisplayed()) {
			element.click();
			log.info(objectName + " element clicked");
			report.logTestInfo(objectName + " element clicked");
		} else {
			log.error(objectName + " element not displayed");
		}
	}

	public void clickElementByXpath(String xpath, String objectName) {

		waitImplicitly(5000);

		By elem = By.xpath(xpath);
		WebElement element =
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(elem)));

		//WebElement element = driver.findElement(elem);
		if (element.isDisplayed()) {
			element.click();
			log.info(objectName + " element clicked");
			report.logTestInfo(objectName + " element clicked");
		} else {
			log.error(objectName + " element not displayed");
		}
	}

	public void goToUrl(String url) {
		driver.get(url);
		log.info(url + "is entered");
	}

	public void closeBrowser() {
		driver.close();
		log.info("current browser closed");
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public String getTextFromWebElement(WebElement element, String name) {
		if (element.isDisplayed()) {
			return element.getText();
		} else {
			log.info(name + "web element is not displayed");
			return null;
		}
	}

	public Alert switchToAlert() {

		Alert alert = driver.switchTo().alert();
		log.info("switched to alert");
		return alert;

	}

	public void AcceptAlert(Alert alert) {

		log.info("Alert accepted");
		alert.accept();
	}

	public String getalertText(Alert alert) {
		log.info("extracting text in the alert");
		return alert.getText();
	}

	public void dismissAlert() {

		Alert alert = switchToAlert();
		alert.dismiss();
		log.info("Alert dismissed");
	}

	public void waitUntilElementVisible(WebElement ele, String ObjName) {
		log.info("waiting for a web element");
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
	// waits

	public void waitImplicitly(long time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	public void waitUntilPageTitleContains(String title) {
		log.info("waiting for title " + title + " to be displayed");
		//WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleContains(title));
	}


	public void waitUntilPageLoads() {
		log.info("waiting until page loads with 30 sec maximum");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}


	public void WaitUntilElementIsVisible(WebElement ele, String objName) {
		log.info("waiting for an web element" + objName + "for its visibility");
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	public void WaitUntilPresenceOfElementLocatedBy(By locator, String objName) {
		log.info("waiting for an web element" + objName + "for its visibility");
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitUntilAlertIsPresent() {
		log.info("waiting for alert to be present");
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public void waitUntilElementToBeClickable(By locator, String objName) {
		log.info("waiting for an web element" + objName + "to be clickable");
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void waitFluentForVisibility(WebElement ele, String objName) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(30)).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	public void selectByTextData(WebElement element, String text, String objName) {
		Select selectElem = new Select(element);
		selectElem.selectByVisibleText(text);
		log.info(objName + " selected " + text);
	}

	public void selectByIndexData(WebElement element, int index, String objName) {
		Select selectElem = new Select(element);
		selectElem.selectByIndex(index);
		log.info(objName + " selected ");
	}

	public void selectByValueData(WebElement element, String text, String objName) {
		Select selectElem = new Select(element);
		selectElem.selectByValue(text);
		log.info(objName + " selected ");
	}

	public void switchToWindowOpened(String mainWindowHandle) {
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String handle : allWindowHandles) {
			if (!mainWindowHandle.equalsIgnoreCase(handle))
				driver.switchTo().window(handle);
		}
		log.info("switched to new window");
	}

	public List<String> getListText(WebElement webElem) {
		Select target = new Select(webElem);
		List<WebElement> targetListElements = target.getOptions();
		List<String> targetList = new ArrayList<String>();
		for (WebElement webElement : targetListElements) {
			targetList.add(webElement.getText());
		}
		return targetList;
	}

	public File getScreenshotofThePage() {
		String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File imgFile = screenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(Constants.SCREENSHOTS_DIRECTORY_PATH + date + ".png");
		try {
			FileUtils.copyFile(imgFile, destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return destFile;
		}
		return destFile;
	}

	public void findElementAndSendKeys(WebElement webElem, String keys) {
		
		//WebDriverWait wait = new WebDriverWait(driver,10);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(webElem));

		Actions actions = new Actions(driver);
		element.sendKeys(keys);
		actions.moveToElement(element).build().perform();
	}

	public WebElement findElementUntilVisibile(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		By elem = By.xpath(xpath);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(driver.findElement(elem)));
		return element;
	}
	
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
	    File dir = new File(downloadPath);
	    File[] dir_contents = dir.listFiles();
	  	    
	    for (int i = 0; i < dir_contents.length; i++) {
	        if (dir_contents[i].getName().startsWith(fileName))
	            return flag=true;
	            }

	    return flag;
	}
	
	public WebElement findElementUntilVisibileById(String id) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		By elem = By.id(id);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(driver.findElement(elem)));
		return element;
		
	}
	
	public List<String> getMenuItems(List<WebElement> elems) {

		List<String> actualList = new ArrayList<String>(elems.size());

		for (int i = 0; i < elems.size(); i++) {
			actualList.add(elems.get(i).getText());
		}

		if (actualList != null) {
			Collections.sort(actualList);
		}

		return actualList;
	}

	public List<String> getExpectedMenuItems() {
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("My Profile");
		expectedList.add("My Settings");
		expectedList.add("Developer Console");
		expectedList.add("Switch to Lightning Experience");
		expectedList.add("Logout");
		Collections.sort(expectedList);
		return expectedList;
	}
	
	public String getLaterDate(int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/YYYY");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Using today's date
		c.add(Calendar.DATE, days); // Adding days
		return sdf.format(c.getTime());
	}
	

}

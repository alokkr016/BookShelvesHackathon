package com.cognizant;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Bookshelves {

	public String baseUrl = "https://www.urbanladder.com/";
	public WebDriver driver;
	public JavascriptExecutor js;
	ReportGenerator report;
	CaptureScreenShot capture;
	@BeforeTest
	public void launchThePage() throws InterruptedException {
		driver = DriverSetup.getDriver("Chrome");
		report = new ReportGenerator();
		capture = new CaptureScreenShot();
		// Launch and maximize
		driver.get(baseUrl);
		driver.manage().window().maximize();
		Thread.sleep(3000);
	}

	@Test(priority = 0)
	public void scrollAndClick() throws InterruptedException {
		report.testName("Click on bookshelves");
		report.logsInfo("Scrolling down to make element visible");
		// Scroll down
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		capture.takeScreenshot();
		report.logsInfo("Clicking on bookshelves");
		// click on bookshelves
		driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/div/div[3]/a[5]")).click();
		Thread.sleep(5000);

		// To close the pop up
		report.logsInfo("Close the pop-up");
		driver.findElement(By.xpath("//*[@id=\"authentication_popup\"]/div[1]/div/div[2]/a[1]")).click();
		capture.takeScreenshot();
		Thread.sleep(3000);

	}

	@Test(priority = 1)
	public void setRequiredFilter() throws InterruptedException {
		report.testName(" Applying filters on Bookshelves available");

		report.logsInfo("Clicking on price filter");
		// Clicking on price caret
		driver.findElement(By.xpath("//*[@id=\"filters-form\"]/div[1]/div/div/ul/li[1]/div[1]/div")).click();
		Thread.sleep(1000);
		WebElement frame = driver.findElement(By.xpath("//div[@class='noUi-handle noUi-handle-upper']"));
		Actions action = new Actions(driver);

		report.logsInfo("Applying the price filter");
		// set on price range
		action.dragAndDropBy(frame, -244, 0).perform();
		Thread.sleep(3000);

		report.logsInfo("Applying storage filter");
		// Click on storage caret
		driver.findElement(By.xpath("//*[@id=\"filters-form\"]/div[1]/div/div/ul/li[2]/div[1]/div")).click();
		Thread.sleep(5000);

		report.logsInfo("Applyting open filter");
		// select open
		driver.findElement(By.xpath("//input[@id='filters_storage_type_Open']")).click();
		Thread.sleep(3000);
		capture.takeScreenshot();
	}

	@Test(priority = 2)
	public void printTheDetails() throws InterruptedException {
		report.testName("Printing the details after applying filter");

		// list of bookshelves
		List<WebElement> bookshelves = driver
				.findElements(By.xpath("//*[@class='product-title product-title-sofa-mattresses']/span"));

		// list of price
		List<WebElement> prices = driver.findElements(By.xpath("//*[@class='price-number']/span"));

		report.logsInfo("Printing the bookshelves details after applying filter");
		// printing 3 bookshelves details
		for (int i = 0; i < 3; i++) {

			String bookshelf = bookshelves.get(i).getText();
			String price = prices.get(i).getText().substring(1);

			System.out.println(bookshelf);
			System.out.println(price);

		}

		report.logsInfo("Scrolling to navbar");
		// navbar
		WebElement navbar = driver.findElement(By.id("topnav_wrapper"));
		js.executeScript("arguments[0].scrollIntoView();", navbar);
		Thread.sleep(3000);

		report.logsInfo("Click on giftcard");
		// click on Gift card
		driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
	}

	@Test(priority = 3)
	public void chooseBirthdayCard() throws InterruptedException {
		report.testName("Choosing the b'day card and proceed");
		js = (JavascriptExecutor) driver;

		report.logsInfo("Scrolling to b'day card");
		// Scrolling down to b'day card
		js.executeScript("window.scrollBy(0,500)");
		Thread.sleep(3000);

		report.logsInfo("Clicked on b'day card successfully");
		// Click on B'day card
		driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]")).click();
		Thread.sleep(3000);

		// Entering amount
		driver.findElement(By.xpath("//input[@id='ip_2251506436']")).sendKeys("1000");
		Thread.sleep(2000);

		// Select month
		Select month = new Select(driver.findElement(
				By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
		month.selectByValue("7/2023");

		// select date
		Select date = new Select(driver.findElement(
				By.xpath("//*[@id=\'app-container\']/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
		date.selectByVisibleText("18");

		// Click on next button
		driver.findElement(By.xpath("//button[@class='_1IFIb _1fVSi action-button _1gIUf _1XfDi']")).click();
		capture.takeScreenshot();
		report.logsInfo("Filled details successfully & clicked on next button");
	}

	@Test(priority = 4)
	public void fillDetails() throws InterruptedException, IOException {
		report.testName("Filling b'day card details & capturing error");
		// Excel file path
		String path = "src/main/java/com/cognizant/DetailsSheet.xlsx";

		report.logsInfo("Filling recipient details");
		// Filling Recipient details
		String[] recipientDetails = ReadExcel.getData(path, 0).split(" ");
		driver.findElement(By.id("ip_4036288348")).sendKeys(recipientDetails[0]);
		driver.findElement(By.id("ip_137656023")).sendKeys(recipientDetails[1]);
		driver.findElement(By.id("ip_3177473671")).sendKeys(recipientDetails[2]);

		report.logsInfo("Filling sender details");
		// Filling sender details
		String[] senderDetails = ReadExcel.getData(path, 1).split(" ");
		driver.findElement(By.id("ip_1082986083")).sendKeys(senderDetails[0]);
		driver.findElement(By.id("ip_4081352456")).sendKeys(senderDetails[1]);
		driver.findElement(By.id("ip_2121573464")).sendKeys(senderDetails[2]);
		driver.findElement(By.id("ip_2194351474")).sendKeys(senderDetails[3]);
		driver.findElement(By.id("ip_567727260")).sendKeys(senderDetails[4]);
		Thread.sleep(3000);

		report.logsInfo("Capturing pincode error message");
		// Getting Pincode error message
		String pincodeError = driver.findElement(By.xpath("//div[@class='_1HVuH']")).getText();
		capture.takeScreenshot();
		System.out.println(pincodeError);

		report.logsInfo("Correcting the details");
		// correcting details
		driver.findElement(By.id("ip_567727260")).clear();
		Thread.sleep(3000);
		driver.findElement(By.id("ip_567727260")).sendKeys("560005");
		Thread.sleep(3000);

		// clicking on confirm button
		driver.findElement(By.xpath("//button[@class='_3Hxyv _1fVSi action-button _1gIUf _1XfDi']")).click();

		report.logsInfo("Capturing incorrect mobile No. error message");
		// Getting mobile number error message
		String mobileNoError = driver.findElement(By.xpath("//input[@name='recipient_mobile_number']"))
				.getAttribute("title");
		System.out.println(mobileNoError);

		// correcting details
		driver.findElement(By.id("ip_3177473671")).clear();
		driver.findElement(By.id("ip_3177473671")).sendKeys("8985425562");
		Thread.sleep(3000);
		capture.takeScreenshot();
		report.logsInfo("After correcting details, Clicking Next button");
		// clicking on confirm button
		driver.findElement(By.xpath("//button[@class='_3Hxyv _1fVSi action-button _1gIUf _1XfDi']")).click();
		Thread.sleep(3000);

		report.logsInfo("Navigating back to home page");
		// navigating back
		driver.navigate().back();
		Thread.sleep(3000);

	}

	@Test(priority = 5)
	public void retrieveAllSubmenuItemsAtHome() throws InterruptedException {
		report.testName("Fetching all submenu items applying @home filter");

		report.logsInfo("Applied brand filter");
		// clicking on brand filter
		driver.findElement(By.xpath("//li[@data-group='brand']")).click();

		report.logsInfo("Applied @home filter");
		// Filtering by At-home
		driver.findElement(By.xpath("//input[@id='filters_brand_name_By_home']")).click();
		Thread.sleep(3000);

		report.logsInfo("Fetching all item in bookshelves");
		// Fetching all data of bookshelves
		WebElement bookshelvesGrid = driver
				.findElement(By.xpath("//ul[@class='productlist withdivider clearfix bookshelves productgrid']"));

		List<WebElement> beingHomeProduct = bookshelvesGrid
				.findElements(By.xpath("//div[@class='product-title product-title-sofa-mattresses']/span"));

		List<WebElement> beingHomePrice = bookshelvesGrid.findElements(By.xpath("//div[@class='price-number']/span"));

		System.out.println("Total number of products" + beingHomeProduct.size());

		report.logsInfo("Printing all the details in console");
		for (int i = 0; i < beingHomeProduct.size(); i++) {

			String productName = beingHomeProduct.get(i).getText();

			String productPrice = beingHomePrice.get(i).getText().substring(1);

			System.out.println(productName);

			System.out.println(productPrice);

		}
	}

	@AfterTest
	public void terminateBrowser() throws IOException {
		report.flushReport();
		driver.close();
	}

}

package com.cognizant;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class BookshelvesWithExplicitWait {

	public String baseUrl = "https://www.urbanladder.com/";
	public WebDriver driver;
	public JavascriptExecutor js;
	ReportGenerator report;
	CaptureScreenShot capture;

	By bookshelves = By.xpath("//*[@id=\"content\"]/div[3]/div/div[3]/a[5]");
	By popup = By.xpath("//*[@id=\"authentication_popup\"]/div[1]/div/div[2]/a[1]");

	By priceCaret = By.xpath("//*[@id=\"filters-form\"]/div[1]/div/div/ul/li[1]/div[1]/div");
	By slider = By.xpath("//div[@class='noUi-handle noUi-handle-upper']");
	By storageCaret = By.xpath("//*[@id=\"filters-form\"]/div[1]/div/div/ul/li[2]/div[1]/div");
	By openFilter = By.xpath("//input[@id='filters_storage_type_Open']");

	By bookshelvesList = By.xpath("//*[@class='product-title product-title-sofa-mattresses']/span");
	By bookshelvesPriceList = By.xpath("//*[@class='price-number']/span");
	By giftCard = By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a");

	By bdayCard = By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]");
	By amountTextBox = By.xpath("//input[@id='ip_2251506436']");
	By monthBox = By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]");
	By dateBox = By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/div[4]/select[2]");
	By nextButton = By.xpath("//button[@class='_1IFIb _1fVSi action-button _1gIUf _1XfDi']");

	By nameRecipient = By.id("ip_4036288348");
	By emailRecipient = By.id("ip_137656023");
	By mobileRecipient = By.id("ip_3177473671");

	By nameSender = By.id("ip_1082986083");
	By emailSender = By.id("ip_4081352456");
	By mobileSender = By.id("ip_2121573464");
	By addressSender = By.id("ip_2194351474");
	By pinCode = By.id("ip_567727260");

	By confirmButton = By.xpath("//button[@class='_3Hxyv _1fVSi action-button _1gIUf _1XfDi']");
	By mobileError = By.xpath("//input[@name='recipient_mobile_number']");

	By brandFilter = By.xpath("//li[@data-group='brand']");
	By atHomeFilter = By.xpath("//input[@id='filters_brand_name_By_home']");

	private void printProductNameAndPrice(List<WebElement> productSizeList, List<WebElement> productPriceList,
										  int size) {
		for (int i = 0; i < size; i++) {
			String productName = productSizeList.get(i).getText();
			String productPrice = productPriceList.get(i).getText().substring(1);
			System.out.println(productName);
			System.out.println(productPrice);
		}
	}

	@Parameters("browser")
	@BeforeTest
	public void launchThePage(@Optional("chrome") String browser) throws InterruptedException, IOException {
		driver = DriverSetup.getDriver(browser);
		report = new ReportGenerator();
		capture = new CaptureScreenShot();
		// Launch and maximize
		driver.get(baseUrl);
		driver.manage().window().maximize();
		// Add an implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	@Test(priority = 0)
	public void scrollAndClick() throws InterruptedException {
		report.testName("Click on bookshelves");
		report.logsInfo("Scrolling down to make element visible");
		// Scroll down
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		capture.takeScreenshot(driver);
		report.logsInfo("Clicking on bookshelves");
		// click on bookshelves
		driver.findElement(bookshelves).click();
		// Add an explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(popup));
		// To close the pop-up
		report.logsInfo("Close the pop-up");
		driver.findElement(popup).click();
		capture.takeScreenshot(driver);
	}

	@Test(priority = 1)
	public void setRequiredFilter() throws InterruptedException {
		report.testName(" Applying filters on Bookshelves available");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		report.logsInfo("Clicking on price filter");
		// Clicking on price caret
		driver.findElement(priceCaret).click();
		// Wait for slider to be visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(slider));
		// slider
		WebElement frame = driver.findElement(slider);
		Actions action = new Actions(driver);
		report.logsInfo("Applying the price filter");
		// set on price range
		action.dragAndDropBy(frame, -240, 0).perform();
		// Wait for filter to be applied
		wait.until(ExpectedConditions.elementToBeClickable(storageCaret));
		report.logsInfo("Applying storage filter");
		// Click on storage caret
		driver.findElement(storageCaret).click();
		// Wait for open filter to be clickable
		wait.until(ExpectedConditions.visibilityOfElementLocated(openFilter));
		report.logsInfo("Applying open filter");
		// select open
		driver.findElement(openFilter).click();
		// Wait for filter to be applied
		wait.until(ExpectedConditions.invisibilityOfElementLocated(openFilter));
		capture.takeScreenshot(driver);
	}

	@Test(priority = 2)
	public void printTheDetails() throws InterruptedException {
		report.testName("Printing the details after applying filter");
		// list of bookshelves
		List<WebElement> bookshelves = driver.findElements(bookshelvesList);
		// list of price
		List<WebElement> prices = driver.findElements(bookshelvesPriceList);
		report.logsInfo("Printing the bookshelves details after applying filter");
		// printing 3 bookshelves details
		printProductNameAndPrice(bookshelves, prices, 3);
		report.logsInfo("Scrolling to navbar");
		// navbar
		WebElement navbar = driver.findElement(By.id("topnav_wrapper"));
		js.executeScript("arguments[0].scrollIntoView();", navbar);
		// Add a small wait to let the page stabilize
		Thread.sleep(1000);
		report.logsInfo("Click on gift card");
		// click on Gift card
		driver.findElement(giftCard).click();
		// Add an explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.urlContains("gift-cards"));
	}

	@Test(priority = 3)
	public void chooseBirthdayCard() throws InterruptedException {
		report.testName("Choosing the b'day card and proceed");
		js = (JavascriptExecutor) driver;
		report.logsInfo("Scrolling to b'day card");
		// Scrolling down to b'day card
		js.executeScript("window.scrollBy(0,500)");
		// Add a small wait to let the page stabilize
		Thread.sleep(1000);
		report.logsInfo("Clicked on b'day card successfully");
		// Click on B'day card
		driver.findElement(bdayCard).click();
		// Add an explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(amountTextBox));
		// Entering amount
		driver.findElement(amountTextBox).sendKeys("1000");
		// Select month
		Select month = new Select(driver.findElement(monthBox));
		month.selectByValue("7/2023");
		// select date
		Select date = new Select(driver.findElement(dateBox));
		date.selectByVisibleText("18");
		// Click on the next button
		driver.findElement(nextButton).click();
		capture.takeScreenshot(driver);
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
		driver.findElement(nameRecipient).sendKeys(recipientDetails[0]);
		driver.findElement(emailRecipient).sendKeys(recipientDetails[1]);
		driver.findElement(mobileRecipient).sendKeys(recipientDetails[2]);
		report.logsInfo("Filling sender details");
		// Filling sender details
		String[] senderDetails = ReadExcel.getData(path, 1).split(" ");
		driver.findElement(nameSender).sendKeys(senderDetails[0]);
		driver.findElement(emailSender).sendKeys(senderDetails[1]);
		driver.findElement(mobileSender).sendKeys(senderDetails[2]);
		driver.findElement(addressSender).sendKeys(senderDetails[3]);
		driver.findElement(pinCode).sendKeys(senderDetails[4]);
		// Add an explicit wait
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
		report.logsInfo("Capturing pin code error message");
		// Getting a Pin code error message
		WebElement pincodeError = driver.findElement(By.xpath("//div[@class='_1HVuH']"));
		System.out.println(pincodeError.getText());
		capture.takeScreenshot(driver);
		report.logsInfo("Correcting the details");
		// correcting details
		driver.findElement(pinCode).clear();
		driver.findElement(pinCode).sendKeys("560005");
		// Add a small wait to let the page stabilize
		Thread.sleep(1000);
		// clicking on the confirmation button
		driver.findElement(confirmButton).click();
		report.logsInfo("Capturing incorrect mobile No. error message");
		// Getting a mobile number error message
		WebElement mobileNoError = driver.findElement(mobileError);
		System.out.println(mobileNoError.getAttribute("title"));
		// correcting details
		driver.findElement(mobileSender).clear();
		driver.findElement(mobileSender).sendKeys("8985425562");
		// Add a small wait to let the page stabilize
		Thread.sleep(1000);
		capture.takeScreenshot(driver);
		report.logsInfo("After correcting details, Clicking Next button");
		// clicking on the confirmation button
		driver.findElement(confirmButton).click();

		report.logsInfo("Navigating back to home page");
		// navigating back
		driver.navigate().back();
		Thread.sleep(5000);
		// Add an explicit wait
//		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
//		wait2.until(ExpectedConditions.urlToBe(baseUrl));
	}

	@Test(priority = 5)
	public void retrieveAllSubmenuItemsAtHome() throws InterruptedException {
		report.testName("Fetching all submenu items applying @home filter");
		report.logsInfo("Applied brand filter");
		// clicking on brand filter
		driver.findElement(brandFilter).click();
		report.logsInfo("Applied @home filter");
		// Filtering by At-home
		driver.findElement(atHomeFilter).click();
		// Add an explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(atHomeFilter));
		report.logsInfo("Fetching all item in bookshelves");
		// Fetching all data of bookshelves
		WebElement bookshelvesGrid = driver
				.findElement(By.xpath("//ul[@class='productlist withdivider clearfix bookshelves productgrid']"));
		List<WebElement> beingHomeProduct = bookshelvesGrid.findElements(bookshelvesList);
		List<WebElement> beingHomePrice = bookshelvesGrid.findElements(bookshelvesPriceList);
		System.out.println("Total number of products" + beingHomeProduct.size());
		report.logsInfo("Printing all the details in console");
		printProductNameAndPrice(beingHomeProduct, beingHomePrice, beingHomeProduct.size());
	}

	// Rest of the code...

	@AfterTest
	public void terminateBrowser() throws IOException {
		report.flushReport();
		driver.close();
	}
}

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

public class Book {

	public String baseUrl = "https://www.urbanladder.com/";
	public WebDriver driver;
	public JavascriptExecutor js;
	
	@BeforeTest
	public void setBaseUrl() throws InterruptedException {
		driver = DriverSetup.getDriver("Chrome");
		// Launch and maximize
		driver.get(baseUrl);
		driver.manage().window().maximize();
		Thread.sleep(3000);
	}

	@Test(priority = 0)
	public void scrollAndClick() throws InterruptedException {
		// Scroll down
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");

		// click on bookshelves
		driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/div/div[3]/a[5]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("/html/body/div[6]/div/div[1]/div/div[2]/a[1]")).click();
		Thread.sleep(3000);
	}
	
	@Test(priority = 1)
	public void setThePriceFilter() throws InterruptedException {
		// click on price
		driver.findElement(
				By.xpath("/html/body/div[2]/div[2]/div[3]/div[2]/div[1]/div/form/div[1]/div/div/ul/li[1]/div[1]/div"))
				.click();
		Thread.sleep(1000);
		WebElement frame = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div[3]/div[2]/div[1]/div/form/div[1]/div/div/ul/li[1]/div[2]/div/div/ul/li[1]/div/div[2]/div[2]/div/div[2]/div"));
		Actions action = new Actions(driver);

		action.dragAndDropBy(frame, -243, 0).perform();

		Thread.sleep(3000);
	}

	@Test(priority = 2)
	public void selectTheStorageType() throws InterruptedException {
		// select storage type
		driver.findElement(
				By.xpath("/html/body/div[2]/div[2]/div[3]/div[2]/div[1]/div/form/div[1]/div/div/ul/li[2]/div[1]/div"))
				.click();

		Thread.sleep(3000);

		driver.findElement(By.xpath("//input[@id='filters_storage_type_Open']")).click();

		Thread.sleep(3000);

		List<WebElement> reddy = driver
				.findElements(By.xpath("//*[@class='product-title product-title-sofa-mattresses']/span"));

		List<WebElement> redd = driver.findElements(By.xpath("//*[@class='price-number']/span"));

//		System.out.println(redd.size());

		for (int i = 0; i < redd.size(); i++) {

			String j = reddy.get(i).getText();

			String v = redd.get(i).getText().substring(1);

			System.out.println(j);

			System.out.println(v);

		}

		String parent = driver.getWindowHandle();

		WebElement element1 = driver.findElement(By.id("topnav_wrapper"));

		JavascriptExecutor js1 = (JavascriptExecutor) driver;

		js1.executeScript("arguments[0].scrollIntoView();", element1);

		Thread.sleep(3000);

		driver.findElement(By.xpath("/html/body/div[1]/header/section/div/ul[2]/li[3]/a")).click();

	}

	@Test(priority = 3)
	public void chooseBdayCard() throws InterruptedException {
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");

		Thread.sleep(3000);

		driver.findElement(By.xpath("/html/body/div[1]/div/main/section/section[1]/ul/li[3]")).click();

		Thread.sleep(3000);

		driver.findElement(By.xpath("//input[@id='ip_2251506436']")).sendKeys("1000");

		Thread.sleep(2000);

		Select month = new Select(driver.findElement(
				By.xpath("/html/body/div[1]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));

		month.selectByValue("7/2023");

		Select date = new Select(driver.findElement(
				By.xpath("//*[@id=\'app-container\']/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));

		date.selectByVisibleText("18");

		driver.findElement(By.xpath("//button[@class='_1IFIb _1fVSi action-button _1gIUf _1XfDi']")).click();

	}

	@Test(priority = 4)
	public void fillDetails() throws InterruptedException, IOException {
		String path = "src/main/java/com/cognizant/Book.xlsx";
		//Recipient
		String[] recipientDetails = ReadExcel.getData(path,0).split(" ");

		driver.findElement(By.id("ip_4036288348")).sendKeys(recipientDetails[0]);
		driver.findElement(By.id("ip_137656023")).sendKeys(recipientDetails[1]);
		driver.findElement(By.id("ip_3177473671")).sendKeys(recipientDetails[2]);

		//Sender
		String[] senderDetails = ReadExcel.getData(path,1).split(" ");
		driver.findElement(By.id("ip_1082986083")).sendKeys(senderDetails[0]);
		driver.findElement(By.id("ip_4081352456")).sendKeys(senderDetails[1]);
		driver.findElement(By.id("ip_2121573464")).sendKeys(senderDetails[2]);
		driver.findElement(By.id("ip_2194351474")).sendKeys(senderDetails[3]);
		driver.findElement(By.id("ip_567727260")).sendKeys(senderDetails[4]);

		Thread.sleep(3000);

		String error = driver.findElement(By.xpath("//div[@class='_1HVuH']")).getText();

		System.out.println(error);

		driver.findElement(By.id("ip_567727260")).clear();
		Thread.sleep(3000);
		driver.findElement(By.id("ip_567727260")).sendKeys("560005");

		Thread.sleep(3000);

		driver.findElement(By.xpath("//button[@class='_3Hxyv _1fVSi action-button _1gIUf _1XfDi']")).click();

		String error2 = driver.findElement(By.xpath("//input[@name='recipient_mobile_number']")).getAttribute("title");
		System.out.println(error2);

		driver.findElement(By.id("ip_3177473671")).clear();
		driver.findElement(By.id("ip_3177473671")).sendKeys("8985425562");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[@class='_3Hxyv _1fVSi action-button _1gIUf _1XfDi']")).click();

		Thread.sleep(3000);

		driver.navigate().back();

	}
	
	@AfterTest
    public void terminateBrowser(){
//        driver.close();
    }

}

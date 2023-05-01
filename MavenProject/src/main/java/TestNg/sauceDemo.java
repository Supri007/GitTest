package TestNg;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import com.google.common.collect.Ordering;

import junit.framework.Assert;

public class sauceDemo {
	ChromeDriver driver;
	
	@Test
	public void demoLogin () {
		System.setProperty("webdriver.chrome.driver", "/Users/suppu/Documents/Selenium/chromedriver_mac64/chromedriver");
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver (option);
		
		driver.get("https://www.saucedemo.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		
		
//		driver.findElement(By.id("user-name")).sendKeys("standard_user");
//		driver.findElement(By.cssSelector("input[name=\"password\"]")).sendKeys("secret_sauce");
//		driver.findElement(By.cssSelector(".submit-button.btn_action")).click();
//		
//		List<WebElement> ele = driver.findElements(By.className("inventory_item_name"));
//		Assert.assertEquals(ele.size(), 6);
		
		
		
		
		//to Logout of the page
//		driver.findElement(By.cssSelector("button[id=\"react-burger-menu-btn\"]")).click();
//		driver.findElement(By.cssSelector("a[id=\"logout_sidebar_link\"]")).click();
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
		// verify the login for user 
		// verify dashboard logo 
		 // verify number of items in navigation bar 
		 // verify the filter  
		 // verify the complete order flow
		 // verify the calculation of price on checkout page
		
		
		
		//driver.close();
		
	}
	
	@Test(priority=1)
	public void loginTest() {
		
		//Test Invalid user
		driver.findElement(By.id("user-name")).sendKeys("test123");
		driver.findElement(By.cssSelector("input[name=\"password\"]")).sendKeys("12345");
		driver.findElement(By.cssSelector(".submit-button.btn_action")).click();
		
		String errorMessage = driver.findElement(By.cssSelector("h3[data-test=\"error\"]")).getText();
		Assert.assertEquals(errorMessage, "Epic sadface: Username and password do not match any user in this service");
		
		//refresh the page
		driver.navigate().refresh();
		
		//Test valid user
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.cssSelector("input[name=\"password\"]")).sendKeys("secret_sauce");
		driver.findElement(By.cssSelector(".submit-button.btn_action")).click();
		String pageTitle = driver.getTitle();
		Assert.assertEquals(pageTitle, "Swag Labs");
		
	}
	
	@Test(priority=2)
	public void dashBoardLogoTest() {
		String appLogo = driver.findElement(By.cssSelector("div[class=\"app_logo\"]")).getText();
		System.out.println(appLogo);
		Assert.assertEquals(appLogo, "Swag Labs");
	}
	
	@Test(priority=3)
	public void navigationItemsTest() {
		List<WebElement> navigationItems = driver.findElements(By.cssSelector("a[class=\"bm-item menu-item\"]"));
		Assert.assertEquals(navigationItems.size(), 4);
		
		 // verify the add to card functionality 
		driver.findElement(By.cssSelector("#add-to-cart-sauce-labs-backpack")).click();
		String cartNumber = driver.findElement(By.cssSelector("span[class=\"shopping_cart_badge\"]")).getText();
		Assert.assertEquals(cartNumber, "1");
	}
	
	@Test(priority=4)
	public void filterTest() throws InterruptedException {
		driver.findElement(By.cssSelector("select[class=\"product_sort_container\"]")).click();
		
		//List of products originally
		List<WebElement> originalProductList = driver.findElements(By.className("inventory_item_name"));
		ArrayList<String> originalList = new ArrayList<String>();
		
		for (WebElement ele : originalProductList) {
			originalList.add(ele.getText());
		}
		System.out.println("Original list is :" + originalList);
		//list of options to filter
		//List<WebElement> optionsList = driver.findElements(By.cssSelector("select[class=\"product_sort_container\"]>option"));
//		List<WebElement> optionsList = driver.findElements(By.cssSelector("#header_container > div.header_secondary_container > div > span > select>option"));
//		System.out.println("optionsList: " + optionsList.size());
		
		//test Z to A
		//optionsList.get(1).click();
		driver.findElement(By.cssSelector("select>option[value=\"za\"]")).click();
		
		List<WebElement> afterSortingList = driver.findElements(By.className("inventory_item_name"));
		ArrayList<String> afterList = new ArrayList<String>();
		
		for (WebElement ele : afterSortingList) {
			afterList.add(ele.getText());
		}
		System.out.println("After sorted list is :" + afterList);
		
		
		Collections.sort(afterList);
		Assert.assertEquals(originalList, afterList);;
		
		//setting to default sorting
		//optionsList.get(0).click();
		driver.findElement(By.cssSelector("select>option[value=\"lohi\"]")).click();
		
		//get price list
		List<WebElement> originalPriceList = driver.findElements(By.cssSelector("div[class=\"inventory_item_price\"]"));
		ArrayList<String> priceList = new ArrayList<String>();
		
		for (WebElement ele1 : originalPriceList) {
			priceList.add(ele1.getText());
		}
		System.out.println("Original price kist: " + priceList);
		
		//clicking high to low
		//optionsList.get(3).click();
		driver.findElement(By.cssSelector("select>option[value=\"hilo\"]")).click();
		
		List<WebElement> priceListAfterSorting = driver.findElements(By.cssSelector("div[class=\"inventory_item_price\"]"));
		ArrayList<String> afterPriceList = new ArrayList<String>();
		
		for (WebElement ele2 : priceListAfterSorting) {
			afterPriceList.add(ele2.getText());
		}
		
		System.out.println("Before sorting price: " + afterPriceList);
		//Collections.sort(afterPriceList);
		//Ordering.natural().isOrdered(afterPriceList);
		System.out.println("After sorting price: " + afterPriceList);
		Assert.assertEquals(priceList.get(0), afterPriceList.get(afterPriceList.size() - 1));
		Thread.sleep(5000);
		
		driver.findElement(By.cssSelector("select>option[value=\"az\"]")).click();
		
		
		
	}
	
	@Test(priority=5)
	public void orderFlowTest() throws InterruptedException {
		
		//removing previously add item from the cart
		driver.findElement(By.id("remove-sauce-labs-backpack")).click();
		
		//getting total of first 4 products
		List<WebElement> priceList = driver.findElements(By.cssSelector("div[class=\"inventory_item_price\"]"));
		double total = 0;
		
		for (int i = 0; i < priceList.size(); i++)
		{
			total = total + Double.parseDouble(priceList.get(i).getText().substring(1));
		}
		total = total + 10.40;
		
		//adding 3 products to cart
		List<WebElement> cartButtonList = driver.findElements(By.cssSelector("button[class=\"btn btn_primary btn_small btn_inventory\"]"));
		Thread.sleep(5000);
		System.out.println("Add to cart button list: " + cartButtonList.size());
		
		for (int i = 0; i < cartButtonList.size(); i++)
		{
			cartButtonList.get(i).click();
		}
		
		driver.findElement(By.cssSelector("a[class=\"shopping_cart_link\"]")).click();
		driver.findElement(By.cssSelector("button[name=\"checkout\"]")).click();
		
		//adding user name and zip code
		driver.findElement(By.cssSelector("input[placeholder=\"First Name\"]")).sendKeys("Daisy");
		driver.findElement(By.cssSelector("input[placeholder=\"Last Name\"]")).sendKeys("Pup");
		driver.findElement(By.cssSelector("input[placeholder=\"Zip/Postal Code\"]")).sendKeys("12345");
		driver.findElement(By.id("continue")).click();
		Thread.sleep(5000);
		
		String totalInCheckoutPage = driver.findElement(By.cssSelector("div[class=\"summary_info_label summary_total_label\"]")).getText();
		double totalCheckOut = Double.parseDouble(totalInCheckoutPage.substring(8));
		
		Assert.assertEquals(totalCheckOut, total);
		
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("#finish")).click();
		String thankYou = driver.findElement(By.className("complete-header")).getText();
		Assert.assertEquals(thankYou, "Thank you for your order!");
		driver.close();
		
	}
	
	@Test(priority=6, enabled=false)
	public void checkoutTest() throws InterruptedException {
		driver.findElement(By.cssSelector("a[class=\"shopping_cart_link\"]")).click();
		driver.findElement(By.cssSelector("button[name=\"checkout\"]")).click();
		String url = driver.getCurrentUrl();
		Assert.assertEquals(url, "https://www.saucedemo.com/checkout-step-one.html");
		
		
		driver.close();
	}

}

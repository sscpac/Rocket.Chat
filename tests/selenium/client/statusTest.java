package myPackages;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class statusTest {
	
	private static WebDriver driver = new SafariDriver();
	
	public static String HOME_URL = "http://localhost:3000";
	
	private static By usernameOrEmailFieldLocator = By.id("emailOrUsername");
    private static By passwordFieldLocator = By.id("pass");
    private static By loginButtonLocator = By.cssSelector("button.button.primary.login");
	
	private static By openMenuLocator = By.cssSelector("span.arrow.bottom");

	private static By onlineStatusIconLocator = By.cssSelector("div.info.status-online");
	private static By onlineButtonLocator = By.cssSelector("button.status.online");
	private static By awayButtonLocator = By.cssSelector("button.status.away");
	private static By awayStatusIconLocator = By.cssSelector("div.info.status-away");
	private static By busyButtonLocator = By.cssSelector("button.status.busy");
	private static By busyStatusIconLocator = By.cssSelector("div.info.status-busy");
	private static By invisibleButtonLocator = By.cssSelector("button.status.offline");
	private static By invisibleStatusIconLocator = By.cssSelector("div.info.status-offline");
	
	private static boolean isElementPresent(By targetElement){
		Boolean isPresent = driver.findElements(targetElement).size() > 0;
		if(isPresent){
			return true;
		}else{
			return false;
		}
			
	}
	
	@BeforeClass
	public static void beforeClass(){
		driver.get(HOME_URL);
		driver.findElement(usernameOrEmailFieldLocator).sendKeys("adrian");
        driver.findElement(passwordFieldLocator).sendKeys("adrian");
        driver.findElement(loginButtonLocator).click();
	}
	
	@Before
	public void before() throws Exception {
		Thread.sleep(100);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(openMenuLocator)).click();
		Thread.sleep(50);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(onlineButtonLocator));
	}
	
	@AfterClass
	public static void doEnd() {
		driver.quit();
	}
	
	//@Test
	public void setOnline() throws Exception {
		Thread.sleep(100);
		new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(onlineButtonLocator)).click();
		Thread.sleep(100);
		Assert.assertTrue(isElementPresent(onlineStatusIconLocator));
	}
	
	@Test
	public void setAway() throws Exception {
		Thread.sleep(100);
		new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(awayButtonLocator)).click();
		Thread.sleep(100);
		Assert.assertTrue(isElementPresent(awayStatusIconLocator));
	}
	
	@Test
	public void setBusy() throws Exception {
		Thread.sleep(100);
		new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(busyButtonLocator)).click();
		Thread.sleep(100);
		Assert.assertTrue(isElementPresent(busyStatusIconLocator));
	}
	
	@Test
	public void setInvisible() throws Exception {
		Thread.sleep(100);
		new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(invisibleButtonLocator)).click();
		Thread.sleep(100);
		Assert.assertTrue(isElementPresent(invisibleStatusIconLocator));
	}

}

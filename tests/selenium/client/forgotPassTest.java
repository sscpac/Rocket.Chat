package myPackages;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
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

public class forgotPassTest {
	public static WebDriver driver;
	
	public static String INVALID_EMAIL = "The email entered is invalid";
	public static String SUCCESS_MESSAGE = "We have sent you an email";
	
	private static By forgotPassLink = By.className("forgot-password");
	private static By emailFieldLocator = By.id("email");
	private static By confirmButtonLocator = By.className("button primary login");
	private static By errorMessageLocator = By.className("input-error");
	private static String emailError;
	private By bannerLocator = By.id("toast-container");
	
	private void fillFormWith(String keys){
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(emailFieldLocator));
		driver.findElement(emailFieldLocator).sendKeys(keys);
		driver.findElement(confirmButtonLocator).click();
	}
	
	@BeforeClass
	public static void setupDriver (){
		driver = new SafariDriver();
		//driver = new SafariDriver();
	}
	
	@Before
	public void beforeEach(){
		driver.get("http://localhost:3000");
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(forgotPassLink)).click();
		
	}
	
	@Test
	public void nullEmailShouldFail(){
		fillFormWith(" ");
		emailError = driver.findElement(errorMessageLocator).getText();
		assertEquals(INVALID_EMAIL, emailError);
	}
	
	@Test
	public void invalidEmailShouldFail(){
		fillFormWith("someEmail");
		emailError = driver.findElement(errorMessageLocator).getText();
		assertEquals(INVALID_EMAIL, emailError);
	}
	
	@Test
	public void validEmailShouldPass(){
		fillFormWith("sample@gmail.com");
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(bannerLocator));
		String message = driver.findElement(bannerLocator).getText();
		Assert.assertThat(message, CoreMatchers.containsString(SUCCESS_MESSAGE));
	}
	
	@AfterClass
	public static void closeDriver(){
		driver.quit();
	}
}

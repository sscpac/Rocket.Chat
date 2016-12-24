/*
 * Tests the forgot pass module in the front page
 * Password recovery currently is used with email so this tests only 3 cases (invalid email,
 * null email, and valid email) 
 */

package testRocketChatPackage;

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
	
	/*
	 * Pass in an email into the text email elements, then click submit
	 */
	
	private void fillFormWith(String email){
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(emailFieldLocator));
		driver.findElement(emailFieldLocator).sendKeys(email);
		driver.findElement(confirmButtonLocator).click();
	}
	
	@BeforeClass
	public static void setupDriver (){
		driver = new SafariDriver();
	}
	
	@Before
	public void beforeEach(){
		driver.get("http://localhost:3000");
		//add this wait to make sure that the webpage loads if there are any issues with loading time
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(forgotPassLink)).click();
		
	}
	
	/*
	 * All tests pass the email string. A test will pass as long as the anticipated email
	 * error shows up. If the email message changes, make sure to change the error above as well
	 * of this test will always fail
	 */
	
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

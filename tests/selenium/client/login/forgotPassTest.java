/*
 * Tests the forgot pass module in the front page
 * Password recovery currently is used with email so this tests only 3 cases (invalid email,
 * null email, and valid email) 
 */

package testRocketChatPackage.login;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class forgotPassTest {
	public static WebDriver driver;
	
	public static String INVALID_EMAIL 		= "The email entered is invalid";
	public static String SUCCESS_MESSAGE 	= "We have sent you an email";
	
	private static By forgotPassLink	 	= By.className("forgot-password");
	private static By emailFieldLocator 	= By.id("email");
	private static By confirmButtonLocator 	= By.className("button primary login");
	private static By errorMessageLocator 	= By.className("input-error");
	private By bannerLocator 				= By.id("toast-container");
	private static String emailError;
	
	/*
	 * Pass in an email into the text email elements, then click submit
	 */
	
	private void fillFormWith(String email){
		driver.findElement(emailFieldLocator).sendKeys(email);
		driver.findElement(confirmButtonLocator).click();
	}
	
	@BeforeClass
	public static void setupDriver (){
		driver = new SafariDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Before
	public void beforeEach(){
		driver.get("http://localhost:3000");
		//add this wait to make sure that the webpage loads if there are any issues with loading time
		driver.findElement(forgotPassLink).click();
	}
	
	@AfterClass
	public static void closeDriver(){
		driver.quit();
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
		fillFormWith("someRandomEmail");
		emailError = driver.findElement(errorMessageLocator).getText();
		assertEquals(INVALID_EMAIL, emailError);
	}
	
	//Note that there is no email validation against the mongoDB
	@Test
	public void validEmailShouldPass(){
		fillFormWith("sample@mail.com");
		String message = driver.findElement(bannerLocator).getText();
		Assert.assertThat(message, CoreMatchers.containsString(SUCCESS_MESSAGE));
	}
	
}

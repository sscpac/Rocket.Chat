package testRocketChatPackage.login;

//	Unit test checking user creation. The name accepts any characters, 
//	the username and email may not be used. Password has no requirements.

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class registerNewUserTest {
	private static  WebDriver driver;
	
	public static String ERROR_EMPTY_NAME 					= "The name must not be empty";
	public static String ERROR_EMPTY_PASS 					= "The password must not be empty";
	public static String ERROR_INVALID_EMAIL 				= "The email entered is invalid";
	public static String ERROR_PASSWORDS_NO_MATCH			= "The password confirmation does not match password";
	public static String TEXT_HOME_TITLE 					= "Home";
	public static String TEXT_REGISTER_DISPLAY_NAME_HEADER 	= "Register username";
	public static String ERROR_DISPLAY_NAME_EXISTS 			= "is already in use :(";

	
	private static By nameFieldLocator 					= By.id("name");
	private static By emailFieldLocator 				= By.id("email");
	private static By passwordFieldLocator		 		= By.id("pass");
	private static By passwordConfirmFieldLocator 		= By.id("confirm-pass");
	private static By registerNewAccLinkLocator 		= By.className("register");
	private static By registerBtnLocator 				= By.xpath("//*[@id=\"login-card\"]/div[2]/button/span");
	private static By displayUsernameLocator 			= By.id("username");
	private static By useThisUsernameBtnLocator 		= By.xpath("//*[@id=\"login-card\"]/div[2]/button");
	private static By roomTitleLocator 					= By.className("room-title");
	private static By confirmUsernameTitlePageLocator 	= By.cssSelector("h2");
	private static By confirmDisplayNameErrorLocator 	= By.className("alert alert-danger");
	
	private static String accountName;							//this will be the name holder of the account, used Unit Test to identify which usernames are generated by this script
	private static String accountUsername;  					//this will be the same as the account name
	private static String accountEmail;							//without @gmail.com
	private static String accountEmailFull; 					//with @gmail.com 
	private static String accountPass = "adrian"; 				//for confirm pass failures well just use space
	private static String existingDisplayName = "rocket.cat";	//this is the default display name of the account thati s automatically created by Rocket Chat
	
	private static By errorFieldLocator = By.className("input-error");
	private static String nameError;
	private static String emailError;
	private static String passwordError;
	private static String passwordConfirmError;
	
	/*
	 * global method to generate random random username for testing
	 */
	
	public static void register(WebDriver drive){
		drive.get(testRocketChatPackage.config.settings.homepageUrl);
		driver.findElement(registerNewAccLinkLocator).click();
		generateTestAccountCredentials();
		autoFillForm(accountName,accountEmailFull, accountPass, accountPass, drive);
		confirmUsername(drive);
	}
	
	/*
	 * global method to generate specific username and password for test passing
	 */
	
	public static void register(String username, String password, WebDriver drive){
		drive.get(testRocketChatPackage.config.settings.homepageUrl);
		driver.findElement(registerNewAccLinkLocator).click();
		generateTestAccountCredentials();	
		autoFillForm(username,username + "@gmail.com", password, password, drive);
		confirmUsername(drive);
	}
	
	/*
	 * Before this test runs we will generate the account credentials using UUID to make sure that the test
	 * will always pass using unique users (a little unnecessary but works well)
	 */
	
	//Note :we need to generate a unique email every test run
	
	private static void generateTestAccountCredentials(){
		accountEmail = 		UUID.randomUUID().toString().substring(5,15);	
		accountUsername = 	"user" + accountEmail.substring(0,10);
		accountName = 		accountUsername;
		accountEmailFull = 	accountEmail + "@gmail.com";
	}
	
	
	private void autoFillForm(WebDriver drive){
		clearForm(drive);	
		drive.findElement(registerBtnLocator).click();
		captureErrorMessages(drive);
	}
	
	private static void autoFillForm(String name, String email, String pass, String confirmPass, WebDriver drive){
		clearForm(drive);	
		drive.findElement(nameFieldLocator).sendKeys(name);
		drive.findElement(emailFieldLocator).sendKeys(email);
		drive.findElement(passwordFieldLocator).sendKeys(pass);
		drive.findElement(passwordConfirmFieldLocator).sendKeys(confirmPass);
		drive.findElement(registerBtnLocator).click();
		captureErrorMessages(drive);
	}
	//Note that the driver in this class is specific to this driver only
	private void autoFillForOnly(String fieldType){
		
		switch(fieldType) {
		   case "name" :
			   	clearForm(driver);
			   	driver.findElement(nameFieldLocator).sendKeys(accountName);
		   
		   case "shortemail" :
			   	clearForm(driver);
			   	driver.findElement(emailFieldLocator).sendKeys(accountEmail);

		   case "fullEmail" :
			   	clearForm(driver);
			   	driver.findElement(emailFieldLocator).sendKeys(accountEmailFull);
		      
		   case "firstPassword" :
			   	clearForm(driver);
			   	driver.findElement(passwordFieldLocator).sendKeys(accountPass);
			   	
		   case "confirmPassword" :
			   	clearForm(driver);
			   	driver.findElement(passwordConfirmFieldLocator).sendKeys(accountPass);
		   default : // Optional
			   driver.findElement(registerBtnLocator).click();
			   captureErrorMessages(driver);
		}
		
	}

	
	private static void clearForm(WebDriver drive){
		drive.findElement(nameFieldLocator).clear();
		drive.findElement(emailFieldLocator).clear();
		drive.findElement(passwordFieldLocator).clear();
		drive.findElement(passwordConfirmFieldLocator).clear();
	}

	
	private static void confirmUsername(WebDriver drive){
		drive.findElement(useThisUsernameBtnLocator).click();
	}
	
	private static void captureErrorMessages(WebDriver drive){
		nameError = drive.findElements(errorFieldLocator).get(0).getText();
		emailError = drive.findElements(errorFieldLocator).get(1).getText();
		passwordError = drive.findElements(errorFieldLocator).get(2).getText();
		passwordConfirmError = drive.findElements(errorFieldLocator).get(3).getText();
	}
	
	private void logoutAndReturnToRegistration(){
		driver.get(testRocketChatPackage.config.settings.homepageUrl);
		driver.findElement(registerNewAccLinkLocator).click();
	}
	
	/*
	 * Before anything we want to go to the website location (URL_CHATLOCKER_MAIN). Wait for the page to load then go to the 
	 * account registration page by clicking on register. Then we will generate our unique user info that will be used for auto
	 * filling later
	 */
	
	@BeforeClass
	public static void beforeClass(){
		driver = new SafariDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(testRocketChatPackage.config.settings.homepageUrl);
		driver.findElement(registerNewAccLinkLocator).click();
		generateTestAccountCredentials();
	
	}
	

	// Called at the end of the class. Closes the current tab in focus then quits the driver

	
	@AfterClass
	public static void afterClass(){
		driver.close();
		driver.quit();			
	}
	
	/*
	 * test below will intentionally fail to make sure the proper error messages are handled with each
	 * different failure cases 
	 */
	
	@Test
	public void enterAllEmptyFieldsShouldFail(){
		//
		autoFillForm(driver);									// enter null for all fields
		Assert.assertEquals(nameError, ERROR_EMPTY_NAME);		//first expect an error message (checking one fail is sufficient)
	}
	
	@Test
	public void enterOnlyValidNameShouldFail(){
		autoFillForOnly("name");								//fill in only a name and the rest as null
		Assert.assertEquals(emailError, ERROR_INVALID_EMAIL);	//first error should be email
	}
	
	@Test
	public void enterOnlyAnIncompleteEmailShouldFail(){
		autoFillForOnly("shortemail");							//fill in an incomplete email with the rest as null
		Assert.assertEquals(emailError, ERROR_INVALID_EMAIL);	//first errror show say invalid email
	}
	
	@Test
	public void enterOnlyEmailShouldFail(){
		autoFillForOnly("fullEmail");							//fill valid email but the other fields as null
		Assert.assertEquals(nameError, ERROR_EMPTY_NAME);		//first error should show empty name
	}
	
	@Test
	public void enterOnlyPassShouldFail(){
		autoFillForOnly("firstPassword");										//enter only one password
		Assert.assertEquals(passwordConfirmError, ERROR_PASSWORDS_NO_MATCH);	//should have passwords dont match error
	}
	
	@Test
	public void enterOnlyConfirmPassShouldFail(){
		
		autoFillForOnly("confirmPassword");						//should only fill the second password confirmation field
		Assert.assertEquals(passwordError, ERROR_EMPTY_PASS);	//error should state first password field is empty
	}
	
	@Test
	public void enterAllWithInvalidEmailFieldShouldFail(){
		autoFillForm(accountName,accountEmail, accountPass, accountPass, driver);	//all fields should be valid except for the email not being complete
		Assert.assertEquals(emailError, ERROR_INVALID_EMAIL);				//email is invalid (incomplete)
	}
	
	//This test below ensures that a valid registered user will pass to the next screen
	// in this case, the user display name selection screen
	
	@Test
	public void enterAllValidFieldsShouldGoToConfirmUsernamePageShouldPass(){	
		generateTestAccountCredentials();
		autoFillForm(accountName,accountEmailFull, accountPass, accountPass, driver);
		WebElement h2Title = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(confirmUsernameTitlePageLocator));
		Assert.assertEquals(h2Title.getText(), TEXT_REGISTER_DISPLAY_NAME_HEADER);
		logoutAndReturnToRegistration();
	}
	
	//This test will register a unique user. The user will successfully create then go to the the 
	// Username Selection Screen, select an unused DisplayName and go to the main page
	// when successful it will logout and go back to the login page
	
	@Test
	public void AfterSuccessAccCreationShouldGoToConfirmUsernameSelectValidDisplayNameShouldPass(){
		generateTestAccountCredentials();
		autoFillForm(accountName,accountEmailFull, accountPass, accountPass, driver);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(confirmUsernameTitlePageLocator));
		confirmUsername(driver);
		String title = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(roomTitleLocator)).getText();
		Assert.assertEquals(title, TEXT_HOME_TITLE);
		logoutAndReturnToRegistration();
	}	
	
	//similar test scenario as the Test above except it will fail because the Display Name selected will already be in use
	
	@Test
	public void AfterSuccessAccCreationShouldGoToConfirmUsernameSelectInvalidDisplayNameShouldFail(){
		generateTestAccountCredentials();	
		autoFillForm(accountName,accountEmailFull, accountPass, accountPass, driver);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(confirmUsernameTitlePageLocator));
		driver.findElement(displayUsernameLocator).clear();
		driver.findElement(displayUsernameLocator).sendKeys(existingDisplayName);	//should be invalid as an account of displayname adrian already exists
		confirmUsername(driver);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(confirmDisplayNameErrorLocator));
		String errorMessage = driver.findElement(confirmDisplayNameErrorLocator).getText();
		Assert.assertThat(errorMessage, CoreMatchers.containsString(ERROR_DISPLAY_NAME_EXISTS));
		logoutAndReturnToRegistration();
	}	
	

}

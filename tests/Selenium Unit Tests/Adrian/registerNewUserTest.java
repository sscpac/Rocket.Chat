package myPackages;


import java.util.UUID;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;

public class registerNewUserTest {
	private static  WebDriver driver;
	
	private static By nameFieldLocator = By.id("name");
	private static By emailFieldLocator = By.id("email");
	private static By passwordFieldLocator = By.id("pass");
	private static By passwordConfirmFieldLocator = By.id("confirm-pass");
	private static By registerNewAccLinkLocator = By.className("register");
	private static By registerBtnLocator = By.xpath("//*[@id=\"login-card\"]/div[2]/button/span");
	
	private static String accountName;
	private static String accountEmail;		//without @gmail.com
	private static String accountEmailFull; //with @gmail.com 
	private static String accountPass; 		//for confirm pass failures well just use space
	
	private By errorFieldLocator = By.className("input-error");
	private String nameError;
	private String emailError;
	private String passwordError;
	private String passwordConfirmError;
	
	private static void generateTestAccountCredentials(){
		accountName = "Adrian//!&%";
		accountEmail = UUID.randomUUID().toString().substring(0,15);	//we need to generate a unique email every test run
		accountEmailFull = accountEmail + "@gmail.com";
		accountPass = "adrian";
	}
	
	private void autoFillForm(){
		clearForm();
		register();
		captureErrorMessages();
	}
	
	private void autoFillForm(String name, String email, String pass, String confirmPass){
		clearForm();
		driver.findElement(nameFieldLocator).sendKeys(name);
		driver.findElement(emailFieldLocator).sendKeys(email);
		driver.findElement(passwordFieldLocator).sendKeys(pass);
		driver.findElement(passwordConfirmFieldLocator).sendKeys(confirmPass);
		register();
		captureErrorMessages();
	}
	
	private void autoFillForOnly(String fieldType){
		
		switch(fieldType) {
		   case "name" :
			   	clearForm();
			   	driver.findElement(nameFieldLocator).sendKeys(accountName);
		   
		   case "shortemail" :
			   	clearForm();
			   	driver.findElement(emailFieldLocator).sendKeys(accountEmail);

		   case "fullEmail" :
			   	clearForm();
			   	driver.findElement(emailFieldLocator).sendKeys(accountEmailFull);
		      
		   case "firstPassword" :
			   	clearForm();
			   	driver.findElement(passwordFieldLocator).sendKeys(accountPass);
			   	
		   case "confirmPassword" :
			   	clearForm();
			   	driver.findElement(passwordConfirmFieldLocator).sendKeys(accountPass);
		   default : // Optional
			   register();
			   captureErrorMessages();
		}
		
	}

	
	private void clearForm(){
		driver.findElement(nameFieldLocator).clear();
		driver.findElement(emailFieldLocator).clear();
		driver.findElement(passwordFieldLocator).clear();
		driver.findElement(passwordConfirmFieldLocator).clear();
	}
	
	private void register(){
		driver.findElement(registerBtnLocator).click();
	}
	
	private void captureErrorMessages(){
		nameError = driver.findElements(errorFieldLocator).get(0).getText();
		emailError = driver.findElements(errorFieldLocator).get(1).getText();
		passwordError = driver.findElements(errorFieldLocator).get(2).getText();
		passwordConfirmError = driver.findElements(errorFieldLocator).get(3).getText();
	}
	
	
	@BeforeClass
	public static void beforeClass(){
		driver = new SafariDriver();
		driver.get("http://localhost:3000");
		WebElement registerLink = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(registerNewAccLinkLocator));
		registerLink.click();
		generateTestAccountCredentials();
	
	}
	
	@Before
	public void beforeTestsCheckIfAtRegistrationPage(){
		new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(nameFieldLocator));
	}
	
	@Test
	public void enterAllEmptyFieldsShouldFail(){
		autoFillForm();
		Assert.assertEquals(nameError, "The name must not be empty");
	}
	
	@Test
	public void enterOnlyValidNameShouldFail(){
		autoFillForOnly("name");
		Assert.assertEquals(emailError, "The email entered is invalid");
	}
	
	@Test
	public void enterOnlyAnIncompleteEmailShouldFail(){
		autoFillForOnly("shortemail");
		Assert.assertEquals(emailError, "The email entered is invalid");
	}
	
	@Test
	public void enterOnlyEmailShouldFail(){
		autoFillForOnly("fullEmail");
		Assert.assertEquals(nameError, "The name must not be empty");
	}
	
	@Test
	public void enterOnlyPassShouldFail(){
		autoFillForOnly("firstPassword");
		Assert.assertEquals(passwordConfirmError, "The password confirmation does not match password");
	}
	
	@Test
	public void enterOnlyConfirmPassShouldFail(){
		autoFillForOnly("firstPassword");
		Assert.assertEquals(passwordError, "The password must not be empty");
	}
	
	
	
	@Test
	public void enterAllWithInvalidEmailFieldShouldFail(){
		autoFillForm(accountName,accountEmail, accountPass, accountPass);
		Assert.assertEquals(emailError, "The email entered is invalid");
	}
	
	
	@AfterClass
	public static void afterClass(){
		driver.close();
		driver.quit();
	}
	
	

}

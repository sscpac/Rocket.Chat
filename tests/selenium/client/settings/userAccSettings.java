package testRocketChatPackage.settings;

import java.util.concurrent.TimeUnit;

import org.junit.After;

/*
 *  Tests main menu > My Account settings and all the sub menus within it 
 *  
 *  NOTE: 
 *  @TODO Tests have issue with referencing menu items for some odd reason. 
 *  Have tried cssSelectors, xPaths and referencing the items as an array and 
 *  fails to click. Other tests including a similar menu experiences same isse
 */

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

public class userAccSettings {

  private static WebDriver driver;

  public static String HOME_URL = "http://localhost:3000";

  private static By usernameOrEmailFieldLocator = By.id("emailOrUsername");
  private static By passwordFieldLocator 		= By.id("pass");
  private static By loginButtonLocator 			= By.cssSelector("button.button.primary.login");
  private static By openMenuLocator 			= By.cssSelector("div#rocket-chat > aside > span.arrow.bottom"); //span.arrow.bottom" //with sleeps
  private static By myAccountBtnLocator 		= By.cssSelector("div#rocket-chat > aside > header > div > nav.options > div.wrapper > button#account"); //button.account-link
  private static By preferencesLinkLocator 		= By.cssSelector("div.flex-nav > section > div.content > div.wrapper > ul > li > a:nth-of-type(1)");
  
  private static By headerLocator 				= By.cssSelector("h4");
  private static By menuItemsLocator 			= By.cssSelector("a.account-link");
  private static By mainPageHeader 				= By.className("span.room-title");
  
  private static By profileBtn 					= By.xpath("//a[@href, '/account/profile']");
  



  @BeforeClass
  	public static void beforeClass() throws Exception {
	  	driver = new SafariDriver();
	  	driver.manage().window().maximize();
	  	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  	testRocketChatPackage.login.loginTest.login("test", "test", driver);
		driver.findElement(openMenuLocator).click();
		Thread.sleep(250);
		driver.findElement(myAccountBtnLocator).click();
  }
  
  /*
   *  checks if the my account settings menu can properly be accessed.
   *  Main Menu> My Account
   */
  
  @Test
  public void checkIfWeAreInSettingsMenu() throws Exception {
	  String menuTitle = driver.findElements(headerLocator).get(1).getText();
	  Thread.sleep(1000);
	  Assert.assertEquals(menuTitle, "My Account");
  }
  
  /*
   * check if we click on Profile we go to the Profile Menu assuming it is 
   * the second item
   * Main Menu > Profile
   */
  
  //@Test @TODO Need to finish this; Reference to the profileBtn is not working
  public void checkProfileMenu() throws Exception{
	  driver.findElement(profileBtn).click();
//	  WebElement profileLink = driver.findElements(menuItemsLocator).get(1);
//	  profileLink.click();
	  String pageTitle = driver.findElement(mainPageHeader).getText();
	  Assert.assertEquals(pageTitle, "Profile");
  }
  
  /*
   * check if we click on Preferences we go to the Preferences Menu assuming it is 
   * the first item
   * Main Menu > Preferences
   */
  
  //@Test @TODO Need to finish this; Reference to the preferencesLinkLocator is not working
  public void navigateToPreferencesMenu() throws Exception{
	  Thread.sleep(1000);
	  //System.out.println(driver.findElements(menuItemsLocator).get(0).getText());
	  Thread.sleep(1000);
	  driver.findElement(preferencesLinkLocator).click();
	  String pageTitle = driver.findElement(mainPageHeader).getText();
	  Assert.assertEquals(pageTitle, "Preferences");
  }
  
  /*
   * check if we click on Avatar we go to the Avatar Menu assuming it is 
   * the third item
   * Main Menu > Avatar
   */
  
  //@Test @TODO Need to finish this; Reference to the profileLink is not working
  public void checkAvatarMenuExist() throws Exception{
	  Thread.sleep(1000);
	  System.out.println(driver.findElements(menuItemsLocator).get(2).getText());
	  Thread.sleep(1000);
	  WebElement profileLink = driver.findElements(menuItemsLocator).get(2);
	  profileLink.click();
	  String pageTitle = driver.findElement(mainPageHeader).getText();
	  Assert.assertEquals(pageTitle, "Avatar");
  }
  
  

  /*
   * We call this after every method to ensure that we are still at the 
   * My Accounts side panel after every test otherwise each test afterwards
   * will fail
   */
  
//  @After
//  public void goToMyAccountMenu() throws Exception {
//	  String title = driver.findElements(headerLocator).get(1).getText();
//	  //check if we are on My Account still, if not reload and go there
//	  if(title != "My Account"){
//		    driver.get(HOME_URL);
//		    driver.findElement(usernameOrEmailFieldLocator).sendKeys("test");
//		    driver.findElement(passwordFieldLocator).sendKeys("test");
//		    driver.findElement(loginButtonLocator).click();
//		    Thread.sleep(1000);
//			driver.findElement(openMenuLocator).click();
//			Thread.sleep(1000);
//			driver.findElement(myAccountBtnLocator).click();
//	  }
//  }


  @AfterClass
  public static void doEnd() {
	driver.close();
    driver.quit();
  }


}
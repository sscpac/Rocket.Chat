package testRocketChatPackage;

import org.junit.After;

//  Unit test testing the Main User Drop Down Menu
//  This tests the following:
//      - changing status to Online, Away, Busy, Invisible via Left menu 
//      - Going to Settings
//      - Logging out

import org.junit.AfterClass;
import org.junit.Assert;
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
  public static String currentUserStatus;

  private static By usernameOrEmailFieldLocator = By.id("emailOrUsername");
  private static By passwordFieldLocator = By.id("pass");
  private static By loginButtonLocator = By.cssSelector("button.button.primary.login");

  private static By openMenuLocator = By.cssSelector("span.arrow.bottom");

  private static By onlineButtonLocator = By.cssSelector("button.status.online");
  private static By awayButtonLocator = By.cssSelector("button.status.away");
  private static By busyButtonLocator = By.cssSelector("button.status.busy");
  private static By invisibleButtonLocator = By.cssSelector("button.status.offline");
  private static By userStatus = By.className("thumb");

  /*
   * Before the test we want to naviagte to the main menu after we login by clicking on the 
   * drop down menu
   */
  
  @BeforeClass
  public static void beforeClass() {
    driver.get(HOME_URL);
    driver.findElement(usernameOrEmailFieldLocator).sendKeys("test");
    driver.findElement(passwordFieldLocator).sendKeys("test");
    driver.findElement(loginButtonLocator).click();
    new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(openMenuLocator)).click();
    new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(onlineButtonLocator)).click();
  }
  
  /*
   * After every test open the main  drop down menu and click on a random status to make sure we are still
   * 
   */

  @After
  public void before() throws Exception {
    new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(openMenuLocator)).click();
    Thread.sleep(100);
    new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(onlineButtonLocator)).click();
  }
  
  /*
   * Close selenium drivers and current window
   */

  @AfterClass
  public static void doEnd() {
	driver.close();
    driver.quit();
  }
  
  /*
   * We will click on a status (Online, Away, Busy or invisible) and check the icon via attribute if it has changed
   * to the correct color
   */

  private static void changeStatusTo(By statusLocator) throws Exception {
    Thread.sleep(100);
    new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(statusLocator)).click();
    Thread.sleep(100);
    currentUserStatus = driver.findElement(userStatus).getAttribute("data-status");
  }
  
  /*
   * Each test will pass the status variables to the method changeStatusTo (which will click and get the
   * icon by storing it in a string via attribute. We will then assert that the catched icon string attribute is 
   * equal to what is hould be (ie. away = away) and so on..
   */

  @Test
  public void setAway() throws Exception {
    changeStatusTo(awayButtonLocator);
    Assert.assertEquals("away", currentUserStatus);
  }

  @Test
  public void setOnline() throws Exception {
    changeStatusTo(onlineButtonLocator);
    Assert.assertEquals("online", currentUserStatus);
  }

  @Test
  public void setBusy() throws Exception {
    changeStatusTo(busyButtonLocator);
    Assert.assertEquals("busy", currentUserStatus);
  }

  @Test
  public void setInvisible() throws Exception {
    changeStatusTo(invisibleButtonLocator);
    Assert.assertEquals("invisible", currentUserStatus);
  }

}
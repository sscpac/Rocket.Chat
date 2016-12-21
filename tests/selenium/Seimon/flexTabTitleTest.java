package testPackage;
 
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
 
//These Unit Tests check for the titles of each clickable/visible flex-tab button. 
//  The test uses the following for a successful login:
//  username: spawar
//  password: spawar

     
public class flexTabTitleTest {
    public static WebDriver driver;     
    //declare all your elements here using By it will make your code cleaner and simple
    private static By usernameOrEmailFieldLocator = By.id("emailOrUsername");
    private static By passwordFieldLocator = By.id("pass");
    private static By loginButtonLocator = By.className("button primary login");
    private static By arrowButtonLocator = By.className("arrow bottom");
    private By channelsLocator = By.className("name");
    private By flexTabBarLocator = By.className("flex-tab-bar");
    private By flexTabButtonsLocator = By.className("tab-button");
    private By currentTabTitleLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section/div/div/div/h2");
    
    private static void inputLoginFieldsWith(String usernameOrEmail, String password){
        driver.findElement(usernameOrEmailFieldLocator).sendKeys(usernameOrEmail);
        driver.findElement(passwordFieldLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();
    }
     
    //BeforeClass
    @BeforeClass //this means the method below will be called Once before this whole Class
    public static void setupTheDriverObject(){
        //driver setup with
    	driver = new SafariDriver();
    }
    
    //Before
    @Before //This will be called Before EVERY test
    public void getThePageWeWantToTest() {
        driver.get("http://localhost:3000");
        //this tells the driver to wait for the username/email field element to load otherwise it will fail the test within 10 secs
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(usernameOrEmailFieldLocator));
        
        inputLoginFieldsWith("spawar", "spawar");
        //Page Load
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(arrowButtonLocator));
        System.out.println("Successful Login!");
        //Click on 1st channel
        driver.findElements(channelsLocator).get(0).click();
        System.out.println("Channel room clicked");
      
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(flexTabBarLocator));
    }
    
    //AfterClass
    @AfterClass //this will be called After ALL tests complete
    public static void closeDriver(){
    	System.out.println("All tests executed!");
    	driver.quit(); //closes driver and browser window
    }
    
    //Tests
    @Test //1st tab
    public void Tab1AsRoomInfoShouldPass() throws InterruptedException { 
    	driver.findElements(flexTabButtonsLocator).get(1).click(); 
    	Thread.sleep(3000);
    	WebElement TabTitle = (new WebDriverWait(driver,3)).until(ExpectedConditions.presenceOfElementLocated(currentTabTitleLocator));
    	Assert.assertEquals(TabTitle.getText(), "Room Info");
    	System.out.println("1st tab name is " + TabTitle.getText() + " .Pass!");    	
    }
    
    @Test //2nd tab
    public void Tab2AsSearchMessagesShouldPass() throws InterruptedException { 
    	driver.findElements(flexTabButtonsLocator).get(2).click(); 
    	Thread.sleep(3000);
    	WebElement TabTitle = (new WebDriverWait(driver,3)).until(ExpectedConditions.presenceOfElementLocated(currentTabTitleLocator));
    	Assert.assertEquals(TabTitle.getText(), "Search Messages");
    	System.out.println("2nd tab name is " + TabTitle.getText() + " .Pass!");    	
    }
    
    @Test //3rd tab
    public void Tab3AsMembersListShouldPass() throws InterruptedException { 
    	driver.findElements(flexTabButtonsLocator).get(4).click(); 
    	Thread.sleep(3000);
    	WebElement TabTitle = (new WebDriverWait(driver,3)).until(ExpectedConditions.presenceOfElementLocated(currentTabTitleLocator));
    	Assert.assertEquals(TabTitle.getText(), "Members List");
    	System.out.println("3rd tab name is " + TabTitle.getText() + " .Pass!");    	
    }
    
    @Test //4th tab
    public void Tab4AsNotificationsShouldPass() throws InterruptedException { 
    	driver.findElements(flexTabButtonsLocator).get(5).click(); 
    	Thread.sleep(3000);
    	WebElement TabTitle = (new WebDriverWait(driver,3)).until(ExpectedConditions.presenceOfElementLocated(currentTabTitleLocator));
    	Assert.assertEquals(TabTitle.getText(), "Notifications");
    	System.out.println("4th tab name is " + TabTitle.getText() + " .Pass!");    	
    }
    
    @Test //5th tab
    public void Tab5AsFilesListShouldPass() throws InterruptedException { 
    	driver.findElements(flexTabButtonsLocator).get(6).click(); 
    	Thread.sleep(3000);
    	WebElement TabTitle = (new WebDriverWait(driver,3)).until(ExpectedConditions.presenceOfElementLocated(currentTabTitleLocator));
    	Assert.assertEquals(TabTitle.getText(), "Files List");
    	System.out.println("5th tab name is " + TabTitle.getText() + " .Pass!");    	
    }
    
    @Test //6th tab
    public void Tab6AsMentionsShouldPass() throws InterruptedException { 
    	driver.findElements(flexTabButtonsLocator).get(7).click(); 
    	Thread.sleep(3000);
    	WebElement TabTitle = (new WebDriverWait(driver,3)).until(ExpectedConditions.presenceOfElementLocated(currentTabTitleLocator));
    	Assert.assertEquals(TabTitle.getText(), "Mentions");
    	System.out.println("6th tab name is " + TabTitle.getText() + " .Pass!");    	
    }
    
    @Test //7th tab
    public void Tab7AsStarredMessagesShouldPass() throws InterruptedException { 
    	driver.findElements(flexTabButtonsLocator).get(8).click(); 
    	Thread.sleep(3000);
    	WebElement TabTitle = (new WebDriverWait(driver,3)).until(ExpectedConditions.presenceOfElementLocated(currentTabTitleLocator));
    	Assert.assertEquals(TabTitle.getText(), "Starred Messages");
    	System.out.println("7th tab name is " + TabTitle.getText() + " .Pass!");    	
    }
    
    @Test //8th tab
    public void Tab8AsPinnedMessagesShouldPass() throws InterruptedException { 
    	driver.findElements(flexTabButtonsLocator).get(10).click(); 
    	Thread.sleep(3000);
    	WebElement TabTitle = (new WebDriverWait(driver,3)).until(ExpectedConditions.presenceOfElementLocated(currentTabTitleLocator));
    	Assert.assertEquals(TabTitle.getText(), "Pinned Messages");
    	System.out.println("8th tab name is " + TabTitle.getText() + " .Pass!");    	
    }
    
}
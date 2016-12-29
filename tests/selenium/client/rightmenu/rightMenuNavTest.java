package testRocketChatPackage.rightmenu;
 
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

/*
 * This test checks that the user can switch between different page views for the right components
 * A test will fail if the expected view is not displayed
 */

     
public class rightMenuNavTest {
    public static WebDriver driver;     
 
    private static By usernameOrEmailFieldLocator = By.id("emailOrUsername");
    private static By passwordFieldLocator = By.id("pass");
    private static By loginButtonLocator = By.className("button primary login");
    private static By arrowButtonLocator = By.className("arrow bottom");
    private By channelsLocator = By.className("name");
    private By flexTabBarLocator = By.className("flex-tab-bar");
    private static By flexTabButtonsLocator = By.className("tab-button");
    private static By currentTabTitleLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section/div/div/div/h2");
    private static String tabTitle;
    
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
        driver.get("http://localhost:3000");
        //this tells the driver to wait for the username/email field element to load otherwise it will fail the test within 10 secs
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(usernameOrEmailFieldLocator));
        inputLoginFieldsWith("test", "test");
    }
    
    //Before
    @Before 
    public void getThePageWeWantToTest() {
   
        //Page Load
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(arrowButtonLocator));
        //Click on 1st channel
        driver.findElements(channelsLocator).get(0).click();
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(flexTabBarLocator));
    }
    
    //AfterClass
    @AfterClass //this will be called After ALL tests complete
    public static void closeDriver(){
 
    	driver.quit(); //closes driver and browser window
    }
    
    private static void clickOnMenuItem(int index) throws InterruptedException{
    	Thread.sleep(500);
    	driver.findElements(flexTabButtonsLocator).get(index).click(); 
    	Thread.sleep(500);
    	WebElement TabElement = (new WebDriverWait(driver,3)).until(ExpectedConditions.presenceOfElementLocated(currentTabTitleLocator));
    	tabTitle = TabElement.getText();
    }
    
    //Tests
    @Test //1st tab
    public void Tab1AsRoomInfoShouldPass() throws InterruptedException { 
    	clickOnMenuItem(1);
    	Assert.assertEquals(tabTitle, "Room Info");	
    }
    
    @Test //2nd tab
    public void Tab2AsSearchMessagesShouldPass() throws InterruptedException { 
    	clickOnMenuItem(2);
    	Assert.assertEquals(tabTitle, "Search Messages");  	
    }
    
    @Test //3rd tab
    public void Tab3AsMembersListShouldPass() throws InterruptedException { 
    	clickOnMenuItem(3);
    	Assert.assertEquals(tabTitle, "Members List"); 	
    }
    
    @Test //4th tab
    public void Tab4AsNotificationsShouldPass() throws InterruptedException { 
    	clickOnMenuItem(4);
    	Assert.assertEquals(tabTitle, "Notifications");  	
    }
    
    @Test //5th tab
    public void Tab5AsFilesListShouldPass() throws InterruptedException { 
    	clickOnMenuItem(4);
    	Assert.assertEquals(tabTitle, "Files List");   	
    }
    
    @Test //6th tab
    public void Tab6AsMentionsShouldPass() throws InterruptedException { 
    	clickOnMenuItem(4);
    	Assert.assertEquals(tabTitle, "Mentions"); 	
    }
    
    @Test //7th tab
    public void Tab7AsStarredMessagesShouldPass() throws InterruptedException { 
    	clickOnMenuItem(4);
    	Assert.assertEquals(tabTitle, "Starred Messages");  	
    }
    
    @Test //8th tab
    public void Tab8AsPinnedMessagesShouldPass() throws InterruptedException { 
    	clickOnMenuItem(4);
    	Assert.assertEquals(tabTitle, "Pinned Messages"); 	
    }
    
}
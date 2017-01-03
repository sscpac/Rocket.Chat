package testRocketChatPackage.rightmenu;
 
import java.util.concurrent.TimeUnit;

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
 
    private By channelsLocator = By.className("name");
    private static By channelLocation 			= By.cssSelector("a.open-room"); 
    private By flexTabBarLocator = By.className("flex-tab-bar");
    private static By currentTabTitleLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section/div/div/div/h2");
    private static String tabTitle;
    
    private static By roomInfoTab = By.xpath("//div[contains(@title, 'Room Info')]");
    private static By searchTab = By.xpath("//div[contains(@title, 'Search')]");
    private static By memberListTab = By.xpath("//div[contains(@title, 'Members List')]");
    private static By notificationTab = By.xpath("//div[contains(@title, 'Notifications')]");
    private static By filesTab = By.xpath("//div[contains(@title, 'Files List')]");
    private static By mentionsTab = By.xpath("//div[contains(@title, 'Mentions')]");
    private static By starredMsgTab = By.xpath("//div[contains(@title, 'Starred Messages')]");
    private static By pinnedMsgTab = By.xpath("//div[contains(@title, 'Pinned Messages')]");
    

    @BeforeClass //this means the method below will be called Once before this whole Class
    public static void setupTheDriverObject(){
        //driver setup with
    	driver = new SafariDriver();
    	driver.manage().window().maximize();
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	testRocketChatPackage.login.loginTest.login("test", "test", driver);
    	driver.findElement(channelLocation).click();
    }
    
//    @Before 
//    public void getThePageWeWantToTest() {
//        //Page Load
//        driver.findElement(channelLocation).click();
//        //new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(flexTabBarLocator));
//    }
    
    @AfterClass 
    public static void closeDriver(){
 
    	driver.quit(); 
    }
    
    private void openTab(By tab){
    	driver.findElement(tab).click(); 
    	tabTitle = driver.findElement(currentTabTitleLocator).getText();
    	System.out.println(tabTitle);
    }
    
    //Tests
    @Test 
    public void openRoomInfoTab() { 
    	openTab(roomInfoTab);
    	Assert.assertEquals(tabTitle, "Room Info");	
    }
    
    //@TODO for some odd issue tabTitle = Starred Messages
    @Test 
    public void openSearchMessagesTab() { 
    	openTab(searchTab);
    	Assert.assertEquals(tabTitle, "Search Messages");  	
    }
    
    @Test 
    public void openMemberListTab() { 
    	openTab(memberListTab);
    	Assert.assertEquals(tabTitle, "Members List"); 	
    }
    
    @Test
    public void openNotificationsTab() { 
    	openTab(notificationTab);
    	Assert.assertEquals(tabTitle, "Notifications");  	
    }
    
    @Test 
    public void shouldOpenFilesTab() { 
    	openTab(filesTab);
    	Assert.assertEquals(tabTitle, "Files List");   	
    }
    
    //@TODO for some odd issue tabTitle = Room Info
    @Test 
    public void openMentions() { 
    	openTab(mentionsTab);
    	Assert.assertEquals(tabTitle, "Mentions"); 	
    }
    
    @Test 
    public void openStarredMessages() { 
    	openTab(starredMsgTab);
    	Assert.assertEquals(tabTitle, "Starred Messages");  	
    }
    
    //@TODO for some odd issue tabTitle = Mentions
    @Test 
    public void openPinnedMessage()  { 
    	openTab(pinnedMsgTab);
    	Assert.assertEquals(tabTitle, "Pinned Messages"); 	
    }
    
}
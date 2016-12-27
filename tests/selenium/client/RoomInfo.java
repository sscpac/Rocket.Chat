/* ROOM INFORMATION  TEST
 * This program tests all the features of the "Room Info" menu 
 * 
 * The test is not complete.  
 * 
 */

package myPackage;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RoomInfo {

    public static WebDriver driver;
    //name: someName
    //username: someUserName
    //password: secretPassword123
    //email: validEmail@gmail.com

    public static String URL_CHATLOCKER_MAIN = "http://localhost:3000";
    public static String VALID_USERNAME = "someUserName";
    public static String VALID_PASSWORD = "secretPassword123";
    public static String VALID_EMAIL = "validEmail@gmail.com";
    public static String CHANNEL_NAME = "channelname";
    private By usernameOrEmailFieldLocator = By.id("emailOrUsername");
    private By passwordFieldLocator = By.id("pass");
    private By loginButtonLocator = By.className("button primary login");
    //only works if the channel is the first and only channel created
    
    private By channelLocator = By.xpath("//*[@id=\"rocket-chat\"]/aside/div[2]/div/ul[1]/li/a/span[1]");
    
    private By infoIconLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[2]/div[2]/button");
	
    
    //FOR CHANGING CHANNEL NAME
    //											 id('rocket-chat')/x:div[3]/x:div/x:div/x:section[2]/x:div/x:div/x:form/x:ul/x:li[1]/x:div/x:input
    private By nameLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[1]/div/span[1]");
    private By namePencilLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[1]/div/span[2]/i");
    private By nameFieldLocator =  By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[1]/div/input");
    private By nameCancelLocator =  By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[1]/div/button[1]");
    private By nameSaveLocator =  By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[1]/div/button[2]");
    
    //FOR CHANGING TOPIC
    private By topicFieldLocator =  By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[2]/div/input");
    private By topicCancelLocator =  By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[2]/div/button[1]");
    private By topicSaveLocator =  By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[2]/div/button[2]");
    
    
    private void inputLoginFieldsWith(String usernameOrEmail, String password){
        driver.findElement(usernameOrEmailFieldLocator).sendKeys(usernameOrEmail);
        driver.findElement(passwordFieldLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();    
    }
     
     
    @BeforeClass //this means the method below will be called Once before this whole Class
    public static void setupTheDriverObject(){
    	driver = new SafariDriver();
    }

     
    @Before
    public void navigateToRoomInfo(){
        driver.get(URL_CHATLOCKER_MAIN);

        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(usernameOrEmailFieldLocator));
        //login
        inputLoginFieldsWith(VALID_USERNAME, VALID_PASSWORD);
        //navigate to room info
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(channelLocator));
        driver.findElement(channelLocator).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(infoIconLocator));
        driver.findElement(infoIconLocator).click();
    }
    
    @AfterClass //this will be called After ALL tests complete
    public static void closeDriver(){
        driver.quit(); //closes driver and browser window
    }


    @Test //1  Changing Channel Name
    public void test1() { 
    	System.out.println("this is test 1 *************");
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(nameLocator));

    	String channelName = driver.findElement(nameLocator).getText();
   
    	if(channelName == "channelname")
    	{
    		System.out.println("The name is true!");
    	}
    	else
    	{
    		System.out.println("Name is:" + channelName + ".");
    	}
        
    	driver.findElement(namePencilLocator).click();
    	
    	//@TODO
    	//Get current value
    	
    	//Check value
    	
    	//Select edit
    	
    	//Cancel
    	
    	//Check value
    	
    	//Select edit
    	
    	//Change value
    	
    	//Save
    	
    	//Check value
    }
    /* 
    @TODO

    @Test //2 Topic
    
    @Test //3 Description
    
    @Test //4 Type
    
    @Test //5 Read Only
    
    @Test //6 Archived
    
    @Test //7 Code
    
    @Test //8 Search Message
    
    @Test //9 Delete Channel




    */
}



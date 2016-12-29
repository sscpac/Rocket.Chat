/* ROOM INFORMATION  TEST
 * This program tests all the features of the "Room Info" menu 
 * 
 * The test is not complete.  
 * 
 * NOTE:
 * only works if the channel is the first and only channel created
 */

package myPackage;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.Thread;


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
    public static String CHANNEL_NAME2 = "second_name";
    public static String TOPIC_1 = "topic1";
    public static String TOPIC_2 = "topic2";

    private By usernameOrEmailFieldLocator = By.id("emailOrUsername");
    private By passwordFieldLocator = By.id("pass");
    private By loginButtonLocator = By.className("button primary login");
    

    private By channelLocator = By.xpath("//*[@id=\"rocket-chat\"]/aside/div[2]/div/ul[1]/li/a/span[1]");
   	private By infoIconLocator = By.className("icon-info-circled");
    
    
    //FOR CHANGING CHANNEL NAME
	private By namePencilLocator = By.xpath("//*[@data-edit=\"name\"]");
    private By nameLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[1]/div/span[1]");
    private By nameCancelLocator =  By.xpath("//*[@class=\"button cancel\"]");
    private By nameSaveLocator =  By.xpath("//*[@class=\"button primary save\"]");
    private By nameFieldLocator = By.xpath("//*[@name=\"name\"]");
    
    //FOR CHANGING TOPIC
    private By topicPencilLocator = By.xpath("//*[@data-edit=\"topic\"]");
    private By topicLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[2]/div/span[1]");
    private By topicCancelLocator =  By.xpath("//*[@class=\"button cancel\"]");
    private By topicSaveLocator =  By.xpath("//*[@class=\"button primary save\"]");
    private By topicFieldLocator = By.xpath("//*[@name=\"topic\"]");

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

        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(usernameOrEmailFieldLocator));
        //login
        inputLoginFieldsWith(VALID_USERNAME, VALID_PASSWORD);
        //navigate to room info
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(channelLocator));
        driver.findElement(channelLocator).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(infoIconLocator));
        
    }
    
    @AfterClass 
    public static void closeDriver(){
        driver.quit(); 
    }

//Test 1 
    @Test //1  Tests full functionality of Changing the Name of the Channel
    public void test1() { 
        driver.findElements(infoIconLocator).get(1).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(nameLocator));
        
        //Get current Value
        String originalName = driver.findElement(nameLocator).getText();
        //SETS THE STARTING VALUE
        if(originalName != "channelname")
        {
        	try {
                Thread.sleep(1000);  //without using sleep Selenium will not wait for the pencils to load, even with the WebDriverWait               
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        	//Select edit
        	new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(namePencilLocator));
            driver.findElement(namePencilLocator).click();
        	
            //Change value
            new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(nameFieldLocator));
            driver.findElement(nameFieldLocator).sendKeys(CHANNEL_NAME);
            originalName = CHANNEL_NAME;
            
            //Save
        	driver.findElement(nameSaveLocator).click();
        }
        
        
        //Select edit
        try {
            Thread.sleep(1000);                 
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        //new WebDriverWait(driver, 15);
        driver.findElement(namePencilLocator).click();
    	//Cancel
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(nameCancelLocator));
    	driver.findElement(nameCancelLocator).click();
    	//Check value
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(nameLocator));
    	Assert.assertEquals(originalName, driver.findElement(nameLocator).getText());
    	
    	
    	//Select edit
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(namePencilLocator));
        driver.findElement(namePencilLocator).click();
        //Change value
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(nameFieldLocator));
        driver.findElement(nameFieldLocator).sendKeys(CHANNEL_NAME2);
        //Cancel
    	driver.findElement(nameCancelLocator).click();
    	//Check value
    	new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(nameLocator));
    	Assert.assertEquals(originalName, driver.findElement(nameLocator).getText());
    	
    	
    	//Select edit
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(namePencilLocator));
        driver.findElement(namePencilLocator).click();
        //Change value
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(nameFieldLocator));
        driver.findElement(nameFieldLocator).sendKeys(CHANNEL_NAME2);
        //Save
    	driver.findElement(nameSaveLocator).click();
    	//Check value
    	new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(nameLocator));
    	Assert.assertEquals(CHANNEL_NAME2, driver.findElement(nameLocator).getText());
    	
    	
    	//Set Channel Name back to original
    	//Select edit
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(namePencilLocator));
        driver.findElement(namePencilLocator).click();
        //Change value
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(nameFieldLocator));
        driver.findElement(nameFieldLocator).sendKeys(originalName);
        //Save
    	driver.findElement(nameSaveLocator).click();
    }
    

    @Test //2 Tests full functionality of Changing the Topic of the Channel
    public void test2() { 
        driver.findElements(infoIconLocator).get(1).click();
        
        //Select edit
        try { 
            Thread.sleep(2000);             
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    	new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicPencilLocator));
        driver.findElement(topicPencilLocator).click();
        //Change value
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicFieldLocator));
        driver.findElement(topicFieldLocator).sendKeys(TOPIC_1);
        //Save
    	driver.findElement(topicSaveLocator).click();
    	
    	
    	//Select edit
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicPencilLocator));
        driver.findElement(topicPencilLocator).click();
        //Cancel
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicCancelLocator));
    	driver.findElement(topicCancelLocator).click();
    	//Check value
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicLocator));
    	Assert.assertEquals(TOPIC_1, driver.findElement(topicLocator).getText());
    	
    	
    	//Select edit
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicPencilLocator));
        driver.findElement(topicPencilLocator).click();
        //Change value
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicFieldLocator));
        driver.findElement(topicFieldLocator).sendKeys(TOPIC_2);
        //Cancel
    	driver.findElement(topicCancelLocator).click();
    	//Check value
    	new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicLocator));
    	Assert.assertEquals(TOPIC_1, driver.findElement(topicLocator).getText());
    	
    	
    	//Select edit
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicPencilLocator));
        driver.findElement(topicPencilLocator).click();
        //Change value
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicFieldLocator));
        driver.findElement(topicFieldLocator).sendKeys(TOPIC_2);
        //Save
    	driver.findElement(topicSaveLocator).click();
    	//Check value
    	new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicLocator));
    	Assert.assertEquals(TOPIC_2, driver.findElement(topicLocator).getText());
    }
    
    //@TODO

    /*
    @Test //3 Description
    
    @Test //4 Type
    
    @Test //5 Read Only
    
    @Test //6 Archived
    
    @Test //7 Code
    
    @Test //8 Search Message
    
    @Test //9 Delete Channel




    */
}



/* Rocket Chat Test:: ROOM INFO
 * 
 * Description:
 * This program tests the features of the "Room Info" menu 
 * 
 * The test is not complete.  
 * 
 * NOTE:
 * only works if the channel is the first and only channel created
 */

package testRocketChatPackage.rightmenu;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.Thread;
import java.util.concurrent.TimeUnit;


public class RoomInfo {

    public static WebDriver driver;

    public static String URL_CHATLOCKER_MAIN = "http://localhost:3000";
    public static String VALID_USERNAME = "test";
    public static String VALID_PASSWORD = "test";
    public static String VALID_EMAIL = "validEmail@gmail.com";
    public static String CHANNEL_NAME = "first_name";
    public static String CHANNEL_NAME2 = "second_name";
    public static String CHANNEL_NAME3 = "third_name";
    public static String TOPIC_1 = "topic1";
    public static String TOPIC_2 = "topic2";
    public static String DESCRIPTION_1 = "description1";
    public static String DESCRIPTION_2 = "description2";
    public static String CODE_1 = "code1";
    public static String CODE_2 = "code2";
    public static String originalName = "defaultChannel";
    public static String originalChannelName = "defaultChannel";
    
    private static By usernameOrEmailFieldLocator = By.id("emailOrUsername");
    private static By passwordFieldLocator = By.id("pass");
    //private By loginButtonLocator = By.className("button primary login");
    private static By loginButtonLocator = By.cssSelector("button.primary.login");
    
    private static By addRoomElement = By.cssSelector("h3.add-room");
    private static By channelCreate = By.id("channel-name");
	private static By buttonCreate = By.cssSelector("button.primary.save-channel");
	private static By PopUpYes = By.className("confirm");
	private static By trashButton = By.cssSelector("button.button.danger.delete");
	private static By bannerNotifications = By.cssSelector("div.toast.toast-success");
    
    //FOR ALL TESTS
    //private By channelLocator = By.xpath("//*[@id=\"rocket-chat\"]/aside/div[2]/div/ul[1]/li/a/span[1]");
	private static By newChannelLocator = By.cssSelector("a.open-room");
    private static By infoIconLocator = By.className("icon-info-circled");
    private static By CancelLocator =  By.xpath("//*[@class=\"button cancel\"]");
    private static By SaveLocator =  By.xpath("//*[@class=\"button primary save\"]");
   	
    //FOR CHANGING CHANNEL NAME
	private static By namePencilLocator = By.xpath("//*[@data-edit=\"name\"]");
    private static By nameLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[1]/div/span[1]");
    private static By nameFieldLocator = By.xpath("//*[@name=\"name\"]");
    
    //FOR CHANGING TOPIC
    private static By topicPencilLocator = By.xpath("//*[@data-edit=\"topic\"]");
    private static By topicLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[2]/div/span[1]");
    private static By topicFieldLocator = By.xpath("//*[@name=\"topic\"]");
    
    //FOR CHANGING DESCRIPTION
    private static By descriptionPencilLocator = By.xpath("//*[@data-edit=\"description\"]");
    private static By descriptionLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[3]/div/span[1]");
    private static By descriptionFieldLocator = By.xpath("//*[@name=\"description\"]");
    
    //FOR CHANGING TYPE
    private static By typePencilLocator = By.xpath("//*[@data-edit=\"t\"]");
    private static By typeLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[4]/div/span[1]");
    private static By typeFieldLocator = By.xpath("//*[@name=\"t\"]");
    
    //FOR CHANGING READ ONLY
    private static By readonlyPencilLocator = By.xpath("//*[@data-edit=\"ro\"]");
    private static By readonlyLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[5]/div/span[1]");
    private static By readonlyFieldLocator = By.xpath("//*[@name=\"ro\"]");
    
    //FOR CHANGING ARCHIVED
    private static By archivedPencilLocator = By.xpath("//*[@data-edit=\"archived\"]");
    private static By archivedLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[6]/div/span[1]");
    private static By archivedFieldLocator = By.xpath("//*[@name=\"archived\"]");
    
    //FOR CHANGING CODE
    private static By codePencilLocator = By.xpath("//*[@data-edit=\"joinCode\"]");
    private static By codeLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/div/div/section[2]/div/div/form/ul/li[7]/div/span[1]");
    private static By codeFieldLocator = By.xpath("//*[@name=\"joinCode\"]");
    
    
    
    private static void inputLoginFieldsWith(String usernameOrEmail, String password){
    	
        driver.findElement(usernameOrEmailFieldLocator).sendKeys(usernameOrEmail);
        driver.findElement(passwordFieldLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();
    }
     
    /*
     * TEST Order
     * 
     * 1. Changing Channel Name
     *  a. Name Change 1
     *  b. Name Change 2
     *  c. Name Change 3
     *  d. Name Change to Original
     * 
     * 2. Changing Channel Topic
     *  a. Topic Change 1
     *  b. Topic Change 2
     * 
     * 3. Changing Channel Description
     *  a. Description Change 1
     *  b. Description Change 2
     * 
     * @TODO
     * 4. Changing Channel Type
     * 5. Changing Channel Read Only Option
     * 6. Changing Channel Archived Option
     * 7. Changing Channel Entry Code
     * 
     */
    
     
    @BeforeClass 
    public static void setupTheDriverObject() throws Exception{
    	driver = new SafariDriver();
    	
		// Sets webdriver to Chrome
//		System.setProperty(
//				"webdriver.chrome.driver",
//				"/home/osboxes/Documents/Selenium Library/chromedriver.exe"
//				);
//	
//		driver = new ChromeDriver(); 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(URL_CHATLOCKER_MAIN);
        //new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(usernameOrEmailFieldLocator));
		inputLoginFieldsWith(VALID_USERNAME, VALID_PASSWORD);
		
    	//new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(addRoomElement));
        createChannel(originalChannelName);
        
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(newChannelLocator));
        driver.findElement(newChannelLocator).click();
        
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(infoIconLocator));
        driver.findElements(infoIconLocator).get(1).click();

    }

     
//    @Before
//    public void checksForCancelButton() {
//    	//Tests the Cancel Button to make sure nothing has been changed if cancel is pressed
//    	if (detectElement(CancelLocator) == true) {
//            new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(CancelLocator));
//        	driver.findElement(CancelLocator).click();
//    	}
//    }
    
//    @After	//Everything works but slows down the tests BY A LOT
//    public void checksForCancelButton() {
//    	if (detectElement(CancelLocator) == true) {
//    		driver.findElement(CancelLocator).click();
//    	}
//    }
    
    @AfterClass 
    public static void closeDriver() throws Exception{
    	//Deletes the channel
    	driver.findElement(trashButton).click();
		Thread.sleep(1500);
		driver.findElement(PopUpYes).click();
		Thread.sleep(2500);
		
		//Closes and quits the session
    	driver.close();
    	driver.quit(); 
    }
    
	private static boolean detectElement(By element) {
		
		boolean Elementdetected = driver.findElements(element).size() > 0;
		if (Elementdetected) {
			return true;
		}
		else {
			return false;
		}	
	}
    
    private static void createChannel(String channelName) throws Exception {
    	driver.findElement(addRoomElement).click();
    	Thread.sleep(1000);
    	
    	new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(channelCreate));
    	driver.findElement(channelCreate).sendKeys(channelName);
		driver.findElement(buttonCreate).click();
    	
    }
    
    private static void channelNameChange(String channelName) throws Exception{ 
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(nameLocator));
        
        //Get current Value
        originalName = driver.findElement(nameLocator).getText();
		
        if (originalName != channelName) {
	    	Thread.sleep(1000);
	    	//Select edit
	    	new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(namePencilLocator));
	        driver.findElement(namePencilLocator).click();
	        //Change value
	        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(nameFieldLocator));
	        driver.findElement(nameFieldLocator).clear();
	        driver.findElement(nameFieldLocator).sendKeys(channelName);
	        originalName = channelName;

	        //Save
	        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(SaveLocator));
        	driver.findElement(SaveLocator).click();
	        //driver.findElement(By.id("Value")).sendKeys(Keys.RETURN);
        	Thread.sleep(800);
        	
        }
    }
    
    private static void channelTopicChange(String topicName) {
    	//Select edit
    	new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicPencilLocator));
        driver.findElement(topicPencilLocator).click();
        //Change value
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicFieldLocator));
        driver.findElement(topicFieldLocator).clear();
        driver.findElement(topicFieldLocator).sendKeys(topicName);
        //Save
    	driver.findElement(SaveLocator).click();
    	
    }
    
    private static void channelDescriptionChange(String description) throws Exception {
    	//Select edit
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(descriptionPencilLocator));
        driver.findElement(descriptionPencilLocator).click();
        //Change value
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(descriptionFieldLocator));
        driver.findElement(descriptionFieldLocator).clear();
        driver.findElement(descriptionFieldLocator).sendKeys(description);
        //Save
    	driver.findElement(SaveLocator).click();
    	Thread.sleep(800);
    	
    }
        

    @Test //1  Tests full functionality of Changing the Name of the Channel
    public void nameChange1() throws Exception {
    	channelNameChange(CHANNEL_NAME);
        System.out.println(originalName);
    	Assert.assertEquals(CHANNEL_NAME, driver.findElement(nameLocator).getText());
    }
    	
    @Test
    public void nameChange2() throws Exception {
    	channelNameChange(CHANNEL_NAME2);
        System.out.println(originalName);
    	Assert.assertEquals(CHANNEL_NAME2, driver.findElement(nameLocator).getText());
    }
    @Test
    public void nameChange3() throws Exception{
    	channelNameChange(CHANNEL_NAME3);
        System.out.println(originalName);
    	Assert.assertEquals(CHANNEL_NAME3, driver.findElement(nameLocator).getText());
    }
    	
    @Test
    public void nameChangeOriginal() throws Exception{
    	channelNameChange(originalChannelName);
        System.out.println(originalName);
    	Assert.assertEquals(originalChannelName, driver.findElement(nameLocator).getText());
    }
    

    @Test //2 Tests full functionality of Changing the Topic of the Channel
    public void topicChange1() {
    	channelTopicChange(TOPIC_1);
    	//Check value
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicLocator));
    	Assert.assertEquals(TOPIC_1, driver.findElement(topicLocator).getText());
    }
//   
    @Test
    public void topicChange2() {  	  	
    	channelTopicChange(TOPIC_2);
    	//Check value
    	new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(topicLocator));
    	Assert.assertEquals(TOPIC_2, driver.findElement(topicLocator).getText());
    }
    
    
    @Test //3 Tests full functionality of Changing the Description of the Channel
    public void descriptionChange1() throws Exception {  
    	channelDescriptionChange(DESCRIPTION_1);
    	//Check value
    	//new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(descriptionLocator));
    	Assert.assertEquals(DESCRIPTION_1, driver.findElement(descriptionLocator).getText());
    }
    
    @Test
    public void descriptionChange2() throws Exception {
    	channelDescriptionChange(DESCRIPTION_2);
    	//Check value
    	new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(descriptionLocator));
    	Assert.assertEquals(DESCRIPTION_2, driver.findElement(descriptionLocator).getText());
    }
    
//    @Test
//    public void typeChange() {
//    	
//    	
//    	
//    }
//    
//    @Test
//    public void readonlyChange() {
//    	
//    	
//    	
//    }
//    
//    @Test
//    public void archivedChange() {
//    	
//    	
//    	
//    }
//    
//    @Test
//    public void codeChange() {
//    	
//    	
//    	
//    }
    /*@TODO
    @Test //4 Type
    
    @Test //5 Read Only
    
    @Test //6 Archived
    
    @Test //7 Code
    
    */
}

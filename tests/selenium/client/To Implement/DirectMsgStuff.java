package myPackages;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.bcel.generic.Select;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.Assert;



public class DirectMsgStuff {
	
	
	public static WebDriver driver;

	public static String Home = "http://localhost:3000/channel/general";
	public static String loginUser = "adrian";	//"sonia.tran@sage.northcom.mil"
	public static String loginPW = "adrian";	//"soniatran"
	
	public static String ValidFriend = "rocket.cat";
	public static String InvalidFriend = "Santa";
	
	
	private static By userName = By.id("emailOrUsername");
	private static By password = By.id("pass");
	private static By loginButton = By.cssSelector("button");
	
	private static By addRoomElement = By.className("add-room");
	private static By findFriendTextField = By.id("who");
	private static By buttonCancel = By.cssSelector("button.button.cancel-direct-message");
	private static By buttonCreate = By.cssSelector("button.button.primary.save-direct-message");
	private static By buttonMoreDMsg = By.cssSelector("button.more.more-direct-messages");
	private static By SearchDM = By.id("channel-search");
	private static By sortOption = By.cssSelector("select#sort.c-select");
	private static By foundfriendDM = By.cssSelector("a.channel-link");
	private static By textFriend = By.cssSelector("textarea.input-message.autogrow-short");
	private static By sendMsgButt = By.cssSelector("i.icon-paper-plane");
	private static By panelLeft = By.cssSelector("div.rooms-list");
	private static By autoCompleteOption = By.cssSelector("div.-autocomplete-container");
	
	private static By roomTitleLocator = By.className("room-title");
	private static By closeDMButtonLocator = By.className("arrow close");
	private static By DMselector = By.cssSelector("a.open-room");
	
/*TEST Order:
 * 
 * Creating and Sending Direct Messages
 * 1. Valid Friend
 * 2. Invalid Friend
 * 3. More Direct Messages Option
 */
	
	private static boolean detectElement(By element) {
		
		boolean ElementDetected = driver.findElements(element).size() > 0;
		if (ElementDetected) {
			return true;
		}
		else {
			return false;
		}
	}

	@BeforeClass
	public static void setupTest() throws Exception{
		
//		System.setProperty(
//				"webdriver.chrome.driver",
//				"/home/osboxes/Documents/Selenium Library/chromedriver.exe"
//				);
//
//
//		driver = new ChromeDriver();
		
		driver = new SafariDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		driver.get(Home);
		//driver.manage().window().maximize();
		
		driver.findElement(userName).sendKeys(loginUser);
		driver.findElement(password).sendKeys(loginPW);
		driver.findElement(loginButton).click();
		//Thread.sleep(500);
		//new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(addRoomElement));
		
		
	}
	
	@Before
	public void checkDMMenu() {
		
		//driver.get(Home);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(addRoomElement));

	}
	
	@After
	public void resetFromPreviousTest(){
		//do this incase of a fail to close the menu from a previous test
		if(detectElement(closeDMButtonLocator)){
			driver.findElement(closeDMButtonLocator).click();
		}
	}
	
	private void searchInDM(String user) throws Exception{
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(panelLeft));
		driver.findElement(buttonMoreDMsg).click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(SearchDM));
		//driver.findElement(sortOption).click();
		driver.findElement(SearchDM).clear();
		driver.findElement(SearchDM).sendKeys(user);	
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(foundfriendDM));
		Thread.sleep(1000);
		driver.findElement(foundfriendDM).click();
		
//		int size = driver.findElements(DMselector).size();
//		
//		for(int i = 0; i <= size; i++){
//			if(driver.findElements(DMselector).get(i).getAttribute("title") == user){
//				driver.findElements(DMselector).get(i).click();
//				break;
//			}
//		}
	}
	
	private void sendDMTo(String user, String message) throws Exception{
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(panelLeft));
		driver.findElement(buttonMoreDMsg).click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(SearchDM));
		//driver.findElement(sortOption).click();
		driver.findElement(SearchDM).clear();
		driver.findElement(SearchDM).sendKeys(user);	
		//Thread.sleep(2000);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(foundfriendDM));
		driver.findElement(foundfriendDM).click();	
		driver.findElement(textFriend).sendKeys(message);
		driver.findElement(sendMsgButt).click();
		
	}
	
	private void searchForFriend(String searchForText) throws Exception {
		//Click on Add DM
		WebElement dmElement = driver.findElements(addRoomElement).get(1);
		dmElement.click();
		
		//search for the friend
		driver.findElement(findFriendTextField).clear();
		driver.findElement(findFriendTextField).sendKeys(searchForText);
		//Thread.sleep(3000);
		//click otherwise enter
		//driver.findElement(By.id("Value")).sendKeys(Keys.ENTER);
		driver.findElement(autoCompleteOption).click();
//		if(detectElement(autoCompleteOption)){
//			driver.findElement(autoCompleteOption).click();
//		}else{
//			driver.findElement(By.id("Value")).sendKeys(Keys.RETURN);
//		}
		driver.findElement(buttonCreate).click();
		
	}
	
	
	@Test // 1
	public void findFriend() throws Exception {
		
		//new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(addRoomElement));
		//WebElement dmElement = driver.findElements(addRoomElement).get(1);
		//dmElement.click();
		//driver.findElement(findFriendTextField).sendKeys(ValidFriend);
		
//		if (detectElement(autoCompleteOption)) {
//			
//			driver.findElement(autoCompleteOption).click();
//			
//		}
//		else {
//			
//			driver.findElement(By.id("Value")).sendKeys(Keys.RETURN);
//			
//		}
//		
//		driver.findElement(buttonCreate).click();
		searchForFriend(ValidFriend);
		Thread.sleep(2000);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(roomTitleLocator));
		String actualRoomTitle = driver.findElement(roomTitleLocator).getAttribute("innerText");
		System.out.println(actualRoomTitle);
		Assert.assertEquals(actualRoomTitle, ValidFriend);
		//driver.findElement(textFriend).sendKeys("Hi " + ValidFriend + ", can I have straight A's for Christmas?");
		//driver.findElement(sendMsgButt).click();
		
		//System.out.println("You have successfully messaged " + ValidFriend + ".");
		
	}
	
	@Test // 2
	public void noFriendByThatName() throws Exception {
		
		//String InvalidFriend = UUID.randomUUID().toString().substring(0, 8);
//		driver.findElement(findFriendTextField).sendKeys(InvalidFriend);
//		driver.findElement(buttonCreate).click();
//		Thread.sleep(2000);
		
//		driver.findElement(buttonCancel).click();
		
		//Detects if "Cancelling" goes back to the page where they can can choose from Channels/DMs
//		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(addRoomElement));
		
//		System.out.println(InvalidFriend + " could not be found.");
		searchForFriend(InvalidFriend);
		String actualSearchResult = driver.findElement(autoCompleteOption).getText();
		System.out.println(actualSearchResult);
		Assert.assertThat(actualSearchResult, CoreMatchers.containsString("Nothing found."));
		driver.findElement(closeDMButtonLocator).click();
	}
	
	@Test // 3
	public void searchThroughDM() throws Exception{
		
		searchInDM(ValidFriend);
		
		//new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(panelLeft));
		//driver.findElement(buttonMoreDMsg).click();
		
		//Thread.sleep(1000);
		
		//new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(sortOption));
		//driver.findElement(sortOption).click();
		
		//driver.findElement(SearchDM).sendKeys(ValidFriend);		
		
		//Thread.sleep(1000);
		
		//new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(foundfriendDM));
		//driver.findElement(foundfriendDM).click();
		
		//driver.findElement(textFriend).sendKeys("Found you " + ValidFriend + "!");
		//driver.findElement(sendMsgButt).click();
		
		//Thread.sleep(3000);
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(roomTitleLocator));
		String actualRoomTitle = driver.findElement(roomTitleLocator).getAttribute("innerText");
		System.out.println(actualRoomTitle);
		Assert.assertEquals(actualRoomTitle, ValidFriend);
	}
	
	@Test //4	//need to somehow add Assert 
	public void sendDMToFriend() throws Exception{
		sendDMTo(ValidFriend, "I found you!");
//		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(roomTitleLocator));
//		String actualRoomTitle = driver.findElement(roomTitleLocator).getAttribute("innerText");
//		System.out.println(actualRoomTitle);
//		Assert.assertEquals(actualRoomTitle, ValidFriend);
	}
	
	
	@AfterClass
	public static void endTesting(){
		
		driver.close();
		driver.quit();
	}
	
	
}

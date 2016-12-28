package testRocketChatPackage;

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



public class DirectMessagesSuite {
	
	
	public static WebDriver driver;

	public static String Home = "http://localhost:3000/channel/general";
	public static String loginUser = "test";	
	public static String loginPW = "test";	
	
	public static String ValidFriend = "rocket.cat";
	public static String InvalidFriend = "Santa";
	
	
	private static By userName = By.id("emailOrUsername");
	private static By password = By.id("pass");
	private static By loginButton = By.cssSelector("button");
	
	private static By addRoomElement = By.className("add-room");
	private static By findFriendTextField = By.id("who");
	private static By buttonCancel = By.cssSelector("button.button.cancel-direct-message");
	private static By buttonCreate = By.cssSelector("button.button.primary.save-direct-message");
	//private static By buttonMoreDMsg = By.cssSelector("button.more.more-direct-messages");
	private static By buttonMoreDMsg = By.xpath("//*[@id=\"rocket-chat\"]/aside/div[2]/div/ul[2]/button");
	
	//private static By SearchDM = By.id("channel-search");
	private static By SearchDM = By.cssSelector("input#channel-search.search");
	
	//private static By sortOption = By.cssSelector("select#sort.c-select");
	private static By foundfriendDM = By.cssSelector("a.channel-link");
	private static By textFriend = By.cssSelector("textarea.input-message.autogrow-short");
	private static By sendMessageButton = By.cssSelector("i.icon-paper-plane");
	private static By panelLeft = By.cssSelector("div.rooms-list");
	private static By autoCompleteOption = By.cssSelector("div.-autocomplete-container");
	
	private static By roomTitleLocator = By.className("room-title");
	//private static By closeDMButtonLocator = By.className("arrow.close");	//DOESN'T WORK - Just use buttonCancel
	private static By createRoomOptions = By.cssSelector("a.open-room");
	
/*TEST Order:
 * 
 * Creating and Sending Direct Messages
 * 1. Find Valid Friend
 * 2. Invalid Friend (No Friend by that Name)
 * 3. Search through More Direct Messages
 * 4. Send Direct Message to Friend
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
	
	private static void clicksAchannelToMakeDirectMessagesNotActive() {
		
		WebElement firstChannel = driver.findElements(createRoomOptions).get(0);
		firstChannel.click();
		
	}
	
	private void sendDMTo(String user, String message) throws Exception{
		
		driver.findElement(buttonMoreDMsg).click();
		
		Thread.sleep(2000);
		//new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(SearchDM));
		//driver.findElement(sortOption).click();	//An OPTION, but not yet bothered with
		driver.findElement(SearchDM).clear();
		driver.findElement(SearchDM).sendKeys(user);
		
		Thread.sleep(1000);
		//new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(foundfriendDM));
		driver.findElement(foundfriendDM).click();	
		driver.findElement(textFriend).sendKeys(message);
		driver.findElement(sendMessageButton).click();
		
	}
	
	private void searchForFriend(String searchForText) throws Exception {
		//Click on Add DM
		WebElement dmElement = driver.findElements(addRoomElement).get(1);
		dmElement.click();
		Thread.sleep(1500);
		
		//search for the friend
		driver.findElement(findFriendTextField).clear();
		driver.findElement(findFriendTextField).sendKeys(searchForText);
		Thread.sleep(1000);
		
		if(detectElement(autoCompleteOption)){
			driver.findElement(autoCompleteOption).click();
		}
		else{
			driver.findElement(By.id("Value")).sendKeys(Keys.RETURN);
		}
		driver.findElement(buttonCreate).click();
		
	}

	private void searchInDM(String user) throws Exception{
		
		//new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(panelLeft));
		driver.findElement(buttonMoreDMsg).click();
		
		//new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(SearchDM)); //Doesn't work so Thread.sleep is used for now
		Thread.sleep(2000);
		driver.findElement(SearchDM).clear();
		driver.findElement(SearchDM).sendKeys(user);	
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(foundfriendDM));
		Thread.sleep(1000);
		driver.findElement(foundfriendDM).click();
	}

	@BeforeClass
	public static void setupTest() throws Exception{
		
		System.setProperty(
				"webdriver.chrome.driver",
				"/home/osboxes/Documents/Selenium Library/chromedriver.exe"
				);

		driver = new ChromeDriver();
		
//		driver = new SafariDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		driver.get(Home);
		//driver.manage().window().maximize();
		
		driver.findElement(userName).sendKeys(loginUser);
		driver.findElement(password).sendKeys(loginPW);
		driver.findElement(loginButton).click();
		//Thread.sleep(500);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(addRoomElement));
		
		
	}
	
	@Before
	public void checkDMMenu() throws Exception {
		
//		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(panelLeft));
		clicksAchannelToMakeDirectMessagesNotActive();
		Thread.sleep(1000);

	}
	
	@Test // 1
	public void findFriend() throws Exception {

		searchForFriend(ValidFriend);
		Thread.sleep(2000);
		//new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(roomTitleLocator)); //DOES NOT WORK - Use Thread.sleep for now
		String actualRoomTitle = driver.findElement(roomTitleLocator).getAttribute("innerText");
		System.out.println(actualRoomTitle);
		Assert.assertEquals(actualRoomTitle, ValidFriend);
		
	}
	
	@Test // 2
	public void noFriendByThatName() throws Exception {
		
		searchForFriend(InvalidFriend);
		String actualSearchResult = driver.findElement(autoCompleteOption).getText();
		System.out.println(actualSearchResult);
		Assert.assertThat(actualSearchResult, CoreMatchers.containsString("Nothing found."));
		driver.findElement(buttonCancel).click();
		
	}
	
	@Test // 3
	public void searchThroughDM() throws Exception{
		
		searchInDM(ValidFriend);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(roomTitleLocator));
		String actualRoomTitle = driver.findElement(roomTitleLocator).getAttribute("innerText");
		System.out.println(actualRoomTitle);
		Assert.assertEquals(actualRoomTitle, ValidFriend);
		
	}
	
	@Test //4	//need to somehow add Assert 
	public void sendDMToFriend() throws Exception{
		
		sendDMTo(ValidFriend, "I found you!");
		//Assert.assertEquals(actualRoomTitle,  ValidFriend);
		
	}
	
//	@After
//	public void resetFromPreviousTest(){
//		//do this incase of a fail to close the menu from a previous test
//		if(detectElement(buttonCancel)){
//			driver.findElement(buttonCancel).click();
//		}
//		System.out.println("Bye");
//	}
	
	@AfterClass
	public static void endTesting(){

		driver.close();
		driver.quit();
	}
	
	
}

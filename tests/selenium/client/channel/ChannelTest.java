package testRocketChatPackage;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.Assert;




public class ChannelTest {


	public static WebDriver driver;

	public static String Home = "http://localhost:3000/channel/general";
	public static String loginUser = "test";
	public static String loginPW = "test";
	
	public static String InvalidChannelName1 = ("North Pole");	// Invalid case when name has spaces
	public static String InvalidChannelName2 = ("North,Pole");	// Invalid case when name has a symbol
	public static String InvalidChannelName3 = (" ");			// Invalid case when name is not present
	public static String ValidFriend1 = ("rocket.cat");
	public static String ValidFriend2 = ("test");				// Users that will already be created 			
	
	static String uniqueChannelName = "general";
	static String uniqueFriendName = "Default_Friend";
	
	private static By userName = By.id("emailOrUsername");
	private static By password = By.id("pass");
	private static By loginButton = By.cssSelector("button");
	
	private static By arrowButton = By.xpath("//*[@class=\"arrow bottom\"]");
	private static By buttonLogout = By.className("icon-logout");
	
	private static By addRoomElementActive = By.cssSelector("h3.add-room.active");
	//private static By addRoomElementActive = By.xpath("//*[@id=\"rocket-chat\"]/aside/div[2]/div/h3[1]");
	
	private static By channelCreate = By.id("channel-name");
	//private static By channelCreate = By.cssSelector("input#channel-name.required");
	private static By channelUsers = By.id("channel-members");
	private static By buttonCreate = By.cssSelector("button.primary.save-channel");
	private static By buttonCancel = By.cssSelector("button.cancel-channel");
	private static By channelLocation = By.cssSelector("a.open-room"); 
	private static By moreChannels = By.cssSelector("button.more.more-channels");
	
	private static By allChannels = By.cssSelector("button.button.all");
	//private static By allChannels = By.xpath("//*[@id=\"rocket-chat\"]/aside/div[4]/section/footer/div/button");
	
	private static By optionPrivacy = By.id("channel-type");
	private static By optionReadOnly = By.id("channel-ro");
	private static By roomInfoSettings = By.className("current-setting");
	
	private static By channelSearch = By.cssSelector("input#channel-search.search");
	private static By channelFound = By.cssSelector("a.channel-link");
	private static By autoCompleteOption = By.cssSelector("div.-autocomplete-container");
	
	private static By textFriend = By.cssSelector("textarea.input-message.autogrow-short");
	private static By sendMessageButton = By.cssSelector("i.icon-paper-plane");
	
	private static By leaveArrow = By.cssSelector("i.icon-logout.leave-room");
	private static By PopUpWindow = By.cssSelector("div.sweet-alert.showSweetAlert.visible");
	private static By PopUpYes = By.className("confirm");
	private static By buttonOK = By.cssSelector("button.confirm");
	
	private static By leftPanel = By.cssSelector("div.rooms-list");
	private static By rightPanelOptions = By.cssSelector("div.tab-button");
	private static By rightPanelClose = By.cssSelector("div.tab-button.active");
	private static By trashButton = By.cssSelector("button.button.danger.delete");

	
	
	private static boolean detectElement(By element) {
		
		boolean Elementdetected = driver.findElements(element).size() > 0;
		if (Elementdetected) {
			return true;
		}
		else {
			return false;
		}	
	}
	
	private static void waitForLeftPanelToLoadToClickCreateChannel() {
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(addRoomElementActive));
		WebElement channelElement = driver.findElement(addRoomElementActive);
		channelElement.click();
	}
	
	private static void generateUniqueName(){
		
		uniqueChannelName = UUID.randomUUID().toString().substring(0, 8);
		uniqueFriendName = UUID.randomUUID().toString().substring(0, 8);
	}
	
	private static void createChannelWithUser(String channelName, String friendName, String privateGroup, String readOnly) throws Exception {
		
		//Will generate and assign names according to the input keywords
		if ((channelName == "unique") && (friendName == "unique")) {
			generateUniqueName();
		}
		else if (channelName == "unique") {
			generateUniqueName();
			uniqueFriendName = friendName;
		}
		else if (friendName == "unique") {
			generateUniqueName();
			uniqueChannelName = channelName;
		}
		else if (friendName == "multiple") {
			generateUniqueName();
			searchAddUsers(ValidFriend1, ValidFriend2);
		}
		else {
			uniqueChannelName = channelName;
			uniqueFriendName = friendName;
		}
		
		//Thread.sleep(1000);
		clicksAchannelToMakeItActiveIfNot();
		Thread.sleep(500);
		waitForLeftPanelToLoadToClickCreateChannel();
		Thread.sleep(500);

		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(channelCreate));
		
		//Will check the Private and/or Read Only option depending on input keywords
		if (privateGroup == "yes") {
			driver.findElement(optionPrivacy).click();
		}
		if (readOnly == "yes") {
			driver.findElement(optionReadOnly).click();
		}
			
			driver.findElement(channelCreate).sendKeys(uniqueChannelName);
			driver.findElement(channelUsers).sendKeys(uniqueFriendName);
			driver.findElement(buttonCreate).click();
	}
	
	private static void searchAddUsers(String friend1, String friend2) throws Exception{
		
		WebElement Users = driver.findElement(channelUsers);
		
		Users.clear();
		Users.sendKeys(friend1);
		//Thread.sleep(1000);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(autoCompleteOption));
		driver.findElement(autoCompleteOption).click();
		
		Users.clear();
		Users.sendKeys(friend2);
		//Thread.sleep(1000);
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(autoCompleteOption));
		driver.findElement(autoCompleteOption).click();
		
		System.out.println(friend1 + " and " + friend2 + " have been added.");
	}
	
	private static void creatingValidChannels(String validChannelName, String friendName, String privateGroup, String readOnly) throws Exception {
		
		createChannelWithUser("unique", ValidFriend1, privateGroup, readOnly);
		System.out.println("Channel " + uniqueChannelName + " has been created.");
	}
	
	private static void creatingInvalidChannels(String invalidChannelName, String friendName, String privateGroup, String readOnly) throws Exception {
		
		createChannelWithUser(invalidChannelName, friendName, privateGroup, readOnly);
		invalidChannelClickCancel(invalidChannelName);
	}
	
	private static void invalidChannelClickCancel(String channelName) throws Exception {
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(buttonCancel));
		driver.findElement(buttonCancel).click();
		System.out.println(channelName + " is not a valid channel name.");
	}
	
	private static void typeChannelNameWhenSearchChannelPanelIsOpened(String channelName) throws Exception {
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(channelSearch));
		driver.findElement(channelSearch).sendKeys(channelName);
		Thread.sleep(1500);
		driver.findElement(channelFound).click();
		
	}
	
	private static void searchChannelsUsingMoreChannels(String channelName) throws Exception {

		waitForLeftPanelToLoadToClickCreateChannel();
		Thread.sleep(2000);
		
		driver.findElement(moreChannels).click();
		//Thread.sleep(2000);
		
		typeChannelNameWhenSearchChannelPanelIsOpened(channelName);
		
	}
	
	private static void searchChannelsUsingAllChannels(String channelName) throws Exception {
		
		//waitForLeftPanelToLoadToClickCreateChannel();
		Thread.sleep(1500);
		driver.findElement(addRoomElementActive).click();
		Thread.sleep(1500);
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(allChannels));
		driver.findElement(allChannels).click();
		
		typeChannelNameWhenSearchChannelPanelIsOpened(channelName);
		
	}
	
	private static void searchChannelsUsingLeftPanelListOfChannels(String channelName) {
		
		waitForLeftPanelToLoadToClickCreateChannel();
		int size = driver.findElements(channelLocation).size();
		//System.out.print(size);
		int i;
		
		for (i = 0; i < size; i++) {
			WebElement channelArrayElement = driver.findElements(channelLocation).get(i);
			String channelAttribute = channelArrayElement.getAttribute("title");
			if (channelAttribute == channelName) {
				channelArrayElement.click();
				break;
			}	
		}	
	}
	
	private static void clicksAchannelToMakeItActiveIfNot() {
		
		if (!(detectElement(addRoomElementActive))) {
			WebElement firstChannel = driver.findElement(channelLocation);
			firstChannel.click();
		}
	}
	
	public void LeaveOrDeleteChannel() throws Exception {
		
		//Leave Channel - Must NOT be the OWNER of the channel 
//		WebElement channelLeaveIcon = driver.findElements(leaveArrow).get(0);
//		channelLeaveIcon.click();
//		
//		driver.findElement(PopUpYes).click();
//		
//		
		//Window will pop up if you are the OWNER of the channel
//		if (detectElement(PopUpWindow)) {
//			
//			driver.findElement(buttonOK).click();
//		
//			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(rightPanelOptions));	
		
		
			//Delete Channel - Must be the OWNER to delete a channel
			WebElement infoIconButt = driver.findElements(rightPanelOptions).get(1);
			infoIconButt.click();
			Thread.sleep(1000);
			
			driver.findElement(trashButton).click();
			Thread.sleep(1500);
			
			driver.findElement(PopUpYes).click();
			Thread.sleep(2500);
			
			driver.findElement(rightPanelClose).click();
			System.out.print("Channel has been deleted.");
//		}
	}
	
	
/*
 * TEST Table of Contents:
 * 
 * 
 * CREATE CHANNEL (CHANNEL NAME / FRIEND NAME):
 * ** A Valid Channel name consists of ONLY letters, numbers, hyphens, and underscores**
 * 
 * 1. Valid / Valid
 * 2. Valid / Invalid
 * 3. Invalid1 / Valid
 * 4. Invalid1 / Invalid
 * 5. Invalid2 / Valid
 * 6. Invalid2 / Invalid
 * 7. Invalid3 / Valid
 * 8. Invalid3 / Invalid
 * 9. Duplicated Channel (Invalid)	@TODO - Creates an ERROR that will make all the next tests fail
 * 10. Creating Valid Channel with Multiple Users
 * 
 * ***NOTE: Invalid USER will STILL create the channel if the channel name is valid.***
 * 
 * 
 * Channel Search and Messaging
 * 
 * 11. Method 1
 * 12. Method 2
 * 13. Method 3
 * 
 * Creating Different Channel Types
 * 
 * 14. Private Group
 * 15. Read Only
 * 
 */

	
	@BeforeClass
	public static void setupTest() throws Exception{
		
		// Sets webdriver to Chrome
		System.setProperty(
				"webdriver.chrome.driver",
				"/home/osboxes/Documents/Selenium Library/chromedriver.exe"
				);
	
		driver = new ChromeDriver(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		// Logging In
		driver.get(Home);
		//driver.manage().window().maximize();
		driver.findElement(userName).sendKeys(loginUser);
		driver.findElement(password).sendKeys(loginPW);
		driver.findElement(loginButton).click();
		//Thread.sleep(500);	
	}
	
	@Before
	public void navLogin() {
		
		//driver.get(Home);
		clicksAchannelToMakeItActiveIfNot();
			
	}

	@Test // 1
	public void createValidChannelWithValidFriend() throws Exception {
		
		creatingValidChannels("unique", ValidFriend1, "no", "no");
		LeaveOrDeleteChannel();

	}
	
	@Test // 2
	public void createValidChannelWithInvalidFriend() throws Exception {
		
		creatingValidChannels("unique", "unique", "no", "no");
		LeaveOrDeleteChannel();
		
	}
	
	@Test // 3 
	public void createInvalidChannel1WithValidFriend() throws Exception {
		
		creatingInvalidChannels(InvalidChannelName1, ValidFriend1, "no", "no");
		
	}
	
	@Test // 4
	public void createInvalidChannel1WithInvalidFriend() throws Exception {
		
		creatingInvalidChannels(InvalidChannelName1, "unique", "no", "no");

	}
	
	@Test // 5
	public void createInvalidChannel2WithValidFriend() throws Exception {
		
		creatingInvalidChannels(InvalidChannelName2, ValidFriend1, "no", "no");
		
	}
	
	@Test // 6
	public void createInvalidChannel2WithInvalidFriend() throws Exception {
		
		creatingInvalidChannels(InvalidChannelName2, "unique", "no", "no");

	}
	
	@Test // 7
	public void createInvalidChannel3WithValidFriend() throws Exception {
		
		creatingInvalidChannels(InvalidChannelName3, ValidFriend1, "no", "no");
		
	}
	
	@Test // 8
	public void createInvalidChannel3WithInvalidFriend() throws Exception {
		
		creatingInvalidChannels(InvalidChannelName3, "unique", "no", "no");
		
	}
	
	//@TODO @Test // 9
	public void createDuplicateChannelFail() throws Exception {
		
		creatingValidChannels("unique", ValidFriend1, "no", "no");
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(addRoomElementActive));
		WebElement dmElement = driver.findElement(addRoomElementActive);
		Thread.sleep(1000);
		dmElement.click();
		
		creatingInvalidChannels(uniqueChannelName, ValidFriend1, "no", "no");
		System.out.println("The channel " + uniqueChannelName + " already exists.");
		
		LeaveOrDeleteChannel();
		
	}
	
	@Test // 10
	public void createChannelWithMultipleUsers() throws Exception {
		
		creatingValidChannels("unique", "multiple", "no", "no");
		
	}

	@Test // 11
	public void channelSearchAndMessagingMethod1() throws Exception {
		
		creatingValidChannels("unique", ValidFriend1, "no", "no");
		
		//Search Method 1
		clicksAchannelToMakeItActiveIfNot();
		searchChannelsUsingMoreChannels(uniqueChannelName);
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(textFriend));
		
		driver.findElement(textFriend).sendKeys("Hello " + uniqueChannelName + "!");
		driver.findElement(sendMessageButton).click();
		
		LeaveOrDeleteChannel();
		
	}
	
	@Test // 12
	public void channelSearchAndMessagingMethod2() throws Exception {
		
		creatingValidChannels("unique", ValidFriend1, "no", "no");
		
		//Search Method 2
		searchChannelsUsingAllChannels(uniqueChannelName);	//ERROR! Within the method, it will click the panel, but the panel does not visually pop up
		
		System.out.println("Test Passed");
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(textFriend));
		driver.findElement(textFriend).sendKeys("Hello " + uniqueChannelName + " !");
		driver.findElement(sendMessageButton).click();
		
		LeaveOrDeleteChannel();
		
	}
	
	@Test // 13
	public void channelSearchAndMessagingMethod3() throws Exception {
		
		creatingValidChannels("unique", ValidFriend1, "no", "no");
		
		//Search Method 3
		clicksAchannelToMakeItActiveIfNot();
		searchChannelsUsingLeftPanelListOfChannels(uniqueChannelName);
		
		driver.findElement(textFriend).sendKeys("Hello " + uniqueChannelName + " !");
		driver.findElement(sendMessageButton).click();
		
		LeaveOrDeleteChannel();
		
	}
	
	@Test // 14
	public void creatingPrivateChannel() throws Exception {
		
		creatingValidChannels("unique", ValidFriend1, "yes", "no");
		
		WebElement infoIconButt = driver.findElements(rightPanelOptions).get(1);
		infoIconButt.click();
		Thread.sleep(500);
		
		//Makes sure that room created is Private
		WebElement settings = driver.findElements(roomInfoSettings).get(3);
		String privacyType = settings.getText();
		System.out.println(privacyType);
		Assert.assertEquals(privacyType, "Private Group");
		
		//Closes the info tab
		driver.findElement(rightPanelClose).click();
		
		LeaveOrDeleteChannel();
		
	}
	
	@Test // 15
	public void creatingReadOnlyChannel() throws Exception {
		
		creatingValidChannels("unique", ValidFriend1, "no", "yes");
		
		WebElement infoIconButt = driver.findElements(rightPanelOptions).get(1);
		infoIconButt.click();
		Thread.sleep(500);
		
		//Makes sure that room created is Read-Only
		WebElement settings = driver.findElements(roomInfoSettings).get(4);
		String readType = settings.getText();
		System.out.println(readType);
		Assert.assertEquals(readType, "True");
		
		//Closes the info tab
		driver.findElement(rightPanelClose).click();
		
		LeaveOrDeleteChannel();
			
		}
		
//	@After
//	public void afterEachTest() {
//		if (detectElement(buttonCancel)) {
//			driver.findElement(buttonCancel).click();
//		}
//		else {
//			
//		}
//	}
	
	@AfterClass
	public static void endTesting() {
		
		//LOGS OUT before closing the tab/window
//		driver.findElement(arrowButton).click();
//		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(buttonLogout));
//		driver.findElement(buttonLogout).click();
		
		driver.close();
		driver.quit();
	}	
	
}
	
	
	

package test;

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




public class ChannelStuff {


	public static WebDriver driver;

	public static String Home = "http://localhost:3000/channel/general";
	public static String loginUser = "test";
	public static String loginPW = "test";
	
	public static String ValidChannelName = ("North_Pole");
	public static String InvalidChannelName1 = ("North Pole");
	public static String InvalidChannelName2 = ("North,Pole");
	public static String ValidFriend = ("rocket.cat");
	
	static String uniqueChannelName;
	static String uniqueFriendName = "Default_Friend";
	
	
	private static By userName = By.id("emailOrUsername");
	private static By password = By.id("pass");
	private static By loginButton = By.cssSelector("button");
	
	private static By addRoomElement = By.className("add-room");
	private static By addRoomElementActive = By.cssSelector("h3.add-room.active");
	
	private static By channelCreate = By.id("channel-name");
	//private static By channelCreate = By.cssSelector("input#channel-name.required");
	private static By channelUsers = By.id("channel-members");
	private static By buttonCreate = By.cssSelector("button.primary.save-channel");
	private static By buttonCancel = By.cssSelector("button.cancel-channel");
	private static By channelLocation = By.cssSelector("a.open-room"); 
	private static By moreChannels = By.cssSelector("button.more.more-channels");
	private static By allChannels = By.cssSelector("button.button.all");
	
	private static By optionAll = By.cssSelector("select#sort.c-select");
	private static By optionJoined = By.cssSelector("option.joined");
	private static By optionSort = By.cssSelector("select#sort-channels.c-select");
	private static By optionPrivacy = By.cssSelector("select#channel-type.c-select");
	private static By channelSearch = By.cssSelector("input#channel-search.search");
	private static By channelFound = By.cssSelector("a.channel-link");
	
	private static By textFriend = By.cssSelector("textarea.input-message.autogrow-short");
	private static By sendMsgButt = By.cssSelector("div.message-buttons.send-message"); 
	
	private static By leaveArrow = By.cssSelector("i.icon-logout.leave-room");
	private static By PopUpWindow = By.cssSelector("div.sweet-alert.showSweetAlert.visible");
	private static By PopUpYes = By.className("confirm");
	private static By buttonOK = By.cssSelector("button.confirm");
	
	private static By leftPanel = By.cssSelector("div.rooms-list");
	private static By rightPanelOptions = By.cssSelector("div.tab-button");
	private static By rightPanelClose = By.cssSelector("div.tab-button.active");
	private static By trashButt = By.cssSelector("button.button.danger.delete");

	
	
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
		WebElement dmElement = driver.findElement(addRoomElementActive);
		dmElement.click();
	}
	
	private static void generateUniqueName(){
		
		uniqueChannelName = UUID.randomUUID().toString().substring(0, 8);
		uniqueFriendName = UUID.randomUUID().toString().substring(0, 8);
	}
	
	private static void createChannelWithUser(String channelName, String friendName) {
		
		waitForLeftPanelToLoadToClickCreateChannel();
		
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
		else {
			uniqueChannelName = channelName;
			uniqueFriendName = friendName;
		}
		
		driver.findElement(channelCreate).sendKeys(uniqueChannelName);
		driver.findElement(channelUsers).sendKeys(uniqueFriendName);
		driver.findElement(buttonCreate).click();
		
	}
	
	private static void typeChannelNameWhenSearchChannelPanelIsOpened(String channelName) {
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(channelSearch));
		driver.findElement(channelSearch).sendKeys(channelName);
		driver.findElement(channelFound).click();
		
	}
	
	private static void searchChannelsUsingMoreChannels(String channelName) {

		waitForLeftPanelToLoadToClickCreateChannel();
		driver.findElement(moreChannels).click();
		typeChannelNameWhenSearchChannelPanelIsOpened(channelName);
		
	}
	
	private static void searchChannelsUsingAllChannels(String channelName) {
		
		waitForLeftPanelToLoadToClickCreateChannel();
		driver.findElement(addRoomElementActive).click();
		driver.findElement(allChannels).click();
		typeChannelNameWhenSearchChannelPanelIsOpened(channelName);
		
	}
	
	private static void searchChannelsUsingLeftPanelList(String channelName) {
		
		waitForLeftPanelToLoadToClickCreateChannel();
		int size = driver.findElements(channelLocation).size();
		
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
	
	private static void invalidChannelClickCancel(String channelName) {
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(buttonCancel));
		driver.findElement(buttonCancel).click();
		System.out.println(channelName + " is not a valid channel name.");
		
	}
	
	private static void clicksAchannelToMakeItActiveIfNot() {
		
		if (!(detectElement(addRoomElementActive))) {
			WebElement firstChannel = driver.findElement(channelLocation);//.get(0);
			firstChannel.click();
		}
	}
	
	public void LeaveOrDeleteChannel() throws Exception {
		
		//Leave Channel
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
			
			driver.findElement(trashButt).click();
			Thread.sleep(1500);
			
			driver.findElement(PopUpYes).click();
			Thread.sleep(2500);
			
			driver.findElement(rightPanelClose).click();
			System.out.print("Channel has been deleted.");
//		}
	}
	
/*
 * TEST Order:
 * 
 * 
 * CREATE CHANNEL (CHANNEL NAME / FRIEND NAME):
 * 
 * 1. Valid / Valid
 * 2. Valid / Invalid
 * 3. Invalid1 / Valid
 * 4. Invalid1 / Invalid
 * 5. Invalid2 / Valid
 * 6. Invalid2 / Invalid
 * 7. Duplicated Channel (Invalid)
 *  
 * ***NOTE: Invalid USER will STILL create the channel if the channel name is valid.***
 * 
 * 
 * VALID CHANNEL CREATED:
 * 
 * 8. Channel Search(3 Methods) and Messaging
 * 9. Creating Different Channel Types
 * 
 */

	
	@BeforeClass
	public static void setupTest() throws Exception{
		
		System.setProperty(
				"webdriver.chrome.driver",
				"/home/osboxes/Documents/Selenium Library/chromedriver.exe"
				);
	
		driver = new ChromeDriver(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
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
		
		createChannelWithUser("unique", ValidFriend);
		
		System.out.println(uniqueChannelName + " has been created.");

		LeaveOrDeleteChannel();

	}
	
	@Test // 2
	public void createValidChannelWithInvalidFriend() throws Exception {
		
		createChannelWithUser("unique", "unique");
		
		System.out.println("Channel " + uniqueChannelName + " has been created. " + uniqueFriendName + " could not be found.");
		
		LeaveOrDeleteChannel();
		
	}
	
	@Test // 3
	public void createInvalidChannel1WithValidFriend() throws Exception {
		
		createChannelWithUser(InvalidChannelName1, ValidFriend);
		
		invalidChannelClickCancel(InvalidChannelName1);
		
		Thread.sleep(2000);
	}
	
	@Test // 4
	public void createInvalidChannel1WithInvalidFriend() throws Exception {
		
		createChannelWithUser(InvalidChannelName1, "unique");
		
		invalidChannelClickCancel(InvalidChannelName1);
		
		Thread.sleep(2000);
	}
	
	@Test // 5
	public void createInvalidChannel2WithValidFriend() throws Exception {
		
		createChannelWithUser(InvalidChannelName2, ValidFriend);
		
		invalidChannelClickCancel(InvalidChannelName2);
		
		Thread.sleep(2000);
	}
	
	@Test // 6
	public void createInvalidChannel2WithInvalidFriend() throws Exception {
		
		createChannelWithUser(InvalidChannelName2, "unique");
		
		invalidChannelClickCancel(InvalidChannelName2);
		
		Thread.sleep(2000);
	}
	
	@Test // 7
	public void createDuplicateChannelFail() throws Exception {
		
		createChannelWithUser("unique", ValidFriend);
		
		//makesChannelActiveIfNot();
		//waitForLeftPanelToLoadToClickCreateChannel();
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(addRoomElementActive));
		WebElement dmElement = driver.findElement(addRoomElementActive);
		//WebElement dmElement = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(addRoomElementActive));
		Thread.sleep(1000);
		dmElement.click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(channelCreate));
		
//		typeChannelNameWhenSearchChannelPanelIsOpened(uniqueChannelName);
		driver.findElement(channelCreate).sendKeys(uniqueChannelName);
		driver.findElement(channelUsers).sendKeys(ValidFriend);
		driver.findElement(buttonCreate).click();
		
		invalidChannelClickCancel(uniqueChannelName);
		
		System.out.println("The channel " + uniqueChannelName + " already exists.");
		
		LeaveOrDeleteChannel();
		
	}

	//@TODO @Test // 8
	public void channelSearchAndMessaging() throws Exception {
		
		createChannelWithUser("unique", "valid");
		
		searchChannelsUsingMoreChannels(uniqueChannelName);
		driver.findElement(textFriend).sendKeys("Hello " + uniqueChannelName + "!");
		driver.findElement(sendMsgButt).click();
		
		System.out.println("Test 1 Passed");
		
//		LeaveOrDeleteChannel();
//		makesChannelActiveIfNot();
		
		searchChannelsUsingAllChannels(uniqueChannelName);
		driver.findElement(textFriend).sendKeys("Hi " + uniqueChannelName + " again.");
		driver.findElement(sendMsgButt).click();
		
		System.out.println("Test 2 Passed");
		
//		LeaveOrDeleteChannel();
//		makesChannelActiveIfNot();
		
		searchChannelsUsingLeftPanelList(uniqueChannelName);
		driver.findElement(textFriend).sendKeys("Goodbye " + uniqueChannelName + "!");
		driver.findElement(sendMsgButt).click();
		
		System.out.print("Test 3 Passed");
		
		LeaveOrDeleteChannel();
		
	}
	
	//@TODO @Test // 9
	public void creatingDifferentChannelTypes() throws Exception {
		
		clicksAchannelToMakeItActiveIfNot();
		Thread.sleep(2000);
		//waitForLeftPanelToLoadToClickCreateChannel();
		
		//driver.findElement(optionAll).click();
		//driver.findElement(optionSort).click();
		//driver.findElement(optionSort).click();
		//driver.findElement(optionPrivacy).click();
		
		//new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(rightPanelOptions));	
		
		//Delete Channel - Must be the owner
		WebElement infoIconButt = driver.findElements(rightPanelOptions).get(1);
		Thread.sleep(1500);
		infoIconButt.click();
		System.out.println("Pass 1");
		Thread.sleep(1000);
		
		driver.findElement(trashButt).click();
		System.out.println("Pass 2");
		Thread.sleep(1500);
		
		driver.findElement(PopUpYes).click();
		Thread.sleep(2500);
		
		driver.findElement(rightPanelClose).click();
//			}
		
	}
		
	@AfterClass
	public static void endTesting(){
		driver.close();
		driver.quit();
	}	
	
}
	
	
	

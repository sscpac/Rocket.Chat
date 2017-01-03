package testRocketChatPackage.channel;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.junit.Assert;

// Tests channel creation and channel deletion using various methods to create
// Create a channel: clicking on Channel + > Create
// Delete a channel: clicking on a channel>info panel> delete (must be admin or channel owner)
// There are 3 total ways to search for  a channel 	1. Clicking on Channel + button
//													2. Click on Channels> All Channels
//													3. Clicking on "More Channels..." Link below the channels list
// @TODO: 	- Test 9: Need to finish Duplicate channels.
// 			- Test 12: Need to fix Click on menu has odd bug where menu does not pop up and test will fail.
//			- Test 11: Need to finish


public class ChannelTest {


	public static WebDriver driver;

	public static String Home 					= "http://localhost:3000/channel/general";
	public static String loginUser 				= "admin";
	public static String loginPW 				= "admin";
	public static String northPoleWithSpace		= ("North Pole");		// Invalid case 1 when name has spaces
	public static String northPoleWithSpecial 	= ("North,Pole");		// Invalid case 2 when name has a symbol
	public static String nullChannelName 		= (" ");				// Invalid case 3 when name is not present
	public static String validUserRocketCat		= ("rocket.cat");		// Valid user 1 (bot)
	public static String validUserTest 		 	= ("test");				// Valid user 2 (regular user)		
	static String typedChannelName 		 		= "general";
	static String typedFriendName 				= "Default_Friend";
	static String uniqueChannelName;
	static String uniqueUserName;
	private static By userName 					= By.id("emailOrUsername");
	private static By password 					= By.id("pass");
	private static By loginButton 				= By.cssSelector("button");
	private static By addRoomElementActive 		= By.cssSelector("h3.add-room.active");
	private static By channelCreate 			= By.id("channel-name");
	private static By channelUsers 				= By.id("channel-members");
	private static By buttonCreate 				= By.cssSelector("button.primary.save-channel");
	private static By channelLocation 			= By.cssSelector("a.open-room"); 
	private static By moreChannels 				= By.cssSelector("button.more.more-channels");
	private static By allChannels 				= By.cssSelector("button.button.all");
	private static By optionPrivacy 			= By.id("channel-type");
	private static By optionReadOnly 			= By.id("channel-ro");
	private static By roomInfoSettings 			= By.className("current-setting");
	private static By channelSearch 			= By.cssSelector("input#channel-search.search");
	private static By channelFound 				= By.cssSelector("a.channel-link");
	private static By autoCompleteOption 		= By.cssSelector("div.-autocomplete-container");
	private static By textFriend 				= By.cssSelector("textarea.input-message.autogrow-short");
	private static By sendMessageButton 		= By.cssSelector("i.icon-paper-plane");
	private static By PopUpYes 					= By.className("confirm");
	private static By rightPanelOptions 		= By.cssSelector("div.tab-button");
	private static By rightPanelClose 			= By.cssSelector("div.tab-button.active");
	private static By trashButton 				= By.cssSelector("button.button.danger.delete");
	private static By creationErrorMessage 		= By.cssSelector("div.input-error");
	private static By cancelChannelBtn 			= By.cssSelector("css = button:contains('cancel-channel')");
	
	
	/*
	 *  Test Helper Methods
	 */
	
	private static boolean detectElement(By element) {

		boolean Elementdetected = driver.findElements(element).size() > 0;
		if (Elementdetected) {
			return true;
		}
		else {
			return false;
		}	
	}
	
	
	private static void generateUniqueName(){
		
		uniqueChannelName = UUID.randomUUID().toString().substring(0, 8);
		uniqueUserName = UUID.randomUUID().toString().substring(0, 8);
		System.out.println("U ChannelName:" + uniqueChannelName);
		System.out.println("U UserName:" + uniqueUserName);
	}
	
	private static void checkIfOnChannelsMenu() {
		
		if (!(detectElement(addRoomElementActive))) {
			driver.findElement(channelLocation).click();
		}
	}
	
	public void LeaveOrDeleteChannel() throws Exception {
		
			//Delete Channel - Must be the OWNER to delete a channel
			driver.findElements(rightPanelOptions).get(1).click();
			Thread.sleep(1000);
			driver.findElement(trashButton).click();
			Thread.sleep(1500);
			driver.findElement(PopUpYes).click();
			Thread.sleep(2500);
			driver.findElement(rightPanelClose).click();
			System.out.print("Channel has been deleted.");
			
	}
	
	private void createChannelWith(String channelName, String userName, boolean privateGroup, boolean readOnly ) {
		generateUniqueName();
		
		if(channelName == "unique"){
			channelName = uniqueChannelName;
		}
		if(userName == "unique"){
			userName = uniqueUserName;
		}
		
		System.out.println("ChannelName:" + channelName);
		System.out.println("UserName:" + userName);
		
		checkIfOnChannelsMenu();

		driver.findElement(addRoomElementActive).click();
		driver.findElement(channelCreate).clear();
		driver.findElement(channelCreate).sendKeys(channelName);
		driver.findElement(channelUsers).clear();
		driver.findElement(channelUsers).sendKeys(userName);
		driver.findElement(optionPrivacy).clear();
		driver.findElement(optionReadOnly).clear();
		if(privateGroup){
			driver.findElement(optionPrivacy).click();
		}
		if(readOnly){
			driver.findElement(optionReadOnly).click();
		}
		driver.findElement(buttonCreate).click();
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
//		System.setProperty(
//				"webdriver.chrome.driver",
//				"/home/osboxes/Documents/Selenium Library/chromedriver.exe"
//				);
//	
//		driver = new ChromeDriver(); 
		driver = new SafariDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// Logging In
		driver.get(Home);
		driver.findElement(userName).sendKeys(loginUser);
		driver.findElement(password).sendKeys(loginPW);
		driver.findElement(loginButton).click();	
	}
	
	@Before
	public void navLogin() {
		//driver.get(Home);
		//checkIfOnChannelsMenu();
		driver.findElement(channelLocation).click();
			
	}
	
	@AfterClass
	public static void endTesting() {
		
		driver.close();
		driver.quit();
	}	
	
	
	/*
	 * The following test uses method 1 of creating a channel by
	 * Clicking on the (+) button to create a channel
	 */

	@Test // 1 Valid channel (Unique)  with Valid User RocketCat
	public void createValidChannelWithFriend() throws Exception {
		
		createChannelWith("unique", validUserRocketCat, false, false);
		LeaveOrDeleteChannel();

	}
	
	@Test // 2 Valid channel (Unique) with Invalid User
	public void createValidChannelWithInvalidFriend() throws Exception {

		createChannelWith("unique", "unique", false, false);
		LeaveOrDeleteChannel();
	}
	
	
	@Test // 3 Invalid channel: "North Pole" with Valid User RocketCat
	public void createInvalidChannelWithValidFriend() {

		createChannelWith(northPoleWithSpace, validUserRocketCat, false, false);
	}
	
	@Test // 4 Invalid channel: "North Pole" with Invalid User
	public void createInvalidChannelWithInvalidFriend() {

		createChannelWith(northPoleWithSpace, uniqueUserName, false, false);

	}
	

	
	@Test // 5 Invalid channel: North,Pole with Valid User RocketCat
	public void createInvalidChannel2WithValidFriend() {

		createChannelWith(northPoleWithSpecial, validUserRocketCat, false, false);
	}
	
	@Test // 6 Invalid channel: North,Pole with Invalid User
	public void createInvalidChannel2WithInvalidFriend() {

		createChannelWith(northPoleWithSpecial, "unique", false, false);

	}
	
	@Test // 7 Null Channel Name with RocketCat
	public void createInvalidChannel3WithValidFriend() throws Exception {

		createChannelWith(nullChannelName, validUserRocketCat, false, false);
		
	}
	
	@Test // 8 Null Channel name with Invalid Friend
	public void createInvalidChannel3WithInvalidFriend() throws Exception {

		createChannelWith(nullChannelName, "unique", false, false);
	}
	
	//@Test // 9
	public void createDuplicateChannelFail() throws Exception {
		
		driver.findElement(addRoomElementActive).click();
        driver.findElement(channelCreate).clear();
		driver.findElement(channelUsers).clear();
		driver.findElement(channelCreate).sendKeys("DuplicateChannelTest");
		driver.findElement(channelUsers).sendKeys(validUserRocketCat);
		driver.findElement(optionPrivacy).clear();
		driver.findElement(optionReadOnly).clear();
		driver.findElement(buttonCreate).click();
		
		checkIfOnChannelsMenu();

		driver.findElement(addRoomElementActive).click();
        driver.findElement(channelCreate).clear();
		driver.findElement(channelUsers).clear();
		driver.findElement(channelCreate).sendKeys("DuplicateChannelTest");
		driver.findElement(channelUsers).sendKeys(validUserRocketCat);
		driver.findElement(optionPrivacy).clear();
		driver.findElement(optionReadOnly).clear();
		driver.findElement(buttonCreate).click();
		
		System.out.println("Next channel created");

		String errorMessage = driver.findElement(creationErrorMessage).getText();
		System.out.println(errorMessage);
		Assert.assertThat(errorMessage, CoreMatchers.containsString("'duplicatechanneltest' exists"));
		System.out.println("The channel " + uniqueChannelName + " already exists.");
		
		driver.findElement(cancelChannelBtn).click();
		LeaveOrDeleteChannel();
		
	}
	
	@Test // 10
	public void createChannelWithMultipleUsers() throws Exception {
		generateUniqueName();
		checkIfOnChannelsMenu();
		driver.findElement(addRoomElementActive).click();
		WebElement Users = driver.findElement(channelUsers);
		Users.clear();
		Users.sendKeys(validUserRocketCat);
		driver.findElement(autoCompleteOption).click();
		Users.clear();
		Users.sendKeys(validUserTest);
		driver.findElement(autoCompleteOption).click();
		System.out.println(validUserRocketCat + " and " + validUserTest + " have been added.");
		checkIfOnChannelsMenu();
		driver.findElement(optionPrivacy).clear();
		driver.findElement(optionReadOnly).clear();

		driver.findElement(channelCreate).sendKeys(uniqueChannelName);
		driver.findElement(buttonCreate).click();
		
		LeaveOrDeleteChannel();
		
	}

	/*
	 * The following tests (11, 12, and 13) are for testing the different methods of 
	 * searching Channels
	 */
	
	//@Test // 11 Searhing a Channel Using the More Channels Button
	public void channelSearchAndMessagingMethod1() throws Exception {
		createChannelWith("unique", validUserRocketCat, false, false);
		//Begin Search Method 1: Clicking on "More Channels" button
		checkIfOnChannelsMenu(); //Clicks on a channel to make sure the channels list is the primary state
		driver.findElement(addRoomElementActive).click();
		driver.findElement(moreChannels).click();
		driver.findElement(channelSearch).sendKeys(uniqueChannelName);
		driver.findElement(channelFound).click();
		
		//send message to friend
		driver.findElement(textFriend).sendKeys("Hello we are now talking in channel " + uniqueChannelName + "!");
		driver.findElement(sendMessageButton).click();
		
		//delete channel
		LeaveOrDeleteChannel();
		
	}
	
	//@Test // 12	
	public void channelSearchAndMessagingMethod2() throws Exception {
		createChannelWith("unique", validUserRocketCat, false, false);
		System.out.println("user created Passed");
		
		//Search Method 2: Using the All Channels Menu
		checkIfOnChannelsMenu();
		driver.findElement(addRoomElementActive).click();
		driver.findElement(allChannels).click();				//@TODO This would have an error: Click will pass but menu does not pop up
		System.out.println("Clicked on all channels Passed");
		driver.findElement(channelSearch).sendKeys(uniqueChannelName);
		driver.findElement(channelFound).click();
		
		//send message to friend
		driver.findElement(textFriend).sendKeys("Hello " + uniqueChannelName + " !");
		driver.findElement(sendMessageButton).click();
		
		//delete channel
		LeaveOrDeleteChannel();
		
	}
	
	@Test // 13
	public void channelSearchAndMessagingMethod3() throws Exception {
		createChannelWith("unique", validUserRocketCat, false, false);
		
		//Search Method 3
		checkIfOnChannelsMenu();
		driver.findElement(addRoomElementActive).click();
		int size = driver.findElements(channelLocation).size();
		int i;
		
		for (i = 0; i < size; i++) {
			WebElement channelArrayElement = driver.findElements(channelLocation).get(i);
			String channelAttribute = channelArrayElement.getAttribute("title");
			if (channelAttribute == uniqueChannelName) {
				channelArrayElement.click();
				break;
			}	
		}
		
		driver.findElement(textFriend).sendKeys("Hello " + uniqueChannelName + " !");
		driver.findElement(sendMessageButton).click();
		
		LeaveOrDeleteChannel();
		
	}
	
	@Test // 14	Creates a private channel, adds a user and then deletes it
	public void creatingPrivateChannel() throws Exception {
		createChannelWith("unique", validUserRocketCat, true, false);	//true for privateGroup
		
		driver.findElements(rightPanelOptions).get(1).click();			//click on info icon panel
		Thread.sleep(500);												//wait for it to slide and open  (still necessary even with implicit wait?)
		
		//Makes sure that room created is Private
		WebElement settings = driver.findElements(roomInfoSettings).get(3);
		String privacyType = settings.getText();
		System.out.println("privacy is" + privacyType);
		Assert.assertEquals(privacyType, "Private Group");
		
		//Closes the info tab
		driver.findElement(rightPanelClose).click();
		
		//send a message
		driver.findElement(textFriend).sendKeys("Hello this channel " + uniqueChannelName + " is private !");
		driver.findElement(sendMessageButton).click();
		
		LeaveOrDeleteChannel();
		
	}
	
	@Test // 15	Creates a Read Only channel then deletes it
	public void creatingReadOnlyChannel() throws Exception {
		createChannelWith("unique", validUserRocketCat, false, true);	//true for readOnly
		
		driver.findElements(rightPanelOptions).get(1).click();			//click on info icon panel
		Thread.sleep(500);												//wait for it to open (still necessary even with implicit wait?)
		
		//Makes sure that room created is Read-Only
		WebElement settings = driver.findElements(roomInfoSettings).get(4);
		String readType = settings.getText();
		System.out.println("ReadType is "+ readType);
		Assert.assertEquals(readType, "True");
		
		//Closes the info tab
		driver.findElement(rightPanelClose).click();
		
		//send a message
		driver.findElement(textFriend).sendKeys("Hello this channel " + uniqueChannelName + " is read only for normal users !");
		driver.findElement(sendMessageButton).click();
		
		LeaveOrDeleteChannel();
			
		}
	
}
	
	
	

package RocketChatTestPackage;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;

public class ChannelsTest {
		static WebDriver wd = RocketChatTestPackage.ForgotPassTest.wd; 
	private static By buttonCreate = By.className("button primary save-channel");
	private static By buttonCancel = By.className("button cancel-channel");
	private static By buttonCloseChnnl = By.className("arrow close");
	private static By statusBar = By.cssSelector("div.data");
	private static By buttonLogout = By.className("icon-logout");
	private static By autoCompleteOption = By.cssSelector("div.-autocomplete-container");
	//private static By errorMessages = By.cssSelector("div.input-error");
	public static void close(){
		if(detectElement(buttonCancel) == false){
			wd.findElement(buttonCancel).click();
			System.out.println("Cancel Pressed");
//			wd.findElement(statusBar).click();
//			System.out.println("Fin 2");
//			wd.findElement(buttonLogout).click();
//			System.out.println("Fin 3");
		}
		else{
//			wd.findElement(statusBar).click();
//			System.out.println("Fin 4");
//			wd.findElement(buttonLogout).click();
			System.out.println("Created Channel");
		}
//		wd.findElement(statusBar).click();
//		System.out.println("Fin 4");
//		wd.findElement(buttonLogout).click();
//		System.out.println("Fin 5");
	}
	public static void sleep() throws Exception{
		Thread.sleep(1000);
	}
	
	private static boolean detectElement(By element) {
		
		//boolean ElementDetected = wd.findElements(element).size() > 0;
		int ElementDetected = wd.findElements(element).size();
		//if (ElementDetected) {
		if (ElementDetected > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	private static void searchAddUsers() throws Exception{
		WebElement curUsers = wd.findElement(By.id("channel-members"));
		curUsers.sendKeys("test");
		sleep();
		//System.out.println("Typed in test");	
		wd.findElement(autoCompleteOption).click();
		curUsers.clear();
		curUsers.sendKeys("test");
		sleep();
		wd.findElement(autoCompleteOption).click();
		curUsers.clear();
		System.out.println("Added selected users");
	}
	


	@BeforeClass
	public static void open() throws Exception{
		wd = new SafariDriver();
		System.out.println("Setup info: " + wd);
		System.out.println("Getting address");
		wd.get("http://localhost:3000");
		System.out.println("Find login and pass boxes and enter strings");
		WebElement loginEmail = wd.findElement(By.id("emailOrUsername"));
		loginEmail.sendKeys("test@gmail.com");
		WebElement loginPass = wd.findElement(By.id("pass"));
		loginPass.sendKeys("test");
		wd.findElement(By.className("button primary login")).click();
	}
	
	@Before
	public void clickOnChannels() throws Exception{
		System.out.println("Now clicking on Channels button");
		sleep();
		wd.findElement(By.className("icon-plus")).click();
		sleep();
	}
	
	//Find users and create channel
	@Test
	public void createWithAddedUsrs() throws Exception {
		
		
		WebElement Name = wd.findElement(By.id("channel-name"));
			Name.sendKeys("TestName");
		searchAddUsers();
		wd.findElement(buttonCreate).click();
		Name.clear();
		close();
	}
	
	//Test Private Group Entry
	@Test
		public void privateGroup() throws Exception{
		
		
		WebElement Name = wd.findElement(By.id("channel-name"));
		Name.sendKeys("TestName2");
		searchAddUsers();
		wd.findElement(buttonCreate).click();
		Name.clear();
		close();
	}
	
	//Test Read Only Channel
	@Test
		public void readOnly() throws Exception{
		
		WebElement Name = wd.findElement(By.id("channel-name"));
		Name.sendKeys("TestName3");
		searchAddUsers();
		wd.findElement(buttonCreate).click();
		Name.clear();
		close();
	}
	
	//Test Read Only Channel and Private Group Entry
	@Test
		public void readOnlyAndPrivateGroup() throws Exception{
		
		WebElement Name = wd.findElement(By.id("channel-name"));
		Name.sendKeys("TestName4");
		searchAddUsers();
		wd.findElement(buttonCreate).click();
		Name.clear();
		close();
	}
	
	
	//Test void entry
	@Test
		public void voidEntry() throws Exception{
		
		System.out.println("Clicking on Create\n");
		//check if error message appears
		wd.findElement(buttonCreate).click();
		sleep();
		wd.findElement(buttonCancel).click();
		
	}
	
	
	//Wait before next test
	@After
		public void pauseBeforeNextTest() throws Exception{
		sleep();
	}
	
	//Close Channels box and close server
	@AfterClass
	public static void end(){
		close();
		
		wd.findElement(statusBar).click();
		System.out.println("Status Bar Clicked");
		wd.findElement(buttonLogout).click();
		System.out.println("Logged Out");
		System.out.println("All tests finished and logged out\n");
		wd.quit();
	}
}
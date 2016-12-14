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
	
private static By autoCompleteOption = By.cssSelector("div.-autocomplete-container");
private static By errorMessages = By.cssSelector("div.input-error .p");
	
	@BeforeClass
	public static void open() throws Exception{
		wd = new SafariDriver();
		System.out.println("Setup info: " + wd);
		System.out.println("Getting address");
		wd.get("http://localhost:3000");
		System.out.println("Find login and pass boxes and enter strings");
		Thread.sleep(3000);	
		WebElement loginEmail = wd.findElement(By.id("emailOrUsername"));
		loginEmail.sendKeys("test@gmail.com");
		WebElement loginPass = wd.findElement(By.id("pass"));
		loginPass.sendKeys("test");
		wd.findElement(By.className("button primary login")).click();
	}
	
	@Before
	public void clickOnChannels() throws Exception{
		System.out.println("Now clicking on Channels button");
		Thread.sleep(1000);
		wd.findElement(By.className("icon-plus")).click();
		Thread.sleep(1000);
	}
	
	//Find users and create channel
	@Test
	public void createWithAddedUsrs() throws Exception {
		WebElement curUsers = wd.findElement(By.id("channel-members"));
		curUsers.sendKeys("ono");
		System.out.println("Typed in ono");	
		Thread.sleep(1000);
		wd.findElement(autoCompleteOption).click();
		curUsers.clear();
		curUsers.sendKeys("ono");
		Thread.sleep(1000);
		wd.findElement(autoCompleteOption).click();
		curUsers.clear();
		System.out.println("Added selected users");
	}
	
//	//Test Private Group Entry
//	@Test
//		public void privateGroup(){
//	
//	}
//	
//	//Test Read Only Channel
//	@Test
//		public void readOnly(){
//		wd.findElement(By.className("button primary save-channel")).click();
//		
//		
//	}
	
	
	//Test void entry
	@Test
		public void voidEntry(){
		System.out.println("Clicking on Create\n");
		wd.findElement(By.className("button primary save-channel")).click();
		//check if error message is correct
		String nameError = wd.findElement(errorMessages).getText();
		System.out.println("The error should say -The field Name is required.");
		
		System.out.println("There is an error that says '" + nameError + "'!");
	}
	
	//Wait before next test
	@After
		public void clearAll() throws Exception{
		System.out.println("Waiting 3 seconds");
		Thread.sleep(3000);
	}
	
	//Close Channels box and close server
	@AfterClass
		public static void end() throws Exception{
		Thread.sleep(2000);
		wd.findElement(By.className("arrow close")).click();
		Thread.sleep(3000);
		System.out.println("All tests finished\n");
		wd.quit();
	}
}

package testRocketChatPackage.channel;

/*
 * This deals with the information of a member within a channel room.
 * When clicked a user can view info and send a message to the individual
 * 
 * If admin, then can make the user an admin
 * 
 * If owner of room then can make person moderator of the room and kick
 */

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

import junit.framework.Assert;

public class userChannelProfile {
	
	public static WebDriver driver;
	private static By generalChannel = By.xpath("//a[contains(@title,'general')]");
	private static By memberList = By.xpath("//button[contains(@aria-label,'Members List')]");
	private static By user = By.xpath("//li[contains(@class,'user-image user-card-room status-online')]");
	private static By viewAllButton = By.xpath("//button[contains(@class, 'button back')]");
	private static By muteUserButton = By.xpath("//button[@class, 'button-block danger mute-user']");
	
	@BeforeClass
	public static void beforeClass(){
		driver = new SafariDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		testRocketChatPackage.login.loginTest.login("admin", "admin", driver);
		//go into a channel
		
	}
	
/*
 *  Test Helpers	
 */
	public static boolean detectElement(By targetElement) {
		
		boolean elementExists = driver.findElements(targetElement).size() > 0;
		if (elementExists) {
			return true;
		}
		else {
			return false;
		}	
	}
	

	@Test
	public void muteUser() {	
		driver.findElement(generalChannel).click();
		driver.findElement(memberList).click();
		driver.findElement(user).click();
		driver.findElement(muteUserButton).click();
	}
	
	@Test
	public void openUserProfile() {
		driver.findElement(generalChannel).click();
		driver.findElement(memberList).click();
		driver.findElement(user).click();
	}
	
	@Test
	public void closeUserProfile() {
		driver.findElement(generalChannel).click();
		driver.findElement(memberList).click();
		driver.findElement(user).click();
		driver.findElement(viewAllButton).click();
	}
	
	
	@AfterClass
	public static void afterClass(){
		driver.quit();
	}

}

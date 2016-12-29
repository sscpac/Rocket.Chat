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

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class userChannelProfile {
	
	public static WebDriver driver;
	public static String HOME_URL = "http://localhost:3000";
	
	private static By usernameOrEmailFieldLocator = By.id("emailOrUsername");
	private static By passwordFieldLocator = By.id("pass");
	private static By loginButtonLocator = By.cssSelector("button.button.primary.login");
	
	@BeforeClass
	public static void beforeClass(){
		driver = new SafariDriver();
		driver.get(HOME_URL);
		 driver.findElement(usernameOrEmailFieldLocator).sendKeys("test");
		    driver.findElement(passwordFieldLocator).sendKeys("test");
		    driver.findElement(loginButtonLocator).click();
		    //Thread.sleep(1000);
	}

	@Test
	public void test() {
		
	}

}

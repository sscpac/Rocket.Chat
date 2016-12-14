package RocketChatTestPackage;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

public class UnitTest {
	static WebDriver wd = RocketChatTestPackage.ForgotPassTest.wd; 
		
		@BeforeClass
		public static void open(){
			wd = new SafariDriver();
			System.out.println("Setup info: " + wd);
			System.out.println("Getting address");
			wd.get("http://localhost:3000");
			System.out.println("Try and find forgot password button");
			try{
				Thread.sleep(3000);
				wd.findElement(By.className("forgot-password")).click();
				System.out.println("Click successful");
			}catch (Exception e){
				System.out.println("Error of: " + e.getMessage());
			}
		}
		
		@Before
		public void inputText() throws InterruptedException {
		ForgotPassTest nullInput = new ForgotPassTest();
		//Thread.sleep(3000);
		System.out.println("Check to find email box to confirm correct page");
			try{
			Thread.sleep(3000);
			wd.findElement(By.id("email"));
			System.out.println("You're on the right page\n");
			}
			catch (Exception e){
			System.out.println("Note: You are currently NOT on the Forgot Password Page... Redirecting to the right page\n");
			wd.findElement(By.className("forgot-password")).click();
			}		
		}
		
		//Check if input is empty or null
		@Test
		public void nullInput(){			
			WebElement email = wd.findElement(By.id("email"));
			email.sendKeys("   ");
				
			System.out.println("Testing if input is empty...\n Found email box and entered empty spaces...");
			
			wd.findElement(By.className("button primary login")).click();
			System.out.println("Just clicked on Reset Password");
			
			String emailError = wd.findElement(By.className("input-error")).getText();
			System.out.println("Checking if error message appears\n");
			System.out.println("There is an error that says '" + emailError + "'!\n");
			assertEquals("The email entered is invalid", emailError);
		}
		
		//Check if input is invalid ("lkjs!&@lkg.com") NO
		@Test
		public void invalidEntry(){			
			WebElement email = wd.findElement(By.id("email"));
			email.sendKeys(" HI ");
			
			System.out.println("Testing if input is invalid...\n Found email box and entered HI...");
			
			wd.findElement(By.className("button primary login")).click();
			System.out.println("Just clicked on Reset Password");
			
			String emailError = wd.findElement(By.className("input-error")).getText();
			System.out.println("Checking if error message appears\n");
			System.out.println("There is an error that says '" + emailError + "'!\n");
			assertEquals("The email entered is invalid", emailError);
		}		
		
		//Check if input is valid ("lkjs!&@lkg.com") YES
		@Test
		public void validEntry(){
			WebElement email = wd.findElement(By.id("email"));
			email.sendKeys("lkja333lskjflja@gmail.com");	
			System.out.println("Testing if input is valid...\n Found email box and entered lkja333lskjflja@gmail.com...");		
			wd.findElement(By.className("button primary login")).click();
			System.out.println("Just clicked on Reset Password");
			
			try{
				Thread.sleep(2000);
				String emailConfirmation = wd.findElement(By.className("toast-message")).getText();
				System.out.println("Email Confirmation Message Appeared\n");
				System.out.println("The message should say '" + emailConfirmation + "'!\n");
			}catch (Exception e){
				System.out.println("Email Confirmation Message did NOT appear!\n");
				}
		}
		
		//Clear email fields
		@After
		public void clear(){
			try {
				wd.findElement(By.id("email")).clear();
			} catch (Exception e) {
				System.out.println("Moving to next step...\n");
			}
		}
		
		//End all tests and close server
		@AfterClass
	    public static void end(){
			System.out.println("All tests finished\n");
			wd.quit();
		}
}

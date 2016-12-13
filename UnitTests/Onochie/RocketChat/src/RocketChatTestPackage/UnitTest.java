package RocketChatTestPackage;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UnitTest {
	static WebDriver wd = RocketChatTestPackage.ForgotPassTest.wd; 
		@Before	
		public void inputText() {
		
		
		WebElement email = wd.findElement(By.id("email"));
				email.sendKeys(" HI ");
		System.out.println("Found email box and entered empty spaces...");
//		WebElement password = wd.findElement(By.id("password"));
//				password.sendKeys("   ");
	}
		
//	Check if input is empty or null
		@Test
		public void nullInput(){
			ForgotPassTest nullInput = new ForgotPassTest();
			System.out.println("Testing if input is empty...\n");
			
			wd.findElement(By.className("button primary login")).click();
			System.out.println("Just clicked on Reset Password\n");
			
			String emailError = wd.findElement(By.className("input-error")).getText();
			System.out.println("Checking if error message appears\n");
			System.out.println("The error should say '" + emailError + "'!\n");
			assertEquals("The email entered is invalid", emailError);
			System.out.println("Test finished\n");
			wd.quit();
			
		}
		
////	Check if input is valid ("lkjs!&@lkg.com")
//		@Test
//		public void validEntry(){
//			
//			
//		}

}

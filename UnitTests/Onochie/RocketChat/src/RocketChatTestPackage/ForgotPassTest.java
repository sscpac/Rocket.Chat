package RocketChatTestPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

public class ForgotPassTest {
	public static WebDriver wd; 
	
	
	public static void main(String[] args) throws Exception {
		
		openServer();
		System.out.println("Try and find forgot password button");
		try{
			Thread.sleep(5000);
			wd.findElement(By.className("forgot-password")).click();
			System.out.println("Click successful");
		}catch (Exception e){
			System.out.println("Error of: " + e.getMessage());
			wd.quit();
		}
		
		org.junit.runner.JUnitCore.main("RocketChatTestPackage.UnitTest");
		
		Thread.sleep(3000);
		end();
	}
	
	private static void openServer(){
		wd = new SafariDriver();
		System.out.println("Setup info: " + wd);
		System.out.println("Getting address");
		wd.get("http://localhost:3000");
	}
	
	private static void end(){
		wd.quit();
		System.out.println("Reached end of test, quitting.");
	}
		
		

}

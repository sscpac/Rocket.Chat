package RocketChatTestPackage;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

public class ForgotPassTest {
	public static WebDriver wd; 
	
	
	public static void main(String[] args) throws Exception {
		org.junit.runner.JUnitCore.main("RocketChatTestPackage.UnitTest");
		
		//openServer();		
		
		Thread.sleep(3000);
		end();
		}
	
//	private static void openServer(){
//		wd = new SafariDriver();
//		System.out.println("Setup info: " + wd);
//		System.out.println("Getting address");
//		wd.get("http://localhost:3000");
//		}
	
	private static void end(){
		wd.quit();
		System.out.println("Reached end of test, quitting.");
		}
		
		

}

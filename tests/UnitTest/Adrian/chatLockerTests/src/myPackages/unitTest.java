package myPackages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

public class unitTest {
	public static WebDriver driver; 
	private static String testUrl = "https://demo.rocket.chat";
	
	//do not run this for now just run each test individually until this file is configured in the future.
	
//	public static void main(String[] args) throws Exception {
//		doConnect();
//		
//		//org.junit.runner.JUnitCore.main("myPackages.registerNewUserTest");
//		
//		doEnd();
//	}
//	
//	private static void doConnect() {
//		System.setProperty(
//				"webdriver.chrome.driver", 
//				"C:/Users/Public/Documents/Work/Eclipse/Libraries/chromedriver.exe"
//				);
//		//driver = new ChromeDriver();
//		driver = new SafariDriver();
//		driver.get(testUrl);
//	}
//	
//	private static void doEnd(){
//        driver.close();
//        driver.quit();
//	}

}

package myPackages;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

@RunWith(Suite.class)
@SuiteClasses({ 
		
		registerNewUserTest.class,
		loginTest.class,
		forgotPassTest.class
		
	})

public class AllTests {
	public static WebDriver driver;
	
	@BeforeClass
	public static void setupDriver(){
		driver = new SafariDriver();
	}

	@AfterClass
	public static void afterClass(){
		driver.close();		//closes browser
		driver.quit();		//quits browser and selenium drivers
	}


}


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
		
		LoginSuite.class
	})


//need to make this into one suite for login

public class AllSuites {
	public static WebDriver driver;
	public static String HOME_URL = "http://localhost:3000";
	
	@BeforeClass
	public static void driverSetup(){
		driver = new SafariDriver();
	}

	@AfterClass
	public static void afterClass(){
		driver.close();		//closes browser
		driver.quit();		//quits browser and selenium drivers
	}
	
}


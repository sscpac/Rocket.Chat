package testRocketChatPackage.login;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
 
// These Unit Tests check for the Login/Logout feature it assumes the 
// admin user (defaultAdmin.java) in the config package
     
public class loginTest {
    public static WebDriver driver;
    
    //Editable Strings
    public static String VALID_USERNAME = 	testRocketChatPackage.config.DefaultAdmin.username;
    public static String VALID_PASSWORD = 	testRocketChatPackage.config.DefaultAdmin.password;
    public static String VALID_EMAIL = 		testRocketChatPackage.config.DefaultAdmin.email;
    public static String ERROR_MESSAGE = 	"User not found or incorrect password";
    public static String HOME_TITLE = 		"Home";
    
    public static String INVALID_USERNAME = "andnsan";
    public static String INVALID_PASSWORD = "jsgdjhasd";
    public static String INVALID_EMAIL = "jksfh@mail.com";

    
    //declare all your elements here using By it will make your code cleaner and simple
    private static By usernameOrEmailFieldLocator = By.id("emailOrUsername");
    private static By passwordFieldLocator = By.id("pass");
    private static By loginButtonLocator = By.className("button primary login");
    private By BannerLocator = By.id("toast-container");
    private static By HomeHeaderLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/section/header/h2/span");
    private static By MenuLocator = By.cssSelector("span.arrow.bottom");
    private static By logoutLocator = By.id("logout"); //#logout
    
    
    /*
     * Use to login with username and password (goes to default page ie. Home)
     */
    
    public static void login(String username, String password, WebDriver drive){
    	drive.get(testRocketChatPackage.config.settings.homepageUrl);
    	drive.findElement(usernameOrEmailFieldLocator).sendKeys(username);
    	drive.findElement(passwordFieldLocator).sendKeys(password);
    	drive.findElement(loginButtonLocator).click();
    }
    
    /*
     * 	Use to logout from anywehre (goes to main menu> logout)
     */
    
    public static void logout(WebDriver drive) {
    	//Try and locate main menu
		if(detectElement(MenuLocator, drive)){
			drive.findElement(MenuLocator).click();
		}
    	drive.findElement(logoutLocator).click();
    }
    
	private static boolean detectElement(By element, WebDriver drive) {
		
		boolean ElementDetected = drive.findElements(element).size() > 0;
		if (ElementDetected) {
			return true;
		}
		else {
			return false;
		}
	}
     
    private static void inputLoginFieldsWith(String usernameOrEmail, String password){
        driver.findElement(usernameOrEmailFieldLocator).sendKeys(usernameOrEmail);
        driver.findElement(passwordFieldLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();
    }
    
    public static void setupDriver(String typeOf){
    	
    	if(typeOf == "safari"){	
    		driver = new SafariDriver();
    	}
    	if(typeOf == "chrome"){	
    		System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
    		driver = new ChromeDriver();
    	}
    	if(typeOf == "firefox"){	
    		System.setProperty("webdriver.gecko.driver", "/path/to/geckodriver.exe");
    		driver = new FirefoxDriver();
    	}
    	driver.manage().window().maximize();
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

     
    @BeforeClass //this means the method below will be called Once before this whole Class
    public static void beforeClass(){
    	setupDriver("safari");
    }
     
    @Before
	public void getThePageWeWantToTest(){
    	driver.get(testRocketChatPackage.config.settings.homepageUrl);
    }
     
    @AfterClass //this will be called After ALL tests complete
    public static void closeDriver() {
        driver.quit(); 
    }
 
    @Test //1 //this is a Unit Test Identifier
    public void invalidUsernameWithValidPassLoginShouldFail() { //notice the Should Fail and descriptive name, dont be shy!
        //I simply pass in my predefined conditions that we planned in the table above
        inputLoginFieldsWith(INVALID_USERNAME, VALID_PASSWORD); //i call the method to simplify each test
        String bannerMessage = driver.findElement(BannerLocator).getText();
        //when it does appear we will compare what we expect for it to display when a login fails
        Assert.assertEquals(bannerMessage, ERROR_MESSAGE);
    }
     
    @Test //2
    public void ValidUsernameWithInvalidPasswordShouldFail() {
        inputLoginFieldsWith(VALID_USERNAME, INVALID_PASSWORD);
        String bannerMessage = driver.findElement(BannerLocator).getText();
        Assert.assertEquals(bannerMessage, ERROR_MESSAGE);
    }
     
    @Test //3
    public void invalidUsernameAndInvalidPasswordShouldFail() {
        inputLoginFieldsWith(INVALID_USERNAME, INVALID_PASSWORD);
        String bannerMessage = driver.findElement(BannerLocator).getText();
        Assert.assertEquals(bannerMessage, ERROR_MESSAGE);
    }
     
    @Test //4
    public void invalidEmailWithValidPasswordShouldFail() {
        inputLoginFieldsWith(INVALID_EMAIL, VALID_PASSWORD);
        String bannerMessage = driver.findElement(BannerLocator).getText();
        Assert.assertEquals(bannerMessage, ERROR_MESSAGE);
    }
     
    @Test //5
    public void validEmailWithInvalidPasswordShouldFail() {
        inputLoginFieldsWith(VALID_EMAIL, INVALID_PASSWORD);
        String bannerMessage = driver.findElement(BannerLocator).getText();
        Assert.assertEquals(bannerMessage, ERROR_MESSAGE);
    }
     
    @Test //6
    public void invalidEmailWithInvalidPasswordShouldFail() {
        inputLoginFieldsWith(INVALID_EMAIL, INVALID_PASSWORD);
        String bannerMessage = driver.findElement(BannerLocator).getText();
        Assert.assertEquals(bannerMessage, ERROR_MESSAGE);
    }
     
    @Test //7
    public void ValidUsernameWithValidPasswordShouldPass() throws Exception {
        inputLoginFieldsWith(VALID_USERNAME, VALID_PASSWORD);
        String homeHeader = driver.findElement(HomeHeaderLocator).getText();
        Assert.assertEquals(homeHeader, HOME_TITLE);
        logout(driver);
    }
    
     
    @Test //8
    public void ValidEmailWithValidPasswordShouldPass() throws Exception {
        inputLoginFieldsWith(VALID_EMAIL, VALID_PASSWORD);
        String homeHeader = driver.findElement(HomeHeaderLocator).getText();
        Assert.assertEquals(homeHeader, HOME_TITLE);
        logout(driver);
    }  
     
}
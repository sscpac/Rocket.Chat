package myPackages;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 
//These Unit Tests check for the Login feature it assumes the user of the
//following credentials is valid
//  username: someUserName
//  password: secretPassword123
//  email: validEmail@gmail.com

     
public class loginTest {
    public static WebDriver driver;
    
    //Editable Strings
    public static String URL_CHATLOCKER_MAIN = "http://localhost:3000";
    public static String VALID_USERNAME = "someUserName";
    public static String VALID_PASSWORD = "secretPassword123";
    public static String VALID_EMAIL = "validEmail@gmail.com";
    public static String ERROR_MESSAGE = "User not found or incorrect password";
    public static String HOME_TITLE = "Home";
    
    public static String INVALID_USERNAME = "andnsan";
    public static String INVALID_PASSWORD = "jsgdjhasd";
    public static String INVALID_EMAIL = "jksfh@gmail.com";

    
    //declare all your elements here using By it will make your code cleaner and simple
    private By usernameOrEmailFieldLocator = By.id("emailOrUsername");
    private By passwordFieldLocator = By.id("pass");
    private By loginButtonLocator = By.className("button primary login");
    private By BannerLocator = By.id("toast-container");
    private By HomeHeaderLocator = By.xpath("//*[@id=\"rocket-chat\"]/div[3]/section/header/h2/span");
     
    private void inputLoginFieldsWith(String usernameOrEmail, String password){
        driver.findElement(usernameOrEmailFieldLocator).sendKeys(usernameOrEmail);
        driver.findElement(passwordFieldLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();
    }
     
     
    @BeforeClass //this means the method below will be called Once before this whole Class
    public static void setupTheDriverObject(){
    	driver = new SafariDriver();
    }
     
    @Before
	public void getThePageWeWantToTest(){
        //I just refresh page since it begins each test with a controlled starting point
        //more in-depth unit testing within the app will have to manually login and navigate
        //to your components
        driver.get(URL_CHATLOCKER_MAIN);
        //this tells the driver to wait for the username/email field element to load otherwise it will fail the test within 10 secs
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(usernameOrEmailFieldLocator));
         
    }
     
    //this has been moved to AllTests.java
    @AfterClass //this will be called After ALL tests complete
    public static void closeDriver(){
        driver.quit(); //closes driver and browser window
    }
 
    @Test //1 //this is a Unit Test Identifier
    public void invalidUsernameWithValidPassLoginShouldFail() { //notice the Should Fail and descriptive name, dont be shy!
        //I simply pass in my predefined conditions that we planned in the table above
        inputLoginFieldsWith(INVALID_USERNAME, VALID_PASSWORD); //i call the method to simplify each test
        //this will wait for the notification banner to appear
        WebElement banner = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(BannerLocator));
        //when it does appear we will compare what we expect for it to display when a login fails
        Assert.assertEquals(banner.getText(), ERROR_MESSAGE);
    }
     
    @Test //2
    public void ValidUsernameWithInvalidPasswordShouldFail() {
        inputLoginFieldsWith(VALID_USERNAME, INVALID_PASSWORD);
        WebElement banner = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(BannerLocator));
        Assert.assertEquals(banner.getText(), ERROR_MESSAGE);
    }
     
    @Test //3
    public void invalidUsernameAndInvalidPasswordShouldFail() {
        inputLoginFieldsWith(INVALID_USERNAME, INVALID_PASSWORD);
        WebElement banner = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(BannerLocator));
        Assert.assertEquals(banner.getText(), ERROR_MESSAGE);
    }
     
    @Test //4
    public void invalidEmailWithValidPasswordShouldFail() {
        inputLoginFieldsWith(INVALID_EMAIL, VALID_PASSWORD);
        WebElement banner = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(BannerLocator));
        Assert.assertEquals(banner.getText(), ERROR_MESSAGE);
    }
     
    @Test //5
    public void validEmailWithInvalidPasswordShouldFail() {
        inputLoginFieldsWith(VALID_EMAIL, INVALID_PASSWORD);
        WebElement banner = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(BannerLocator));
        Assert.assertEquals(banner.getText(), ERROR_MESSAGE);
    }
     
    @Test //6
    public void invalidEmailWithInvalidPasswordShouldFail() {
        inputLoginFieldsWith(INVALID_EMAIL, INVALID_PASSWORD);
        WebElement banner = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(BannerLocator));
        Assert.assertEquals(banner.getText(), ERROR_MESSAGE);
    }
     
    @Test //7
    public void ValidUsernameWithValidPasswordShouldPass() {
        //this case uses valid inputs to login
        inputLoginFieldsWith(VALID_USERNAME, VALID_PASSWORD);
        //when we do login I will wait at least 10 seconds for the tile component in the next page to load which is <h2>Home</h2>
        WebElement HomeHeader = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(HomeHeaderLocator));
        //When it has loaded lets compare and ensure it is Home
        Assert.assertEquals(HomeHeader.getText(), HOME_TITLE);
    }
    
     
    @Test //8
    public void ValidEmailWithValidPasswordShouldPass() {
        inputLoginFieldsWith(VALID_EMAIL, VALID_PASSWORD);
        WebElement HomeHeader = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(HomeHeaderLocator));
        Assert.assertEquals(HomeHeader.getText(), HOME_TITLE);
    }  
     
}
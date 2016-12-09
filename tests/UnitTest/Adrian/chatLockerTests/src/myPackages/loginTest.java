package myPackages;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 
//These Unit Tests check for the Login feature it assumes the user of the
//following credentials is valid
//  username: adrian
//  password: adrian
//  email: adrian@gmail.com
     
public class loginTest {
    public static WebDriver driver;
     
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
    public void setupTheDriverObject(){
        driver = new SafariDriver();
    }
     
    @BeforeMethod
	public void getThePageWeWantToTest(){
        //I just refresh page since it begins each test with a controlled starting point
        //more in-depthunit testing within the app will have to manually login and navigate
        //to your components
        driver.get("http://localhost:3000");
        //this tells the driver to wait for the username/email field element to load otherwise it will fail the test within 10 secs
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(usernameOrEmailFieldLocator));
         
    }
     
    @AfterClass //this will be called After ALL tests complete
    public static void closeDriver(){
        driver.quit(); //closes driver and browser window
    }
 
    @Test //1 //this is a Unit Test Identifier
    public void invalidUsernameWithValidPassLoginShouldFail() { //notice the Should Fail and descriptive name, dont be shy!
        //I simply pass in my predefined conditions that we planned in the table above
        inputLoginFieldsWith("andnsan", "adrian"); //i call the method to simplify each test
        //this will wait for the notification banner to appear
        WebElement banner = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(BannerLocator));
        //when it does appear we will compare what we expect for it to display when a login fails
        AssertJUnit.assertEquals(banner.getText(), "User not found or incorrect password");
    }
     
    @Test //2
    public void ValidUsernameWithInvalidPasswordShouldFail() {
        inputLoginFieldsWith("adrian", "jsgdjhasd");
        WebElement banner = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(BannerLocator));
        AssertJUnit.assertEquals(banner.getText(), "User not found or incorrect password");
    }
     
    @Test //3
    public void invalidUsernameAndInvalidPasswordShouldFail() {
        inputLoginFieldsWith("andnsan", "jsgdjhasd");
        WebElement banner = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(BannerLocator));
        AssertJUnit.assertEquals(banner.getText(), "User not found or incorrect password");
    }
     
    @Test //4
    public void invalidEmailWithValidPasswordShouldFail() {
        inputLoginFieldsWith("jksfh@gmail.com", "adrian");
        WebElement banner = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(BannerLocator));
        AssertJUnit.assertEquals(banner.getText(), "User not found or incorrect password");
    }
     
    @Test //5
    public void validEmailWithInvalidPasswordShouldFail() {
        inputLoginFieldsWith("adrian@gmail.com", "hghjbjhg");
        WebElement banner = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(BannerLocator));
        AssertJUnit.assertEquals(banner.getText(), "User not found or incorrect password");
    }
     
    @Test //6
    public void invalidEmailWithInvalidPasswordShouldFail() {
        inputLoginFieldsWith("jksfh@gmail.com", "jgfhgvn");
        WebElement banner = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(BannerLocator));
        AssertJUnit.assertEquals(banner.getText(), "User not found or incorrect password");
    }
     
    @Test //7
    public void ValidUsernameWithValidPasswordShouldPass() {
        //this case uses valid inputs to login
        inputLoginFieldsWith("adrian", "adrian");
        //when we do login I will wait at least 10 seconds for the tile component in the next page to load which is <h2>Home</h2>
        WebElement HomeHeader = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(HomeHeaderLocator));
        //When it has loaded lets compare and ensure it is Home
        AssertJUnit.assertEquals(HomeHeader.getText(), "Home");
    }
     
    @Test //8
    public void ValidEmailWithValidPasswordShouldPass() {
        inputLoginFieldsWith("adrian@gmail.com", "adrian");
        WebElement HomeHeader = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(HomeHeaderLocator));
        AssertJUnit.assertEquals(HomeHeader.getText(), "Home");
    }  
     
}
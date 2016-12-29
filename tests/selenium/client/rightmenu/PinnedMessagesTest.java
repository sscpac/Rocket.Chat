/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  * * *
 * @organization: SPAWAR System Center Pacific                             *
 * @usage:        Automated Application Testing using Selenium Webdriver   *
 * @environment:  Eclipse Neon.2 IDE for Java Developers v4.6.2/64-bit     *
 * @plugins:      Maven Integration for Eclipse v1.7.0                     *
 * @browser:      Google Chrome v55.0.0/64-bit (See line: 61)              *
 * @application:  Rocket.Chat v0.48.2/64-bit                               *
 * @filename:     PinnedMessagesTest.java                                  *
 * @feature:      'Pinned Messages' tab on the left panel                  *
 *                JUnit v5.0.0                                             *
 *                                                                         *
 * NOTE:    By default Maven uses the following naming conventions for     *
 *          the class file when looking for tests to run:                  *
 *                                                                         *
 *              Test*, *Test, or *TestCase                                 *
 *                                                                         *
 *          Otherwise it will NOT recognize the test in this program.      *
 *                                                                         *
 *          Based on a Behavior Driven Design.                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package myPackage;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.safari.SafariDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class PinnedMessagesTest {  
    
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Variables                                                                                                    *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
        // private static String firefoxDriver = "webdriver.firefox.driver"; 
        // private static String safariDriver = "webdriver.safari.driver"; 
        // private static String firefoxDirectory = "C:\\Users\\Kent\\Documents\\Workspace\\Libraries\\geckodriver.exe";
        // private static String safariDirectory = "C:\\Users\\Kent\\Documents\\Workspace\\Libraries\\safaridriver.exe";

        private static WebDriver driver;
        private static String chromeDriver = "webdriver.chrome.driver";
        private static String generalChannel = "http://localhost:3000/channel/general";
        // Change the string "Hello World!" to test with a different message.
        private static String sampleMessage = "Hello World!";
        // Change to the appropriate driver directory.
        private static String chromeDirectory = "C:\\Users\\Kent\\Documents\\Workspace\\Libraries\\chromedriver.exe";
        
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Page Elements                                                                                                *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        
        // Elements involved in this feature are obtained by the optimimal performance made available.
        // Best to worse case: ID > Class > CSS > XPath
        private static By password = By.id("pass");
        private static By username = By.id("emailOrUsername");
        private static By loginButton = By.className("submit");
        private static By inputMessageBox = By.className("input-message");
        private static By sendMessageButton = By.className("message-buttons");
        private static By pinMessageButton = By.className("icon-pin");
        private static By pinnedMessageTabButton = By.className("icon-pin");
        private static By jumpToPinnedMessageButton = By.className("icon-right-hand");
        private static By roomMessageCogButton = By.className("icon-cog");
        private static By pinnedMessageCogButton = By.className("icon-cog");
        private static By unpinMessageButton = By.className("unpin-message");
        private static By permalinkButton = By.className("icon-link");
        
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Page Objects                                                                                                 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    //  public static void firefox() {
    //      System.setProperty(firefoxDriver,firefoxDirectory);
    //      driver = new firefoxDriver();
    //  }
      
    //  public static void safari() {
    //      System.setProperty(safariDriver,safariDirectory);
    //      driver = new safariDriver();
    //  }
        
        public static void chrome() {
            System.setProperty(chromeDriver,chromeDirectory);
            driver = new ChromeDriver();
        }
        
        public static void userLogin(String myUsername, String myPassword) {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(generalChannel);
            driver.findElement(username).sendKeys(myUsername);
            driver.findElement(password).sendKeys(myPassword);
            driver.findElement(loginButton).click();
        }
        
        public void sendMessageToChannel(String messageToSend) {
            driver.findElement(inputMessageBox).clear();
            driver.findElement(inputMessageBox).sendKeys(messageToSend);
            driver.findElement(sendMessageButton).click();
        }
        
        public void roomMessageCogButton() throws Exception {
            List<WebElement> hiddenElement = driver.findElements(roomMessageCogButton);
            JavascriptExecutor executer = (JavascriptExecutor)driver;
                executer.executeScript("arguments[0].click();", hiddenElement.get(0));
        }
        
        public void pinMessageButton() throws Exception {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(pinMessageButton));
            List<WebElement> hiddenElement = driver.findElements(pinMessageButton);
            JavascriptExecutor executer = (JavascriptExecutor)driver;
                executer.executeScript("arguments[0].click();", hiddenElement.get(1));
        }
        
        public void pinnedMessageTabButton() throws Exception {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(pinnedMessageTabButton));
            driver.findElement(pinnedMessageTabButton).click();
        }
        
        public void pinnedMessageCogButton() throws Exception {
            List<WebElement> hiddenElement = driver.findElements(pinnedMessageCogButton);
            JavascriptExecutor executer = (JavascriptExecutor)driver;
                executer.executeScript("arguments[0].click();", hiddenElement.get(2));
        }
        
        public void permalinkButton() throws Exception {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(permalinkButton));
            driver.findElement(permalinkButton).click();
        }
        
        public void jumpToPinnedMessageButton() throws Exception {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(jumpToPinnedMessageButton));
            driver.findElement(jumpToPinnedMessageButton).click();
        }

        public void unpinMessageButton() throws Exception {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(unpinMessageButton));
            driver.findElement(unpinMessageButton).click();
        }

        
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Test                                                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        
        @BeforeClass            
        public static void beforeTest() {
            chrome();
            // firefox();
            // safari();
            userLogin("kent","kent");
        }
        
        @Rule
        public TestRule watcher = new TestWatcher() {
            protected void starting(Description description) {
                System.out.println("Starting test: " + description.getMethodName());
            }
        };
        
        @Test // #1
        public void testA_SendsMessageThenPin() throws Exception {
            sendMessageToChannel(sampleMessage);
            roomMessageCogButton();
            pinMessageButton();
        }
        
        @Test // #2
        public void testB_CopyMessageLink() throws Exception {
            pinnedMessageTabButton();
            pinnedMessageCogButton();
            permalinkButton();
        }
        
        @Test // #3
        public void testC_JumpToMessage() throws Exception {
            pinnedMessageCogButton();
            jumpToPinnedMessageButton();
        }
        
        @Test // #4
        public void testD_UnpinMessage() throws Exception {
            pinnedMessageCogButton();
            unpinMessageButton();
        }
}

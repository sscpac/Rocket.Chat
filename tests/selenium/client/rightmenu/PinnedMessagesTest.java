package testRocketChatPackage.rightmenu;

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
import org.openqa.selenium.safari.SafariDriver;

/*
 * @TODO: Need to fix errors in all tests (not working properly. Cannot open mini message menu)
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class PinnedMessagesTest {  
    
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Variables                                                                                                    *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
        // private static String firefoxDriver = "webdriver.firefox.driver"; 
        // private static String safariDriver = "webdriver.safari.driver"; 
        // private static String firefoxDirectory = "Insert directory here.";
        // private static String safariDirectory = "Insert directory here.";

        private static WebDriver driver;
        private static String chromeDriver = "webdriver.chrome.driver";
        private static String generalChannel = "http://localhost:3000/channel/general";
        // Change the string "Hello World!" to test with a different message.
        private static String sampleMessage = "Hello World!";
        // Change to the appropriate driver directory.
        private static String chromeDirectory = "Insert directory here.";
        
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
      
        public static void safari() {     
        	driver = new SafariDriver();
        }
        
//        public static void chrome() {
//            System.setProperty(chromeDriver,chromeDirectory);
//            driver = new ChromeDriver();
//        }
        
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
            //chrome();
            // firefox();
            safari();
            userLogin("admin","admin");
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

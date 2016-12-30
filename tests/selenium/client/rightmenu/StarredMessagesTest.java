package myPackage;

import org.junit.*;
import java.util.List;
import org.junit.rules.*;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.junit.runner.Description;
import java.util.concurrent.TimeUnit;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.safari.SafariDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class StarredMessagesTest {  
    
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Variables                                                                                                    *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
        // private static String firefoxDriver = "webdriver.firefox.driver"; 
        // private static String safariDriver = "webdriver.safari.driver"; 
        // private static String firefoxDirectory = "Insert driver directory here.";
        // private static String safariDirectory = "Insert driver directory here.";
        private static WebDriver driver;
        // Initiated at 0 indicates the first activity at the top of the room will be tested. Change the value
        // accordingly to target a different one.
        private static int totalChannelActivity = 0;
        // Change the string "Hello World!" to test with a different message.
        private static String sampleMessage = "Hello World!";
        private static String chromeDriver = "webdriver.chrome.driver";
        private static String generalChannel = "http://localhost:3000/channel/general";
        // Change to the appropriate driver directory.
        private static String chromeDirectory = "Insert driver directory here.";
        
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Page Elements                                                                                                *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        
        // Elements involved in this feature are obtained by the optimimal performance made available. From the best to
        // the worse case: ID > Class > CSS > XPath
        private static By password = By.id("pass");
        private static By username = By.id("emailOrUsername");
        private static By loginButton = By.className("submit");
        private static By confirmButton = By.className("confirm");
        private static By permalinkButton = By.className("icon-link");
        private static By inputMessageBox = By.className("input-message");
        private static By roomMessageCogButton = By.className("icon-cog");
        private static By unstarMessageButton = By.className("icon-star");
        private static By starMessageButton = By.className("star-message");
        private static By starredMessageCogButton = By.className("icon-cog");
        private static By sendMessageButton = By.className("message-buttons");
        private static By starredMessageTabButton = By.className("icon-star");
        private static By deleteMessageButton = By.className("delete-message");
        private static By jumpToStarredMessageButton = By.className("icon-right-hand");
        
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
            for(int i = 0; i <= 2; i++) {
                try {
                    new WebDriverWait(driver, 10).until(
                            ExpectedConditions.presenceOfElementLocated(roomMessageCogButton));
                    List<WebElement> hiddenElement = driver.findElements(roomMessageCogButton);
                    JavascriptExecutor executer = (JavascriptExecutor)driver;
                    executer.executeScript("arguments[0].click();", hiddenElement.get(totalChannelActivity));
                    break;
                }
                catch(Exception e) { System.out.println(e.getMessage()); }
            }
        }
        
        public void starMessageButton() throws Exception {
            for(int i = 0; i <= 2; i++) {
                try {
                    new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(starMessageButton));
                    driver.findElement(starMessageButton).click();
                    break;
                }
                catch(Exception e) { System.out.println(e.getMessage()); }
            }
        }
        
        public void starredMessageTabButton() throws Exception {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(starredMessageTabButton));
            driver.findElement(starredMessageTabButton).click();
        }
        
        public void starredMessageCogButton() throws Exception {
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(starredMessageCogButton));
            List<WebElement> hiddenElement = driver.findElements(starredMessageCogButton);
            JavascriptExecutor executer = (JavascriptExecutor)driver;
            executer.executeScript("arguments[0].click();", hiddenElement.get(1));
        }
        
        public void permalinkButton() throws Exception {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(permalinkButton));
            driver.findElement(permalinkButton).click();
        }
        
        public void jumpToStarredMessageButton() throws Exception {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(jumpToStarredMessageButton));
            driver.findElement(jumpToStarredMessageButton).click();
        }

        public void unstarMessageButton() throws Exception {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(unstarMessageButton));
            driver.findElement(unstarMessageButton).click();
        }

        
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Test                                                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        
        @Rule
        public TestRule watcher = new TestWatcher() {
            protected void starting(Description description) {
                System.out.println("Starting test: " + description.getMethodName());
            }
        };
        
        @BeforeClass            
        public static void beforeTest() { chrome(); userLogin("test","test"); } // firefox(); safari();
        
        @AfterClass
        public static void deleteMessageAndExit() {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(deleteMessageButton));
            driver.findElement(deleteMessageButton).click();
            driver.findElement(confirmButton).click();
            driver.close();
            driver.quit();
        }
        
        @Test // #1
        public void testA_StarTheMessage() throws Exception {
            // Assuming channel #general is empty to begin with.
            sendMessageToChannel(sampleMessage);    
            roomMessageCogButton();
            starMessageButton();
        }
        
        @Test // #2
        public void testB_CpenStarredMessagesTab() throws Exception { starredMessageTabButton(); }
        
        @Test // #3
        public void testC_CopyMessageLink() throws Exception { starredMessageCogButton(); permalinkButton(); }
        
        @Test // #4
        public void testD_JumpToMessage() throws Exception { starredMessageCogButton(); jumpToStarredMessageButton(); }
        
        @Test // #5
        public void testE_UnstarMessage() throws Exception { starredMessageCogButton(); unstarMessageButton(); }
        
        @Test // #6
        public void testF_CloseStarredMessagesTab() throws Exception {
            starredMessageTabButton();
            roomMessageCogButton();
        }
}

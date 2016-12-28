/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  * * *
 * @organization: SPAWAR System Center Pacific                             *
 * @usage:        Automated Application Testing using Selenium Webdriver   *
 * @environment:  Eclipse Neon.2 IDE for Java Developers v4.6.2/64-bit     *
 * @plugins:      Maven Integration for Eclipse v1.7.0                     *
 * @browser:      Google Chrome v55.0.0/64-bit (See line: 61)              *
 * @application:  Rocket.Chat v0.48.2/64-bit                               *
 * @filename:     SearchMessagesTest.java                                  *
 * @feature:      'Search Messages' tab on the left panel                  *
 *                JUnit v4.12.0                                            *
 *                                                                         *
 * NOTE:    By default Maven uses the following naming conventions for     *
 *          the class file when looking for tests to run:                  *
 *                                                                         *
 *              Test*, *Test, or *TestCase                                 *
 *                                                                         *
 *          Otherwise it will NOT recognize the test in this program       *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package myPackage;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public final class SearchMessagesTest {  
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Declarations                                                                                                 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        private static WebDriver driver;
        // Insert your Rocket.Chat username & password.
        private static String myUsername = "kvo";    
        private static String myPassword = "kent";  
        private static String generalChannel = "http://localhost:3000/channel/general";
        // Elements involved in this feature are obtained by the optimimal performance made available.
        // Best to worse case: ID > Class > CSS > XPath
        private static By password = By.id("pass");
        private static By username = By.id("emailOrUsername");
        private static By loginButton = By.className("submit");
        private static By searchMessagesBox = By.id("message-search");
        private static By inputMessageBox = By.className("input-message");
        private static By sendMessageButton = By.className("message-buttons");
        private static By openSearchMessagesTab = By.cssSelector("div:nth-child(3) > button > i");
        private static By closeSearchMessagesTab = By.cssSelector("div.tab-button.active > button > i");
        private static By hasMoreButton = By.cssSelector(
                "div.main-content.flex-opened > div > div > section.flex-tab > div > ul > li.load-more > button");
        private static boolean elementExist(By element) { 
            if (driver.findElements(element).size() > 0) {return true;} else return false; }
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Test Helpers                                                                                                 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        @BeforeClass            
        public static void beforeTest() {
            // Change to the appropriate driver directory.
            System.setProperty(
                    "webdriver.chrome.driver","C:\\Users\\Kent\\Documents\\Workspace\\Libraries\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get(generalChannel);
            driver.findElement(username).sendKeys(myUsername);
            driver.findElement(password).sendKeys(myPassword);
            driver.findElement(loginButton).click();
        }
        
        public void sendMessageToChannel(String messageToSend) throws Exception {
            driver.findElement(inputMessageBox).clear();
            driver.findElement(inputMessageBox).sendKeys(messageToSend);
            driver.findElement(sendMessageButton).click();
        }
        
        public void inputToSearch(String messageToFind) throws Exception {
            driver.findElement(searchMessagesBox).sendKeys(messageToFind);
            if(elementExist(hasMoreButton)) { driver.findElement(hasMoreButton).click(); }
        }
        
        public void openSearchMessagesTab() {
            driver.findElement(openSearchMessagesTab).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(searchMessagesBox));
            driver.findElement(searchMessagesBox).clear();
        }
        
        @After
        public void closeSearchMessagesTab() {
            driver.findElement(closeSearchMessagesTab).click();
        }
        
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Test                                                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */ 
        // Change the string "Hello World!" to test for something different.
        @Test // #1
        public void sendsMessageThenSearch() throws Exception {
            sendMessageToChannel("Hello World!");
            openSearchMessagesTab();
            inputToSearch("Hello World!");
            String messageHistory = (driver.findElement(By.cssSelector("ul.list.clearfix"))).getText();
            messageHistory = messageHistory.replaceAll("\n", " ");
            assertThat(messageHistory, containsString("Hello World!"));
            System.out.println("Successfully found the message.");
        }
        
        @AfterClass
        public static void afterTest() {
            driver.close();
            driver.quit();
        }
}

package testRocketChatPackage.rightmenu;

import org.junit.*;
import java.util.List;
import org.junit.rules.*;
import org.openqa.selenium.*;
import org.junit.runner.Description;
import java.util.concurrent.TimeUnit;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class NotificationsPanelTest {  
	
/*
 * Tests Notifications Panel (Right Menu) and editing settings
 * @TODO: Tests 2-6 need to be completed and asserted.
 */
    
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Variables                                                                                                    *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
        // private static String firefoxDriver = "webdriver.firefox.driver"; 
        // private static String safariDriver = "webdriver.safari.driver"; 
        // private static String firefoxDirectory = "C:\\Users\\Kent\\Documents\\Workspace\\Libraries\\geckodriver.exe";
        // private static String safariDirectory = "C:\\Users\\Kent\\Documents\\Workspace\\Libraries\\safaridriver.exe";
        private static WebDriver driver;
        //private static String chromeDriver = "webdriver.chrome.driver";
        private static String generalChannel = "http://localhost:3000/channel/general";
        // Change to the appropriate driver directory.
        //private static String chromeDirectory = "C:\\Users\\Kent\\Documents\\Workspace\\Libraries\\chromedriver.exe";
        private static String testValue = "1";
        
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Page Elements                                                                                                *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        
        // Elements involved in this feature are obtained by the optimimal performance made available. From the best to
        // the worse case: ID > Class > CSS > XPath
        private static By password = By.id("pass");
        private static By saveButton = By.className("save");
        private static By username = By.id("emailOrUsername");
        private static By loginButton = By.className("submit");
        private static By desktopDurationOption = By.name("duration");
        private static By editOptionsButton = By.className("icon-pencil");
        private static By unreadAlertOptionsButton = By.name("unreadAlert");
        private static By emailOptionsButton = By.name("emailNotifications");
        private static By desktopOptionsButton = By.name("desktopNotifications");
        private static By notificationsTabButton = By.className("icon-bell-alt");
        private static By mobilePushOptionsButton = By.name("mobilePushNotifications");

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Page Objects                                                                                                 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    //  public static void firefox() {
    //      System.setProperty(firefoxDriver,firefoxDirectory);
    //      driver = new firefoxDriver();
    //  }
      
        public static void safari() {
	      	driver = new SafariDriver();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  	}
        
//        public static void chrome() {
//            System.setProperty(chromeDriver,chromeDirectory);
//            driver = new ChromeDriver();
//        }
        
        public static void userLogin(String myUsername, String myPassword) {
            driver.get(generalChannel);
            driver.findElement(username).sendKeys(myUsername);
            driver.findElement(password).sendKeys(myPassword);
            driver.findElement(loginButton).click();
        }

        public void notificationsTabButton() throws Exception {
            driver.findElement(notificationsTabButton).click();
        }
        
        public void editOptionsButton(int pointerValue) {
            //new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(editOptionsButton));
            List<WebElement> editOptionsPointer = driver.findElements(editOptionsButton);
            editOptionsPointer.get(pointerValue).click();
        }
        
        public void desktopOptionsButton(int pointerValue) {
        	//new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(desktopOptionsButton));
            List<WebElement> desktopOptionsPointer = driver.findElements(desktopOptionsButton);
            desktopOptionsPointer.get(pointerValue).click();
        }
        
        public void selectEachDesktopOptions() throws Exception {
            for(int i = 0; i < 3; i++) {
                editOptionsButton(0);
                desktopOptionsButton(i);
                driver.findElement(saveButton).click();
            }
        }
        
        public void desktopDurationOption() {
            editOptionsButton(0);
            //new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(desktopDurationOption));
            driver.findElement(desktopDurationOption).clear();
            driver.findElement(desktopDurationOption).sendKeys(testValue);
            driver.findElement(saveButton).click();
        }
        
        public void mobilePushOptionsButton(int pointerValue) throws Exception {
            //new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(mobilePushOptionsButton));
            List<WebElement> mobilePushOptionsPointer = driver.findElements(mobilePushOptionsButton);
            mobilePushOptionsPointer.get(pointerValue).click();
        }
        
        public void selectEachMobilePushOptions() throws Exception {
            for(int i = 0; i < 3; i++) {
                editOptionsButton(1);
                mobilePushOptionsButton(i);
                driver.findElement(saveButton).click();
            }
        }
        
        public void emailOptionsButton(int pointerValue) throws Exception {
            //new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(emailOptionsButton));
            List<WebElement> emailOptionsPointer = driver.findElements(emailOptionsButton);
            emailOptionsPointer.get(pointerValue).click();
        }
        
        public void selectEachEmailOptions() throws Exception {
            for(int i = 0; i < 4; i++) {
                editOptionsButton(2);
                emailOptionsButton(i);
                driver.findElement(saveButton).click();
            }
        }
        
        public void unreadAlertOptionsButton(int pointerValue) throws Exception {
            //new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(unreadAlertOptionsButton));
            List<WebElement> unreadAlertOptionsPointer = driver.findElements(unreadAlertOptionsButton);
            unreadAlertOptionsPointer.get(pointerValue).click();
        }
        
        public void selectEachUnreadAlertOptions() throws Exception {
            for(int i = 0; i < 3; i++) {
                editOptionsButton(3);
                unreadAlertOptionsButton(i);
                driver.findElement(saveButton).click();
            }
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
        public static void beforeClass() { 
        	safari(); // firefox(); chrome();
        	userLogin("admin","admin"); 
        	} 
        
        @AfterClass
        public static void afterClass() { 
        	driver.close(); 
        	driver.quit(); 
        }
        
        @Test // #1
        public void testA_OpenNotificationsTab() throws Exception { 
        	notificationsTabButton(); 
        }
        
        //@Test // #2
        public void testB_EditDesktopNotifications() throws Exception { 
        	notificationsTabButton();
            selectEachDesktopOptions();
            desktopDurationOption();
        }
        
        //@Test // #3
        public void testC_EditPushMobileNotifications() throws Exception { 
        	notificationsTabButton();
        	selectEachMobilePushOptions(); 
        }
        
        //@Test // #4
        public void testD_EditEmailNotifications() throws Exception { 
        	selectEachEmailOptions(); 
        }
        
        //@Test // #5
        public void testE_EditUnreadAlertSettings() throws Exception { 
        	selectEachUnreadAlertOptions(); 
        }
        
        //@Test // #6
        public void testF_CloseNotificationsTab() throws Exception { 
        	notificationsTabButton(); 
        }
}

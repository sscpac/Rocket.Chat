package testRocketChatPackage.rightmenu;

/*
 * Assumes a file is already uploaded by default (or automate the process if possible)
 * @TODO: All tests need to be asserted properly
 */

import org.junit.*;
import org.junit.rules.*;
import org.openqa.selenium.*;
import org.junit.runner.Description;
import java.util.concurrent.TimeUnit;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class FilesListTest {  
    
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Variables                                                                                                    *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
        // private static String firefoxDriver = "webdriver.firefox.driver"; 
        // private static String safariDriver = "webdriver.safari.driver"; 
        // private static String firefoxDirectory = "Insert driver directory here.";
        // private static String safariDirectory = "Insert driver directory here.";
        private static WebDriver driver;
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
        private static By openFileButton = By.className("file-name");
        private static By deleteFileButton = By.className("file-delete");
        private static By confirmButton = By.cssSelector("div.sa-confirm-button-container");
        private static By filesListTabButton = By.className("icon-attach");
        private static By downloadFileButton = By.cssSelector("i.icon-download.file-download");
        

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *      Page Objects                                                                                                 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    //  public static void firefox() {
    //      System.setProperty(firefoxDriver,firefoxDirectory);
    //      driver = new firefoxDriver();
    //  }
      
      public static void safari() {
          driver = new SafariDriver();
          driver.manage().window().maximize();
          driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

        public void filesListTabButton() throws Exception {
            driver.findElement(filesListTabButton).click();
        }
        
        public void fileExist() throws Exception {
        	driver.findElement(openFileButton);
        }
        
        public void openFileButton() throws Exception {
            driver.findElement(openFileButton).click();
        }
        
        public void downloadFileButton() throws Exception {
            driver.findElement(downloadFileButton).click();
        }
        
        public void deleteFileButton() throws Exception {
            driver.findElement(deleteFileButton).click();
        }
        
        public void confirmButton() throws Exception {
            driver.findElement(confirmButton).click();
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
        public static void afterClass(){
        	driver.close();
        	driver.quit();
        }
            
        @Test // #1
        public void testA_OpenFilesListTab() throws Exception { filesListTabButton(); }
        
        @Test // #2
        public void testB_FilesListNotEmpty() throws Exception { fileExist(); }
        
        @Test // #3
        public void testC_OpenFile() throws Exception { openFileButton(); }
        
        @Test // #4
        public void testD_DownloadFile() throws Exception { downloadFileButton(); }
        
        @Test // #5
        public void testE_DeleteFile() throws Exception { deleteFileButton(); }
}

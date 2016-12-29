package testRocketChatPackage.login;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	forgotPassTest.class, 			
	loginTest.class, 				
	registerNewUserTest.class 		
	})
public class LoginSuite {

}

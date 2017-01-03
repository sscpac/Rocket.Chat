package testRocketChatPackage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	testRocketChatPackage.login.LoginSuite.class,		
	//testRocketChatPackage.messages.MessagesSuite.class,		
	testRocketChatPackage.channel.ChannelSuite.class,
	testRocketChatPackage.rightmenu.RightMenuSuite.class,
	testRocketChatPackage.settings.SettingSuite.class
})
public class AllTests {

}

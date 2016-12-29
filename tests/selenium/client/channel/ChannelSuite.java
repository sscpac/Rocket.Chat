package testRocketChatPackage.channel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	ChannelsTest_2.class, 		//@TODO this will be refactored and combined into ChannelTest
	ChannelTest.class, 
	userChannelProfile.class 
	})
public class ChannelSuite {

}

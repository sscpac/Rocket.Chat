package testRocketChatPackage.rightmenu;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	rightMenuNavTest.class, 
	RoomInfo.class, 
	SearchMessagesTest.class })
public class RightMenuSuite {

}

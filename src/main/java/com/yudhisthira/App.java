package com.yudhisthira;

/**
 * 
 * @author yudhisthira
 *
 * App class which has entry function of program
 */
public class App 
{
    public static void main( String[] args )
    {	
    	//we need to pass 3 parameter to PlayerController constructor.
    	// First is the message which we want to sent to another player
    	// Second is number of message to be processed or send
    	// Third is added only debugging purpose. Its is time delay between 2 message send by player.
    	// It is in millis.
    	PlayerController controller = new PlayerController("360T", 10, 1000);
		controller.start();
    	
		/*
		Player initiator = new Player(true, 10);
		Player receiver = new Player(false, 10);
		
		initiator.setPlayerInterface(receiver);
		receiver.setPlayerInterface(initiator);
		
		receiver.start(null);
		
		PlayerMessage msg = new PlayerMessage("Yudhisthira");
		initiator.start(msg);
		*/
    }
}

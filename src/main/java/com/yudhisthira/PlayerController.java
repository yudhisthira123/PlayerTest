package com.yudhisthira;
/**
 * @author yudhisthira
 *
 * This is controller class which controls creating of players and starting of communication
 */
public class PlayerController {
	/**
	 * Test message which will be sent by player initiator
	 */
	private String				mMessage;
	
	/**
	 * Instance of player initiator
	 */
	private PlayerInterface 	mPlayerInitiator = null;
	
	/**
	 * Instance of player receiver
	 */
	private PlayerInterface 	mPlayerReceiver = null;
	
	/**
	 * Controller state which holds current controller state
	 */
	private ControllerState 	mControllerState = ControllerState.NONE;
	
	
	/**
	 * @author yudhisthira
	 * Different controller states
	 */
	public enum ControllerState{
		/**
		 * State NONE
		 */
		NONE,
		
		/**
		 * Controller initiated state. 
		 */
		INITIATED,
		
		/**
		 * Controller started state.
		 */
		STARTED,
		
		/**
		 * Controller stopped state
		 */
		STOPED
	}
	
	
	/**
	 * @param strMessage Text message which will be sent by initiator to receiver
	 * @param numberOfMsg number of messages to be exchange by each other
	 * @param messageInterval this is time in millis. each player will wait before sending message
	 * back to other player. This added only debugging purpose. we should pass 0 to it in normal execution
	 */
	public PlayerController(String strMessage, int numberOfMsg, int messageInterval) {
		mMessage = strMessage;
		createPlayers( numberOfMsg, messageInterval );
	}
	
	/**
	 * @param numberOfMsg number of messages to be exchange by each other
	 * @param messageInterval this is time in millis. each player will wait before sending message
	 * back to other player. This added only debugging purpose. we should pass 0 to it in normal execution
	 * 
	 * This will create initiator and receiver player. Controller will be in INITIATED state
	 */
	private void createPlayers(int numberOfMsg, int messageInterval) {
		mPlayerInitiator = new Player(true, numberOfMsg, messageInterval);
		mPlayerReceiver = new Player(false, numberOfMsg, messageInterval);
		
		mPlayerInitiator.setPlayerInterface(mPlayerReceiver);
		mPlayerReceiver.setPlayerInterface(mPlayerInitiator);
		
		mControllerState = ControllerState.INITIATED;
	}

	/**
	 * This function start the actual communication between players. 
	 * Controller become in STARTED state.
	 */
	public void start() {
		mPlayerReceiver.start(null);
		
		PlayerMessage msg = new PlayerMessage(mMessage);
		mPlayerInitiator.start(msg);
		
		mControllerState = ControllerState.STARTED;
	}
	
	/**
	 * This function stops the communication and stops both player. After this function call player will can not
	 * communicate to each other. Controller become in STOPED state.
	 */
	public void stop() {
		try {
			mPlayerInitiator.stopPlayer();
			mPlayerReceiver.stopPlayer();
		}
		catch(Exception e) {
			
		}
		finally {
			mPlayerInitiator = null;
			mPlayerReceiver = null;
			mControllerState = ControllerState.STOPED;
		}
	}
	
	/**
	 * @return the ControllerState
	 */
	public ControllerState getControllerState() {
		return mControllerState;
	}
}

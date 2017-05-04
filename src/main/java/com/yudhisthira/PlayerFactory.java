package com.yudhisthira;
/**
 * @author yudhisthira
 * 
 * This is factory class for creating Player initiator and receiver instance
 */
public class PlayerFactory {
	/**
	 * int value, this denotes how many message we want to send from each player
	 */
	private int mNumberOfMsg = 0;
	
	/**
	 * messageInterval this is time in millis. each player will wait before sending message
	 * back to other player
	 */
	private int					mMessageInterval = 0;
	
	/**
	 * @param aNumberOfMsg number of message to be send by each other
	 * @param messageInterval this is time in millis. each player will wait before sending message
	 * back to other player. This added only debugging purpose. we should pass 0 to it in normal execution
	 */
	public PlayerFactory(int aNumberOfMsg, int messageInterval) {
		mNumberOfMsg = aNumberOfMsg;
		
		if(messageInterval > 0) {
			mMessageInterval = messageInterval;
		}
		else {
			messageInterval = 0;
		}
	}
	
	/**
	 * 
	 * @return PlayerInterface as a initiator
	 * 
	 * This will create Player object and initialize as an initiator
	 */
	public PlayerInterface createPlayerIniator() {
		PlayerInterface playerInitiator = new Player(true, mNumberOfMsg, mMessageInterval);
		
		return playerInitiator;
	}
	
	/**
	 * @return PlayerInterface as a receiver
	 * 
	 * This will create Player object and initialize as a receiver
	 */
	public PlayerInterface createPlayerReceiver() {
		PlayerInterface playerInitiator = new Player(false, mNumberOfMsg, mMessageInterval);
		
		return playerInitiator;
	}
}

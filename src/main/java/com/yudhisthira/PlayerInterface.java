package com.yudhisthira;
/**
 * @author yudhisthira
 *
 * This is player interface which has to be implemented by player class.
 * Actual communication will done with this interface. Both players should have each other interface
 * to communicate other player 
 */
public interface PlayerInterface {
	/**
	 * @param playerInterface player interface of other player to whom it will send message
	 * 
	 * Set
	 */
	public void setPlayerInterface(PlayerInterface playerInterface);
	
	/**
	 * @param msg PlayerMessage object 
	 * 
	 * This will start player and set initial message to send by player to another player
	 */
	public void start(PlayerMessage msg);
	
	/**
	 * @param msg PlayerMessage object
	 * 
	 * This method will send message to another player
	 */
	public void sendMessage(PlayerMessage msg);
	
	/**
	 * This will stop the player. After that will will neither be able to send message nor able to receive a message
	 */
	public void stopPlayer();
}

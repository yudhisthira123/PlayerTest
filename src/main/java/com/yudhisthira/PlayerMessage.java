package com.yudhisthira;
/**
 * @author yudhisthira
 *
 * This class encapsulate text message which will be sent. 
 */
public class PlayerMessage {
	
	/**
	 * Text message which we each player wants to send to other player player.
	 */
	private String mMessageText;
	
	/**
	 * @param messageText Text message which each player will send to each other
	 * 
	 * constructor of this class. It will be initialized with text message
	 */
	public PlayerMessage(String messageText) {
		mMessageText = messageText;
	}
	
	/**
	 * @param messageText
	 * 
	 * setter method for set text message
	 */
	public void setMessage(String messageText) {
		mMessageText = messageText;
	}
	
	/**
	 * @return
	 * 
	 * getter method for text message
	 */
	public String getMessage() {
		return mMessageText;
	}

}

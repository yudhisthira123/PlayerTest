package com.yudhisthira;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author yudhisthira
 *
 * This is actual player class which implements PlyerInterface. 
 * This can be initiated as an initiator or as a receiver.
 * It has one blocking queue and a thread which will wait on that queue for
 * any incoming message from other player. 
 * As it implements PlayerInterface class, by this two players can communicate each other
 */
public class Player implements PlayerInterface{
	
	/**
	 * Instance of another player to whom this player will communicate and send message.
	 */
	private PlayerInterface 	mAotherPlayer;
	
	/**
	 * flag to initiate player as initiator or as receiver. 
	 * true for initiator else receiver
	 */
	private boolean 			mIsInitiator = false;
	
	/**
	 * Instance of Thread class which will read message from message queue
	 */
	private Thread 				mThread;
	
	/**
	 * A boolean flag to to set true if we want to execution of thread.
	 */
	private boolean				mStopPlayer = false;
	
	/**
	 * integer value which holds number of messages to be sent
	 */
	private int					mNumberOfmsg = 0;
	
	/**
	 * Text message which we get from initiator as first message
	 */
	private String				mActualMessage;
	
	/**
	 * integer value holds value how many message has been processed
	 */
	private int					mProcessedMsg = 0;
	
	/**
	 * messageInterval this is time in millis. each player will wait before sending message
	 * back to other player
	 */
	private int					mMessageInterval = 0;
	
	/**
	 * A blocking queue of PlayerMessage type. Another player will push message to this queue
	 */
	private BlockingQueue<PlayerMessage> mQueue;
	
	/**
	 * @param isInitiator boolean flag which indicate either initiator or receover
	 * true for initiator else receiver
	 * @param aNumberOfMsg number of message to be processed
	 * @param messageInterval this is time in millis. each player will wait before sending message
	 * back to other player. This added only debugging purpose. we should pass 0 to it in normal execution
	 */
	public Player(boolean isInitiator, int aNumberOfMsg, int messageInterval) {
		mIsInitiator = isInitiator;
		mQueue = new ArrayBlockingQueue<>(15);
		mNumberOfmsg = aNumberOfMsg;
		
		if(messageInterval > 0) {
			mMessageInterval = messageInterval;
		}
		else {
			mMessageInterval = 0;
		}
		init();
	}
	
	/**
	 * Initiate the player. after that player is ready to start
	 * It creates a thread which takes message from blocking queue and process them.
	 */
	private void init() {
		mThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					while(false == mStopPlayer) {
						PlayerMessage msg = mQueue.take();
						
						Thread.sleep(mMessageInterval);
						
						if(null != msg) {
							handleMessage(msg);
						}
						else {
							System.out.println("Received null message so stopping .. ID" + mThread.getId());
						}
					}
					
				} catch (InterruptedException e) {
					System.out.println("caught exception... ID" + mThread.getId());
					e.printStackTrace();
				}
				
			}
		});
	}
	
	/**
	 * @param msg PlayerMesage object.
	 * 
	 * This will handle received message and send a new message to another player
	 */
	private void handleMessage(PlayerMessage msg) {

		if(true == mIsInitiator) {
			
			System.out.println("Initiator message received = " + msg.getMessage());
			
			if(mProcessedMsg == mNumberOfmsg) {
				mStopPlayer = true;
			}
			
			if(false == mStopPlayer) {
				msg.setMessage(mActualMessage);
				mAotherPlayer.sendMessage(msg);
				System.out.println("Initiator message send = " + msg.getMessage());
				mProcessedMsg++;
			}
		}
		else {
			
			System.out.println("Receiver message received = " + msg.getMessage());
			
			if(false == mStopPlayer) {
				String strMsg = msg.getMessage();
				strMsg += " ";
				strMsg += mProcessedMsg;
				
				msg.setMessage(strMsg);
				mAotherPlayer.sendMessage(msg);
				System.out.println("Receiver message send = " + msg.getMessage());
			}
			
			mProcessedMsg++;
			if(mProcessedMsg == mNumberOfmsg) {
				//System.out.println("Total message send = " + mNumberOfmsg + " AND Total message received = " + mProcessedMsg);
				mStopPlayer = true;
			}
		}
		

		
	}
	
	/* (non-Javadoc)
	 * @see PlayerInterface#start(PlayerMessage)
	 */
	@Override
	public void start(PlayerMessage msg) {
		if(null != msg) {
			mActualMessage = msg.getMessage();
		}
		
		mThread.start();
		if(true == mIsInitiator) {
			if(mNumberOfmsg > 0) {
				mProcessedMsg++;
				System.out.println("Initiator message send = " + msg.getMessage());
				mAotherPlayer.sendMessage(msg);
			}
			else {
				System.out.println("mNumberOfmsg = " + mNumberOfmsg);
				stopPlayer();
				mAotherPlayer.stopPlayer();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see PlayerInterface#setPlayerInterface(PlayerInterface)
	 */
	@Override
	public void setPlayerInterface(PlayerInterface playerInterface) {
		mAotherPlayer = playerInterface;
	}

	/* (non-Javadoc)
	 * @see PlayerInterface#sendMessage(PlayerMessage)
	 */
	@Override
	public void sendMessage(PlayerMessage msg) {
		mQueue.add(msg);
	}

	/* (non-Javadoc)
	 * @see PlayerInterface#stopPlayer()
	 */
	@Override
	public void stopPlayer() {
		
		if(null != mQueue) {
			System.out.println("stopThread Stopping Thrad .... ID = " + mThread.getId());
			mStopPlayer = true;
			
			synchronized(mQueue) {
				mQueue.notifyAll();
			}
		}
		
	}
}

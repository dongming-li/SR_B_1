package chessAI;

import java.util.ArrayList;

/**
 * Class for GSON to create JSON objects out of and to be able to query those objects
 * @author alecl
 *
 */
public class GSONResponse {
	/**
	 * a placeholder for an error
	 */
	private boolean error;
	/**
	 * a placeholder for all the data
	 */
	private ArrayList<GSONData> data;
	/**
	 * a placeholder for any messages
	 */
	private String message;
	
	/**
	 * Gets the error
	 * @return returns error as a boolean
	 */
	public boolean getError() {
		return error;
	}
	
	/**
	 * gets the array of data
	 * @return returns data which is an arraylist
	 */
	public ArrayList<GSONData> getData() {
		return data;
	}
	
	/**
	 * gets the message
	 * @return returns the message as a string
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Internal Helper class for data objects
	 * @author alecl
	 *
	 */
	class GSONData {
		/**
		 * a placeholder for id
		 */
		private int id;
		/**
		 * a placeholder for history
		 */
		private String history;
		/**
		 * a placeholder for user 1
		 */
		private String user1;
		/**
		 * a placeholder for user2;
		 */
		private String user2;
		/**
		 * a placeholder for the chat ID
		 */
		private int chatID;
		/**
		 * a place holder for the chat log
		 */
		private String chat_log;
		
		/**
		 * Returns the id field as an int
		 * @return returns id
		 */
		public int getID() {
			return id;
		}

		/**
		 * Returns the history field as a string
		 * @return returns history
		 */
		public String getHistory() {
			return history;
		}
		
		/**
		 * Return's User1's name as a string
		 * @return return user1
		 */
		public String getUser1() {
			return user1;
		}
		
		/**
		 * Return's User2's name as a string
		 * @return return user2
		 */
		public String getUser2() {
			return user2;
		}
		
		/**
		 * Returns the chat ID as an int
		 * @return returns chatID
		 */
		public int getChatID() {
			return chatID;
		}
		
		/**
		 * Returns the chat log as a string
		 * @return returns chat_log
		 */
		public String getChatLog() {
			return chat_log;
		}
	}

}

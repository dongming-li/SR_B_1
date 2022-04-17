package gameQueue;

/**
 * Class creating GSONData objects.
 * Used to fill in the GSONResponse used to create JSON objects in java.
 * @author alecl
 *
 */
public class GSONData {

	private int id;
	private String history;
	private String user1;
	private String user2;
	private int chatID;
	private String chat_log;
	private int fieldCount;
	private int affectedRows;
	private int insertId;
	private int serverStatus;
	private int warningCount;
	private String message;
	private boolean protocol41;
	private int changedRows;
	
	/**
	 * Returns the id
	 * @return return id
	 */
	public int getID() {
		return id;
	}

	/**
	 * Gets the history
	 * @return returns history
	 */
	public String getHistory() {
		return history;
	}
	
	/**
	 * Gets user1
	 * @return returns user1
	 */
	public String getUser1() {
		return user1;
	}
	
	/**
	 * Gets user2
	 * @return returns user2
	 */
	public String getUser2() {
		return user2;
	}
	
	/**
	 * gets the chatID
	 * @return return chatID
	 */
	public int getChatID() {
		return chatID;
	}
	
	/**
	 * gets the chat log
	 * @return return chat_log
	 */
	public String getChatLog() {
		return chat_log;
	}
}
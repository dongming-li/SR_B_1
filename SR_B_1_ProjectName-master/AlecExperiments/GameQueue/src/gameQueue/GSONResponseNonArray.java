package gameQueue;
/**
 * JSON response class used with GSON.
 * Used for the response of creating a game since it doesn't return a data array, but instead a data object.
 * @author alecl
 *
 */
public class GSONResponseNonArray {
	/**
	 * if there is an error or not
	 */
	private boolean error;
	/**
	 * the data object
	 */
	private GSONData data;
	/**
	 * the message
	 */
	private String message;
	/**
	 * the id
	 */
	private int id;
	
	/**
	 * Gets the game ID
	 * @return returns id
	 */
	public int getGameID() {
		return id;
	}
	
	/**
	 * gets the error
	 * @return returns the error
	 */
	public boolean getError() {
		return error;
	}
	
	/**
	 * gets the data object
	 * @return returns data as a GSONData object
	 */
	public GSONData getData() {
		return data;
	}
	
	/**
	 * gets the message
	 * @return returns the message
	 */
	public String getMessage() {
		return message;
	}
}

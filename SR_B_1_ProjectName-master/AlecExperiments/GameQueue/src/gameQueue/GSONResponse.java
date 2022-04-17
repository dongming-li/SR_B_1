package gameQueue;

import java.util.ArrayList;

/**
 * JSON object used by GSON to create JSON objects.
 * Assumes data is an array.
 * @author alecl
 *
 */
public class GSONResponse {
	/**
	 * If there is an error
	 */
	private boolean error;
	/**
	 * The data which is an ArrayList of type GSONData
	 */
	private ArrayList<GSONData> data;
	/**
	 * The message
	 */
	private String message;
	/**
	 * The id
	 */
	private int id;
	
	/**
	 * Gets the id
	 * @return returns id
	 */
	public int getGameID() {
		return id;
	}
	
	/**
	 * Gets the error
	 * @return returns error
	 */
	public boolean getError() {
		return error;
	}
	
	/**
	 * Gets data as an arraylist of type GSONData
	 * @return returns data
	 */
	public ArrayList<GSONData> getData() {
		return data;
	}
	
	/**
	 * Gets the message
	 * @return returns message
	 */
	public String getMessage() {
		return message;
	}
}

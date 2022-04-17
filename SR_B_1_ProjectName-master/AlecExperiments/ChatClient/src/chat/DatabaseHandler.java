package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Database Handling Class to handle all calls to the database
 * @author alecl
 *
 */

public class DatabaseHandler {	
	private static final String USER_AGENT = "Mozilla/5.0";
	
	/**
	 * Talks to the database to get a list of all users that the supplied user is in.
	 * @param user the user is being searched for
	 * @return returns a string that is really a JSON object/response
	 * @throws IOException Throws an IOException for any sort of network crashing
	 */
	public static String getAllUserChats(String user) throws IOException {
		String GET_URL = "http://proj-309-sr-b-1.cs.iastate.edu:8008/chats/user/" + user;
		URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// return result
			return response.toString();
			
		} else {
			return "GET request blew up";
		}

	}
	
	/**
	 * Talks to the database to get the log of the supplied chat ID
	 * @param chatID The id to get the log of
	 * @return returns a string that is really a JSON object/response
	 * @throws IOException Throws an IOException for any sort of network crashing
	 */
	public static String getChatLog(String chatID) throws IOException {
		String GET_URL = "http://proj-309-sr-b-1.cs.iastate.edu:8008/chat/logs/" + chatID;
		URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// return result
			return response.toString();
			
		} else {
			return "GET request blew up";
		}
	}
	
	/**
	 * Talks to the database to add a message to the database
	 * @param chatID the chat ID of the chat to be written to
	 * @param message the message to be written
	 * @return returns a string that is really a JSON object/response
	 * @throws IOException Throws an IOException for any sort of network crashing
	 */
	public static boolean sendMessage(String chatID, String message) throws IOException {
		String POST_URL = "http://proj-309-sr-b-1.cs.iastate.edu:8008/chat/send";
		
		String POST_PARAMS = "chatID="+ chatID + "&msg=" + message;
		
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);

		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.toString().getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		//System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//the false means that an error did not occur
			return false;
		} else {
			//the true means an error occurred
			return true;
		}
	}
}

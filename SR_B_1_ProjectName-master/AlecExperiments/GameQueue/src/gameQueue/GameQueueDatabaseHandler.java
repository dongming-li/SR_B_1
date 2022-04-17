package gameQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gameQueue.GSONResponse;

/**
 * Class to handle Queue database calls
 * @author alecl
 *
 */
public class GameQueueDatabaseHandler {
	/**
	 * The user agent needed for get and post requests
	 */
	private static final String USER_AGENT = "Mozilla/5.0";
	
	/**
	 * Creates a game with the two users
	 * @param user1 user1 who ends up being white
	 * @param user2 user2 who ends up being black
	 * @return returns a JSON response formatted as a string
	 * @throws IOException throws an IOException if something network related implodes
	 */
	public static String createGame(String user1, String user2) throws IOException {		
		String POST_URL = "http://proj-309-sr-b-1.cs.iastate.edu:8008/game/create";
		String POST_PARAMS = "player1="+ user1 + "&player2=" + user2;
		
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
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			return response.toString();
		} else {
			return "Something Blew Up";
		}
	}
	
	/**
	 * Method to create a chat between two users.
	 * Tests with the chatAlreadyExists to see if a chat already exists between those two users
	 * @param user1 the first user to test
	 * @param user2 the second user to test
	 * @throws IOException throws an IOException if something network related implodes
	 */
	public static void createChat(String user1, String user2) throws IOException {
		//check to make sure a chat doesn't already exist between these two
		if(chatAlreadyExists(user1, user2) == false) {
			String POST_URL = "http://proj-309-sr-b-1.cs.iastate.edu:8008/chat/create";
			String POST_PARAMS = "user1="+ user1 + "&user2=" + user2;
			
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
			System.out.println("chat creation responseCode = " + responseCode);
			//System.out.println("POST Response Code :: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				System.out.println(response.toString());
			} else {
				System.out.println("Something blew up in the chat creation");
			}
		}
	}
	
	/**
	 * Checks if a chat already exists between these two users
	 * @param user1 the first user
	 * @param user2 the second user
	 * @return returns true if a chat already exists, false otherwise
	 * @throws IOException throws an IOException if something network related implodes
	 */
	public static boolean chatAlreadyExists(String user1, String user2) throws IOException {
		String GET_URL = "http://proj-309-sr-b-1.cs.iastate.edu:8008/chats/user/" + user1;
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
			
			//take our response and see if these two players are in a chat or not
			String jsonResponse = response.toString();
			System.out.println(jsonResponse);
			//parses the json stuff to a string
			GsonBuilder builder = new GsonBuilder(); 
		    builder.setPrettyPrinting(); 
		    Gson gson = builder.create();
		    GSONResponse responseJson = gson.fromJson(jsonResponse.toString(), GSONResponse.class);
		    
		    //check for if user2 is in any of user1's existing chats
		    //if so return true, otherwise just return false
		    for(int i =0; i<responseJson.getData().size(); i++) {
		    	String jsonuser1 = responseJson.getData().get(i).getUser1();
		    	String jsonuser2 = responseJson.getData().get(i).getUser2();
		    	if(user2.equals(jsonuser2) || user2.equals(jsonuser1)) {
		    		return true;
		    	}
		    }
		    return false;
		} else {
			return false;
		}
		
	}
}
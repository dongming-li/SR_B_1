package chessAI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class to handle all database communication with the AI class
 * @author alecl
 *
 */
public class AIDatabaseCommunication {
	/**
	 * What type of User Agent we are, needed for get and post requests
	 */
	private static final String USER_AGENT = "Mozilla/5.0";
	
	/**
	 * Gets the current board state from the supplied game ID
	 * @param gameID the game ID
	 * @return returns a json object/response
	 * @throws IOException throws an error if something crashes with the network
	 */
	public static String getBoard(String gameID) throws IOException {
		String GET_URL = "http://proj-309-sr-b-1.cs.iastate.edu:8008/game/board/" + gameID;
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
	 * handles talking to the server and makes a move based on the supplied info
	 * @param gameID the game being moved on
	 * @param origPosition the original position
	 * @param newPosition the new position
	 * @return returns a json object/response
	 * @throws IOException throws an error if something crashes with the network
	 */
	public static boolean makeMove(String gameID, String origPosition, String newPosition) throws IOException {
		String POST_URL = "http://proj-309-sr-b-1.cs.iastate.edu:8008/game/board/update";
		
		String POST_PARAMS = "move="+ origPosition + '\n' + newPosition + '\n' + "&gameID=" + gameID;
		
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

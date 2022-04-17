package gameQueue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Spawned thread to handle several things.
 * First the thread gets the name of both players
 * Second it creates a chat between those two players if one doesn't already exist
 * Third it creates a game between the players
 * Fourth it sends back needed information to both clients so the clients can move to the next activity
 * @author alecl
 *
 */
public class GameQQHandler implements Runnable {
	/**
	 * a socket for client1
	 */
	Socket client1;
	/**
	 * a socket for client2
	 */
	Socket client2;

	/**
	 * Constructor for a runnable instance
	 * @param client1 The first client's socket
	 * @param client2 The second client's socket
	 */
	GameQQHandler(Socket client1, Socket client2){
		this.client1 = client1;
		this.client2 = client2;
	}
	
	/**
	 * The run method that handles all the steps that this class does
	 */
	public void run() {
		Scanner in1;
		PrintWriter out1;
		Scanner in2;
		PrintWriter out2;
		
		try {
			//GET SOCKET IN/OUT STREAMS
			in1 = new Scanner(new BufferedInputStream(client1.getInputStream())); 
			out1 = new PrintWriter(new BufferedOutputStream(client1.getOutputStream()));
			in2 = new Scanner(new BufferedInputStream(client2.getInputStream())); 
			out2 = new PrintWriter(new BufferedOutputStream(client2.getOutputStream()));
			
			//clients send their usernames
			String user1 = in1.nextLine();
			String user2 = in2.nextLine();
			
			//some moron somehow ended up in the queue twice
			//Solution: add him back only 1 time
			if(user1.equals(user2)) {
				GameQueueServer.add(client2);
			    in1.close();
			    in2.close();
			    out1.close();
			    out2.close();
			    client1.close();
			    //Not going to close the socket with client2 cause that is the newest socket with this stupid player
				return;
			}
			
			//user the usernames to create a chat for two users
			GameQueueDatabaseHandler.createChat(user1, user2);
			
			//use usernames to create a game with the database
			String jsonResponse = GameQueueDatabaseHandler.createGame(user1, user2);
			GsonBuilder builder = new GsonBuilder(); 
		    builder.setPrettyPrinting(); 
		    Gson gson = builder.create();
		    
		    System.out.println(jsonResponse.toString());
		    
		    GSONResponseNonArray responseJson = gson.fromJson(jsonResponse.toString(), GSONResponseNonArray.class);
		    
		    int gameID = responseJson.getGameID();
			
		    //send info to the clients
		    out1.println(gameID);
		    out1.println("player1");
		    out1.println(user2);
		    out1.flush();
		    out2.println(gameID);
		    out2.println("player2");
		    out2.println(user1);
		    out2.flush();
			
		    //close
		    in1.close();
		    in2.close();
		    out1.close();
		    out2.close();
		    client1.close();
		    client2.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
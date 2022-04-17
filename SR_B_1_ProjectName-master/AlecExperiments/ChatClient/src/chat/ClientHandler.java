package chat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import chat.GSONResponse;

/**
 * Spawned thread class to handle requests
 * @author alecl
 *
 */
public class ClientHandler implements Runnable {
	/**
	 * this is socket on the server side that connects to the CLIENT
	 */
	Socket s;
	/**
	 * keeps track of its number just for identifying purposes
	 */
	int num;
	
	/**
	 * Constructor to create a runnable object
	 * @param s the socket
	 * @param n the client number
	 */
	ClientHandler(Socket s, int n) {
		this.s = s;
		num = n;
	}

	// This is the client handling code
	// This keeps running handling client requests 
	// after initially sending some stuff to the client
	/**
	 * Run Method that handles clients and their requests and closes after completion.
	 */
	public void run() {
		Scanner in;
		PrintWriter out;
		try {
			// 1. GET SOCKET IN/OUT STREAMS
			in = new Scanner(new BufferedInputStream(s.getInputStream())); 
			out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));
			
			//2. listen for a request and grab username
			String request = in.nextLine();
			String chatID;
			String jsonResponse;
			
			// 3. depending on the request, do different things
			switch(request) {
				//if this is the request string, then a list of all chats needs to be sent
				case "Requesting a list of chats":
					String username = in.nextLine();
					jsonResponse = DatabaseHandler.getAllUserChats(username);
					//parses the json stuff to a string
					GsonBuilder builder = new GsonBuilder(); 
				    builder.setPrettyPrinting(); 
				    Gson gson = builder.create();
				    GSONResponse responseJson = gson.fromJson(jsonResponse.toString(), GSONResponse.class);
				    String names = "";
				    for(int i =0; i<responseJson.getData().size(); i++) {
				    	String user1 = responseJson.getData().get(i).getUser1();
				    	String user2 = responseJson.getData().get(i).getUser2();
				    	int id = responseJson.getData().get(i).getChatID();
				    	if(user1.equals(username)) {
				    		names += id + " " + user2 + '\n';
				    	}
				    	else {
				    		names += id + " " + user1 + '\n';
				    	}
				    }
				    //sends back to client
					out.println(names);
					out.flush();
					break;
				//if this is the request string, then a chat log needs to be sent
				case "Requesting chat log":
					chatID = in.nextLine();
					jsonResponse = DatabaseHandler.getChatLog(chatID);
					//parse the json
					GsonBuilder builder2 = new GsonBuilder(); 
					builder2.setPrettyPrinting(); 
				    Gson gson2 = builder2.create();
				    GSONResponse responseJson2 = gson2.fromJson(jsonResponse.toString(), GSONResponse.class);
				    String toSend = responseJson2.getData().get(0).getChatLog();
				    //sends back to client
				    out.println(toSend);
				    out.flush();
					break;
				//if this is the request string then the user wants to send a message
				case "Sending a message":
					chatID = in.nextLine();
					String message = in.nextLine();
					DatabaseHandler.sendMessage(chatID, message);
					break;
			}
			
			//cleanup
			in.close();
			out.close();
			s.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	} // end of method run()
} // end of class ClientHandler
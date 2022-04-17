package aiTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
//import android.widget.Toast;

public class ChessAIMoveRequest implements Runnable {
	//Make a socket
    private Socket serverSocket;
    //Host name
    private String serverHostName = "10.25.70.61";
    //Port Number
    private int serverPortNumber = 4442; //using 4442 because nothing else uses it?
    //AI Color
    String color;
    //AI difficulty
    String difficulty;
    //game ID
    String gameID;
    //boolean if the run is done
    boolean isDone;
    
    //constructor
    public ChessAIMoveRequest(String color, String gameDiff, String gameID) {
    	this.color = color;
    	this.difficulty = gameDiff;
    	this.gameID = gameID;
    	isDone = false;
    }
    
    public boolean getIsDone() {
    	return isDone;
    }
    
	@Override
	public void run() {
		//connect to server
		connect();
		//how we are going to read and write to the server
		PrintWriter out;
		Scanner in;
        try {
        	//setup in and out
        	out = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()));
            in = new Scanner(new BufferedInputStream(serverSocket.getInputStream()));
            
            //send our info
            out.println(color);
            out.println(difficulty);
            out.println(gameID);
            out.flush();
            
            //our response, hopefully no errors
            String response = in.nextLine();
            if (!response.equals("Done")) {
            	//we have an error
            	//Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            	System.out.println(response);
            }

            //cleanup
            out.close();
            in.close();
            serverSocket.close();
            
            isDone = true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	//Private method used to connect
    private void connect(){
        try {
            serverSocket = new Socket(serverHostName, serverPortNumber);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}

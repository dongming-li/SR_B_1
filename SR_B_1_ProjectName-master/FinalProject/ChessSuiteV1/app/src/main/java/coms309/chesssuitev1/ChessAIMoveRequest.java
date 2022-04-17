package coms309.chesssuitev1;

/**
 * Created by alecl on 11/27/2017.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Async class handling connections with the AI server and requesting AI moves.
 * @author alecl
 */
public class ChessAIMoveRequest extends AsyncTask<Object[], Void, Void> {
    /**
     * Socket to be talked over
     */
    private Socket serverSocket;
    /**
     * The server's host name
     */
    private String serverHostName = "10.25.70.61";
    /**
     * Server's port number
     */
    private int serverPortNumber = 4442; //using 4442 because nothing else uses it?
    /**
     * The color that the AI should be moving
     */
    String color;
    /**
     * AI difficulty (not implemented yet)
     */
    String difficulty;
    /**
     * The database game ID
     */
    String gameID;
	//the activity to call from
	//AIMainActivity ama;

    /**
     * Async method to handle connecting to the AI server.
     * Sends info requesting a move on a game with the supplied info
     * @param params The info supplied to the Async task.
     *               Needs to be in the following format:
     *               Object[] params = {String AIColor, String AIDifficulty, String GameID}
     * @return return nothing/void
     */
    protected Void doInBackground(Object[]... params) {
        this.color = params[0][0].toString();
        this.difficulty = params[0][1].toString();
        this.gameID = params[0][2].toString();
		Object temp = params[0][3];
		//ama = (AIMainActivity) temp;

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
                Log.d("connection3", response);
                System.out.println(response);
            }

            //cleanup
            out.close();
            in.close();
            serverSocket.close();
        }
        catch (IOException e) {
            Log.d("connect4", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method executed upon completion of server negotiations
     */
	protected void onPostExecute() {
		//refresh the screen
       // ama.refresh(ama.findViewById(R.id.button));
    }

    /**
     * Method used to connect to the server
     */
    private void connect(){
        try {
            serverSocket = new Socket(serverHostName, serverPortNumber);
        } catch (UnknownHostException e) {
            Log.d("connect", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("connect2", e.getMessage());
            e.printStackTrace();
        }
    }
}

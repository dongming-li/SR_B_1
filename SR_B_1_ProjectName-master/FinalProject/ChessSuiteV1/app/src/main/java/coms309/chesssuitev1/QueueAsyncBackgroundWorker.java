package coms309.chesssuitev1;

import android.content.Intent;
import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by alecl on 12/3/2017.
 */

/**
 * Spawned Async thread to handle queue connections and negotiations.
 * @author alecl
 */
public class QueueAsyncBackgroundWorker extends AsyncTask<Object[], Void, String[]> {
    /**
     * Socket to connect over
     */
    private Socket serverSocket;
    /**
     * Server's Host name
     */
    private String serverHostName = "10.25.70.61";
    /**
     * Server's Port number
     */
    private int serverPortNumber = 4441; //using 4441 because nothing else uses it?
    /**
     * This client's name
     */
    private String clientName = "";
    /**
     * The database game ID
     */
    private String gameID;
    /**
     * The client's port number
     */
    private String playerNum;
    /**
     * The opponent's name
     */
    private String opponentName;
    /**
     * The activity to call the callback function on
     */
    private QueueConnectionActivity QCA;


    /**
     * The main Async function.  Handles connecting, sending, and receiving with the server.
     * @param params The passed parameters.
     *               Needs to be in the following format:
     *               Object[] params = {String ClientName, QueueConnectionActivity ActivityToCallbackTo}
     * @return Returns a string array in the form of String[] toReturn = {gameID, playerNum, opponentName}
     */
    protected String[] doInBackground(Object[]... params){
        //client name is the first param
        clientName = params[0][0].toString();
        Object temp = params[0][1];
        QCA = (QueueConnectionActivity)temp;

        //connect and send/receive data (see below for more info)
        connect();
        sendAndReceive();

        //return the received game ID
        String[] toReturn = {gameID, playerNum, opponentName};
        return toReturn;
    }

    /**
     * Method to call the activity's callback function which starts the game activity
     * @param result The supplied result string in the form String[] result = {gameID, playerNum, opponentName};
     */
    protected void onPostExecute(String[] result) {
        QCA.loadGame(result[0], result[1], result[2]);
    }

    /**
     * method to handle connecting to the server
     */
    private void connect(){
        try {
            serverSocket = new Socket(serverHostName, serverPortNumber);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Handles sending and receiving of info from the server
     */
    private void sendAndReceive(){
        PrintWriter out;
        Scanner in;
        try {
            //get the in and out streams
            out = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()));
            in = new Scanner(new BufferedInputStream(serverSocket.getInputStream()));

            //send the client name
            out.println(clientName);
            out.flush();

            //wait for an int in return, this is the gameID that is created
            gameID = in.nextLine().trim();
            playerNum = in.nextLine().trim();
            opponentName = in.nextLine().trim();

            //cleanup
            in.close();
            out.close();
            serverSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}

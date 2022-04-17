package coms309.chesssuitev1;

/**
 * Created by alecl
 */

import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * main class that handles connecting, sending/receiving, and drawing
 * @author alecl
 */
public class ChatScreenClient extends AsyncTask<Object[], Void, String> {
    /**
     * The socket
     */
    private Socket serverSocket;
    /**
     * The server's host name
     */
    private String serverHostName = "10.25.70.61";
    /**
     * The server's port number
     */
    private int serverPortNumber = 4443; //using 4443 because nothing else uses it?
    /**
     * The chat id
     */
    private String chatID;
    /**
     * Boolean to determine if a message is being sent or not
     */
    private boolean sendMess;
    /**
     * Message to send
     */
    private String message;
    /**
     * String to hold the returned message history
     */
    private String messageHistory = "";
    /**
     * The activity to draw to
     */
    private ChatScreenActivity csa;


    /**
     * Asyc background method handling connecting to the server, sending info, and receiving responses
     * @param params The parameters sent.  Params need to be sent in an Object array in the following format: Object[] params = {String ChatID, Boolean SendMessage, String Message, ChatScreenActivity ActivityToDrawTo}
     * @return Returns a string of the chat log
     */
    protected String doInBackground(Object[]... params){

        this.chatID = params[0][0].toString();
        this.sendMess = Boolean.parseBoolean(params[0][1].toString());
        message = params[0][2].toString();
        Object temp = params[0][3];
        csa = (ChatScreenActivity) temp;

        //CONNECT TO THE SERVER
        connect();
        //send request and receive info
        if (sendMess){
            send();
        }
        else {
            receive();
        }

        return messageHistory;
    }

    /**
     * Upon completing the send and receive process, draw the resulting message on screen
     * @param result The received chat log
     */
    protected void onPostExecute(String result) {
        csa.draw(result);
    }

    /**
     * Method to connect to the server and open sockets
     */
    private void connect(){
        try {
            serverSocket = new Socket(serverHostName, serverPortNumber);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to send information to server
     */
    private void send(){
        PrintWriter out2;
        try {
            out2 = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()));

            out2.println("Sending a message");
            out2.println(chatID);
            out2.println(message);
            out2.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to receive info from server
     */
    private void receive(){
        PrintWriter out;
        Scanner in;
        try {
            out = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()));
            in = new Scanner(new BufferedInputStream(serverSocket.getInputStream()));

            out.println("Requesting chat log");
            out.println(chatID);
            out.flush();

            while(in.hasNext()) {
                messageHistory += in.nextLine() + '\n';
            }

            in.close();
            out.close();
            serverSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
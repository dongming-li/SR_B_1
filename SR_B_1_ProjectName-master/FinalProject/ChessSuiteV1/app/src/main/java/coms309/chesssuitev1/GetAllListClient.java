package coms309.chesssuitev1;

/**
 * Created by alecl
 */

import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class - handles connecting to the server, sending/receiving info, and draws info on screen
 * @author alecl
 */
public class GetAllListClient extends AsyncTask<Object[], Void, ArrayList<NameIDPair>> {
    /**
     * Socket to connect over
     */
    private Socket serverSocket;
    /**
     * Server's host name
     */
    private String serverHostName = "10.25.70.61";
    /**
     * Server's port number
     */
    private int serverPortNumber = 4443; //using 4443 because nothing else uses it?
    /**
     * The client's name
     */
    private String clientName = "";
    /**
     * Arraylist to hold received info in NameIDPairs
     */
    private ArrayList<NameIDPair> otherUsernames = new ArrayList<NameIDPair>();
    /**
     * The activity to callback to and draw on
     */
    private ChatMainActivity cma;

    /**
     * Main Async method used to connect to the server and get requested info
     * @param params The given params.
     *               Need to be in the following format:
     *               Object[] params = {String clientName, ChatMainActivity ActivityToDrawTo}
     * @return Returns an Arraylist of NameIDPairs to draw
     */
    protected ArrayList<NameIDPair> doInBackground(Object[]... params){
        clientName = params[0][0].toString();
        Object temp = params[0][1];
        cma = (ChatMainActivity) temp;

        // CONNECT TO THE SERVER
        connect();
        //send request and receive info
        sendAndReceive();

        return otherUsernames;
    }

    /**
     * Method called upon finishing of server connections, calls the callback draw function
     * @param result The NameIDPair result in the form of an arraylist
     */
    protected void onPostExecute(ArrayList<NameIDPair> result) {
        cma.draw(result);
    }

    /**
     * connects to the server
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
     * Sends a request for a list of all chats
     * waits around until all chats have been received and then closes the socket
     */
    private void sendAndReceive(){
        PrintWriter out;
        Scanner in;
        try {
            out = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()));
            in = new Scanner(new BufferedInputStream(serverSocket.getInputStream()));
            out.println("Requesting a list of chats");
            out.println(clientName);
            out.flush();

            while(in.hasNext()) {
                int id = in.nextInt();
                String name = in.nextLine().trim();
                otherUsernames.add(new NameIDPair(name, id));
            }
            in.close();
            out.close();
            serverSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
} //end of GetAllListClient class

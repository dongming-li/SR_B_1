package alecl.queuegui;

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

public class QueueAsyncBackgroundWorker extends AsyncTask<Object[], Void, String[]> {
    //Make a socket
    private Socket serverSocket;
    //Host name
    private String serverHostName = "10.25.70.61";
    //Port Number
    private int serverPortNumber = 4441; //using 4441 because nothing else uses it?
    //Client's Name
    private String clientName = "";
    //new game id num
    private String gameID;
    //String so the client knows which player number it is
    private String playerNum;
    //String so the client knows what the opponent's name is
    private String opponentName;
    //my activity
    QueueConnectionActivity QCA;


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

    protected void onPostExecute(String[] result) {
        QCA.loadGame(result[0], result[1], result[2]);
    }

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

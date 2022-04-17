package com.example.alecl.chatui;

/**
 * Created by alecl on 11/27/2017.
 */

import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChessAIMoveRequest extends AsyncTask<String[], Void, String> {
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
	
	AIMainActivity ama;

    protected void doInBackground(String[]... params) {
        this.color = params[0][0].toString();
        this.difficulty = params[0][1].toString();
        this.gameID = params[0][2].toString();
		AIMainActivity temp = this.gameID = params[0][3];
		ama = (AIMainActivity) temp;

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
                System.out.println(response);
            }

            //cleanup
            out.close();
            in.close();
            serverSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	protected void onPostExecute(String result) {
		//refresh the screen
        ama.refresh(this.findViewById(R.id.button));
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

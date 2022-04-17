package com.example.alecl.chatui;

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
 */
public class ChatScreenClient extends AsyncTask<Object[], Void, String> {
    //Make a socket
    private Socket serverSocket;
    //Host name
    private String serverHostName = "10.25.70.61";
    //Port Number
    private int serverPortNumber = 4443; //using 4443 because nothing else uses it?
    //the Chat ID
    private String chatID;
    //booleans to determine if we are sending info
    private boolean sendMess;
    //String to send
    private String message;
    //A String to hold the returned information
    private String messageHistory = "";

    private ChatScreenActivity csa;


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

    protected void onPostExecute(String result) {
        csa.draw(result);
    }

    private void connect(){
        try {
            serverSocket = new Socket(serverHostName, serverPortNumber);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
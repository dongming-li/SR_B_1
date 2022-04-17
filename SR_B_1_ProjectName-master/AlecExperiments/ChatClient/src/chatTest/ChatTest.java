package chatTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class ChatTest {
	public static void main(String[] args) {
		ChatScreenClient test = new ChatScreenClient("1512251253", true, "I hate threadingkjlkl");
		test.run();
		ChatScreenClient test2 = new ChatScreenClient("1512251253", false, "");
		test2.run();
	}
}
class ChatScreenClient implements Runnable {
    //Make a socket
    private Socket serverSocket;
    //Host name
    private String serverHostName = "10.25.70.61";
    //Port Number
    private int serverPortNumber = 4443; //using 4443 because nothing else uses it?
    //the Chat ID
    private String chatID;
    //booleans to determine if we are sending info or receiving info
    private boolean sendMess;
    //String to send
    private String message;
    //A String to hold the returned information
    private String messageHistory = "";

    public ChatScreenClient (String chatID, boolean sendMess, String mess){
        this.chatID = chatID;
        this.sendMess = sendMess;
        message = mess;
    }

    @Override
    public void run() {
        // 2. CONNECT TO THE SERVER
        connect();
        //send request and receive info
        if (sendMess){
            send();
        }
        else {
        	receive();
        }
        //Last thing to do is to draw everything
        draw();
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

    private void draw() {
        //textView.setText(messageHistory);
        System.out.print(messageHistory);
    }
}
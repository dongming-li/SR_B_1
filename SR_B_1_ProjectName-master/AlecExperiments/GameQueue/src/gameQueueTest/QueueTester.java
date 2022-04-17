package gameQueueTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class QueueTester {

	public static void main(String[] args) throws InterruptedException {
		QueueTest test = new QueueTest("aragorn");
		test.run();
	}

}


class QueueTest implements Runnable {
	//Make a socket
    private Socket serverSocket;
    //Host name
    private String serverHostName = "localhost";
    //Port Number
    private int serverPortNumber = 4441; //using 4443 because nothing else uses it?
    private String clientName = "";
    //new game id num
    private String gameID;
    //String so the client knows which player number it is
    private String playerNum;
    //String so the client knows what the opponent's name is
    private String opponentName;
    
    public QueueTest(String name) {
    	this.clientName = name;
    }
    
    
	@Override
	public void run() {
		connect();
		sendAndReceive();
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
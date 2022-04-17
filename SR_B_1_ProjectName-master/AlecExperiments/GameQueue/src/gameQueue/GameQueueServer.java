package gameQueue;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;

/**
 * Main queue server.
 * Handles creating a queue and pops out two people when the queue is of length greater than 1.
 * Takes those two players and dumps them in their own thread.
 * @author alecl
 *
 */
public class GameQueueServer {
	/**
	 * The main queue
	 */
	public static ArrayDeque<Socket> queue;

	/**
	 * Main method, starts server and continuously listens for requests
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;  //serversocket
		int clientNum = 0; // keeps track of how many clients were created
		int socketNum = 4441; //using 4441 because nothing else uses it?

		// 1. CREATE A NEW SERVERSOCKET
		try {
			serverSocket = new ServerSocket(socketNum); // provide MYSERVICE at port socketNum
			System.out.println(serverSocket);
		} catch (IOException e) {
			System.out.println("Could not listen on port: " + socketNum);
			System.exit(-1);
		}
		
		//1.5 create a queue
		queue = new ArrayDeque<Socket>();
		
		// 2. LOOP FOREVER - SERVER IS ALWAYS WAITING TO PROVIDE SERVICE!
		while (true) { // 3.
			Socket clientSocket = null;
			try {
				// 2.1 WAIT FOR CLIENT TO TRY TO CONNECT TO SERVER
				System.out.println("Waiting for client " + (clientNum + 1) + " to connect!");
				clientSocket = serverSocket.accept();
				
				// 2.2 SPAWN A THREAD TO HANDLE CLIENT REQUEST
				System.out.println("Server got connected to a client" + ++clientNum);
				add(clientSocket);
				
			} catch (IOException e) {
				System.out.println("Accept failed: 4444");
				System.exit(-1);
			}
			// 2.3 GO BACK TO WAITING FOR OTHER CLIENTS
			// (While the thread that was created handles the connected client's request)
		} // end while loop
	} // end of main method
	
	/**
	 * Adds a client to the queue.
	 * Is synchronized to allow threads to add back
	 * @param s the client socket
	 */
	public static synchronized void add(Socket s) {
		queue.add(s);
		if(queue.size() > 1) {
			Thread t = new Thread(new GameQQHandler(queue.remove(), queue.remove()));
			t.start();
		}
	}
}

package chessAI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main thread listening for AI move requests.  Spawns a new thread to handle the move computations.
 * @author alecl
 *
 */
public class AIListener {
	/**
	 * Main method
	 * @param args
	 * @throws IOException - throws errors if a network error happens
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;  // 1. serversocket
		int clientNum = 0; // keeps track of how many clients were created
		int socketNum = 4442; //using 4442 because nothing else uses it?

		// 1. CREATE A NEW SERVERSOCKET
		try {
			serverSocket = new ServerSocket(socketNum); // provide MYSERVICE at port socketNum
			System.out.println(serverSocket);
		} catch (IOException e) {
			System.out.println("Could not listen on port: " + socketNum);
			System.exit(-1);
		}
		// 2. LOOP FOREVER - SERVER IS ALWAYS WAITING TO PROVIDE SERVICE!
		while (true) { // 3.
			Socket clientSocket = null;
			try {
				// 2.1 WAIT FOR CLIENT TO TRY TO CONNECT TO SERVER
				System.out.println("Waiting for client " + (clientNum + 1) + " to connect!");
				clientSocket = serverSocket.accept();
				
				// 2.2 SPAWN A THREAD TO HANDLE CLIENT REQUEST
				System.out.println("Server got connected to a client" + ++clientNum);
				Thread t = new Thread(new AIThread(clientSocket, clientNum));
				t.start();
			} catch (IOException e) {
				System.out.println("Accept failed: 4444");
				e.printStackTrace();
			}
			// 2.3 GO BACK TO WAITING FOR OTHER CLIENTS
			// (While the thread that was created handles the connected client's request)
		} // end while loop
	} // end of main method
}//end of AIListener
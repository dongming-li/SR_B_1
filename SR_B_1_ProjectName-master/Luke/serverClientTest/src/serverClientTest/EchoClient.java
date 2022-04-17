package serverClientTest;

import java.io.*;
import java.net.*;

public class EchoClient {

	public static void main(String[] args) throws IOException {
		String hostName = args[0];
		int port = Integer.parseInt(args[1]);

		try (Socket echo = new Socket(hostName, port);
				PrintWriter output = new PrintWriter(echo.getOutputStream(), true);
				BufferedReader input = new BufferedReader(new InputStreamReader(echo.getInputStream()));
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
				output.println(userInput);
				System.out.println("echo: " + input.readLine());
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}

}

package serverClientTest;

import java.net.*;
import java.io.*;

public class EchoServer {

	public static void main(String[] args) throws IOException {

		int port = Integer.parseInt(args[0]);

		try (ServerSocket socket = new ServerSocket(port);
				Socket client = socket.accept();
				PrintWriter echo = new PrintWriter(client.getOutputStream(), true);
				BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

		) {
			String inputLine;
			while ((inputLine = input.readLine()) != null) {
				echo.println(inputLine);
			}
		} catch (IOException e) {
			System.out.println("Exception from trying to listen on port" + port + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

}

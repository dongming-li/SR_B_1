package gameQueueTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class QueueTester2 {

	public static void main(String[] args) throws InterruptedException {
		QueueTest test = new QueueTest("Cpre");
		test.run();
	}

}
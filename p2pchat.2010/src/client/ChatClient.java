package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	public static void main (String args[]){
		String host = "localhost";
		int serverport = 2222;
		String testfile = "C:/temp/test.txt";
		if (args.length == 2){
			host = args[0];
			serverport = Integer.parseInt(args[1]);
		}else if (args.length == 3){
			host = args[0];
			serverport = Integer.parseInt(args[1]);
			testfile = args[2];
		}
		try {
			System.out.println("Connect to localhost on port 2222");
			Socket s = new Socket(host, serverport);
			ClientInputStreamHandler cish = new ClientInputStreamHandler(s, testfile);
			cish.start();
			System.out.println("Client handler started");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

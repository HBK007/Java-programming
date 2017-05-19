package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocketHandler extends Thread {
	private Socket csocket;
	public ClientSocketHandler(Socket s){
		csocket = s;
	}
	public void run(){
		try {
			InputStream is = csocket.getInputStream();
			OutputStream os = csocket.getOutputStream();
			InputStreamHandler ish = new InputStreamHandler(is, os);
			ish.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

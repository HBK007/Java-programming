package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread{
	private ServerSocket ssocket;
	private int serverPort;
	public ServerListener(int port){
		serverPort = port;
	}
	public void run(){
		try {
			ssocket = new ServerSocket(serverPort);
			while(true){
				Socket s = ssocket.accept();
				ClientSocketHandler csh = new ClientSocketHandler(s);
				csh.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

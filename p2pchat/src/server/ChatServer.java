/**
 * 
 */
package server;

public class ChatServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 2222;
		if (args.length == 1){
			System.out.println(args[0]);
			port = Integer.parseInt(args[0]);
		}
		ServerListener sl = new ServerListener (port);
		sl.start();
		System.out.println("Server is listenning on port: " + port);
	}
}

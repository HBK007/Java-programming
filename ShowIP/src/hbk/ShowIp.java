package hbk;

import java.net.*;

public class ShowIp {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShowIp myIp = new ShowIp();
		myIp.init();
	}
	public void init(){
		try{
			InetAddress myHost = InetAddress.getLocalHost();
			System.out.println("This my ip : " + myHost.getHostAddress());
		}catch(UnknownHostException ex){
			System.err.println("cannot find local host");
		}
	}
}

package hbk;

import java.util.Timer;
import java.util.TimerTask;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Run me \n");
			}
		};
		Timer timer = new Timer();
    	timer.schedule(task, 1000,60);
	}

}

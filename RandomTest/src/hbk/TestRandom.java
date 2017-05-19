package hbk;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestRandom {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		System.out.println("Value random : " + (rand.nextInt(3) + 1));
		Date time = new Date();
		DateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
		String strTime = formatTime.format(time);
		System.out.println("Time: " + strTime);
	}
}

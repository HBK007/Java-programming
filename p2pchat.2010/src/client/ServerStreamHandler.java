package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import common.ChatState;
import common.TagFormatException;
import common.TagReader;
import common.TagValue;
import common.TagWriter;
import common.Tags;

public class ServerStreamHandler extends Thread {
	private TagReader reader;
	private TagWriter writer;
	private ChatState currentState = ChatState.CHAT_REQ;
	private ChatClientGUI clientGUI;
	public ServerStreamHandler(ChatClientGUI gui, TagReader tr, TagWriter tw){
		clientGUI = gui;
		reader = tr;
		writer = tw;
	}
	public void run(){
		try{
			TagValue tv = reader.nextTagValue();
			boolean running = true;
			while (running){
				switch (currentState){
					case CHAT_REQ:
						if (tv.isTag(Tags.OPEN_SESSION_ACK)){
							currentState = ChatState.CHAT;
							tv = reader.nextTagValue();
						}
						break;
					case CHAT:
						if (tv.isTag(Tags.OPEN_CHAT_MSG)){
							System.out.println(new String(tv.getContent()));
							clientGUI.display("Received: " + new String(tv.getContent()));
							tv = reader.nextTagValue();
						}else if (tv.isTag(Tags.SESSION_CLOSE)){
							running = false;
							currentState = ChatState.END;
							clientGUI.display("Session close \n");
						}
						break;
					case END:
					default:
						break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

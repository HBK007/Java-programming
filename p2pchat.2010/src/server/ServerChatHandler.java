package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

import common.*;


public class ServerChatHandler extends Thread {
	private TagReader reader;
	private TagWriter writer;
	private OutputStream myOS;
	private ChatState currentState;
	private String userID = "";
	private String myName = "Server";
	private int msgcnt = 0;
	private Socket mySock;
	private String filename;
	private String basedir = "C:/temp/";
	public ServerChatHandler(Socket sock) throws IOException{
		mySock = sock;
		reader = new TagReader(mySock.getInputStream());
		writer = new TagWriter(mySock.getOutputStream());
//		myOS = os;
		currentState = ChatState.START;
	}
	public void run(){
		try{
			boolean running = true;
			TagValue tv = reader.nextTagValue();
			while (running){
				switch (currentState){
					case START:
						if (tv.isTag(Tags.OPEN_SESSION_REQ)){
							userID = new String (tv.getContent());
							TagValue atv = new TagValue (Tags.OPEN_SESSION_ACK, myName.getBytes(), myName.getBytes().length);
							writer.writeTag(atv);
							this.sendChatMSG("Hello " + userID);
							currentState = ChatState.CHAT;
						}
						tv = reader.nextTagValue();
						break;
					case CHAT:
						if (tv.isTag(Tags.OPEN_CHAT_MSG)){
							writer.writeTag(tv);
							System.out.println(new String(tv.getContent()));
							tv = reader.nextTagValue();
						}else if (tv.isTag(Tags.OPEN_FILE_REQ)){
							this.sendChatMSG("Received req. " + new String(tv.getContent()));
							filename = new String(tv.getContent());
							int an = getRand();
							TagValue atv = new TagValue (Tags.OPEN_FILE_REQ_ACK, (an + "").getBytes(), "0".getBytes().length);
							writer.writeTag(atv);
							if (an == 1){
								currentState = ChatState.FILE_REQ_ACK;
							}
							tv = reader.nextTagValue();
						}else if (tv.isTag(Tags.SESSION_CLOSE)){
							running = false;
							currentState = ChatState.END;
							this.close();
						}
						break;
					case FILE_REQ_ACK:
						if (tv.isTag(Tags.OPEN_CHAT_MSG)){
							writer.writeTag(tv);
							System.out.println(new String(tv.getContent()));
							tv = reader.nextTagValue();
						}else if (tv.isTag(Tags.FILE_DATA_BEGIN)){
							File f = new File(basedir + filename + ".bak");
							f.delete();
							this.sendChatMSG("Start receiving file ");
							currentState = ChatState.FILE_RECEIVED;
							tv = reader.nextTagValue();
						}else if (tv.isTag(Tags.SESSION_CLOSE)){
							running = false;
							currentState = ChatState.END;
							this.close();
						}
						break;
					case FILE_SENT:
						break;
					case FILE_REQ_SENT:
						if (tv.isTag(Tags.OPEN_CHAT_MSG)){
							writer.writeTag(tv);
							System.out.println(new String(tv.getContent()));
							tv = reader.nextTagValue();
						}else if (tv.isTag(Tags.OPEN_FILE_REQ_ACK)){
							String ack = new String(tv.getContent());
							System.out.println("File req. " + ack);
							if (ack.trim().equals("1")){
								currentState = ChatState.CHAT;
								sendFile();
							}else {
								currentState = ChatState.CHAT;
							}
							tv = reader.nextTagValue();
						}else if (tv.isTag(Tags.SESSION_CLOSE)){
							running = false;
							currentState = ChatState.END;
							this.close();
						}
						break;
					case FILE_RECEIVED:
						if (tv.isTag(Tags.OPEN_CHAT_MSG)){
							writer.writeTag(tv);
							System.out.println(new String(tv.getContent()));
							tv = reader.nextTagValue();
						}else if (tv.isTag(Tags.OPEN_FILE_DATA)){
						//	System.out.println(new String(tv.getContent()));
							FileOutputStream fos = new FileOutputStream(basedir + filename + ".bak", true);
							byte [] buf = tv.getContent();
							int i = 0;
							while (i < buf.length){
								byte b = buf[i];
								if (i < (buf.length - 1) && b == '<' && buf[i + 1] == '<'){
									fos.write(b);
									i++;
								}else if (i < (buf.length - 1) && b == '>' && buf[i + 1] == '>'){
									fos.write(b);
									i++;
								}else{
									fos.write(b);
								}
								i++;
							}
					//		System.out.println("Tag size: " + tv.getContent().length);
							fos.close();
							tv = reader.nextTagValue();
						}else if (tv.isTag(Tags.FILE_DATA_END)){
							this.sendChatMSG("Finish receiving file");
							TagValue atv = new TagValue(Tags.OPEN_FILE_REQ, (filename + ".bak").getBytes(),(filename + ".bak").getBytes().length);
							writer.writeTag(atv);
							currentState = ChatState.FILE_REQ_SENT;
							tv = reader.nextTagValue();
						}else if (tv.isTag(Tags.SESSION_CLOSE)){
							running = false;
							currentState = ChatState.END;
							this.close();
						}
						break;
					case END:
					default:
						System.out.println("Wrong protocol");
						break;
				}
			}
		}catch(TagFormatException tfe){
			tfe.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	private void sendChatMSG (String s) throws IOException{
		TagValue tv = new TagValue (Tags.OPEN_CHAT_MSG, s.getBytes(), s.getBytes().length);
		writer.writeTag(tv);
	}
	private void sendChatMSG (byte[] b)throws IOException {
		TagValue tv = new TagValue (Tags.OPEN_CHAT_MSG, b, b.length);
		writer.writeTag(tv);
	}
	private void close(){
		try{
			reader.close();
			writer.close();
			mySock.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	public int getRand(){
		Date d = new Date();
		return (int)(d.getTime()%2);
	}
	private void sendFile() throws IOException{
		System.out.println("Start sending file");
		FileSender fs = new FileSender(writer, basedir + filename + ".bak");
		fs.start();
	}
}

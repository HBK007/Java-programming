package client;

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
import java.util.Random;

import common.ChatState;
import common.FileSender;
import common.TagFormatException;
import common.TagReader;
import common.TagValue;
import common.TagWriter;
import common.Tags;

public class ClientInputStreamHandler extends Thread {
//	private BufferedReader reader;
//	private PrintWriter writer;
	private String file2send = "C:/temp/Comp.doc";
	private TagReader reader;
	private TagWriter writer;
	private ChatState currentState;
	private Socket mySock;
	private String userID = "TestClient";
	private String receivedFile;
	private String basedir = "C:/temp/";
	private boolean sendfile = true;
	private String [] testMsgs = {
			"Hello there!",
			"How are you?",
			"Tick tick.....",
			"Bye bye",
			"See you again"
	};
	private int msgcnt = 0;
	public ClientInputStreamHandler(Socket sock, String testfile) throws IOException{
		mySock = sock;
		file2send = testfile;
		currentState = ChatState.START;
		reader = new TagReader(sock.getInputStream());
		writer =new TagWriter(sock.getOutputStream());
	}
	public void run(){
		try{
			TagValue tv = null;
			boolean running = true;
			while (running){
				if (msgcnt == 3 && sendfile){
					File f = new File(file2send);
					String fshort = f.getName();
					TagValue atv = new TagValue(Tags.OPEN_FILE_REQ, fshort.getBytes(), fshort.getBytes().length);
					writer.writeTag(atv);
					currentState = ChatState.FILE_REQ_SENT;
					sendfile = false;
				}
				switch (currentState){
					case START:
						TagValue atv = new TagValue(Tags.OPEN_SESSION_REQ,
											userID.getBytes(), userID.getBytes().length);
						writer.writeTag(atv);
						currentState = ChatState.CHAT_REQ;
						tv = reader.nextTagValue();
						break;
					case CHAT_REQ:
						if (tv.isTag(Tags.OPEN_SESSION_ACK)){
							currentState = ChatState.CHAT;
							tv = reader.nextTagValue();
							this.sendChatMSG(testMsgs[msgcnt%5]);
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							msgcnt++;
							if (msgcnt == 1000){
								running = false;
								this.close();
								currentState = ChatState.END;
							}
						}
						break;
					case CHAT:
						if (tv.isTag(Tags.OPEN_CHAT_MSG)){
							System.out.println(new String(tv.getContent()));
							this.sendChatMSG(testMsgs[msgcnt%5]);
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							msgcnt++;
							if (msgcnt == 1000){
								running = false;
								this.close();
								currentState = ChatState.END;
							}else{
								tv = reader.nextTagValue();
							}
						}else if (tv.isTag(Tags.OPEN_FILE_REQ)){
							this.sendChatMSG("Received req. " + new String(tv.getContent()));
							TagValue atvv = new TagValue (Tags.OPEN_FILE_REQ_ACK, "1".getBytes(), "1".getBytes().length);
							writer.writeTag(atvv);
							receivedFile = new String(tv.getContent());
							currentState = ChatState.FILE_REQ_ACK;
							tv = reader.nextTagValue();
						}else if (tv.isTag(Tags.SESSION_CLOSE)){
							running = false;
							currentState = ChatState.END;
							this.close();
						}
						break;
					case FILE_REQ_SENT:
						if (tv.isTag(Tags.OPEN_CHAT_MSG)){
							System.out.println(new String(tv.getContent()));
							this.sendChatMSG(testMsgs[msgcnt%5]);
							msgcnt++;
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							if (msgcnt == 1000){
								running = false;
								this.close();
								currentState = ChatState.END;
							}else{
								tv = reader.nextTagValue();
							}
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
					case FILE_REQ_RECEIVED:
						break;
					case FILE_REQ_ACK:
						if (tv.isTag(Tags.OPEN_CHAT_MSG)){
							System.out.println(new String(tv.getContent()));
							this.sendChatMSG(testMsgs[msgcnt%5]);
							msgcnt++;
							if (msgcnt == 1000){
								running = false;
								this.close();
								currentState = ChatState.END;
							}else{
								tv = reader.nextTagValue();
							}
						}else if (tv.isTag(Tags.FILE_DATA_BEGIN)){
							this.sendChatMSG("Start receiving file ");
							File f = new File(basedir + receivedFile + ".bak");
							f.delete();
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
					case FILE_RECEIVED:
						if (tv.isTag(Tags.OPEN_CHAT_MSG)){
							System.out.println(new String(tv.getContent()));
							this.sendChatMSG(testMsgs[msgcnt%5]);
							msgcnt++;
							if (msgcnt == 1000){
								running = false;
								this.close();
								currentState = ChatState.END;
							}else{
								tv = reader.nextTagValue();
							}
						}else if (tv.isTag(Tags.OPEN_FILE_DATA)){
						//	System.out.println(new String(tv.getContent()));
							FileOutputStream fos = new FileOutputStream(basedir + receivedFile + ".bak", true);
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
							currentState = ChatState.CHAT;
							tv = reader.nextTagValue();
						}else if (tv.isTag(Tags.SESSION_CLOSE)){
							currentState = ChatState.END;
							running = false;
							this.close();
						}
						break;
					case END:
					default:
						break;
				}
			}
		}catch(IOException ioe){
			System.out.println("Error reading InputStream");
			ioe.printStackTrace();
		}catch(TagFormatException tfe){
			tfe.printStackTrace();
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
	private void sendFile() throws IOException{
		System.out.println("Start sending file");
		FileSender fs = new FileSender(writer, file2send);
		fs.start();
	}

}

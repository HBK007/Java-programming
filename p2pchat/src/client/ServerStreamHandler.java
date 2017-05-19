package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import common.TagFormatException;
import common.TagValue;
import common.Tags;

public class ServerStreamHandler extends Thread {
	private BufferedReader reader;
	private PrintWriter writer;
	private String expectedTag;
	private ChatClientGUI clientGUI;
	public ServerStreamHandler(ChatClientGUI gui, BufferedReader br, PrintWriter pw){
		clientGUI = gui;
		reader = br;
		writer = pw;
		expectedTag = Tags.OPEN_SEND;
	}
	public void run(){
		try{
			int c = reader.read();
			while (c != -1){
				TagValue tv;
				try {
					tv = this.getTagValue(c);
					if (tv.getTag().equals(Tags.DISC)){
						writer.print(Tags.DISC);
						clientGUI.display("Disconnect...");
//						System.out.println("Send: " + Tags.DISC);					
						reader.close();
						writer.close();
						return;
					}
					if (!tv.getTag().equals(this.expectedTag)){
						writer.print(Tags.OPEN_SEND + "Expected to receive --" + this.expectedTag.substring(1, expectedTag.length()-1) + "--" + Tags.END_SEND);
						writer.flush();
//						System.out.println("Send: " + "Expected to receive --" + this.expectedTag.substring(1, expectedTag.length()-1) + "--");
						clientGUI.display("Send: " + "Expected to receive --" + this.expectedTag.substring(1, expectedTag.length()-1) + "--");
					}else{
						if (tv.getTag().equals(Tags.OPEN_SEND)){
//							System.out.println("Received: " + tv.getContent());
							clientGUI.display("Received: " + tv.getContent());
							//Random r = new Random();
							//int m = r.nextInt(this.testMsgs.length);
							//writer.print(Tags.OPEN_SEND + this.testMsgs[m] + Tags.END_SEND);
							//System.out.println("Send: " + Tags.OPEN_SEND + this.testMsgs[m] + Tags.END_SEND);
							//writer.flush();
							//try {
							//	Thread.sleep(1000);
							//} catch (InterruptedException e) {
								// TODO Auto-generated catch block
							//	e.printStackTrace();
							//}
							//this.msgcnt++;
							//if (this.msgcnt == this.testMsgs.length){
							//	writer.print(Tags.DISC);
							//	System.out.println("Send: " + Tags.DISC);
							//	writer.flush();
							//	reader.close();
							//	writer.close();
							//}
						}
					}
				} catch (TagFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					writer.print(Tags.OPEN_SEND + "Malformed message received" + Tags.END_SEND);
					writer.flush();
					clientGUI.display("Malformed message received");
				}
				c = reader.read();
			}
		}catch(IOException ioe){
			System.out.println("Error reading InputStream");
			ioe.printStackTrace();
		}
	}
	public TagValue getTagValue (int preread) throws IOException, TagFormatException{
		TagValue tv = new TagValue();
		int c = preread;
		while (Character.isWhitespace(c)){
			c = reader.read();
		}
		String tag;
		if (c == '<'){
			tag = this.getTag();
			//if this is a disconnect tag, return
			if (tag.toLowerCase().equals(Tags.DISC)){
				tv = new TagValue(Tags.DISC, "");
			}else{
				// if this is not a disconnect tag
				String val = this.getValue();
				String endtag = this.getTag();
				if (Tags.validateTags(tag, endtag)){
					tv = new TagValue (tag, val);
				}else{
					TagFormatException tfe = new TagFormatException("End tag doesn't match start tag: " + tag + ":" + endtag);
					throw tfe;
				}
			}
		}else{
			TagFormatException tfe = new TagFormatException("An open tag is expected instead of --" + Character.toString((char)c) +"--");
			throw tfe;			
		}
		return tv;
	}
	public String getTag() throws IOException {
		String tag = "<";
		int c = reader.read();
		while (c != '>'){
			tag += Character.toString((char)c);
			c = reader.read();
		}
		tag += ">";
		return tag.toLowerCase();
	}
	public String getValue () throws IOException {
		String val = "";
		int c = reader.read();
		while (c != '<'){
			val += Character.toString((char) c);
			c = reader.read();
		}
		return val;
	}
}

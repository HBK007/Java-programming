package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import common.TagFormatException;
import common.TagValue;
import common.Tags;

public class InputStreamHandler extends Thread {
	private BufferedReader reader;
	private PrintWriter writer;
	private String expectedTag;
	private String userID = "";
	private int msgcnt = 0;
	public InputStreamHandler(InputStream is, OutputStream os){
		reader = new BufferedReader(new InputStreamReader(is));
		writer = new PrintWriter(new OutputStreamWriter(os));
		expectedTag = Tags.OPEN_CONN;
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
						System.out.println("Send: " + Tags.DISC);
						writer.flush();
						reader.close();
						writer.close();
						return;
					}
					if (!tv.getTag().equals(this.expectedTag)){
						writer.print(Tags.END_SEND + "Expected to receive --" + this.expectedTag.substring(1, expectedTag.length()-1) + "--" + Tags.END_SEND);
						System.out.println("Send: " + Tags.END_SEND + "Expected to receive --" + this.expectedTag.substring(1, expectedTag.length()-1) + "--" + Tags.END_SEND);
						writer.flush();
					}else{
						if (tv.getTag().equals(Tags.OPEN_CONN)){
							userID = tv.getContent();
							this.expectedTag = Tags.OPEN_SEND;
							writer.print(Tags.OPEN_SEND + "Hi " + userID + Tags.END_SEND);
							System.out.println("Send: " + Tags.OPEN_SEND + "Hi " + userID + Tags.END_SEND);
							writer.flush();
						}else if (tv.getTag().equals(Tags.OPEN_SEND)){
							writer.print(Tags.OPEN_SEND + "I have received: " + tv.getContent() + Tags.END_SEND);
							System.out.println("Send: " + Tags.OPEN_SEND + "I have received: " + tv.getContent() + Tags.END_SEND);
							writer.flush();
							msgcnt++;
							if (msgcnt >= 20){
								writer.print(Tags.OPEN_SEND + "You are talking too much!" + Tags.END_SEND);
								writer.print(Tags.OPEN_SEND + "Bye bye!" + Tags.END_SEND);
								System.out.println("Send: " + Tags.OPEN_SEND + "Bye bye!" + Tags.END_SEND);
								writer.print(Tags.DISC);
								System.out.println("Send: " + Tags.DISC);
								writer.flush();
								reader.close();
								writer.close();
							}
						}
					}
				} catch (TagFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					writer.print(Tags.OPEN_SEND + "Malformed message received" + Tags.END_SEND);
					writer.flush();
					System.out.println("Send: " + Tags.OPEN_SEND + "Malformed message received" + Tags.END_SEND);
				}
				c = reader.read();
			}
		}catch(IOException ioe){
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

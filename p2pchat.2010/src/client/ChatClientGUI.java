package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import common.TagReader;
import common.TagValue;
import common.TagWriter;
import common.Tags;

public class ChatClientGUI extends JFrame {

	private JTextField userIDTF;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea sendTA;
	private JTextField serverPortTF;
	private JTextField serverURLTF;
	private JTextArea displayTA;
	private TagReader reader;
	private TagWriter writer;
	private Socket socket;
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			ChatClientGUI frame = new ChatClientGUI();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	public ChatClientGUI() {
		super();
		setTitle("Chat Client");
		setBounds(100, 100, 549, 375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		userIDTF = new JTextField();
		userIDTF.setColumns(7);
		userIDTF.setText("Test Client");
		panel.add(userIDTF);

		serverURLTF = new JTextField();
		serverURLTF.setText("localhost");
		serverURLTF.setColumns(20);
		panel.add(serverURLTF);

		serverPortTF = new JTextField();
		serverPortTF.setText("2222");
		serverPortTF.setColumns(5);
		panel.add(serverPortTF);

		final JButton connectButton = new JButton();
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				connectButtonAction(arg0);
			}
		});
		connectButton.setText("Connect");
		panel.add(connectButton);

		final JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		final JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);

		sendTA = new JTextArea();
		scrollPane.setViewportView(sendTA);
		sendTA.setColumns(30);
		sendTA.setRows(3);

		final JButton sendButton = new JButton();
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				if (writer != null){
					sendChatMSG(sendTA.getText().trim());
					display("Send: "+ sendTA.getText());
					sendTA.setText("");
				}else{
					display("Error: "+ "Not connected");
				}
			}
		});
		sendButton.setText("Send");
		panel_1.add(sendButton);

		final JButton disconnectButton = new JButton();
		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				if (socket != null){
					try {
						TagValue tv = new TagValue(Tags.SESSION_CLOSE,null, 0);
						writer.writeTag(tv);
						writer.close();
						writer = null;
						reader.close();
						reader = null;
						socket.close();
						socket = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		disconnectButton.setText("disconnect");
		panel_1.add(disconnectButton);

		final JScrollPane scrollPane_1 = new JScrollPane();
		getContentPane().add(scrollPane_1, BorderLayout.CENTER);

		displayTA = new JTextArea();
		scrollPane_1.setViewportView(displayTA);
		//
	}
	public void display(String s){
		displayTA.append("\n" + s);
	}
	public void connectButtonAction(ActionEvent ae){
		try {
			if (socket != null){
				socket.close();
			}
			socket = new Socket(serverURLTF.getText(), Integer.parseInt(serverPortTF.getText()));
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			reader = new TagReader(is);
			writer = new TagWriter(os);
			TagValue tv = new TagValue(Tags.OPEN_SESSION_REQ,userIDTF.getText().trim().getBytes(),userIDTF.getText().trim().getBytes().length);
			writer.writeTag(tv);
			display("Connect to server using userid: " + userIDTF.getText());
			ServerStreamHandler ssh = new ServerStreamHandler(this, reader, writer);
			ssh.start();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			display("Exception: " + e.getMessage());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			display("Exception: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			display("Exception: " + e.getMessage());
		}		
	}
	private void sendChatMSG (String s){
		try{
			String temp = "";
			for (int i = 0; i < s.length(); i++){
				if (s.charAt(i) == '<'){
					temp += '<';
				}else if(s.charAt(i) == '>'){
					temp += '>';
				}
				temp += s.charAt(i);
			}
			TagValue tv = new TagValue (Tags.OPEN_CHAT_MSG, temp.getBytes(), temp.getBytes().length);
			writer.writeTag(tv);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}

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
	private BufferedReader reader;
	private PrintWriter writer;
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
					writer.print(Tags.OPEN_SEND + sendTA.getText() + Tags.END_SEND);
					writer.flush();
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
						writer.print(Tags.DISC);
						writer.flush();
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
			reader = new BufferedReader(new InputStreamReader(is));
			writer = new PrintWriter(new OutputStreamWriter(os));
			writer.print(Tags.OPEN_CONN + userIDTF.getText() + Tags.END_CONN);
			writer.flush();
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
}

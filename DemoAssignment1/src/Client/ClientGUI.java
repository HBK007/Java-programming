package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.Panel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;

public class ClientGUI implements ActionListener{

	private JFrame frmClient;
	private JPanel panelFile;
	private JTextField txtNameClient;
	private JTextField txtPort;
	private JButton btnConnect;
	private JButton btnOpenFile;
	private JTextField txtNameFile;
	private JTextField txtTime;
	private JButton btnReadFile;
	private JButton btnCancel;
	private JFileChooser fc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
					window.frmClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClient = new JFrame();
		frmClient.setTitle("Client");
		frmClient.setBounds(100, 100, 450, 300);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClient.getContentPane().setLayout(null);
		
		JPanel panelConnect = new JPanel();
		panelConnect.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Connect", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18)));
		panelConnect.setBounds(12, 13, 408, 71);
		frmClient.getContentPane().add(panelConnect);
		panelConnect.setName("");
		panelConnect.setLayout(null);
		
		txtNameClient = new JTextField();
		txtNameClient.setText("Client 1");
		txtNameClient.setBounds(12, 23, 159, 22);
		panelConnect.add(txtNameClient);
		txtNameClient.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.setText("5000");
		txtPort.setBounds(183, 23, 97, 22);
		panelConnect.add(txtPort);
		txtPort.setColumns(10);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConnect.setBounds(299, 22, 97, 25);
		panelConnect.add(btnConnect);;
		panelFile = new JPanel();
		panelFile.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "File", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18)));
		panelFile.setBounds(12, 97, 408, 128);
		frmClient.getContentPane().add(panelFile);
		panelFile.setLayout(null);
		
		btnOpenFile = new JButton("Open File");
		btnOpenFile.addActionListener(this);
		btnOpenFile.setBounds(12, 25, 97, 25);
		panelFile.add(btnOpenFile);
		
		txtNameFile = new JTextField();
		txtNameFile.setText("File name");
		txtNameFile.setBounds(130, 26, 167, 22);
		panelFile.add(txtNameFile);
		txtNameFile.setColumns(10);
		
		txtTime = new JTextField();
		txtTime.setHorizontalAlignment(SwingConstants.CENTER);
		txtTime.setText("30");
		txtTime.setBounds(309, 26, 87, 22);
		panelFile.add(txtTime);
		txtTime.setColumns(10);
		
		btnReadFile = new JButton("Read File");
		btnReadFile.addActionListener(this);
		btnReadFile.setBounds(12, 63, 97, 25);
		panelFile.add(btnReadFile);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
			
		btnCancel.setBounds(130, 63, 97, 25);
		panelFile.add(btnCancel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnConnect){
			//connectServer();
			return;
		}
		if(e.getSource() == btnOpenFile){
			openFile();
			return;
		}
		if(e.getSource() == btnReadFile){
			//readFile();
		}
		if(e.getSource() == btnCancel){
			//cancelReadFile();
			return;
		}
		
	}
	private void openFile(){
		fc = new JFileChooser();
		File inputFile;
		int select = fc.showOpenDialog(null);
		if(select == JFileChooser.APPROVE_OPTION){
			inputFile = fc.getSelectedFile();
			txtNameFile.setText(inputFile.getName());
		}
	}
	
}

package server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class GatewayGUI {

	private JFrame frmGateway;
	private JTextField txtTimeSend;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GatewayGUI window = new GatewayGUI();
					window.frmGateway.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GatewayGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGateway = new JFrame();
		frmGateway.setTitle("Gateway");
		frmGateway.setBounds(100, 100, 450, 485);
		frmGateway.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGateway.getContentPane().setLayout(null);
		
		JPanel panelControl = new JPanel();
		panelControl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Control", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18)));
		panelControl.setBounds(12, 13, 408, 66);
		frmGateway.getContentPane().add(panelControl);
		panelControl.setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startServer(e);
			}
		});
		btnStart.setBounds(12, 28, 97, 25);
		panelControl.add(btnStart);
		
		JButton btnCancel = new JButton("Stop");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopServer(e);
			}
		});
		btnCancel.setBounds(299, 28, 97, 25);
		panelControl.add(btnCancel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "View", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18)));
		scrollPane.setBounds(12, 105, 408, 222);
		frmGateway.getContentPane().add(scrollPane);
		
		JTextArea txtInfor = new JTextArea();
		scrollPane.setViewportView(txtInfor);
		
		JPanel panelSen = new JPanel();
		panelSen.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Send", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18)));
		panelSen.setBounds(12, 340, 408, 85);
		frmGateway.getContentPane().add(panelSen);
		panelSen.setLayout(null);
		
		txtTimeSend = new JTextField();
		txtTimeSend.setHorizontalAlignment(SwingConstants.CENTER);
		txtTimeSend.setText("30");
		txtTimeSend.setBounds(35, 38, 116, 22);
		panelSen.add(txtTimeSend);
		txtTimeSend.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendDataGPS(e);
			}
		});
		btnSend.setBounds(240, 37, 97, 25);
		panelSen.add(btnSend);
	}
	
	// start server
	private void startServer(ActionEvent e){
		
	}
	// stop server
	private void stopServer(ActionEvent e){
		
	}
	// send data GPS
	private void sendDataGPS(ActionEvent e){
		
	}
	
}

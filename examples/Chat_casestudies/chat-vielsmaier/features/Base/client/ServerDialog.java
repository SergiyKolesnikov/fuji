package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ServerDialog extends JDialog {
	private JTextField hostnameField;
    private JTextField portField;
    private JLabel hostnameLabel;
    private JLabel portLabel;
    private JButton connectButton;
	
	public ServerDialog(final Client chatClient) {
		hostnameField = new JTextField("localhost");
	    portField = new JTextField("8017");
	    
	    getContentPane().setLayout(null);
	    setSize(new Dimension(300,200)); 
	    setLocationRelativeTo(this);
	    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    setModal(true);
	    
	    hostnameLabel = new JLabel("Address:");
	    hostnameLabel.setBounds(20,20,160,20); 
	    getContentPane().add(hostnameLabel);
	    hostnameField.setBounds(20,40,260,20);
	    getContentPane().add(hostnameField);
	    
	    portLabel = new JLabel("Port:");
	    portLabel.setBounds(20,60,160,20);
	    getContentPane().add(portLabel);
	    portField.setBounds(20,80,260,20);
	    getContentPane().add(portField);
	    
	    connectButton = new JButton("Login");
	    connectButton.setBounds(20,120,100,20);
	    connectButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e) {
	        	chatClient.setServerAddress(hostnameField.getText());
	        	chatClient.setPort(Integer.parseInt(portField.getText()));
	        	chatClient.connect();
	        	setVisible(false);
	        }
	    });
	    getContentPane().add(connectButton);
	    setVisible(true);
	}
}

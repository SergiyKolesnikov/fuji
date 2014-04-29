package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginDialog extends JDialog {
	private JPasswordField passwordField;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
	
	public LoginDialog(final Client chatClient) {
		passwordField = new JPasswordField("");
	    usernameField = new JTextField("");
	    
	    getContentPane().setLayout(null);
	    setSize(new Dimension(300,200)); 
	    setLocationRelativeTo(this);
	    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    setModal(true);
	    
	    usernameLabel = new JLabel("User:");
	    usernameLabel.setBounds(20,20,160,20); 
	    getContentPane().add(usernameLabel);
	    usernameField.setBounds(20,40,260,20);
	    getContentPane().add(usernameField);
	    
	    passwordLabel = new JLabel("Password:");
	    passwordLabel.setBounds(20,60,160,20);
	    getContentPane().add(passwordLabel);
	    passwordField.setBounds(20,80,260,20);
	    getContentPane().add(passwordField);
	    
	    loginButton = new JButton("Login");
	    loginButton.setBounds(20,120,100,20);
	    loginButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e) {
	        	chatClient.login(usernameField.getText(), new String(passwordField.getPassword()));
	        	setVisible(false);
	        }
	    });
	    getContentPane().add(loginButton);
	    setVisible(true);
	}
}

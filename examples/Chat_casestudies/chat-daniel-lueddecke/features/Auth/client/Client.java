package client;

import interfaces.IClient;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.BevelBorder;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * simple chat client
 */
public class Client implements Runnable, IClientProxy {
	
	private final static String canAuthPasswd = "ich bin der martin";
	private static String enteredPassword = null;
	
	private void prepareStart() {
		if(canAuthPasswd != null) {
			boolean correct = false;
			while(!correct) {
				AdminCheck(null);
				
				if(enteredPassword == null)
					System.exit(0);
				
				if(enteredPassword.equals(canAuthPasswd))
					correct = true;
			}
		}
		
		original();
	}
	
	public static void enteredPassword(String password) {
		enteredPassword = password;
	}	
	
	public static void AdminCheck(Frame parent) {
		enteredPassword(null);
		
		final JDialog dbDlg = new JDialog(parent,"Administration Security",true); //myframe
		JPanel dbpanel = new JPanel();
		dbpanel.setLayout(null);
		dbDlg.setSize(300,200);
		dbDlg.setLocation(300,200);
		dbpanel.setSize(300,200);
		dbpanel.setBorder(new BevelBorder(BevelBorder.RAISED));

		JLabel passwdlabel = new JLabel("Password");
		passwdlabel.setSize(80,20);
		passwdlabel.setLocation(10,50);
		dbpanel.add(passwdlabel);
		final JPasswordField passwdfield = new JPasswordField();
		passwdfield.setSize(200,20);
		passwdfield.setLocation(80,50);
		dbpanel.add(passwdfield);

		passwdfield.addKeyListener(
			new KeyAdapter() {
				public void keyPressed(KeyEvent evt) {
					if (evt.getKeyCode()==KeyEvent.VK_ESCAPE) {
						enteredPassword(null);
						dbDlg.dispose();
					}
					if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
						enteredPassword(new String(passwdfield.getPassword()));
						dbDlg.dispose();
					}
				}
			}
		);

		JButton connbtn = new JButton("OK");
		connbtn.setSize(100,30);
		connbtn.setLocation(35,120);
		dbpanel.add(connbtn);
		
		connbtn.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e) { 
					enteredPassword(new String(passwdfield.getPassword()));
					dbDlg.dispose();
				}
			}
		);
		
		JButton canbtn = new JButton("Cancel");
		canbtn.setSize(100,30);
		canbtn.setLocation(155,120);
		canbtn.addActionListener(
			new ActionListener() { 
				public void actionPerformed(ActionEvent e) { 
					enteredPassword(null);
					dbDlg.dispose();
				}
			}
		);
		dbpanel.add(canbtn);
		dbDlg.getContentPane().add(dbpanel);
		dbDlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
		dbDlg.setVisible(true);
	}
}

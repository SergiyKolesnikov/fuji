package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import client.Client;

/**
 * TODO description
 */
public class Gui {
	JButton btnSave;
	public Gui(String title, Client chatClient)
	{
		JButton btnsave = new JButton("Save");
	    pnlChat.add(btnsave);
	    
	    btnsave.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          System.out.println(e.getSource());
	          saveMessage();
	        }
	      });
	    
	}
	
protected void saveMessage() {
		
		try {
			FileOutputStream fileoutput = new FileOutputStream(
					"message.txt", true);
			String temp = outputTextbox.getText();
			
			fileoutput.write(temp.getBytes());
			fileoutput.close();
			JOptionPane.showMessageDialog(null, "The Chat wird gespreichert in" + "message.txt");
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

}
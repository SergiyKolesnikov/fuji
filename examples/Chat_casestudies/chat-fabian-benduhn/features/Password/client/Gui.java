package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout;


/**
 * simple swing gui for the chat client
 */
public class Gui extends JFrame implements ChatLineListener{


	private void GuiConstructor(String title, Client chatClient) {
		
	if (showPasswordDialog("Please enter your password!") == false)
		return;
		
		original(title,chatClient);
	
}}

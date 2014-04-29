package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
//import javax.swing.JTextArea;
import javax.swing.GroupLayout;

import common.Message;
//import javax.swing.GroupLayout.Alignment.*;



/**
 * simple swing gui for the chat client
 */
public class Gui extends JFrame implements ChatPlugin {

	public Gui(String title, Client chatClient) {


		outputTextbox = new JEditorPane ("text/html","<html><body>Welcome to EPMD_Chat!<br>\n</body></html>");

	}

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	@Override
	public Message process(Message msg) {
		if (msg.getStatus()==0||msg.getStatus()==3){ // only react on typematching Messages
			// new line is appended to JEditorPane, in HTML
			outputTextbox.setText(outputTextbox.getText().replaceAll("</body>\\s</html>", msg.getText() + "<br>\n</body></html>"));
			try { // Scroll to the last entered line
				outputTextbox.setCaretPosition(outputTextbox.getDocument().getLength());
			}
			catch(IllegalArgumentException ex){	
			}
		}
		return msg;
	}

}

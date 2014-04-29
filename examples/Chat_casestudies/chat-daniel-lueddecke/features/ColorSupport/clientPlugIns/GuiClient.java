package clientPlugIns;

import javax.swing.GroupLayout;
import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import java.awt.Color;
import java.awt.Dimension;

/**
 * simple swing gui for the chat client
 */
public class GuiClient extends JFrame implements IClient {

	protected JColorChooser colorChooser;
	
	private void createComponents() {
		System.out.println("starting gui...");
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		ParallelGroup pGroup = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		SequentialGroup sGroup = layout.createSequentialGroup();

		outputTextbox = new JEditorPane();
		outputTextbox.setContentType("text/html");
		outputTextbox.setPreferredSize(new Dimension(colstextarea*20, rowstextarea*20));
		outputTextbox.setEditable(false);
		pGroup.addComponent(outputTextbox);
		sGroup.addComponent(outputTextbox);
		
		inputField = new JTextField();
		inputField.addActionListener(getInput());
		pGroup.addComponent(inputField);
		sGroup.addComponent(inputField);
		
		colorChooser = new JColorChooser(Color.black);
		pGroup.addComponent(colorChooser);		
		sGroup.addComponent(colorChooser);
		
		layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(pGroup));
        layout.setVerticalGroup(sGroup);
        
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = inputField.getText();
				
				String messageToSend = message;
				
				Color color = colorChooser.getColor();
				messageToSend = "<font color=\"#" + Integer.toHexString(color.getRGB()).substring(2) + "\">" + message + "</font>";
				
				if(myProxy == null)
					return;
				
				myProxy.send((String) messageToSend);
				inputField.setText("");
			}
		};
	}
}

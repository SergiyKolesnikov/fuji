
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;




public class StartFrame extends Frame{

	JButton guiButton = new JButton("GUI");
	JButton consoleButton = new JButton("Console");
	Client client;
	
	
	public StartFrame (String title, final String info, final Client client){
		
		super(title);
				
		setLayout(new BorderLayout());
		
		add("East", guiButton);
		add("West", consoleButton);
	

		pack();
		setVisible(true);
	
        guiButton.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent event) {
            	
            	client.startGui(info, client);            	
            	StartFrame.this.dispose();
            }
        });

        consoleButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

            	client.startConsole(info, client);
            	StartFrame.this.dispose();
            	
            }
        });           
	}
}
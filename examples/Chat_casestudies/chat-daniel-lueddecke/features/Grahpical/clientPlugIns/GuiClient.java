package clientPlugIns;

import interfaces.IClient;
import interfaces.IClientProxy;

//#if ColorSupport
//@import javax.swing.JColorChooser;
//@import java.awt.Color;
//#endif

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;

/**
 * simple swing gui for the chat client
 */
public class GuiClient extends JFrame implements IClient {

	private static final long serialVersionUID = 1L;

	protected JEditorPane outputTextbox;
	protected JTextField inputField;

	private static int rowstextarea = 20;
	private static int colstextarea = 60;
	
	private IClientProxy myProxy;
	
	public GuiClient() {
		
		createComponents();
	}
	
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
				
				if(myProxy == null)
					return;
				
				myProxy.send((String) messageToSend);
				inputField.setText("");
			}
		};
	}

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(String line) {
		outputTextbox.setText(outputTextbox.getText().replace("</body>", line+"<br /></body>"));
	}

	@Override
	public void start() {
		setVisible(true);
	}

	@Override
	public void stop() {
		setVisible(false);
	}

	@Override
	public void setClientProxy(IClientProxy proxy) {
		myProxy = proxy;
	}

}

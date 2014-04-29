package client; 

import java.awt.Adjustable; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.AdjustmentEvent; 
import java.awt.event.AdjustmentListener; 
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 

import javax.swing.GroupLayout; 
import javax.swing.JFrame; 
import javax.swing.JScrollPane; 
import javax.swing.JTextField; 
import javax.swing.JTextPane; 
import javax.swing.text.BadLocationException; 
import javax.swing.text.Style; 
import javax.swing.text.StyledDocument; 

import java.awt.Color; 
import javax.swing.text.StyleConstants; 

public   class  Gui  extends JFrame {
	

	private static final long serialVersionUID = 1L;

	

	protected JTextPane outputTextPane;

	
	protected JScrollPane outputScrollPane;

	
	protected JTextField inputField;

	
	
	protected StyledDocument outputDoc;

	
	private final Style[] styles;

	

	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui() {
		setTitle("Chatclient");
        setLocationByPlatform(true);
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                Client.close();
            }
        });

		outputTextPane = new JTextPane();
		outputTextPane.setEditable(false);
		
		styles = new Style[Message.NUM_SENDERTYPES];
		createStyles();
		
		outputScrollPane = new JScrollPane();
		outputScrollPane.setViewportView(outputTextPane);
		outputScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			private int lastMaximum = 0;
			public void adjustmentValueChanged(AdjustmentEvent event) {
				Adjustable adj = event.getAdjustable();
				final int maximum = adj.getMaximum();
				if (lastMaximum != maximum) {
					lastMaximum = maximum;
					adj.setValue(maximum);
				}
			}
		});

		outputDoc = outputTextPane.getStyledDocument();
		
		inputField = new JTextField();
		inputField.addActionListener(getInput());
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputScrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addComponent(inputField)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                    .addComponent(outputScrollPane, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(inputField));

		pack();

		inputField.grabFocus();
		setVisible(true);
	}

	

	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.sendMessage(inputField.getText());
				inputField.setText("");
			}
		};
	}

	

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(Message msg) {
		try {
			int docLength = outputDoc.getLength();
			if (docLength > 0) {
				outputDoc.insertString(docLength, System.getProperty("line.separator") , null);
			}
			outputDoc.insertString(outputDoc.getLength(), msg.getLine(), 
					styles[msg.getSenderType()]);			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	

	public void setUsername(String username) {
		setTitle("Chatclient " + username);
	}

	
	
	 private void  createStyles__wrappee__GUI  () {
		for (int i = 0; i < styles.length; i++) {
			styles[i] = outputTextPane.addStyle("Style" + i, null);
		}
	}

	

	private void createStyles() {
		createStyles__wrappee__GUI();
		StyleConstants.setForeground(styles[Message.SENDERTYPE_DEFATULT], Color.black);
        StyleConstants.setForeground(styles[Message.SENDERTYPE_SELF], Color.blue);
        StyleConstants.setForeground(styles[Message.SENDERTYPE_OTHER], Color.red);
        StyleConstants.setForeground(styles[Message.SENDERTYPE_SERVER], Color.gray);
	}


}

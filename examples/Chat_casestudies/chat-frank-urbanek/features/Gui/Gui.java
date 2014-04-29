//package com.frank.client.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class Gui extends javax.swing.JFrame implements ChatLineListener {
	private static final long serialVersionUID = 1L;

	protected JTextArea outputTextbox;
	protected JTextField inputField;

	private int rowstextarea = 20;
	private int colstextarea = 60;

	private Client client;

	private JPanel jPanel3;

	// #if Color
	// @ ColorPanel colorPanel;
	// @
	// #endif

	public Gui() {
		super();
		client = new Client();
		initGUI();

		// #if Color
		// @ attachColorPanel();
		// #endif
		setListeners();
		client.start();
	}

	private void setListeners() {
		client.addLineListener(this);
		// #if Console
		// @ client.addLineListener(new ConsoleOutput());
		// #endif
		inputField.addActionListener(getInput());
	}

	private void initGUI() {
		try {
			System.out.println("starting gui...");

			outputTextbox = new JTextArea(rowstextarea, colstextarea);
			outputTextbox.setEditable(false);
			inputField = new JTextField();

			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel3 = new JPanel();
				getContentPane().add(jPanel3, BorderLayout.CENTER);
				GroupLayout jPanel3Layout = new GroupLayout(
						(JComponent) jPanel3);
				jPanel3.setLayout(jPanel3Layout);
				jPanel3.setPreferredSize(new java.awt.Dimension(140, 262));
				// jPanel3Layout.setVerticalGroup(jPanel3Layout.createSequentialGroup());
				jPanel3Layout
						.setHorizontalGroup(jPanel3Layout
								.createSequentialGroup()
								.addGroup(
										jPanel3Layout
												.createParallelGroup(
														javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(outputTextbox)
												.addComponent(inputField)));
				jPanel3Layout.setVerticalGroup(jPanel3Layout
						.createSequentialGroup().addComponent(outputTextbox)
						.addComponent(inputField));
			}
			setTitle("Chat");
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pack();
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = (String) inputField.getText();
				
				TextMessage message;
				// #if Color
				// @ message = new TextMessage((String) inputField.getText());
				// @ message.setColor(colorPanel.getTextColor());
				// #endif
				// #if !Color
				message = new TextMessage((String) inputField.getText());
				processMessage(message);
				// #endif
				client.send(message);
				inputField.setText("");
			}
		};
	}

	private void processMessage(TextMessage msg){
		
	}
	
	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	@Override
	public void newChatLine(String line) {
		outputTextbox.append(line);
	}

	@Override
	public void newTextMessage(TextMessage msg, MessageContext context) {
		//String text = msg.getContent();
		//String formatedtext = msg.getContent();

		String text = "";
		text = String.format("%s: %s\n", context.getInfo().getName(),
				CreateFormattedText(msg.getContent(), msg));
		outputTextbox.append(text);
	}

	private String CreateFormattedText(String text, TextMessage msg) {
		
		// #if Color
		// @ out = String.format("<%X>%s</%X>", msg.getColor().getRGB(), out,
//@		// msg
		// @ .getColor().getRGB());
		// #endif
		return text;
	}
}

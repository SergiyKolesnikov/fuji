package client;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.AbstractButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.ButtonGroup;

/**
 * simple swing gui for the chat client
 */
public class Gui extends JFrame implements ChatLineListener {

	private static final String COLOR = "color";
	private static final String ENCRYPTION = "encryption";

	private static final long serialVersionUID = 1L;

	protected JTextArea outputTextbox;
	protected JTextField inputField;

	private static int rowstextarea = 20;
	private static int colstextarea = 60;

	private Client chatClient;
	private final static ButtonGroup colorGroup = new ButtonGroup();
	private final static ButtonGroup encryptionGroup = new ButtonGroup();

	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 * @throws InterruptedException
	 */
	public Gui(String title, Client chatClient) throws InterruptedException {
		System.out.println("starting gui...");

		outputTextbox = new JTextArea(Gui.rowstextarea, Gui.colstextarea);
		outputTextbox.setEditable(false);
		inputField = new JTextField();
		inputField.addActionListener(getInput());

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Black");
		rdbtnNewRadioButton.setSelected(true);
		colorGroup.add(rdbtnNewRadioButton);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Red");
		colorGroup.add(rdbtnNewRadioButton_1);

		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Green");
		colorGroup.add(rdbtnNewRadioButton_2);

		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Blue");
		colorGroup.add(rdbtnNewRadioButton_3);

		JLabel lblNewLabel = new JLabel("Text Color");

		JLabel lblNewLabel_1 = new JLabel("Message Encryption");

		JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("ROT13");
		encryptionGroup.add(rdbtnNewRadioButton_4);

		JRadioButton rdbtnReversal = new JRadioButton("Reversal");
		encryptionGroup.add(rdbtnReversal);

		JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("None");
		encryptionGroup.add(rdbtnNewRadioButton_5);
		rdbtnNewRadioButton_5.setSelected(true);

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												Alignment.LEADING)
												.addComponent(
														rdbtnNewRadioButton_5)
												.addComponent(
														rdbtnNewRadioButton_4)
												.addComponent(lblNewLabel_1)
												.addComponent(
														rdbtnNewRadioButton_3)
												.addComponent(
														rdbtnNewRadioButton_2)
												.addComponent(
														rdbtnNewRadioButton_1)
												.addComponent(
														rdbtnNewRadioButton)
												.addComponent(lblNewLabel)
												.addComponent(rdbtnReversal))
								.addGap(18)
								.addGroup(
										layout.createParallelGroup(
												Alignment.LEADING, false)
												.addComponent(inputField,
														Alignment.TRAILING)
												.addComponent(
														outputTextbox,
														Alignment.TRAILING,
														GroupLayout.DEFAULT_SIZE,
														800, Short.MAX_VALUE))
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(28)
																.addComponent(
																		lblNewLabel)
																.addPreferredGap(
																		ComponentPlacement.UNRELATED)
																.addComponent(
																		rdbtnNewRadioButton)
																.addPreferredGap(
																		ComponentPlacement.UNRELATED)
																.addComponent(
																		rdbtnNewRadioButton_1)
																.addPreferredGap(
																		ComponentPlacement.UNRELATED)
																.addComponent(
																		rdbtnNewRadioButton_2)
																.addPreferredGap(
																		ComponentPlacement.UNRELATED)
																.addComponent(
																		rdbtnNewRadioButton_3)
																.addGap(18)
																.addComponent(
																		lblNewLabel_1)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		rdbtnNewRadioButton_5)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		rdbtnNewRadioButton_4)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		rdbtnReversal))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(32)
																.addComponent(
																		outputTextbox,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(inputField,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);

		setTitle(title);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.chatClient = chatClient;
	}

	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chatClient.send((String) inputField.getText());
				inputField.setText("");
			}
		};
	}

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(String line) {
		outputTextbox.append(line);
	}

	public static JRadioButton getSelection(String selector) {

		if (selector.equals(COLOR)) {
			for (Enumeration<AbstractButton> e = colorGroup.getElements(); e
					.hasMoreElements();) {
				JRadioButton b = (JRadioButton) e.nextElement();
				if (b.getModel() == colorGroup.getSelection()) {
					return b;
				}
			}
		} else if (selector.equals(ENCRYPTION)) {
			for (Enumeration<AbstractButton> e = encryptionGroup.getElements(); e
					.hasMoreElements();) {
				JRadioButton b = (JRadioButton) e.nextElement();
				if (b.getModel() == encryptionGroup.getSelection()) {
					return b;
				}
			}
		}
		return null;
	}

	public void wrongPassword() {

		PopupFactory factory = PopupFactory.getSharedInstance();
		Popup popup = factory.getPopup(this.rootPane, new JLabel(
				"Wrong Password!"), 300, 300);
		popup.show();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}

	public void enableElement(String name) {
		Component[] components = this.getComponents();

		for (int i = 0; i < components.length; i++) {
			if (components[i].getName() == name) {
				components[i].setEnabled(true);
			}
		}

	}
}

package gui.panels; 

import java.awt.BorderLayout; 
import java.awt.Dimension; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 

import javax.swing.Box; 
import javax.swing.BoxLayout; 

import javax.swing.JButton; 
import javax.swing.JOptionPane; 
import javax.swing.JPanel; 
import javax.swing.JPasswordField; 
import javax.swing.JScrollPane; 
import javax.swing.JSpinner; 
import javax.swing.JTextArea; 
import javax.swing.JTextField; 
import javax.swing.JTextPane; 
import javax.swing.SpinnerNumberModel; 
import javax.swing.event.DocumentEvent; 
import javax.swing.event.DocumentListener; 
import javax.swing.text.BadLocationException; 
import javax.swing.text.Document; 
import javax.swing.text.Style; 

import cutil.RandomNameGen; 

import engine.ChatClient; 
import engine.ChatClient.ChatClientEvent; 
import engine.ChatClient.ChatClientStatus; 
import engine.ChatClient.ChatClientListener; 

import gui.Gui; 
import gui.util.ChatStyles; 

import gui.util.GuiTLH; 
import gui.util.ImagePanel; 

import network.server.packets.DataPacket; 
import network.server.packets.JoinMessage; 
import network.server.packets.TextMessage; 
import network.server.packets.QuitMessage; 

import java.awt.Component; 
import javax.swing.JComboBox; 
import javax.swing.JLabel; 
import java.awt.GridLayout; 
import java.util.HashMap; 
import java.util.Map.Entry; 

import javax.swing.ImageIcon; 
import javax.swing.SwingUtilities; 
import javax.swing.text.SimpleAttributeSet; 
import javax.swing.text.StyleConstants; 
import javax.swing.text.StyledDocument; 

import java.util.ArrayList; 
import java.util.List; 
import javax.swing.JList; 
import javax.swing.DefaultListModel; 
import gui.panels.ClientPanel; 
import network.server.packets.OnlineStatusMessage; 
import network.server.packets.PrivateMessage; 

/**
 * TODO description
 */
public   class  ClientPanel  extends AbstractCloseTabPanel  implements
		ChatClientListener, ActionListener {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4090642373194281416L;

	

	private ChatClient chatClient;

	
	// JContainers
	private JTextField txtAdress;

	
	private JSpinner spinnerPort;

	
	private JTextField txtChatName;

	
	private JPasswordField passwordField;

	
	private JPanel northPanel;

	
	private JPanel centerPanel;

	
	private JTextArea taSendText;

	
	private JTextPane tpShowText;

	
	private ChatStyles chatStyles;

	
	private JButton btGeneralHandler;

	

	public ClientPanel(Gui g) {
		super(new BorderLayout(), g);
		chatStyles = new ChatStyles();
		init();
		initView();
	}

	

	 private void  init__wrappee__Unicast  () {
		setName("Client");
		setToolTipText("A Clientpanel");
		setIcon(GuiTLH.loadImageIcon("Client.png"));
	}

	

	private void init() {
		init__wrappee__Unicast();
		images = new HashMap<String, ImageIcon>();
		images.put(":D", GuiTLH.loadImageIcon("smilies/icon_biggrin.gif"));
		images.put(":~", GuiTLH.loadImageIcon("smilies/icon_confused.gif"));
		images.put("^^", GuiTLH.loadImageIcon("smilies/icon_cool.gif"));
		images.put(":(", GuiTLH.loadImageIcon("smilies/icon_cry.gif"));
		images.put(">(", GuiTLH.loadImageIcon("smilies/icon_evil.gif"));
		images.put(":!", GuiTLH.loadImageIcon("smilies/icon_idea.gif"));
		images.put(":|", GuiTLH.loadImageIcon("smilies/icon_neutral.gif"));
		images.put(":?", GuiTLH.loadImageIcon("smilies/icon_question.gif"));
		images.put(":p", GuiTLH.loadImageIcon("smilies/icon_razz.gif"));
		images.put(":*", GuiTLH.loadImageIcon("smilies/icon_redface.gif"));
		images.put(":r", GuiTLH.loadImageIcon("smilies/icon_rolleyes.gif"));
		images.put(":)", GuiTLH.loadImageIcon("smilies/icon_smile.gif"));
		images.put(":o", GuiTLH.loadImageIcon("smilies/icon_surprised.gif"));
		images.put(";)", GuiTLH.loadImageIcon("smilies/icon_wink.gif"));
	}

	

	private void initView() {
		removeAll();
		// Splash Preview
		northPanel = new JPanel();
		ImagePanel imgView = new ImagePanel(550, 300);
		imgView.setImage(GuiTLH.loadImage("epmd_client.png"));
		northPanel.add(imgView);

		// Layout
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		// Adress-Field
		txtAdress = new JTextField();
		txtAdress.setText("localhost");
		txtAdress.setColumns(20);
		panel.add(txtAdress);

		// RigidArea
		panel.add(Box.createRigidArea(new Dimension(20, 0)));

		// Port-Field
		spinnerPort = new JSpinner(new SpinnerNumberModel(8080, 1024, 49151, 1));
		panel.add(spinnerPort);

		// RigidArea
		panel.add(Box.createRigidArea(new Dimension(20, 0)));

		// ChatName-Field
		txtChatName = new JTextField();
		txtChatName.setText(RandomNameGen.getRandomName());
		txtChatName.setColumns(20);
		panel.add(txtChatName);

		// RigidArea
		panel.add(Box.createRigidArea(new Dimension(20, 0)));

		// Button StartServer
		btGeneralHandler = new JButton("Client starten");
		btGeneralHandler.addActionListener(this);
		btGeneralHandler.setActionCommand("btStartClient");
		panel.add(btGeneralHandler);

		// Border
		centerPanel = new JPanel();
		centerPanel.add(panel);

		// Finalize
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);

	}

	

	private void initLoginView() {
		// Clear CenterPanel
		centerPanel.removeAll();

		// Layout
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		// ChatName-Field
		txtChatName.setEnabled(false);
		panel.add(txtChatName);

		// RigidArea
		panel.add(Box.createRigidArea(new Dimension(20, 0)));

		// Passwort-Field
		passwordField = new JPasswordField(20);
		passwordField.setText(chatClient.getName());
		passwordField.setActionCommand("btLogin");
		passwordField.addActionListener(this);
		panel.add(passwordField);

		// RigidArea
		panel.add(Box.createRigidArea(new Dimension(20, 0)));

		// Button StartServer
		btGeneralHandler = new JButton("Login");
		btGeneralHandler.addActionListener(this);
		btGeneralHandler.setActionCommand("btLogin");
		panel.add(btGeneralHandler);

		// Border
		centerPanel.add(panel);

		validate();
		repaint();
	}

	

	 private void  initChatView__wrappee__Unicast() {
		// Clear CenterPanel
		centerPanel.removeAll();

		// TextPane and Scroll
		tpShowText = new JTextPane();
		tpShowText.setEditable(false);
		tpShowText.setBounds(0, 0, 550, 300);
		JScrollPane jScrollPane = new JScrollPane(tpShowText);
		jScrollPane.setPreferredSize(new Dimension(550, 300));
		// North add
		centerPanel.add(jScrollPane, BorderLayout.CENTER);

		// Load ClientPlugin Panels
		JPanel extensionPanel = new JPanel();
		modifyExtensionPanel(extensionPanel);
		centerPanel.add(extensionPanel, BorderLayout.EAST);

		// North finalize
		northPanel.removeAll();

		// South Panel
		JPanel southPanel = new JPanel(new BorderLayout());

		// TextArea SendMessage
		taSendText = new JTextArea();
		taSendText.setRows(5);
		taSendText.setMaximumSize(taSendText.getPreferredSize());
		taSendText.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				checkLength(arg0);
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				checkLength(arg0);
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				checkLength(arg0);
			}

			private void checkLength(DocumentEvent de) {
				if (de.getDocument().getLength() > 0) {
					btGeneralHandler.setEnabled(true);
				} else {
					btGeneralHandler.setEnabled(false);
				}
			}
		});
		JScrollPane textFieldScroll = new JScrollPane(taSendText);

		// Button SendMSF
		btGeneralHandler = new JButton("Senden");
		btGeneralHandler.addActionListener(this);
		btGeneralHandler.setEnabled(false);
		btGeneralHandler.setActionCommand("btSendMSG");

		// South finalize
		southPanel.add(textFieldScroll, BorderLayout.CENTER);
		southPanel.add(btGeneralHandler, BorderLayout.EAST);

		// Finalize
		add(southPanel, BorderLayout.SOUTH);
		validate();
		repaint();
	}

	

	 private void  initChatView__wrappee__Smilies() {
		initChatView__wrappee__Unicast();
		Document doc = tpShowText.getDocument();
		doc.addDocumentListener(new DocumentListener() {

			private int lastCheck = 0;

			@Override
			public void insertUpdate(DocumentEvent event) {
				final DocumentEvent e = event;
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (e.getDocument() instanceof StyledDocument) {
							try {

								StyledDocument doc = (StyledDocument) e
										.getDocument();
								int doclength = doc.getLength();
								String text = doc.getText(lastCheck, doclength
										- lastCheck);

								// Check for :
								checkForMatch(":", text, doc, lastCheck);
								checkForMatch(";", text, doc, lastCheck);
								checkForMatch("^", text, doc, lastCheck);
								checkForMatch(">", text, doc, lastCheck);
								lastCheck = doclength - 1;
							} catch (BadLocationException e1) {
								e1.printStackTrace();
							}
						}
					}

				});
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
			}
		});
	}

	

	private void initChatView() {
		initChatView__wrappee__Smilies();
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BorderLayout());
		friendsArray = new ArrayList<String>();
		friendsListModel = new DefaultListModel<String>();
		friendsList = new JList<String>(friendsListModel);
		friendsList.setVisibleRowCount(10);
		friendsList.setFixedCellWidth(100);
		JPanel wrapperPanel = new JPanel();
		wrapperPanel.add(friendsList);

		westPanel.add(wrapperPanel, BorderLayout.CENTER);
		add(westPanel, BorderLayout.WEST);
	}

	

	 private void  modifyExtensionPanel__wrappee__GUI  (JPanel panel) {

	}

	

	 private void  modifyExtensionPanel__wrappee__Unicast  (JPanel panel) {
		// Color Chooser
		JLabel colorLabel = new JLabel("Farbe:");
		colorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		cbColorStyle = new JComboBox<String>(chatStyles.getStyleList());
		cbColorStyle.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
		colorPanel.add(colorLabel);
		colorPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		colorPanel.add(cbColorStyle);
		panel.add(colorPanel);
		modifyExtensionPanel__wrappee__GUI(panel);
	}

	

	private void modifyExtensionPanel(JPanel panel) {
		JPanel smiliesPanel = new JPanel();
		smiliesPanel.setLayout(new BorderLayout());
		JLabel colorLabel = new JLabel("Smilies:");
		smiliesPanel.add(colorLabel, BorderLayout.NORTH);
		JPanel btPanel = new JPanel();
		btPanel.setLayout(new GridLayout(0, 4));
		for (Entry<String, ImageIcon> e : images.entrySet()) {
			JButton sButton = new JButton(e.getValue());
			sButton.setActionCommand("smi"+e.getKey());
			sButton.addActionListener(this);
			btPanel.add(sButton);
		}
		smiliesPanel.add(btPanel, BorderLayout.CENTER);
		panel.add(smiliesPanel);
		modifyExtensionPanel__wrappee__Unicast(panel);
	}

	

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void onEntering() {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void onLeaving() {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void onClose() {
		if (chatClient != null) {
			chatClient.removeChatClientListener(this);
			chatClient.stop();
		}
	}

	

	@Override
	public void onTabButtonClose() {
		getGui().removeTabPanel(this);
	}

	

	 private void  actionPerformed__wrappee__GUI  (ActionEvent e) {
		String acom = e.getActionCommand();
		if (acom.equals("btStartClient")) {
			JButton btStartServer = (JButton) e.getSource();
			int port = ((Number) spinnerPort.getValue()).intValue();
			btStartServer.setEnabled(false);
			spinnerPort.setEnabled(false);
			txtChatName.setEnabled(false);
			txtAdress.setEnabled(false);
			chatClient = new ChatClient(txtAdress.getText(), port,
					txtChatName.getText());
			chatClient.addChatClientListener(this);
		} else if (acom.equals("btSendMSG")) {
			chatClient.sendTextMessage(taSendText.getText());
			taSendText.setText("");
		} else if (acom.equals("btLogin")) {
			// Disable Button and Field
			passwordField.setEnabled(false);
			btGeneralHandler.setEnabled(false);
			chatClient.login(new String(passwordField.getPassword()));
		}
	}

	

	 private void  actionPerformed__wrappee__Unicast  (ActionEvent e) {
		String acom = e.getActionCommand();
		if (acom.equals("btSendMSG")) {
			String stylename = (String) cbColorStyle.getSelectedItem();
			TextMessage txm = new TextMessage(chatClient.getName(), stylename,
					taSendText.getText());
			chatClient.sendPacket(txm);
			taSendText.setText("");
		} else {
			actionPerformed__wrappee__GUI(e);
		}
	}

	
	
	 private void  actionPerformed__wrappee__Smilies  (ActionEvent e) {
		String acom = e.getActionCommand();
		if (acom.substring(0,3).equals("smi")) {
			taSendText.append(acom.substring(3, acom.length()));
		}else{
			actionPerformed__wrappee__Unicast(e);
		}
	}

	

	public void actionPerformed(ActionEvent e) {
		String acom = e.getActionCommand();
		if (acom.equals("btSendMSG") && friendsList.getSelectedValue() != null) {
			List<String> selected = friendsList.getSelectedValuesList();
			for (String name : selected) {
				PrivateMessage pm = new PrivateMessage(chatClient.getName(),
						"", taSendText.getText());
				pm.setDestination(name);
				chatClient.sendPacket(pm);
			}
			taSendText.setText("");
		} else {
			actionPerformed__wrappee__Smilies(e);
		}
	}

	

	@Override
	public void handleClientChatStatusChanged(ChatClientEvent clientEvent) {
		ChatClientStatus status = clientEvent.getChatClientStatus();
		if (status == ChatClientStatus.CONNECTIONESTABLISHED) {
			initLoginView();
		} else if (status == ChatClientStatus.LOGINSUCESSFUL) {
			initChatView();
		} else if (status == ChatClientStatus.CONNECTIONLOST) {
			JOptionPane.showMessageDialog(null,
					"Die Verbindung zum Server wurde beendet.",
					"Verbindungsabbruch", JOptionPane.ERROR_MESSAGE);
			initView();
			validate();
			repaint();
		} else if (status == ChatClientStatus.CONNECTIONNOTPOSSIBLE) {
			JOptionPane
					.showMessageDialog(null,
							"Verbindung zum Server nicht m�glich.",
							"Verbindungsversuch gescheitert",
							JOptionPane.ERROR_MESSAGE);
			btGeneralHandler.setEnabled(true);
			spinnerPort.setEnabled(true);
			txtChatName.setEnabled(true);
			txtAdress.setEnabled(true);
		}
	}

	

	 private void  handlePacketIn__wrappee__GUI  (DataPacket packet) {
		Document doc = tpShowText.getDocument();
		Style style = chatStyles.getDefaultStyle();
		String msg = "";
		boolean handlePacket = true;
		if (packet instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) packet;
			msg = txtMsg.getName() + ": " + txtMsg.getContent();
		} else if (packet instanceof JoinMessage) {
			JoinMessage joinMSG = (JoinMessage) packet;
			msg = "-> " + joinMSG.getName() + " joins the Chat.";
		} else if (packet instanceof QuitMessage) {
			QuitMessage quitMSG = (QuitMessage) packet;
			msg = "-> " + quitMSG.getName() + " leaves the Chat.";
		} else {
			handlePacket = false;
		}
		if (handlePacket) {
			msg += "\n";
			try {
				doc.insertString(doc.getLength(), msg, style);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			tpShowText.setCaretPosition(tpShowText.getDocument().getLength());
		}
	}

	

	 private void  handlePacketIn__wrappee__Smilies  (DataPacket packet) {
		Document doc = tpShowText.getDocument();
		Style style = chatStyles.getDefaultStyle();
		String msg = "";
		boolean handlePacket = true;
		if (packet instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) packet;
			style = chatStyles.getStyle(txtMsg.getStyleName());
			msg = txtMsg.getName() + ": " + txtMsg.getContent();
		} else if (packet instanceof JoinMessage) {
			JoinMessage joinMSG = (JoinMessage) packet;
			style = chatStyles.getStyle("blue");
			msg = "-> " + joinMSG.getName() + " joins the Chat.";
		} else if (packet instanceof QuitMessage) {
			QuitMessage quitMSG = (QuitMessage) packet;
			style = chatStyles.getStyle("red");
			msg = "-> " + quitMSG.getName() + " leaves the Chat.";
		} else {
			handlePacket = false;
		}
		msg += "\n";
		if (handlePacket) {
			try {
				doc.insertString(doc.getLength(), msg, style);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			tpShowText.setCaretPosition(tpShowText.getDocument().getLength());
		} else {
			handlePacketIn__wrappee__GUI(packet);
		}
	}

	

	public void handlePacketIn(DataPacket packet) {
		handlePacketIn__wrappee__Smilies(packet);
		if (packet instanceof JoinMessage) {
			JoinMessage joinMSG = (JoinMessage) packet;
			if (!chatClient.getName().equals(joinMSG.getName())) {
				addFriend(joinMSG.getName());
			}
		} else if (packet instanceof OnlineStatusMessage) {
			OnlineStatusMessage onlineMSG = (OnlineStatusMessage) packet;
			addFriend(onlineMSG.getName());
		} else if (packet instanceof QuitMessage) {
			QuitMessage quitMSG = (QuitMessage) packet;
			friendsListModel.removeElement(quitMSG.getName());
		} else if (packet instanceof PrivateMessage) {
			Document doc = tpShowText.getDocument();
			Style style = chatStyles.getStyle("private");
			PrivateMessage pm = (PrivateMessage) packet;
			String msg = "(p) " + pm.getName() + ": " + pm.getContent();
			if (!chatClient.getName().equals(pm.getDestination())) {
				msg += " (" + pm.getDestination() + ")";
			}
			msg += "\n";
			try {
				doc.insertString(doc.getLength(), msg, style);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			tpShowText.setCaretPosition(tpShowText.getDocument().getLength());
		}
	}

	

	private JComboBox<String> cbColorStyle;

	

	private HashMap<String, ImageIcon> images;

	
	
	private void checkForMatch(String firstMatcher, String text,
			StyledDocument doc, int offset) throws BadLocationException {
		int i = text.indexOf(firstMatcher);
		// && (i+1) < doclength
		while (i >= 0) {
			ImageIcon icon = getMatchedSmiley(text.substring(i, i + 2));
			if (icon != null) {
				final SimpleAttributeSet attrs = new SimpleAttributeSet(doc
						.getCharacterElement(i).getAttributes());
				if (StyleConstants.getIcon(attrs) == null) {
					StyleConstants.setIcon(attrs, icon);
					doc.remove(offset + i, 2);
					doc.insertString(offset + i, ":)", attrs);
				}
				i = text.indexOf(firstMatcher, i + 2);
			} else {
				i = text.indexOf(firstMatcher, i + 1);
			}
		}
	}

	

	private ImageIcon getMatchedSmiley(String sCode) {
		sCode = sCode.trim();
		if (sCode.length() < 2) {
			return null;
		}
		return images.get(sCode);
	}

	

	private DefaultListModel<String> friendsListModel;

	
	private JList<String> friendsList;

	
	private ArrayList<String> friendsArray;

	
	
	private void addFriend(String friendsName) {
		if (!friendsArray.contains(friendsName)) {
			friendsListModel.addElement(friendsName);
			friendsArray.add(friendsName);
		}
	}


}

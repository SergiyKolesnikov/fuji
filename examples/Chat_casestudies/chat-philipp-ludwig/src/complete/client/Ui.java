package client; 

import java.util.ArrayList; 

import common.Logging; 

import java.awt.BorderLayout; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.util.Arrays; 
import java.util.regex.Matcher; 
import java.util.regex.Pattern; 

import javax.swing.JButton; 
import javax.swing.JEditorPane; 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JPanel; 
import javax.swing.JScrollPane; 
import javax.swing.JTextField; 

public   class  Ui {
	
	private String mIp = "";

	
	protected void saveIp(String aIp) { mIp = aIp; }

	
	
	 private void  onDisconnected__wrappee__Log  ()
	{
		addText( "Verbindung getrennt.\n" );
	}

	
	
	public void onDisconnected()
	{
		onDisconnected__wrappee__Log();

		// Verbinden-Button und Eingabefeld deaktivieren.
		mConnectBtn.setEnabled(true);
		mServerField.setEnabled(true);
		mSendBtn.setEnabled(false);
	}

	
	
	 private void  onConnected__wrappee__Spamfilter  ()
	{
		addText( "Verbindung hergestellt.\n" );
	}

	
	 private void  onConnected__wrappee__PrivateMessaging  ()
	{
		onConnected__wrappee__Spamfilter();
		addText( "Um deinen Benutzernamen anzugeben, schreibe: /username <Name>\n" );
	}

	
	 private void  onConnected__wrappee__Authentication  ()
	{
		onConnected__wrappee__PrivateMessaging();
		addText( "Login erforderlich. Passwort eingabe via: /auth <Passwort>\n" );
	}

	
	 private void  onConnected__wrappee__Log  ()
	{
		onConnected__wrappee__Authentication();
		addText( "Um verschl�sselte Nachrichten zu senden, schreibe: /xor <Text> oder /rot13 <Text>\n" );
	}

	
	
	public void onConnected()
	{
		onConnected__wrappee__Log();
		mChatField.requestFocus();
	}

	
	
	 private void  addText__wrappee__Encryption  (String aText)
	{
		
	}

	
	
	 private void  addText__wrappee__Log  (String aText)
	{
		addText__wrappee__Encryption(aText);
		mLog.log(aText);
	}

	
	
	public void addText(String aText)
	{
		addText__wrappee__Log(aText);
		Integer scroll = mChatScroll.getVerticalScrollBar().getValue();
		
		// Nur bestimmte Tags erlauben
		Pattern regex = Pattern.compile("\\<[^\\<\\>]+\\>");
		Matcher matcher = regex.matcher(aText);
		while( matcher.find() )
		{
			if( !Arrays.asList("<b>","</b>","<font color='blue'>","</font>","<br>").contains(matcher.group()) )
			{
				String c = matcher.group();
				c = c.replace("<", "&lt;");
				c = c.replace(">", "&gt;");
				aText = aText.replace(matcher.group(), c);				
			}
		}		
		
		// Umlaute verarbeiten
		aText = aText.replace("�", "&auml;");
		aText = aText.replace("�", "&ouml;");
		aText = aText.replace("�", "&uuml;");
		aText = aText.replace("�", "&szlig;");
		
		aText = aText.replace("\n", "<br>");
		String currentText = mChatText.getText();
		currentText = currentText.replace("</html>", "");
		currentText = currentText.replace("</body>", "");
		currentText += aText;
		currentText += "</body></html>";
		mChatText.setText( currentText );
		mChatScroll.getVerticalScrollBar().setValue(scroll);
	}

	
	
	// Gibt die vom Benutzer eingegebene Server-IP zur�ck.
	public String getServerIp() { String tmp = mIp; mIp = ""; return tmp; }

	
		
	// Buffer f�r ausgehende Daten
	protected ArrayList<String> mBuffer = new ArrayList<String>();

	
	public Boolean hasInput() {	return !mBuffer.isEmpty(); }

	
	public String nextInput() { return mBuffer.remove(0); }

	
	protected Logging mLog;

	
	
	public Ui  ()
	{
		mLog = new Logging();
		mLog.open("clientlog.txt");
	
		mWindow = new JFrame();
		
		// Bedienelemente zum Verbinden zum Server
		mNameField = new JTextField(15);		
		mServerField = new JTextField(20);	
		mServerField.setText("127.0.0.1");
		
		mConnectBtn = new JButton("Verbinden");
		mConnectBtn.addActionListener(new Listener());
		
		JPanel connectPanel = new JPanel();
		connectPanel.add(new JLabel("Server: "));
		connectPanel.add(mServerField);	
		connectPanel.add(mConnectBtn);
		
		mWindow.getContentPane().add(BorderLayout.NORTH,connectPanel);
		
		// Textfeld
		mChatText = new JEditorPane("text/html", "");	
		mChatText.setEditable(false);
		mChatScroll = new JScrollPane(mChatText);		
		mWindow.getContentPane().add(BorderLayout.CENTER,mChatScroll);
		
		// Nachrichteneingabe
		JPanel messagePanel = new JPanel();
		messagePanel.add(new JLabel("Nachricht: "));
		
		mChatField = new JTextField(30);
		mChatField.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e){
				mChatBtnListener.actionPerformed(null);
			}});		
		
		messagePanel.add(mChatField);
		mSendBtn = new JButton("Senden");
		mChatBtnListener = new ChatBtnListener();
		mSendBtn.addActionListener(mChatBtnListener);
		mSendBtn.setEnabled(false);
		messagePanel.add(mSendBtn);
			
		mWindow.getContentPane().add(BorderLayout.SOUTH,messagePanel);
				
		mWindow.pack();
		mWindow.setSize(900, 500);
		mWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mWindow.setVisible(true);
	}

	
	// GUI-Elemente
	protected JTextField mChatField;

	
	protected JTextField mNameField;

	
	protected JTextField mServerField;

	
	protected JButton mConnectBtn;

	
	protected JButton mSendBtn;

	
	protected JEditorPane mChatText;

	
	protected JScrollPane mChatScroll;

	
	protected JFrame mWindow;

	
	
	protected ChatBtnListener mChatBtnListener;

	
	
	public  class  Listener  implements ActionListener {
			
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{			
			// Server IP speichern
			saveIp(mServerField.getText());
			
			// Verbinden-Button und Eingabefeld deaktivieren.
			mConnectBtn.setEnabled(false);
			mServerField.setEnabled(false);
			mSendBtn.setEnabled(true);
		}


	}

	
	
	public  class  ChatBtnListener  implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{						
			mBuffer.add(mChatField.getText());
			mChatField.setText("");
		}


	}


}

package ui;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

import client.ChatLineListener;
import client.Client;

import javax.swing.ScrollPaneConstants;

import common.HtmlUtil;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;

import common.LoginMessage;





/**
 * Klasse fuer die Benutzeroberflaeche.
 * Basisimplementierung der Oberflaeche stammt aus Window Builder Pro.
 * @author Ralf Beczkowiak
 *
 */
abstract class ClientGui$$GUI$ui implements ChatLineListener {

    protected JFrame frame;
    protected JTextPane inputField;
    protected JTextPane outputField;
    protected JButton sendButton;
    protected JScrollPane scrollPane;
    protected JScrollPane scrollPane_1;
    protected JMenuBar menuBar;
    protected JMenu startField;
    
    protected Client client;

    /**
     * Create the application.
     */
    public ClientGui$$GUI$ui(String title, Client client) {
        this.client = client;
        this.client.addLineListener(this);
        initialize(title);
        
        frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    protected void initialize(String title) {
        frame = new JFrame();
        frame.setTitle(title);
        frame.setBounds(100, 100, 450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // Eingabefeld einlesen und ueberschuessige html-Tags
                // entfernen
                String message = inputField.getText();
                message = HtmlUtil.getBody(message);
                inputField.setText("");
                if (message != "") {
                	send(message);
                }
            }
        });
        
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        scrollPane_1 = new JScrollPane();
        scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                            .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                        .addComponent(sendButton))
                    .addContainerGap())
        );
        
        inputField = new JTextPane();
        
        inputField.setContentType("text/html");
        scrollPane_1.setViewportView(inputField);
        
        outputField = new JTextPane();
        scrollPane.setViewportView(outputField);
        outputField.setContentType("text/html");
        outputField.setEditable(false);
        outputField.setText("<html><head> </head><body> </body></html>");
        frame.getContentPane().setLayout(groupLayout);
        
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        startField = new JMenu("Start");
        menuBar.add(startField);
        
        JMenuItem connectField = new JMenuItem("Connect");
        connectField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                client.connect();
            }
        });
        startField.add(connectField);
        
        JMenuItem exitEntry = new JMenuItem("Exit");
        exitEntry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        
        startField.add(exitEntry);
    }
    public JTextPane getInputField() {
        return ((ClientGui) ((ClientGui) this)).inputField;
    }
    
    public void newChatLine(String line) {
        outputField.setText(HtmlUtil.addToBody(outputField.getText(), line));
    }
    
    public void send(String line) {
    	((ClientGui) this).client.send(line);
    }
}



abstract class ClientGui$$Colors$ui extends  ClientGui$$GUI$ui  {
	protected ButtonGroup buttonGroup;
	protected String currentColor;
    
    protected void initialize(String title) {
		super.initialize(title);
		
		inputField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
	            if (arg0.getKeyCode() != KeyEvent.VK_ENTER
	                    && arg0.getKeyCode() != KeyEvent.VK_SPACE
	                    && arg0.getKeyCode() != KeyEvent.VK_BACK_SPACE
	                    && !arg0.isActionKey()) {
	                if (!currentColor.equalsIgnoreCase("black")) {
	                    String inputtext = HtmlUtil.getBody(inputField.getText());
	                    
	                    inputtext = "<font color=\"" + currentColor + "\">"
	                        + inputtext + "</font>";
	                    
	                    inputField.setText(inputtext);
	                }
	            }
	        }
		});
		
		JMenu mnColor = new JMenu("Color");
        buttonGroup = new ButtonGroup();
        currentColor = "aqua";
        
        JRadioButtonMenuItem rdbtnmntmBlack = new JRadioButtonMenuItem("Black");
        buttonGroup.add(rdbtnmntmBlack);
        rdbtnmntmBlack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                currentColor = "black";
            }
        });
        mnColor.add(rdbtnmntmBlack);
        
        JRadioButtonMenuItem rdbtnmntmYellow = new JRadioButtonMenuItem("Yellow");
        buttonGroup.add(rdbtnmntmYellow);
        rdbtnmntmYellow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                currentColor = "yellow";
            }
        });
        mnColor.add(rdbtnmntmYellow);
        
        JRadioButtonMenuItem rdbtnmntmRed = new JRadioButtonMenuItem("Red");
        rdbtnmntmRed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                currentColor = "red";
            }
        });
        buttonGroup.add(rdbtnmntmRed);
        mnColor.add(rdbtnmntmRed);
        
        JRadioButtonMenuItem rdbtnmntmBlue = new JRadioButtonMenuItem("Blue");
        buttonGroup.add(rdbtnmntmBlue);
        rdbtnmntmBlue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                currentColor = "blue";
            }
        });
        mnColor.add(rdbtnmntmBlue);
        
        JRadioButtonMenuItem rdbtnmntmAqua = new JRadioButtonMenuItem("Aqua");
        buttonGroup.add(rdbtnmntmAqua);
        rdbtnmntmAqua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                currentColor = "aqua";
            }
        });
        rdbtnmntmAqua.setSelected(true);
        mnColor.add(rdbtnmntmAqua);
        
        menuBar.add(mnColor);
    }
    
    public void send(String line) {
    	super.send("<font color=\"" + currentColor + "\">"
        + line + "</font>");
    }
      // inherited constructors



    /**
     * Create the application.
     */
    public ClientGui$$Colors$ui ( String title, Client client ) { super(title, client); }
}



public class ClientGui extends  ClientGui$$Colors$ui  {
	
	protected JMenuItem mntmLogin;
    protected ConnectDialog diag;
	
	protected void initialize(String title) {
		super.initialize(title);
		
		mntmLogin = new JMenuItem("Login");
        mntmLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                diag.setVisible(true);
            }
        });
        startField.add(mntmLogin,1);
        
        diag = new ConnectDialog();
        diag.addButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String command = arg0.getActionCommand();
                
                if (command.equalsIgnoreCase("Login")) {
                    String name = diag.getUsername();
                    char[] pw = diag.getPassword();
                    if (name != null && name != "" && pw != null && pw.length != 0) {
                        client.send(new LoginMessage(name, pw));
                        diag.clearPw();
                        diag.setVisible(false);
                    }
                } else if (command.equalsIgnoreCase("Cancel")) {
                    diag.clearPw();
                    diag.setVisible(false);
                }
            }
        });
	}
      // inherited constructors



    /**
     * Create the application.
     */
    public ClientGui ( String title, Client client ) { super(title, client); }
}
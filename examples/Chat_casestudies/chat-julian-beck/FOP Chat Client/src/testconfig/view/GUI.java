package view; 

import java.awt.event.ActionListener; 
import java.net.Socket; 
import javax.swing.JButton; 
import javax.swing.JFrame; 
import javax.swing.JMenu; 
import javax.swing.JMenuBar; 
import javax.swing.JMenuItem; 
import javax.swing.JScrollPane; 
import javax.swing.JSeparator; 
import javax.swing.JTextArea; 
import javax.swing.JTextField; 

import presenter.ViewPresenter; 

import model.Application; 
import javax.swing.AbstractButton; 
import javax.swing.ImageIcon; 

public   class  GUI  extends Thread {
	
	private JFrame frame;

	
	private JTextArea chatWindow;

	
	private JButton sendBtn;

	
	private JMenuItem mntmExitApplication;

	
	private JMenuItem mntmPreferences;

	
	private JTextField inputField;

	
	
	
	public GUI(Socket skt, Application app) {
		initialize(skt, app);
	}

	

	private void initialize(Socket skt, Application app) {
		ViewPresenter presenter = new ViewPresenter(this, skt, app);
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 500, 366);
		frame.setTitle("Simple Chat");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 32, 464, 217);
		frame.getContentPane().add(scrollPane);
		chatWindow = new JTextArea();
		chatWindow.setEditable(false);
		scrollPane.setViewportView(chatWindow);
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 260, 464, 11);
		frame.getContentPane().add(separator);
		sendBtn = new JButton("send");
		sendBtn.setBounds(385, 270, 89, 23);
		sendBtn.addActionListener(presenter);
		frame.getContentPane().add(sendBtn);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 484, 21);
		frame.getContentPane().add(menuBar);
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		mntmPreferences = new JMenuItem("Preferences");
		mntmPreferences.addActionListener(presenter);
		mnEdit.add(mntmPreferences);
		JMenu mnClose = new JMenu("Close");
		menuBar.add(mnClose);
		mntmExitApplication = new JMenuItem("Exit application");
		mntmExitApplication.addActionListener(presenter);
		mnClose.add(mntmExitApplication);
		inputField = new JTextField();
		inputField.setBounds(10, 301, 464, 20);
		inputField.addKeyListener(presenter);
		frame.getContentPane().add(inputField);
		inputField.setColumns(10);
		
		addColor(presenter);

		frame.setLocationRelativeTo(null);
	}

	
	
	private void addColor  (ActionListener presenter) {
		redBtn = new JButton("");
		redBtn.setIcon(new ImageIcon(getClass().getResource(
				"/resources/redBtnIcon.png")));
		redBtn.setText("");
		redBtn.setBounds(10, 270, 25, 23);
		redBtn.addActionListener(presenter);
		frame.getContentPane().add(redBtn);
		
		blueBtn = new JButton("");
		blueBtn.setIcon(new ImageIcon(getClass().getResource(
				"/resources/blueBtnIcon.png")));
		blueBtn.setText("");
		blueBtn.setBounds(40, 270, 25, 23);
		blueBtn.addActionListener(presenter);
		frame.getContentPane().add(blueBtn);
		
		greenBtn = new JButton("");
		greenBtn.setIcon(new ImageIcon(getClass().getResource(
				"/resources/greenBtnIcon.png")));
		greenBtn.setText("");
		greenBtn.setBounds(70, 270, 25, 23);
		greenBtn.addActionListener(presenter);
		frame.getContentPane().add(greenBtn);
		
		blackBtn = new JButton("");
		blackBtn.setIcon(new ImageIcon(getClass().getResource(
				"/resources/blackBtnIcon.png")));
		blackBtn.setBounds(100, 270, 25, 23);
		blackBtn.setText("");
		blackBtn.addActionListener(presenter);
		frame.getContentPane().add(blackBtn);
	}

	
	
	public JFrame getFrame() {
		return frame;
	}

	

	public JTextArea getChatWindow() {
		return chatWindow;
	}

	

	public JButton getSendBtn() {
		return sendBtn;
	}

	

	public JMenuItem getMntmExitApplication() {
		return mntmExitApplication;
	}

	

	public JMenuItem getMntmPreferences() {
		return mntmPreferences;
	}

	

	public JTextField getInputField() {
		return inputField;
	}

	

	private JButton redBtn;

	
	private JButton blueBtn;

	
	private AbstractButton greenBtn;

	
	private AbstractButton blackBtn;

	
	
	public JButton getRedBtn() {
		return redBtn;
	}

	

	public JButton getBlueBtn() {
		return blueBtn;
	}

	

	public AbstractButton getGreenBtn() {
		return greenBtn;
	}

	

	public AbstractButton getBlackBtn() {
		return blackBtn;
	}


}

package view; 

import java.awt.BorderLayout; 
import java.awt.FlowLayout; 
import java.awt.GridBagConstraints; 
import java.awt.GridBagLayout; 
import java.awt.Insets; 
import java.net.InetAddress; 
import java.net.UnknownHostException; 

import javax.swing.JButton; 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JOptionPane; 
import javax.swing.JPanel; 
import javax.swing.JPasswordField; 
import javax.swing.JTextField; 
import javax.swing.border.EmptyBorder; 

import presenter.PreferencesPresenter; 

import model.Application;  

public    class   PreferencesView {
	
	private final JPanel contentPanel = new JPanel();

	
	private JTextField ipField;

	
	private JTextField portField;

	
	private JButton okButton;

	
	private JButton cancelButton;

	
	private JPanel buttonPane;

	
	private JFrame frame;

	
	private JPasswordField pwField;

	

	public PreferencesView(Application app) {
		initDialoge(app);
	}

	

	private void initDialoge(Application app) {
		frame = new JFrame();
		frame.setTitle("Preferences");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 297, 177);
		frame.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 61, 118, 86, 0 };
		gbl_contentPanel.rowHeights = new int[] { 20, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		ipField = new JTextField();
		try {
			ipField.setText(InetAddress.getLocalHost().getHostAddress()
					.toString());
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(frame,
					"Error retrieving your IP-Adress.",
					"Enter valid Server IP!", JOptionPane.ERROR_MESSAGE);
		}
		JLabel lblYourPartnersIp = new JLabel("Server IP adress:");
		GridBagConstraints gbc_lblYourPartnersIp = new GridBagConstraints();
		gbc_lblYourPartnersIp.anchor = GridBagConstraints.WEST;
		gbc_lblYourPartnersIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblYourPartnersIp.gridx = 0;
		gbc_lblYourPartnersIp.gridy = 0;
		contentPanel.add(lblYourPartnersIp, gbc_lblYourPartnersIp);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		contentPanel.add(ipField, gbc_textField);
		ipField.setColumns(10);
		JLabel lblThePortTo = new JLabel("The port to use:");
		GridBagConstraints gbc_lblThePortTo = new GridBagConstraints();
		gbc_lblThePortTo.anchor = GridBagConstraints.EAST;
		gbc_lblThePortTo.insets = new Insets(0, 0, 5, 5);
		gbc_lblThePortTo.gridx = 0;
		gbc_lblThePortTo.gridy = 1;
		contentPanel.add(lblThePortTo, gbc_lblThePortTo);
		portField = new JTextField();
		portField.setText("5555");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		contentPanel.add(portField, gbc_textField_1);
		portField.setColumns(10);

		addAuth();

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		frame.getContentPane().add(buttonPane, BorderLayout.SOUTH);
		PreferencesPresenter presenter = new PreferencesPresenter(this, app);
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(presenter);
		buttonPane.add(okButton);
		frame.getRootPane().setDefaultButton(okButton);
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(presenter);
		buttonPane.add(cancelButton);
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);

		frame.getContentPane().add(buttonPane, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	

	private void addAuth  () {
		JLabel lblYourPassword = new JLabel("Your password:");
		GridBagConstraints gbc_lblYourPassword = new GridBagConstraints();
		gbc_lblYourPassword.anchor = GridBagConstraints.EAST;
		gbc_lblYourPassword.insets = new Insets(0, 0, 0, 5);
		gbc_lblYourPassword.gridx = 0;
		gbc_lblYourPassword.gridy = 3;
		contentPanel.add(lblYourPassword, gbc_lblYourPassword);
		
		pwField = new JPasswordField();
		GridBagConstraints gbc_pwField = new GridBagConstraints();
		gbc_pwField.insets = new Insets(0, 0, 0, 5);
		gbc_pwField.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwField.gridx = 1;
		gbc_pwField.gridy = 3;
		contentPanel.add(pwField, gbc_pwField);		
	}

	

	// Getters & Setters... //

	public JPanel getContentPanel() {
		return contentPanel;
	}

	

	public JTextField getIpField() {
		return ipField;
	}

	

	public JTextField getPortField() {
		return portField;
	}

	

	public JButton getOkButton() {
		return okButton;
	}

	

	public JButton getCancelButton() {
		return cancelButton;
	}

	

	public JPanel getButtonPane() {
		return buttonPane;
	}

	

	public JFrame getFrame() {
		return frame;
	}

	

	public JPasswordField getPwField() {
		return pwField;
	}


}

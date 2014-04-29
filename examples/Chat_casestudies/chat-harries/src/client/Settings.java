package client;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Collection;
import java.util.HashMap;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;




public class Settings extends JDialog {
	
	public static String nickname;
	public static String ownColor;
	public static String foreignColor;
	public static String trennzeichen = ": ";
	
	private JTextField txtNickname;
	private final JPanel contentPanel = new JPanel();
	private JComboBox cbOwnColor;
	private JComboBox cbForeignColor;
	private JTextField textField;
	private JTextField txtPort;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Settings dialog = new Settings();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Settings() {
		JLabel lblIpadresse = new JLabel("Host:");
		lblIpadresse.setBounds(6, 95, 83, 16);
		contentPanel.add(lblIpadresse);
		
		textField = new JTextField();
		textField.setBounds(107, 89, 134, 28);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(6, 123, 61, 16);
		contentPanel.add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setText("Port");
		txtPort.setBounds(107, 117, 134, 28);
		setPreferences();
		setBounds(100, 100, 554, 388);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtNickname = new JTextField();
			txtNickname.setText(getNickname());
			txtNickname.setBounds(107, 6, 134, 28);
			contentPanel.add(txtNickname);
			txtNickname.setColumns(10);
		}
		
		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(6, 12, 83, 16);
		contentPanel.add(lblNickname);
		
		JLabel lblEigeneFarbe = new JLabel("Eigene Farbe:");
		lblEigeneFarbe.setBounds(6, 40, 83, 16);
		contentPanel.add(lblEigeneFarbe);
		
		cbOwnColor = new JComboBox();
		String[] colors = new String[] {"Weiss", "Grau", "Schwarz", "Pink", "Gelb", "Magenta", "Blau", "Rot", "Orange", "GrÃ¼n", "Cyan"};
		cbOwnColor.setModel(new DefaultComboBoxModel(colors));
		cbOwnColor.setSelectedIndex(2);
		cbOwnColor.setBounds(107, 35, 134, 27);
		contentPanel.add(cbOwnColor);
		
		JLabel lblChatpartner = new JLabel("Chatpartner:");
		lblChatpartner.setBounds(6, 67, 83, 16);
		contentPanel.add(lblChatpartner);
		
		cbForeignColor = new JComboBox();
		cbForeignColor.setModel(new DefaultComboBoxModel(colors));
		cbForeignColor.setSelectedIndex(0);
		cbForeignColor.setBounds(107, 62, 134, 27);
		contentPanel.add(cbForeignColor);
		
		contentPanel.add(txtPort);
		txtPort.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						closeAndSave();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent arg0) {
						close();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void closeAndSave() {
		storePreferences();
		((Settings) this).dispose();
	}
	
	private void close() {
		((Settings) this).dispose();
	}

	void setPreferences() {
	    // Ermitteln des zustÃ¯Â¿Â½ndigen Knotens
	    Preferences prefs;
	    prefs = Preferences.userNodeForPackage(getClass());
	    
	    setNickname(prefs.get("nickname","user"));
	    setOwnColor(prefs.get("ownColor", "Rot"));
	    setForeignColor(prefs.get("foreignColor", "Blau"));
	    textField.setText(prefs.get("host", "127.0.0.1"));
	    txtPort.setText(prefs.get("port", "8080"));
	}
	
	void storePreferences(){
		try {
			// Ermitteln des zustÃ¯Â¿Â½ndigen Knotens
			Preferences prefs =
			          Preferences.userNodeForPackage(getClass());
			
			prefs.put("nickname", txtNickname.getText());
			setNickname(txtNickname.getText());
			
			prefs.put("ownColor", cbOwnColor.getSelectedItem().toString());
			setOwnColor(cbOwnColor.getSelectedItem().toString());
			
			prefs.put("foreignColor", cbForeignColor.getSelectedItem().toString());
			setForeignColor(cbForeignColor.getSelectedItem().toString());
			
			prefs.put("host",textField.getText());
			prefs.put("port",txtPort.getText());
			
			// Zur Sicherheit alles zurÃ¯Â¿Â½ckschreiben
			prefs.flush();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void setNickname(String nickname) {
		((Settings) this).nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public static String getOwnColor() {
		return ownColor;
	}

	public static void setOwnColor(String ownColor) {
		Settings.ownColor = ownColor;
	}

	public static String getForeignColor() {
		return foreignColor;
	}

	public static void setForeignColor(String foreignColor) {
		Settings.foreignColor = foreignColor;
	}
	
}
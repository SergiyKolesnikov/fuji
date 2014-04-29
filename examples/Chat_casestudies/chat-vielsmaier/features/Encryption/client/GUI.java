package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.GroupLayout.Group;

import common.RotationCryptoProvider;
import common.XORCryptoProvider;

public class GUI extends JFrame implements ChatListener {	
	protected JCheckBox encryptCheckBox;
	protected JComboBox encryptionComboBox;
	
	private void addComponents(Group h, Group v) {
		original(h,v);
		encryptCheckBox = new JCheckBox("Encrypt");
		encryptionComboBox = new JComboBox(new String[] { "XOR", "ROT13" });
		encryptionComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(encryptionComboBox.getSelectedItem().toString().equals("XOR"))
					chatClient.setCryptoProvider(new XORCryptoProvider("lala"));
				else
					chatClient.setCryptoProvider(new RotationCryptoProvider(13));
			}
		});
		h.addComponent(encryptCheckBox);
		v.addComponent(encryptCheckBox);
		h.addComponent(encryptionComboBox);
		v.addComponent(encryptionComboBox);
	}
	
	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inputField.getText().startsWith("/")) {
					handleCommand(inputField.getText());
				} else {
					if(encryptCheckBox.isSelected())
						chatClient.sendEncrypted((String) inputField.getText());
					else
						chatClient.send((String) inputField.getText());
				}
				inputField.setText("");
			}
		};
	}
}
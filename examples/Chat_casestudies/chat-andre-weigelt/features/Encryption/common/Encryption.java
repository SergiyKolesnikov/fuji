package common;

import javax.swing.JComboBox;

import common.TextMessage;

public class Encryption {
	public static String ROT13 = "rot13";
	public static String SWITCH = "switch first letters";
	private String[] encrypts = { "none", Encryption.ROT13, Encryption.SWITCH };

	protected JComboBox<String> encryptChooser;

	public Encryption() {
		encryptChooser = new JComboBox<String>(encrypts);
		encryptChooser.setSelectedIndex(0);
	}

	private String encodeRot13(String str) {
		StringBuilder outstr = new StringBuilder();
		for (char c : str.toCharArray()) {
			if (c >= 'a' && c <= 'm')
				c += 13;
			else if (c >= 'A' && c <= 'M')
				c += 13;
			else if (c >= 'n' && c <= 'z')
				c -= 13;
			else if (c >= 'N' && c <= 'Z')
				c -= 13;

			outstr.append(c);

		}
		return outstr.toString();
	}

	private String encodeSwitch(String str) {
		StringBuilder outstr = new StringBuilder();
		String[] words = str.split("\\s+");
		for (int i = 0; i < words.length; i++) {
			String w = words[i];
			if (w.length() > 1) {

				w = w.charAt(1) + "" + w.charAt(0) + w.substring(2);
			}
			outstr.append(w);
			if (i < words.length - 1) {
				outstr.append(" ");
			}

		}
		return outstr.toString();
	}

	public void decryptTextMessage(TextMessage msg) {
		String decrypted;
		if (msg.getEncryptionType() != null
				&& msg.getEncryptionType().equals(Encryption.ROT13)) {
			decrypted = this.encodeRot13(msg.getContent());
		} else if (msg.getEncryptionType() != null
				&& msg.getEncryptionType().equals(Encryption.SWITCH)) {
			decrypted = this.encodeSwitch(msg.getContent());
		} else {
			decrypted = msg.getContent();
		}
		msg.setContent(decrypted);
	}

	public void handleIncomingMessage(TextMessage msg) {
		decryptTextMessage(msg);

	}

	public void handleOutgoingMessage(TextMessage msg) {
		String content = msg.getContent();
		String encryptionType = encryptChooser.getSelectedItem().toString();
		if (encryptionType != null) {
			if (encryptionType.equals(ROT13)) {
				msg.setEncryptionType(encryptionType);
				msg.setContent(encodeRot13(content));
			} else if (encryptionType.equals(SWITCH)) {
				msg.setEncryptionType(encryptionType);
				msg.setContent(encodeSwitch(content));
			}
		}
	}

	public void handleOutgoingServerMessage(TextMessage msg) {
		String content = msg.getContent();
		String encryptionType = msg.getEncryptionType();
		if (encryptionType != null) {
			if (encryptionType.equals(ROT13)) {
				msg.setEncryptionType(encryptionType);
				msg.setContent(encodeRot13(content));
			} else if (encryptionType.equals(SWITCH)) {
				msg.setEncryptionType(encryptionType);
				msg.setContent(encodeSwitch(content));
			}
		}
	}

	public JComboBox<String> getComboBox() {
		return encryptChooser;
	}

}

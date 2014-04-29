package view; 

public  class  PreferencesView {

	private void addAuth() {
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
}

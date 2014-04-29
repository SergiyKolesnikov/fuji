package chat.client; 

import java.awt.Color; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 

import javax.swing.JButton; 
import javax.swing.JDialog; 
import javax.swing.JLabel; 
import javax.swing.JPanel; 
import javax.swing.JPasswordField; 
import javax.swing.JTextField; 

import chat.client.Client; 

public  class  AuthenticationDialog  extends JDialog {
	
	private static final long serialVersionUID = 1L;

	

	private JButton 		mOKButton;

	
    private JLabel 			mNameLabel;

	
    private JLabel 			mPasswordLabel;

	
    private JPanel 			mAuthPanel;

	
    private JPasswordField 	mPasswordField;

	
    private JTextField 		mNameField;

	

    private Client			mClient;

	
    
	public AuthenticationDialog(Client client) 
	{
		this.mClient = client;
		
	    this.mOKButton = new JButton();
		this.mOKButton.setText("OK");
		this.mOKButton.addActionListener(new ActionListener() 
		{
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) 
			{
				if(AuthenticationDialog.this.mNameField.getText().equals(""))
				{
					AuthenticationDialog.this.mNameField.setBackground(Color.RED);
					AuthenticationDialog.this.mNameField.setForeground(Color.WHITE);
					return;
				}
				else
				{
					AuthenticationDialog.this.mNameField.setBackground(Color.WHITE);
					AuthenticationDialog.this.mNameField.setForeground(Color.BLACK);
				}
				
				if(AuthenticationDialog.this.mPasswordField.getText().equals(""))
				{
					AuthenticationDialog.this.mPasswordField.setBackground(Color.RED);
					AuthenticationDialog.this.mPasswordField.setForeground(Color.WHITE);
					return;
				}
				else
				{
					AuthenticationDialog.this.mPasswordField.setBackground(Color.WHITE);
					AuthenticationDialog.this.mPasswordField.setForeground(Color.BLACK);
				}

				/*
				 * Send entered username and password to server.
				 */
				AuthenticationDialog.this.mClient.sendSystem(
						AuthenticationDialog.this.mNameField.getText(),
						"@" + 
						AuthenticationDialog.this.mPasswordField.getText() + 
						"|" + 
						AuthenticationDialog.this.mNameField.getText());
				
				/*
				 * Set entered UserName in client.
				 */
				AuthenticationDialog.this.mClient.setUserName(
						AuthenticationDialog.this.mNameField.getText());
				
				/*
				 * Hide the dialog.
				 */
				//AuthenticationDialog.this.dispose();
				AuthenticationDialog.this.setVisible(false);
			}	
		});		
		
		this.mAuthPanel = new JPanel();
        this.mAuthPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Authentifizierung"));

        this.mNameLabel = new JLabel();
        this.mNameLabel.setText("Name: ");

        this.mPasswordLabel = new JLabel();
        this.mPasswordLabel.setText("Pass.: ");
        
        this.mNameField = new JTextField();
        this.mNameField.setText("");

        this.mPasswordField = new JPasswordField();
        this.mPasswordField.setText("");

        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
		this.setSize(200, 300);

        javax.swing.GroupLayout authPanelLayout = new javax.swing.GroupLayout(mAuthPanel);
        this.mAuthPanel.setLayout(authPanelLayout);
        authPanelLayout.setHorizontalGroup(
        		authPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(authPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(authPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(authPanelLayout.createSequentialGroup()
                        .addComponent(this.mNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(this.mNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(authPanelLayout.createSequentialGroup()
                        .addComponent(this.mPasswordLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(this.mPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        authPanelLayout.setVerticalGroup(
        		authPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(authPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(authPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(this.mNameLabel)
                    .addComponent(this.mNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(authPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(this.mPasswordLabel)
                    .addComponent(this.mPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(this.mAuthPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(this.mOKButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this.mAuthPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this.mOKButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        pack();
	}


}

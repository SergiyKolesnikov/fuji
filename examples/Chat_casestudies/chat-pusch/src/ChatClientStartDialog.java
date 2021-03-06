
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;





public class ChatClientStartDialog extends JDialog {

	private static final long serialVersionUID = -579968751835925457L;
	protected JPanel contentPanel = new JPanel();
	protected JTextField textUsername;
	protected ChatClient chatClient;

	/**
	 * Create the dialog.
	 */
	public ChatClientStartDialog(ChatClient chatClient) {
		this.chatClient= chatClient;
		// GUI
		setModal(true);
		setTitle("Login - joChat");
		setBounds(100, 100, 300, 180);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnLogin = new JButton("Login");
				btnLogin.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						handleLoginClick();
					}
				});
				buttonPane.add(btnLogin);
			}
		}
		initGUIElements();
		
	}
	
	protected void handleLoginClick() {
		String username = textUsername.getText();
		chatClient.sendMessage(new MessageObject(MessageType.AUTH, username, null, null,"spl11"));
		chatClient.setUsername(username);
	}
	
	protected void initGUIElements() {
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 330, 0};
		gbl_contentPanel.rowHeights = new int[]{36, 36, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.gridwidth = 2;
		gbc_lblLogin.fill = GridBagConstraints.BOTH;
		gbc_lblLogin.insets = new Insets(0, 0, 5, 0);
		gbc_lblLogin.gridx = 0;
		gbc_lblLogin.gridy = 0;
		contentPanel.add(lblLogin, gbc_lblLogin);
		
		JLabel lblUsername = new JLabel("username:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.fill = GridBagConstraints.VERTICAL;
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 1;
		contentPanel.add(lblUsername, gbc_lblUsername);
		
		textUsername = new JTextField();
		GridBagConstraints gbc_textUsername = new GridBagConstraints();
		gbc_textUsername.insets = new Insets(0, 0, 5, 0);
		gbc_textUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUsername.gridx = 1;
		gbc_textUsername.gridy = 1;
		contentPanel.add(textUsername, gbc_textUsername);
		textUsername.setColumns(10);
		

	}
}
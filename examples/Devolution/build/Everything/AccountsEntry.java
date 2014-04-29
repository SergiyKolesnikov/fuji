package Everything;import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import java.awt.event.ActionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import java.util.Properties;




abstract class AccountsEntry$$MailBase extends PreferencesEntry implements TreeSelectionListener, ActionListener{

	protected static final long serialVersionUID = 5392201352308380222L;
	protected final Mail modul;
	protected DefaultMutableTreeNode listRoot = new DefaultMutableTreeNode( "Accounts", true );
	protected final DefaultTreeModel mdTree = new DefaultTreeModel( ((AccountsEntry) this).listRoot );
	//
	protected final JLabel lbAccounts = new JLabel();
	protected final JTree trAccounts = new JTree( ((AccountsEntry) this).mdTree );
	//private final JButton btAccountsAdd = new JButton( ProgramImages.LIST_ADD );
	protected final JButton btAccountsAdd = new JButton( "Add Account" );
	//private final JButton btAccountsRemove = new JButton( ProgramImages.LIST_REMOVE );
	protected final JButton btAccountsRemove = new JButton( "Remove Account" );
	//private final JButton btAliasAdd = new JButton( ProgramImages.LIST_ADD );
	protected final JButton btAliasAdd = new JButton( "Add Alias" );
	//private final JButton btAliasRemove = new JButton( ProgramImages.LIST_REMOVE );
	protected final JButton btAliasRemove = new JButton( "Remove Alias" );
	//
	protected final JLabel lbSettingUser = new JLabel();
	protected final JTextField tfSettingUser = new JTextField();
	protected final JLabel lbSettingMail = new JLabel();
	protected final JTextField tfSettingMail = new JTextField();
	protected final JLabel lbSettingAnswer = new JLabel();
	protected final JTextField tfSettingAnswer = new JTextField();
	//
	protected final JLabel lbSend = new JLabel();
	protected final JTextField tfSend = new JTextField();
	protected final JLabel lbProtocolSend = new JLabel();
	protected final JComboBox cbProtocolSend = new JComboBox();
	protected final JLabel lbUserSend = new JLabel();
	protected final JTextField tfUserSend = new JTextField();
	protected final JLabel lbSslSend = new JLabel();
	protected final JCheckBox cbSslSend = new JCheckBox( "", false );
	protected final JLabel lbSaveSend = new JLabel();
	protected final JCheckBox cbSaveSend = new JCheckBox( "", false );
	//
	protected final JLabel lbReceive = new JLabel();
	protected final JTextField tfReceive = new JTextField();
	protected final JLabel lbProtocolReceive = new JLabel();
	protected final JComboBox cbProtocolReceive = new JComboBox();
	protected final JLabel lbUserReceive = new JLabel();
	protected final JTextField tfUserReceive = new JTextField();
	protected final JLabel lbSslReceive = new JLabel();
	protected final JCheckBox cbSslReceive = new JCheckBox( "", false );
	protected final JLabel lbSaveReceive = new JLabel();
	protected final JCheckBox cbSaveReceive = new JCheckBox( "", false );
	protected final JLabel lbPwReceive = new JLabel();
	protected final JPasswordField pfPwReceive = new JPasswordField();

	protected boolean initialized = false;

	/**
	 * @param modul
	 */
	public AccountsEntry$$MailBase ( final Mail modul ) {
		super( "Accounts" );
		this.modul = modul;
		this.cbProtocolSend.addItem( "SMTP" );
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rephstone.rezar.miscellaneous.PreferencesEntry#initContent()
	 */
	protected void initContent () {
		final JPanel pnList = new JPanel( new BorderLayout() );
		final JTabbedPane tpSendReceive = new JTabbedPane();
		final FormPanel pnSettings = new FormPanel();
		final FormPanel pnSend = new FormPanel();
		final FormPanel pnReceive = new FormPanel();
		final JPanel pnListButtons = new JPanel( new GridLayout( 0, 1 ) );
		pnListButtons.setBorder( BorderFactory.createEmptyBorder( 0, 10, 0, 0 ) );

		((AccountsEntry) this).trAccounts.setRootVisible( false );
		((AccountsEntry) this).trAccounts.addTreeSelectionListener( ((AccountsEntry) this) );
		insertAccounts();

		pnList.add( new JScrollPane( ((AccountsEntry) this).trAccounts ), BorderLayout.CENTER );
		pnList.add( pnListButtons, BorderLayout.EAST );
		((AccountsEntry) this).btAccountsAdd.addActionListener( ((AccountsEntry) this) );
		pnListButtons.add( ((AccountsEntry) this).btAccountsAdd );
		((AccountsEntry) this).btAccountsRemove.addActionListener( ((AccountsEntry) this) );
		pnListButtons.add( ((AccountsEntry) this).btAccountsRemove );
		pnListButtons.add( Box.createGlue() );
		((AccountsEntry) this).btAliasAdd.addActionListener( ((AccountsEntry) this) );
		pnListButtons.add( ((AccountsEntry) this).btAliasAdd );
		((AccountsEntry) this).btAliasRemove.addActionListener( ((AccountsEntry) this) );
		pnListButtons.add( ((AccountsEntry) this).btAliasRemove );
		//
		addHeadline( ((AccountsEntry) this).lbAccounts );
		addElement( pnList );
		addSeperator();
		addElement( tpSendReceive );
		tpSendReceive.addTab( "Settings", pnSettings );
		tpSendReceive.addTab( "Send", pnSend );
		tpSendReceive.addTab( "Receive", pnReceive );
		//
		pnSettings.addElement( ((AccountsEntry) this).lbSettingUser, ((AccountsEntry) this).tfSettingUser );
		pnSettings.addElement( ((AccountsEntry) this).lbSettingMail, ((AccountsEntry) this).tfSettingMail );
		// pnSettings.addSeperator();
		pnSettings.addElement( ((AccountsEntry) this).lbSettingAnswer, ((AccountsEntry) this).tfSettingAnswer );
		pnSettings.addEnd();
		//
		pnReceive.addElement( ((AccountsEntry) this).lbReceive, ((AccountsEntry) this).tfReceive );
		pnReceive.addElement( ((AccountsEntry) this).lbProtocolReceive, ((AccountsEntry) this).cbProtocolReceive );
		pnReceive.addElement( ((AccountsEntry) this).lbUserReceive, ((AccountsEntry) this).tfUserReceive );
		pnReceive.addElement( ((AccountsEntry) this).lbSslReceive, ((AccountsEntry) this).cbSslReceive );
		pnReceive.addElement( ((AccountsEntry) this).lbSaveReceive, ((AccountsEntry) this).cbSaveReceive );
		pnReceive.addElement( ((AccountsEntry) this).lbPwReceive, ((AccountsEntry) this).pfPwReceive );
		pnReceive.addEnd();
		//
		pnSend.addElement( ((AccountsEntry) this).lbSend, ((AccountsEntry) this).tfSend );
		pnSend.addElement( ((AccountsEntry) this).lbProtocolSend, ((AccountsEntry) this).cbProtocolSend );
		pnSend.addElement( ((AccountsEntry) this).lbUserSend, ((AccountsEntry) this).tfUserSend );
		pnSend.addElement( ((AccountsEntry) this).lbSslSend, ((AccountsEntry) this).cbSslSend );
		pnSend.addElement( ((AccountsEntry) this).lbSaveSend, ((AccountsEntry) this).cbSaveSend );
		pnSend.addEnd();

		((AccountsEntry) this).initialized = true;
	}

	private void insertAccounts(){
		((AccountsEntry) this).trAccounts.removeTreeSelectionListener( ((AccountsEntry) this) );
		listRoot = new DefaultMutableTreeNode( "Accounts", true );
		((AccountsEntry) this).mdTree.setRoot(listRoot);
		Account[] accounts = Account.getAccounts();
		AccountModel newAccount = null;

		for (int i=0; i<accounts.length; i++){
			newAccount = new AccountModel( accounts[i].getName() , true );
			newAccount.setProperties(accounts[i].getProperties());
			((AccountsEntry) this).mdTree.insertNodeInto( newAccount, ((AccountsEntry) this).listRoot, ((AccountsEntry) this).listRoot.getChildCount());
			((AccountsEntry) this).trAccounts.scrollPathToVisible(new TreePath(newAccount.getPath()));
			((AccountsEntry) this).trAccounts.setSelectionPath(new TreePath(newAccount.getPath()));

			String aliases = newAccount.getProperties().getProperty("aliases");
			if (aliases!=null && aliases.length()!=0){
				for (int j=0; j<aliases.length(); j++){
					String aliass = "";
				    while(j<aliases.length() && aliases.charAt(j)!=';'){
						aliass = aliass + aliases.charAt(j);
						j++;
					}
					AccountModel newAlias = new AccountModel( aliass , false );
					((AccountsEntry) this).mdTree.insertNodeInto( newAlias, newAccount, newAccount.getChildCount());
					((AccountsEntry) this).trAccounts.scrollPathToVisible(new TreePath(newAlias.getPath()));
				}
			}
		}
		if ( newAccount!= null )
			insertProperties(newAccount.getProperties());
		((AccountsEntry) this).trAccounts.addTreeSelectionListener( ((AccountsEntry) this) );
	}


	private void insertProperties(Properties props){
		if ( props != null ){
			tfSettingUser.setText(props.getProperty("realName"));
			tfSettingMail.setText(props.getProperty("address"));
			tfSettingAnswer.setText(props.getProperty("response"));

			tfUserSend.setText(props.getProperty("userSend"));
			cbSslSend.setSelected(props.getProperty("sslSend").equalsIgnoreCase("true"));
			cbSaveSend.setSelected(props.getProperty("saveSend").equalsIgnoreCase("true"));
			if (props.getProperty("sendProtocol").equalsIgnoreCase("smtp")){
				tfSend.setText(props.getProperty("smtpServer"));
				cbProtocolSend.setSelectedItem("SMTP");
			}

			tfUserReceive.setText(props.getProperty("userReceive"));
			cbSslReceive.setSelected(props.getProperty("sslReceive").equalsIgnoreCase("true"));
			cbSaveReceive.setSelected(props.getProperty("saveReceive").equalsIgnoreCase("true"));
			pfPwReceive.setText(TextCoding.rot48(props.getProperty("password")));
			if (props.getProperty("receiveProtocol").equalsIgnoreCase("pop3")){
				tfReceive.setText(props.getProperty("pop3Server"));
				cbProtocolReceive.setSelectedItem("POP3");
			}
			if (props.getProperty("receiveProtocol").equalsIgnoreCase("imap")){
				tfReceive.setText(props.getProperty("imapServer"));
				cbProtocolReceive.setSelectedItem("imap");
			}				
		}
		else {
			tfSettingUser.setText("");
			tfSettingMail.setText("");
			tfSettingAnswer.setText("");
			tfSend.setText("");
			tfUserSend.setText("");
			cbSslSend.setSelected( false );
			cbSaveSend.setSelected( false );
			tfSend.setText("");
			tfUserReceive.setText("");
			cbSslReceive.setSelected( false );
			cbSaveReceive.setSelected( false );
			tfReceive.setText("");
			pfPwReceive.setText("");
			cbProtocolReceive.setSelectedIndex(0);
			cbProtocolSend.setSelectedIndex(0);
		}
	}


	public Properties getValues() {
		Properties props = new Properties();

		props.put("realName",tfSettingUser.getText());
		props.put("address",tfSettingMail.getText());
		props.put("response",tfSettingAnswer.getText());
		if (((String)cbProtocolSend.getSelectedItem()).equalsIgnoreCase("smtp")){
			props.put("sendProtocol","smtp");
			props.put("smtpServer",tfSend.getText());
		}
		props.put("userSend",tfUserSend.getText());
		props.put("sslSend",cbSslSend.isSelected()+"");
		props.put("saveSend",cbSaveSend.isSelected()+"");
		if (((String)cbProtocolReceive.getSelectedItem()).equalsIgnoreCase("imap")){
			props.put("receiveProtocol","imap");
			props.put("imapServer",tfReceive.getText());
		}
		if (((String)cbProtocolReceive.getSelectedItem()).equalsIgnoreCase("pop3")){
			props.put("receiveProtocol","pop3");
			props.put("pop3Server",tfReceive.getText());
		}
		props.put("userReceive",tfUserReceive.getText());
		props.put("sslReceive",cbSslReceive.isSelected()+"");
		props.put("saveReceive",cbSaveReceive.isSelected()+"");
		props.put("password",TextCoding.rot48(new String(pfPwReceive.getPassword())));
		return props;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rephstone.rezar.miscellaneous.MultilingualListener#changeLanguage()
	 */
	public void changeLanguage () {
		FormPanel.setHeadlineText( "Accounts", ((AccountsEntry) this).lbAccounts );
		//
		((AccountsEntry) this).btAccountsAdd.setText( "Add Account" );
		((AccountsEntry) this).btAccountsRemove.setText( "Remove Account" );
		((AccountsEntry) this).btAliasAdd.setText( "Add Alias" );
		((AccountsEntry) this).btAliasRemove.setText( "Remove Alias" );
		//
		FormPanel.setLabelText( "Real Name", ((AccountsEntry) this).lbSettingUser );
		FormPanel.setLabelText( "E-Mail-Address", ((AccountsEntry) this).lbSettingMail );
		FormPanel.setLabelText( "Response to", ((AccountsEntry) this).lbSettingAnswer );
		//
		FormPanel.setLabelText( "Server", ((AccountsEntry) this).lbSend );
		FormPanel.setLabelText( "Protocol", ((AccountsEntry) this).lbProtocolSend );
		FormPanel.setLabelText( "Username", ((AccountsEntry) this).lbUserSend );
		FormPanel.setLabelText( "Secure", ((AccountsEntry) this).lbSslSend );
		((AccountsEntry) this).lbSslSend.setName( "SSL" );
		FormPanel.setLabelText( "Save", ((AccountsEntry) this).lbSaveSend );
		//
		FormPanel.setLabelText( "Server", ((AccountsEntry) this).lbReceive );
		FormPanel.setLabelText( "Protocol", ((AccountsEntry) this).lbProtocolReceive );
		FormPanel.setLabelText( "Username", ((AccountsEntry) this).lbUserReceive );
		FormPanel.setLabelText( "Secure", ((AccountsEntry) this).lbSslReceive );
		((AccountsEntry) this).lbSslReceive.setName( "SSL" );
		FormPanel.setLabelText( "Save", ((AccountsEntry) this).lbSaveReceive );
		FormPanel.setLabelText( "Password", ((AccountsEntry) this).lbPwReceive );
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see de.rephstone.rezar.miscellaneous.PreferencesEntry#checkInput()
	 */
	public boolean checkInput () {
		// TODO Auto-generated method stub
		return false;
	}





	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed ( final ActionEvent event ) {

		final Object source = event.getSource();

		if ( ((AccountsEntry) this).btAccountsAdd == source ) {
			String response = JOptionPane.showInputDialog(((AccountsEntry) this).modul.getBase().getView(), "Enter the name of the the new account.", "Name of the new account", JOptionPane.QUESTION_MESSAGE );
			AccountModel newAccount = new AccountModel( response , true );
			((AccountsEntry) this).mdTree.insertNodeInto( newAccount, ((AccountsEntry) this).listRoot, ((AccountsEntry) this).listRoot.getChildCount());
			((AccountsEntry) this).trAccounts.scrollPathToVisible(new TreePath(newAccount.getPath()));
			((AccountsEntry) this).trAccounts.setSelectionPath(new TreePath(newAccount.getPath()));
			return;
		}

		if ( ((AccountsEntry) this).btAccountsRemove == source ) {
			if ( ((AccountsEntry) this).trAccounts.getSelectionPath() != null ){
				AccountModel account = (AccountModel) ((AccountsEntry) this).trAccounts.getSelectionPath().getLastPathComponent();
				if(account.isAccount()){
					int response = JOptionPane.showConfirmDialog(((AccountsEntry) this).modul.getBase().getView(), "Do you really want to delete this Account?.", "Delete Account", JOptionPane.YES_NO_OPTION );
					if (response==0){
						account.deleteAccount();
						((AccountsEntry) this).mdTree.removeNodeFromParent(account);
					}
				}
			}
			return;
		}

		if ( ((AccountsEntry) this).btAliasAdd == source ) {
			if ( ((AccountsEntry) this).trAccounts.getSelectionPath() != null && ((AccountModel)((AccountsEntry) this).trAccounts.getSelectionPath().getLastPathComponent()).isAccount() ){
				String response = JOptionPane.showInputDialog(((AccountsEntry) this).modul.getBase().getView(), "Enter the name of the the new alias.", "Name of the new alias", JOptionPane.QUESTION_MESSAGE );
				AccountModel account = (AccountModel) ((AccountsEntry) this).trAccounts.getSelectionPath().getLastPathComponent();
				AccountModel newAlias = new AccountModel( response , false );
				((AccountsEntry) this).mdTree.insertNodeInto( newAlias, account, account.getChildCount());
				account.addAlias( newAlias );
				((AccountsEntry) this).trAccounts.scrollPathToVisible(new TreePath(newAlias.getPath()));
			}
			return;
		}

		if ( ((AccountsEntry) this).btAliasRemove == source ) {
			if ( ((AccountsEntry) this).trAccounts.getSelectionPath() != null ){
				AccountModel account = (AccountModel) ((AccountsEntry) this).trAccounts.getSelectionPath().getLastPathComponent();
				if(account.isAccount()){
					int response = JOptionPane.showConfirmDialog(((AccountsEntry) this).modul.getBase().getView(), "Do you really want to delete this Account?.", "Delete Account", JOptionPane.YES_NO_OPTION );
					if (response==0){
						account.deleteAccount();
						((AccountsEntry) this).mdTree.removeNodeFromParent(account);
					}
					return;
				}
				if(account.isAlias()){
					((AccountsEntry) this).mdTree.removeNodeFromParent(account);
					account.removeAlias();
				}
			}
			return;
		}
	}

	public void valueChanged(TreeSelectionEvent e){

		if ( e.getOldLeadSelectionPath() == null ){
			if ( e.getNewLeadSelectionPath() == null )
				return;
			AccountModel newAccount = ((AccountModel) e.getNewLeadSelectionPath().getLastPathComponent()).getAccountNode();
			Properties props = newAccount.getProperties();
			insertProperties(props);
			return;
		}

		AccountModel oldAccount = ((AccountModel) e.getOldLeadSelectionPath().getLastPathComponent()).getAccountNode();

		if ( e.getNewLeadSelectionPath() == null ){
			Properties props = getValues();
			oldAccount.setProperties(props);
			return;
		}

		AccountModel newAccount = ((AccountModel) e.getNewLeadSelectionPath().getLastPathComponent()).getAccountNode();

		if (newAccount != oldAccount){
			Properties props = getValues();
			oldAccount.setProperties(props);
			props = newAccount.getProperties();
			insertProperties(props);
		}
	} 



	/*
	 * (non-Javadoc)
	 * 
	 * @see PreferencesEntry#ok()
	 */
	protected void ok(){
		if (!initialized) return;
		insertAccounts();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see PreferencesEntry#apply()
	 */
	protected void apply(){
		if (!initialized) return;
		for (int i=0; i<listRoot.getChildCount(); i++){
			//checkInput();
			AccountModel account = (AccountModel) listRoot.getChildAt(i);
			Account acc = new Account(account.toString());
			acc.setProperties(account.getProperties());
			acc.save();
		}
		
		if ( ((AccountsEntry) this).trAccounts.getSelectionPath() == null )
			return;
		//save current selected node
		Properties props = getValues();
		AccountModel account = (AccountModel) (AccountModel) ((AccountsEntry) this).trAccounts.getSelectionPath().getLastPathComponent();
		Account acc = new Account(account.toString());
		acc.setProperties(props);
		acc.save();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see PreferencesEntry#cancel()
	 */
	protected void cancel(){
		if (!initialized) return;
		insertAccounts();
	}
}



abstract class AccountsEntry$$POP3 extends  AccountsEntry$$MailBase {

	/**
	 * @param modul
	 */
	public AccountsEntry$$POP3( final Mail modul) { super(modul); 

		((AccountsEntry) this).cbProtocolReceive.addItem( "POP3" ); }

      // inherited constructors


}



public class AccountsEntry extends  AccountsEntry$$POP3 {

	/**
	 * @param modul
	 */
	public AccountsEntry( final Mail modul) { super(modul); 

		((AccountsEntry) this).cbProtocolReceive.addItem( "IMAP" ); }

      // inherited constructors


}
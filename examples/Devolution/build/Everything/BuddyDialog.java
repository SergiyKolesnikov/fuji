package Everything;import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * @author Marcel Jaeschke
 * @since 1.6
 */
public class BuddyDialog extends JDialog implements ActionListener, MultilingualListener, FocusListener {
	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 7033517833385609168L;
	protected final JButton btCancel = new JButton( ProgramImages.CANCEL );
	protected final JButton btOk = new JButton();
	private final Addressbook modul;
	private final Buddy buddy;
	//
	private final JLabel lbReal = new JLabel();
	private final JLabel lbNick = new JLabel();
	//
	private final JLabel lbTitle = new JLabel();
	private final JTextField txTitle = new JTextField();
	private final JLabel lbName = new JLabel();
	private final JTextField txName = new JTextField();
	private final JLabel lbLastName = new JLabel();
	private final JTextField txLastName = new JTextField();
	//
	private final JLabel lbBirthday = new JLabel();
	private final JTextField txBirthday = new JTextField();
	private final JLabel lbMail = new JLabel();
	private final JTextField txMail = new JTextField();
	private final JLabel lbJabber = new JLabel();
	private final JTextField txJabber = new JTextField();
	//
	private final JLabel lbNickName = new JLabel();
	private final JTextField txNickName = new JTextField();
	private final JLabel lbNickMail = new JLabel();
	private final JTextField txNickMail = new JTextField();
	//
	public BuddyDialog ( final Addressbook addressbook ) {
		this( addressbook.add() , addressbook );
	}
	public BuddyDialog ( final Buddy buddy, final Addressbook addressbook ) {
		super( addressbook.getBase().getView(), buddy.toString(), false );
		this.modul = addressbook;
		this.buddy = buddy;
		buddyToForm();
		//
		final FormPanel pnForm = new FormPanel();
		final JPanel pnButtons = new JPanel( new GridLayout( 1, 0 ) );
		//
		pnForm.addHeadline( this.lbReal );
		this.txTitle.addFocusListener( this );
		pnForm.addElement( this.lbTitle, this.txTitle );
		this.txName.addFocusListener( this );
		pnForm.addElement( this.lbName, this.txName );
		this.txLastName.addFocusListener( this );
		pnForm.addElement( this.lbLastName, this.txLastName );
		pnForm.addSeperator();
		pnForm.addElement( this.lbBirthday, this.txBirthday );
		pnForm.addElement( this.lbMail, this.txMail );
		pnForm.addElement( this.lbJabber, this.txJabber );
		pnForm.addHeadline( this.lbNick );
		pnForm.addElement( this.lbNickName, this.txNickName );
		pnForm.addElement( this.lbNickMail, this.txNickMail );
		add( pnForm, BorderLayout.CENTER );
		this.txTitle.requestFocusInWindow();
		//
		pnButtons.add( Box.createHorizontalGlue() );
		pnButtons.add( Box.createHorizontalGlue() );
		pnButtons.add( Box.createHorizontalGlue() );
		pnButtons.add( Box.createHorizontalGlue() );
		this.btCancel.addActionListener( this );
		pnButtons.add( this.btCancel );
		this.btOk.addActionListener( this );
		pnButtons.add( this.btOk );
		add( pnButtons, BorderLayout.SOUTH );
		//
		changeLanguage();
		pack();
		setLocationRelativeTo( addressbook.getBase().getView() );
		//
		setVisible( true );
	}
	public void buddyToForm () {
		((BuddyDialog) this).txTitle.setText( ((BuddyDialog) this).buddy.getInfo( Buddy.TITLE ) );
		((BuddyDialog) this).txName.setText( ((BuddyDialog) this).buddy.getInfo( Buddy.NAME ) );
		((BuddyDialog) this).txLastName.setText( ((BuddyDialog) this).buddy.getInfo( Buddy.LASTNAME ) );
		((BuddyDialog) this).txBirthday.setText( ((BuddyDialog) this).buddy.getInfo( Buddy.BIRTHDAY ) );
		((BuddyDialog) this).txMail.setText( ((BuddyDialog) this).buddy.getInfo( Buddy.MAIL_PRIVATE ) );
		((BuddyDialog) this).txJabber.setText( ((BuddyDialog) this).buddy.getInfo( Buddy.JABBER ) );
		((BuddyDialog) this).txNickName.setText( ((BuddyDialog) this).buddy.getInfo( Buddy.NICK ) );
		((BuddyDialog) this).txNickMail.setText( ((BuddyDialog) this).buddy.getInfo( Buddy.MAIL_NICK ) );
	}
	public void formToBuddy () {
		((BuddyDialog) this).buddy.setInfo( Buddy.TITLE, ((BuddyDialog) this).txTitle.getText() );
		((BuddyDialog) this).buddy.setInfo( Buddy.NAME, ((BuddyDialog) this).txName.getText() );
		((BuddyDialog) this).buddy.setInfo( Buddy.LASTNAME, ((BuddyDialog) this).txLastName.getText() );
		((BuddyDialog) this).buddy.setInfo( Buddy.BIRTHDAY, ((BuddyDialog) this).txBirthday.getText() );
		((BuddyDialog) this).buddy.setInfo( Buddy.MAIL_PRIVATE, ((BuddyDialog) this).txMail.getText() );
		((BuddyDialog) this).buddy.setInfo( Buddy.JABBER, ((BuddyDialog) this).txJabber.getText() );
		((BuddyDialog) this).buddy.setInfo( Buddy.NICK, ((BuddyDialog) this).txNickName.getText() );
		((BuddyDialog) this).buddy.setInfo( Buddy.MAIL_NICK, ((BuddyDialog) this).txNickMail.getText() );
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see miscellaneous.MultilingualListener#changeLanguage()
	 */
	public void changeLanguage () {
		((BuddyDialog) this).btCancel.setText( "Cancel" );
		((BuddyDialog) this).btOk.setText( "OK" );
		//
		FormPanel.setHeadlineText( "Real ID", ((BuddyDialog) this).lbReal );
		FormPanel.setHeadlineText( "Nick ID", ((BuddyDialog) this).lbNick );
		//
		FormPanel.setLabelText( "Title", ((BuddyDialog) this).lbTitle );
		FormPanel.setLabelText( "Name", ((BuddyDialog) this).lbName );
		FormPanel.setLabelText( "Last Name", ((BuddyDialog) this).lbLastName );
		//
		FormPanel.setLabelText( "Birthday", ((BuddyDialog) this).lbBirthday );
		FormPanel.setLabelText( "Mail", ((BuddyDialog) this).lbMail );
		FormPanel.setLabelText( "Jabber", ((BuddyDialog) this).lbJabber );
		//
		FormPanel.setLabelText( "Name", ((BuddyDialog) this).lbNickName );
		FormPanel.setLabelText( "Mail", ((BuddyDialog) this).lbNickMail );
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed ( final ActionEvent event ) {
		final Object source = event.getSource();
		if ( ((BuddyDialog) this).btOk.equals( source ) ) {
			formToBuddy();
			((BuddyDialog) this).buddy.save();
			if ( !( (AddressbookView) ((BuddyDialog) this).modul.getView() ).getListModel().contains( ((BuddyDialog) this).buddy ) ) {
				( (AddressbookView) ((BuddyDialog) this).modul.getView() ).getListModel().addElement( ((BuddyDialog) this).buddy );
			}
			dispose();
		} else if ( ((BuddyDialog) this).btCancel.equals( source ) ) {
			dispose();
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained ( final FocusEvent event ) {}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost ( final FocusEvent event ) {
		setTitle( String.format( "%s %s %s", ((BuddyDialog) this).txTitle.getText(), ((BuddyDialog) this).txName.getText(), ((BuddyDialog) this).txLastName.getText()) );
	}
}
package Everything;import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;
import java.util.Properties;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;




abstract class MailWritePane$$SMTP extends JFrame implements ActionListener, ItemListener{

    private static final long serialVersionUID = 1L;
    private String account, address;
    private JTextArea text;
    private JTextField tfCC,tfBCC,tfSubject;
    protected JComboBox cbFrom,cbTo;
    private JButton add,send,cancel;
    private JPanel files;
    private LinkedList filePaths;


    public MailWritePane$$SMTP(String accountName){
		super("Write Mail");
		account = accountName;
	
		Account acc = new Account(accountName);
		Properties props = acc.getProperties();
	    address = props.getProperty("address");
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setSize(700,400);
    	createGUI();
    	this.setVisible(true);
    }

    public MailWritePane$$SMTP(){
		super("Write Mail");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setSize(700,400);
    	createGUI();
    	this.setVisible(true);
    }


    public void createGUI(){
	((MailWritePane) this).setLayout(new BorderLayout());

	JPanel settings = new JPanel();
	settings.setLayout(new GridLayout(5,1));
	settings.setBorder(javax.swing.border.LineBorder.createGrayLineBorder());

	JPanel pnFrom = new JPanel(new BorderLayout());
	pnFrom.setBorder(javax.swing.border.LineBorder.createGrayLineBorder());
	JLabel lbFrom = new JLabel("From:");
	cbFrom = new JComboBox();
	cbFrom.setEditable( false );
	pnFrom.add(lbFrom,BorderLayout.WEST);
	pnFrom.add(cbFrom,BorderLayout.CENTER);
	if ( address!=null ) {
		cbFrom.addItem(address);
	}
	else {
		cbFrom.addItemListener( ((MailWritePane) this) );
		Account[] accounts = Account.getAccounts();
		for (int i=0; i<accounts.length; i++)
			cbFrom.addItem(accounts[i].getProperties().get("userSend"));
	}

	JPanel pnTo = new JPanel(new BorderLayout());
	pnTo.setBorder(javax.swing.border.LineBorder.createGrayLineBorder());
	JLabel lbTo = new JLabel("To:");
	cbTo = new JComboBox();
	cbTo.setEditable( true );
	pnTo.add(lbTo,BorderLayout.WEST);
	pnTo.add(cbTo,BorderLayout.CENTER);

	JPanel pnCC = new JPanel(new BorderLayout());
	pnCC.setBorder(javax.swing.border.LineBorder.createGrayLineBorder());
	JLabel lbCC = new JLabel("CC:");
	tfCC = new JTextField();
	pnCC.add(lbCC,BorderLayout.WEST);
	pnCC.add(tfCC,BorderLayout.CENTER);

	JPanel pnBCC = new JPanel(new BorderLayout());
	pnBCC.setBorder(javax.swing.border.LineBorder.createGrayLineBorder());
	JLabel lbBCC = new JLabel("BCC:");
	tfBCC = new JTextField();
	pnBCC.add(lbBCC,BorderLayout.WEST);
	pnBCC.add(tfBCC,BorderLayout.CENTER);

	JPanel pnSubject = new JPanel(new BorderLayout());
	pnSubject.setBorder(javax.swing.border.LineBorder.createGrayLineBorder());
	JLabel lbSubject = new JLabel("Subject:");
	tfSubject = new JTextField();
	pnSubject.add(lbSubject,BorderLayout.WEST);
	pnSubject.add(tfSubject,BorderLayout.CENTER);

	settings.add(pnFrom);
	settings.add(pnTo);
	settings.add(pnCC);
	settings.add(pnBCC);
	settings.add(pnSubject);

	((MailWritePane) this).add(settings,BorderLayout.NORTH);

	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

	JScrollPane left = new JScrollPane();
	text = new JTextArea();
	text.setPreferredSize(new Dimension(500,200));
	left.getViewport().add(text);
	splitPane.setLeftComponent(left);


	JPanel right = new JPanel(new BorderLayout());
	JScrollPane spfiles = new JScrollPane();
	files = new JPanel();
	files.setLayout(new VerticalLayout(5,VerticalLayout.LEFT));
	spfiles.getViewport().add(files);
	right.add(spfiles,BorderLayout.CENTER);

	JPanel buttons = new JPanel(new GridLayout(3,1));
	add = new JButton("Add File");
	add.addActionListener(((MailWritePane) this));
	send = new JButton("Send");
	send.addActionListener(((MailWritePane) this));
	cancel = new JButton("Cancel");
	cancel.addActionListener(((MailWritePane) this));
	buttons.add(add);
	buttons.add(send);
	buttons.add(cancel);

	right.add(buttons,BorderLayout.SOUTH);
	splitPane.setRightComponent(right);
	splitPane.repaint();
	splitPane.setDividerLocation(0.5);
	splitPane.validate();
	((MailWritePane) this).add(splitPane,BorderLayout.CENTER);
	splitPane.setDividerLocation(0.5);
    }

    public static LinkedList getAdrressList(String line){
	if(line == null || line.length()<=0) return null;

	LinkedList list = new LinkedList();
	for(int i=0; i<line.length(); i++){
	    String address = "";
	    while(i<line.length() && (line.charAt(i)==' ' || line.charAt(i)==';' || line.charAt(i)==','))
		i++;
	    while(i<line.length() && line.charAt(i)!=' ' &&  line.charAt(i)!=';' &&  line.charAt(i)!=','){
		address = address + line.charAt(i);	
		i++;
	    }
	    if(address.matches("[\\S]+[\\@][\\S]+[\\.][\\S]+"))
		list.add(address);
	}

	return list;
    }



    public void actionPerformed(ActionEvent e) {

		if(e.getSource() == cancel){
		    ((MailWritePane) this).setVisible(false);
		    try {
		        ((MailWritePane) this).finalize();
		        return;
	        } catch (Throwable e1) {}
		}
	
		if(e.getSource() == send){
		    LinkedList to = getAdrressList((String)cbTo.getSelectedItem());
		    if(to == null)
				return;
	
		    LinkedList cc = getAdrressList(tfCC.getText());
		    LinkedList bcc = getAdrressList(tfBCC.getText());
	
		    SMTPTransmitter st = new SMTPTransmitter(account);
		    for(int i=0; i<to.size(); i++)
				st.addDestination((String)to.get(i));
		    if(cc!=null)
		   		for(int i=0; i<cc.size(); i++)
		   	   		st.addCC((String)cc.get(i));
		    if(bcc!=null)
		   		for(int i=0; i<bcc.size(); i++)
		   	   		st.addBCC((String)bcc.get(i));
		    if(filePaths!=null)
		   		for(int i=0; i<filePaths.size(); i++)
		   	    	st.addAttachment((String)filePaths.get(i));
	
		    if(tfSubject.getText()!=null)
				st.setSubject(tfSubject.getText());
		    else
				st.setSubject("");
		    if(text.getText()!=null)
				st.setMessage(text.getText());
		    else
				st.setMessage("");
	
		    st.send();
		    ((MailWritePane) this).setVisible(false);
		    try {
		        ((MailWritePane) this).finalize();
		        return;
	        } catch (Throwable e1) {}
		}
	
		if(e.getSource() == add){
		    JFileChooser fc = new JFileChooser();
		    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    int result = fc.showOpenDialog(((MailWritePane) this));
		    if(result == JFileChooser.APPROVE_OPTION){
			try {
			    if(filePaths==null)
				    filePaths = new LinkedList();
		            String path = fc.getSelectedFile().getCanonicalPath();
		            if(!filePaths.contains(path)){
				filePaths.add(path);
	        	        String name = fc.getSelectedFile().getName();
	        	        int posOfDot = name.lastIndexOf(".");
	        	        String prefix = name.substring(0,posOfDot);
	        	        String postfix = name.substring(posOfDot+1);
	
	        	        final Icon icon = FileSystemView.getFileSystemView().getSystemIcon(fc.getSelectedFile());
	        	        if(prefix.length() > 10) prefix = prefix.substring(0,9) + "_";
	        	        String text = prefix + "." + postfix;
	
	        	        JLabel entry = new JLabel();
	        	        entry.setToolTipText(name);
	        	        entry.setIcon(icon);
	        	        entry.setText(text);
	        	        entry.setSize(entry.getWidth(),50);
	        	        files.add(entry);
	        	        ((MailWritePane) this).validate();
		            }
	                } catch (IOException e1) {}
		    }
		}
    }

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == cbFrom && e.getStateChange()==ItemEvent.SELECTED){
			address = (String) e.getItem();
			Account[] accounts = Account.getAccounts();
				for (int i=0; i<accounts.length; i++)
					if (accounts[i].getProperties().get("userSend").equals(address))
						account = accounts[i].getName();
		}
	}

    public static void main(String[] args){
		new MailWritePane("###");
    }
}




public class MailWritePane extends  MailWritePane$$SMTP  {

    private Modul modul;

    public void createGUI(){
		super.createGUI();
		((MailWritePane) this).cbTo.getEditor().addActionListener( ((MailWritePane) this) );
    }

    public void registerModul(Modul modul){
    	((MailWritePane) this).modul = modul;
    }

    public void actionPerformed(ActionEvent e) {

		super.actionPerformed(e);

		if (e.getSource() == cbTo.getEditor().getEditorComponent()){
			Addressbook ab = (Addressbook)((MailWritePane) this).modul.getBase().getModul("Addressbook");
		    String to = (String) ((MailWritePane) this).cbTo.getEditor().getItem();

		    List list = ab.getAllBuddy();
			if ( to!=null && to.length()>0 && list != null && !list.isEmpty()){
				((MailWritePane) this).cbTo.removeAllItems();
				for (int i=0; i<list.size(); i++){
					Buddy buddy = (Buddy)list.get(i);
					String nick = buddy.getInfo(Buddy.MAIL_NICK);
					String priv = buddy.getInfo(Buddy.MAIL_PRIVATE);
					String busi = buddy.getInfo(Buddy.MAIL_BUSINESS);
					String nickName = buddy.getInfo(Buddy.NICK);
					String realName = buddy.getInfo(Buddy.NAME);

					if (nick!=null && nick.length()>0 && nick.startsWith(to))
						((MailWritePane) this).cbTo.addItem(nick);
					if (priv!=null && priv.length()>0 && priv.startsWith(to))
						((MailWritePane) this).cbTo.addItem(priv);
					if (busi!=null && busi.length()>0 && busi.startsWith(to))
						((MailWritePane) this).cbTo.addItem(busi);
					if (nickName!=null && nickName.length()>0 && nickName.startsWith(to)){
						if (nick!=null && nick.length()>0 && !JComboBoxContains(cbTo,nick))
							((MailWritePane) this).cbTo.addItem(nick);
						if (priv!=null && priv.length()>0 && !JComboBoxContains(cbTo,priv))
							((MailWritePane) this).cbTo.addItem(priv);
						if (busi!=null && busi.length()>0 && !JComboBoxContains(cbTo,busi))
							((MailWritePane) this).cbTo.addItem(busi);
					}
					if (realName!=null && realName.length()>0 && realName.startsWith(to)){
						if (nick!=null && nick.length()>0 && !JComboBoxContains(cbTo,nick))
							((MailWritePane) this).cbTo.addItem(nick);
						if (priv!=null && priv.length()>0 && !JComboBoxContains(cbTo,priv))
							((MailWritePane) this).cbTo.addItem(priv);
						if (busi!=null && busi.length()>0 && !JComboBoxContains(cbTo,busi))
							((MailWritePane) this).cbTo.addItem(busi);
					}
				}//ENDFOR
				((MailWritePane) this).cbTo.getEditor().setItem(to);
				((MailWritePane) this).cbTo.setPopupVisible( true );
				((MailWritePane) this).cbTo.showPopup();
				((BasicComboBoxRenderer)((MailWritePane) this).cbTo.getRenderer()).validate();
				return;
			}//ENDIF
		}
    }

    private boolean JComboBoxContains (JComboBox cb, Object o){
		int size = ((DefaultComboBoxModel)cb.getModel()).getSize();
		for (int i=0; i<size; i++)
			if (((DefaultComboBoxModel)cb.getModel()).getElementAt(i).equals(o))
				return true;
		return false;
    }
      // inherited constructors




    public MailWritePane ( String accountName ) { super(accountName); }

    public MailWritePane (  ) { super(); }
}
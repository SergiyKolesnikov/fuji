
package NewEquation1;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import java.awt.Color;



abstract class UserInterface$$Basic implements ChatLineListener{
	Client client;
	
	public UserInterface$$Basic(Client client){
		this.client=client;	
		initUI();
	
	}
	
	public void newChatLine(String line){
	
	}
	
	public void initUI(){
		client.addLineListener(((UserInterface) this));
		
	}
}




abstract class UserInterface$$GUI extends  UserInterface$$Basic  {
	private JFrame frame;
	private JPanel jPanel;
	public JPanel jPanel_add;
	public JTextField jTextField;
	public JTextArea jTextArea;
	private JScrollPane jScrollPane;
	
	
	public void newChatLine(String line){
		
		jTextArea.append(line);
	}
	
	
	public void initUI(){
		
		frame=new JFrame();
		super.initUI();
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setPreferredSize(new java.awt.Dimension(408, 315));
		{
				frame.setSize(408, 320);
				frame.setVisible(true);
				jScrollPane = new JScrollPane();
				frame.getContentPane().add(jScrollPane);
				jScrollPane.setBounds(12, 12, 368, 196);
				{
					jTextArea = new JTextArea();
					jScrollPane.setViewportView(jTextArea);
					jTextArea.setBounds(12, 12, 368, 196);
					jTextArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
					jTextArea.setEditable(false);
				}
		}
		{
				
				jTextField = new JTextField();
				jTextField.setText("");
				frame.getContentPane().add(jTextField);
				
				jTextField.setBounds(12, 220, 368, 21);
				
				jTextField.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jTextField1ActionPerformed(evt);
					}
				});
		}
		{
				jPanel_add = new JPanel();
				frame.getContentPane().add(jPanel_add);
				jPanel_add.setBounds(12, 247, 368, 34);
		
		}
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
	public void handleMessage(){
		client.send(jTextField.getText());
		jTextField.setText("");
	}
	
	private void jTextField1ActionPerformed(ActionEvent evt) {
		handleMessage();
		/*
		
		client.send(jTextField.getText());
		jTextField.setText("");
		*/
	}
      // inherited constructors


	
	public UserInterface$$GUI ( Client client ) { super(client); }
	
	
}


public class UserInterface extends  UserInterface$$GUI  {

	private JComboBox farbauswahl;
	private JPanel panel;
	public void initUI(){
		super.initUI();
		farbauswahl=new JComboBox(new DefaultComboBoxModel(new String[] { "Schwarz", "Rot", "Blau", "Grün", "Gelb" }));
		jPanel_add.add(farbauswahl);
	}
	
	
	/*	
	public void newChatLine(String line){
			jTextArea.append(line);
	}
	*/
	
	public void handleMessage(){
		TextMessage msg=new ColorMessage(jTextField.getText());
		((ColorMessage) msg).setColor(farbauswahl.getSelectedItem().toString());
		client.send(msg);
		jTextField.setText("");
	}
	
	private void jTextField1ActionPerformed(ActionEvent evt) {
		
		TextMessage msg=new ColorMessage(jTextField.getText());
		((ColorMessage) msg).setColor(farbauswahl.getSelectedItem().toString());
		client.send(msg);
		jTextField.setText("");
	}
      // inherited constructors


	
	public UserInterface ( Client client ) { super(client); }	
	
}
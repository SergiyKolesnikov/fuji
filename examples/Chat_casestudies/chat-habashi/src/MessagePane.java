
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;



abstract class MessagePane$$Base extends JPanel {
	private static final long serialVersionUID = 1L;

	public Client chatClient;

	public MessagePane$$Base(Client chatClient) {
		this.chatClient = chatClient;
		
		setLocation(0, 420);
		setSize(795, 130);
		setLayout(null);

		drawComponents();
	}
	

	protected void drawComponents() {
		addMessageTF();
		addOKButton();
	}

	public JTextField tfMessage;

	private void addMessageTF() {
		tfMessage = new JTextField();
		tfMessage.setLocation(0, 0);
		tfMessage.setSize(725, 55);

		tfMessage.addKeyListener(new KeyListener() {

			
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});

		add(tfMessage);
	}

	private void addOKButton() {
		JButton button = new JButton();
		button.setLocation(725, 0);
		button.setSize(70, 55);
		button.setText("Send");

		button.addActionListener(new ActionListener() {

		
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}

		});

		add(button);
	}

	private void sendMessage() {
		if (chatClient != null)
			chatClient.send(tfMessage.getText());
		tfMessage.setText("");
	}
}

public class MessagePane extends  MessagePane$$Base  {

	URL[] resources;
	Color[] colors;
	
	int count = 2;
	
	protected void drawComponents(){
		super.drawComponents();
		addComponents();	
	}
	
	public void addComponents(){
		resources = new URL[] {
			getClass().getResource("/pics/black.png"),
			getClass().getResource("/pics/red.png"),
			getClass().getResource("/pics/green.png"),
			getClass().getResource("/pics/yellow.png"),
			getClass().getResource("/pics/blue.png"),
			getClass().getResource("/pics/orange.png") };
		
		colors = new Color[] { Color.BLACK, Color.RED, Color.GREEN,
			Color.YELLOW, Color.BLUE, Color.ORANGE };
		
		for (int i = 0; i < colors.length; i++) {
			final JButton button = new JButton();
			button.setLocation(i * 60, 54);
			button.setSize(60, 51);
			button.setIcon(new ImageIcon(resources[i]));

			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					chatClient.setDefaultColor(colors[button.getLocation().x / 60]);
				}
			});
			count++;
			add(button);
		}
		
	}
      // inherited constructors



	public MessagePane ( Client chatClient ) { super(chatClient); }


}
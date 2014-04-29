
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Toolkit;
import javax.swing.JFrame;



abstract class GUI$$Base extends JFrame implements ChatLineListener {
	private static final long serialVersionUID = 1L;
	private Client chatClient;

	public GUI$$Base(Client chatClient) {
		
		this.chatClient = chatClient;	
		setTitle("Chat");

		Dimension frameSize = setLocationCenter(800,500);
		setSize(frameSize);
		setLayout(null);
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		chatClient.addListener(this);

		drawComponents();
		setVisible(true);
	}

	protected Dimension setLocationCenter(int x, int y) {
		
		Dimension frameSize;
		frameSize = new Dimension(x, y);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int top = (screenSize.height - frameSize.height) / 4;
		int left = (screenSize.width - frameSize.width) / 4;
		setLocation(left, top);

		return frameSize;
	}

	private MessagePane messagePane;
	private ChatMessagePane chatMessagePane;

	protected void drawComponents() {

		messagePane = new MessagePane(chatClient);
		chatMessagePane = new ChatMessagePane();
		
		add(messagePane);
		add(chatMessagePane);

		// pack();
		setSize(setLocationCenter(800,500));

		repaint();
	}

	
	public void newChatLine(String line) {
		chatMessagePane.addLineToTA(line);
	}

}

public class GUI extends  GUI$$Base  {
	
	protected Dimension setLocationCenter(int x, int y) {
		return super.setLocationCenter(800,550);
	}
      // inherited constructors



	public GUI ( Client chatClient ) { super(chatClient); }
	
}
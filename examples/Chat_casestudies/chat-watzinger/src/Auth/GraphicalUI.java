import java.awt.BorderLayout; 
import java.awt.Color; 
import java.awt.Dimension; 
import java.awt.Font; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.net.InetAddress; 
import java.util.Observable; 

import javax.swing.JButton; 
import javax.swing.JColorChooser; 
import javax.swing.JFrame; 
import javax.swing.JPanel; 
import javax.swing.JScrollPane; 
import javax.swing.JTextArea; 
import javax.swing.JTextField; public   class  GraphicalUI  extends View {
	

	private static final long serialVersionUID = 2140042960693982634L;

	
	private JScrollPane scrollPane;

	
	private JPanel sendPanel;

	
	private JFrame frame;

	
	private JTextArea textArea;

	
	private JTextField inputField;

	
	private JButton sendButton;

	

	public GraphicalUI(InetAddress serverAddress, int serverPort) {
		super(serverAddress, serverPort);
		frame = new JFrame();
		frame.setTitle("Yet Another Simple Chat Client");
		frame.setSize(600, 400);
		frame.setMinimumSize(new Dimension(300, 200));
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setBackground(Color.WHITE);
		initializeComponents();
		frame.setVisible(true);

		while (true) {
			try {
				processMessage(queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	

	private void sendMessage(String msg) {
		if (!msg.isEmpty())
			client.send(new ChatMessage(getUsername(), msg));
	}

	

	private void processMessage(ChatMessage msg) {
		String display = msg.getSource() + "> " + msg.getMsg();
		display += "\n";
		textArea.append(display);
		textArea.getCaret().setDot(textArea.getText().length());
		scrollPane.scrollRectToVisible(textArea.getVisibleRect());
	}

	

	 private void  processCommand__wrappee__Base  (String[] tokens) {
		StringBuilder m = new StringBuilder();

		for (String t : tokens)
			m.append(t + " ");
		sendMessage(m.toString());
	}

	

	private void processCommand(String[] tokens) {
		if (tokens[0].equals("\\username") && tokens.length == 2) {
			setUsername(tokens[1]);
		} else {
			processCommand__wrappee__Base(tokens);
		}
	}

	

	private void processInput(String input) {
		if (!input.isEmpty()) {
			String[] tokens = input.split("\\s+");
			processCommand(tokens);
		}
	}

	

	private void initializeComponents() {
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Verdana", Font.BOLD, 12));
		textArea.setLineWrap(true);
		scrollPane = new JScrollPane(textArea);
		sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				processInput(inputField.getText());
				inputField.setText("");
			}
		});
		inputField = new JTextField();
		inputField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				processInput(inputField.getText());
				inputField.setText("");
			}
		});

		sendPanel = new JPanel();
		sendPanel.setLayout(new BorderLayout());
		sendPanel.add(inputField, BorderLayout.CENTER);
		sendPanel.add(sendButton, BorderLayout.EAST);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(sendPanel, BorderLayout.SOUTH);
	}


}

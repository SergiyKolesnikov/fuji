package client; 

import java.awt.Color; 
import java.awt.Dimension; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 

import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.FileInputStream; 
import java.io.FileWriter; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.lang.reflect.Field; 
import java.nio.charset.Charset; 
import java.util.Vector; 

import java.util.regex.Matcher; 
import java.util.regex.Pattern; 

import javax.swing.*; 
import javax.swing.GroupLayout.Alignment.*; 
import javax.swing.text.BadLocationException; 
import javax.swing.text.Style; 
import javax.swing.text.StyleConstants; 

import javax.swing.text.StyleContext; 
import javax.swing.text.StyledDocument; 
import javax.swing.text.AbstractDocument.BranchElement; 

import client.Gui.ColoredLine; 

/**
 * TODO description
 */
public   class  Gui  extends JFrame  implements ChatLineListener {
	

	private static final long serialVersionUID = 1L;

	

	protected JTextPane outputTextbox;

	
	protected JTextField inputField;

	
	public StyledDocument doc;

	

	private static int rowstextarea = 20;

	
	private static int colstextarea = 60;

	

	private String transferedChatNameDivider = " - ";

	 
	
	private Client chatClient;

	
	
	public Gui  (String title, Client chatClient) {
		System.out.println("starting gui...");
		chatClient.gui = this;

		outputTextbox = new JTextPane();
		outputTextbox.setEditable(false);
		inputField = new JTextField();
		inputField.addActionListener(getInput());
		
		doc = outputTextbox.getStyledDocument();
		//outputTextbox.setSize(colstextarea*200, rowstextarea*200);
		outputTextbox.setPreferredSize(new Dimension(1009, 500));
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(outputTextbox)
                .addComponent(inputField))
        );

        // layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

        layout.setVerticalGroup(
        	layout
        		.createSequentialGroup()
                .addComponent(outputTextbox)
                .addComponent(inputField)
        );
		
    	// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);

		setTitle(title);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.chatClient = chatClient;
	
		loadLogFile();
	}

	
		
	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chatClient.send((String) inputField.getText());
				inputField.setText("");
			}
		};
	}

	
	
	private Style getStyleOfColor(Color color){
		Style style = (Style)doc.getStyle(StyleContext.DEFAULT_STYLE).copyAttributes();
		StyleConstants.setForeground(style, color);
		
		return style;
	}

	
	
	public Style getStyleOfColor(String colorName){
		Color color;
		try {
		    Field field = Color.class.getField(colorName);
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = Color.BLACK; // Not defined
		}
		return getStyleOfColor(color);
	}

	
	
	private ColoredLine parseTextForColor(String line){
		ColoredLine lineObject = new ColoredLine();
		
		//First part: Make Chat name grey
		int nameEndposition = line.indexOf(transferedChatNameDivider) + transferedChatNameDivider.length();
		if(nameEndposition > 0){
			lineObject.lineParts.add(line.substring(0, nameEndposition));
			lineObject.lineStyles.add(getStyleOfColor(Color.LIGHT_GRAY));
			
			line = line.substring(nameEndposition);
		}
		
		lineObject = coloringALine(line, lineObject);
		
		if(lineObject.lineParts.size()<=1){
				lineObject.lineParts.add(line);
				lineObject.lineStyles.add(doc.getStyle(StyleContext.DEFAULT_STYLE));				
		}
		
		int coloredPartsLength = 0;
		for(String part : lineObject.lineParts)
			coloredPartsLength+=part.length();
		
		
		if(coloredPartsLength < line.length()){
			if(line.lastIndexOf(">")>0){
				lineObject.lineParts.add(line.substring(line.lastIndexOf(">")+1));
				lineObject.lineStyles.add(doc.getStyle(StyleContext.DEFAULT_STYLE));
			}
		}
		
		return lineObject;
	}

	
	
	 private ColoredLine  coloringALine__wrappee__UsernameSupport  (String originalLine, ColoredLine line) {return line;}

	
	private ColoredLine coloringALine(String originalLine, ColoredLine line) {
		//Second part: Parse for color Tags
		//This should Match stuff like: "Hello lovely <blue>World</blue> of hot<red>Dogs</red>."
		Pattern pattern = Pattern.compile("<([^>]+)>(.*)</\\1>");
		Matcher matcher = pattern.matcher(originalLine);
		int endPointOfPreviousTag = -1;
		Boolean firstBeforeString = true;
		while(matcher.find()){
			String color = matcher.group(1);
			
			//Before
			String before = (firstBeforeString)
					?originalLine.substring(0,matcher.start())
					:originalLine.substring(endPointOfPreviousTag, matcher.start());
			;
			firstBeforeString = false;
			endPointOfPreviousTag = matcher.end();  
			
			//Colored tag
			String coloredString = originalLine.substring(matcher.start(2),matcher.end(2));
			
			//Insertions
			if(!before.equals("")){
				line.lineParts.add(before);
				line.lineStyles.add(this.doc.getStyle(StyleContext.DEFAULT_STYLE));
			}
			
			if(!coloredString.equals("")){
				line.lineParts.add(coloredString);
				line.lineStyles.add(this.getStyleOfColor(color));
			}
		}
		return coloringALine__wrappee__UsernameSupport(originalLine, line);
	}

	

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	 private void  newChatLine__wrappee__SpamFilter  (String line) {
		line = line.trim();
		ColoredLine lineObject = parseTextForColor(line);
		try {
			for (int i = 0; i < lineObject.lineParts.size(); i++) {
				doc.insertString(
					doc.getLength(), 
					lineObject.lineParts.get(i), 
					lineObject.lineStyles.get(i)
				);
			}
			
			doc.insertString(doc.getLength(), "\n",doc.getStyle(StyleContext.DEFAULT_STYLE));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	

	public void newChatLine(String line) {
		line = line.trim();
		if(!amLoadingAnLogFile)
			logLine(line);
		
		newChatLine__wrappee__SpamFilter(line);
	}

	
		
	public  class  ColoredLine {
		
		public Vector<String> lineParts;

		
		public Vector<Style> lineStyles;

		
		
		public ColoredLine() {
			lineParts = new Vector<String>();
			lineStyles = new Vector<Style>();
		}

		
		public int length(){
			return lineParts.size();
		}


	}

	
	boolean amLoadingAnLogFile;

	
	
	private void logLine(String line){
		  try{
			  // Create file 
			  FileWriter fstream = new FileWriter(chatClient.username+".txt",true);
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(line+"\n");
			  //Close the output stream
			  out.close();
		  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		  }		
	}

	
	
	private void loadLogFile(){
		amLoadingAnLogFile = true;
		try{
			InputStream    fis= new FileInputStream(chatClient.username+".txt");
			BufferedReader br= new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
			String         line;
			while ((line = br.readLine()) != null) {
			 chatClient.gui.newChatLine(line);
			}
		}catch(Exception e){
			
		}finally{
	
		}
		amLoadingAnLogFile = false;
	}


}

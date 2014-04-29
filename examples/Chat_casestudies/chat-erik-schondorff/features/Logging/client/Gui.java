package client;

/**
 * TODO description
 */
public class Gui
{
	public Gui (String title, Client chatClient)
	{
		original (title, chatClient);
		
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(WindowEvent winEvt)
			{
				try
				{
					PrintWriter writer = new PrintWriter("log.txt");
					String[] lines = outputTextbox.getText().split("\\n");

					for (String line : lines)
						writer.println(line);

					writer.close();
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
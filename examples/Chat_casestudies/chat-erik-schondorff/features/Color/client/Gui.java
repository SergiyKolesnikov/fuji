package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TODO description
 */
public class Gui
{
	protected ButtonGroup groupColors;
	protected JRadioButton radioDefault, radioRed, radioGreen, radioBlue;

	public Gui(String title, Client chatClient)
	{
		groupColors = new ButtonGroup();

		radioDefault = new JRadioButton("Default");
		radioRed = new JRadioButton("Red");
		radioGreen = new JRadioButton("Green");
		radioBlue = new JRadioButton("Blue");

		this.radioBlue.setVisible(false);
		this.radioDefault.setVisible(false);
		this.radioGreen.setVisible(false);
		this.radioRed.setVisible(false);

		groupColors.add(radioDefault);
		groupColors.add(radioRed);
		groupColors.add(radioGreen);
		groupColors.add(radioBlue);

		original(title, chatClient);

		this.radioBlue.setVisible(true);
		this.radioDefault.setVisible(true);
		this.radioGreen.setVisible(true);
		this.radioRed.setVisible(true);
		radioDefault.setSelected(true);
	}

	protected Component[] addChatComponents()
	{
		return new Component[] { this.radioDefault, this.radioBlue, this.radioGreen, this.radioRed };
	}

	private ActionListener getInput()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String inputText = inputField.getText();

				String colorModification = "<%s>%s</%s>";

				if (radioRed.isSelected())
					colorModification = String.format(colorModification, "Red",
							inputText, "Red");
				else if (radioGreen.isSelected())
					colorModification = String.format(colorModification,
							"Green", inputText, "Green");
				else if (radioBlue.isSelected())
					colorModification = String.format(colorModification,
							"Blue", inputText, "Blue");
				else
					colorModification = inputText;

				inputText = colorModification;

				chatClient.send(inputText);
				inputField.setText("");
			}
		};
	}
}
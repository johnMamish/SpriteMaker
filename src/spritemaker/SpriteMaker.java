package spritemaker;

import javax.swing.*;

class SpriteMaker
{
	public static void main(String[] args)
	{
		SpriteMakerWindow theWindow = new SpriteMakerWindow();
		theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theWindow.setTitle("Make sprites");
		theWindow.setSize(500, 500);
		theWindow.setVisible(true);
	}
}

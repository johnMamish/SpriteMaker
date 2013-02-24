package spritemaker;

import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

class SpriteMakerWindow extends JFrame
{
	private class GenerateStringListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println(drawingPanel.generateHexString());
		}
	}
	
	
	//menu bars
    //menu variables
    JMenuBar mainMenuBar;
    
    JMenu formatMenu;
    JMenuItem inputFormatOptions;
    JMenuItem outputFormatOptions;
    
	private SpriteDrawingPanel drawingPanel;
	private JButton getStringButton;
	
	private final int updateRate = 20;

	public SpriteMakerWindow()
	{
		//set up menubar
        inputFormatOptions = new JMenuItem("Input Format");
        inputFormatOptions.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				throw new UnsupportedOperationException("Not supported yet.");
			}
		});
        
        outputFormatOptions = new JMenuItem("ping programmer");
        outputFormatOptions.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				throw new UnsupportedOperationException("Not supported yet.");
			}
		});
        
        //next, set up menu items and add submenus
        formatMenu = new JMenu("Format Options");
        formatMenu.add(inputFormatOptions);
        formatMenu.add(outputFormatOptions);
        
        //now, set up the main menu bar
        mainMenuBar = new JMenuBar();
        mainMenuBar.add(formatMenu);
        
        //set this menu bar as the frame's menu
        this.setJMenuBar(mainMenuBar);
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 5;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		drawingPanel = new SpriteDrawingPanel(1, 1, 12);
		drawingPanel.setPostfix(", ");
		drawingPanel.setPrefix("$");
		this.add(drawingPanel, c);
		
		c.weighty = 0;
		c.gridy = 1;
		getStringButton = new JButton("make string");
		getStringButton.addActionListener(new GenerateStringListener());
		this.add(getStringButton, c);
		
		Timer graphicsTimer = new Timer();
		graphicsTimer.scheduleAtFixedRate(new UpdateGraphics(), updateRate, updateRate);
	}
	
   	class UpdateGraphics extends TimerTask
	{
		public void run()
		{
			drawingPanel.repaint();
		}
	}
}

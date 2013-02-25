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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private class GenerateStringListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			stringOutput.setText(drawingPanel.generateHexString());
		}
	}
	
	
	//menu bars
    //menu variables
    JMenuBar mainMenuBar;
    
    JMenu propertiesMenu;
    JMenuItem pixelMappingMenu;
    JMenuItem sizeMenu;
    JMenuItem outputFormatOptions;
    
	private SpriteDrawingPanel drawingPanel;
	private JButton getStringButton;
	private JTextArea stringOutput;
	
	private final int updateRate = 20;

	public SpriteMakerWindow()
	{
		//set up menubar
        pixelMappingMenu = new JMenuItem("Pixel Mapping");
        pixelMappingMenu.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				OrientationDialog mappingSelection = new OrientationDialog(SpriteMakerWindow.this);
				mappingSelection.showDialog();
			}
		});
        
        sizeMenu = new JMenuItem("Canvas Size");
        sizeMenu.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int[] canvasSize = drawingPanel.getCanvasSize();
				SizeSelectionDialog select = new SizeSelectionDialog(canvasSize[0], canvasSize[1], SpriteMakerWindow.this);
				drawingPanel.setCanvasSize(select.showDialog());
			}
		});
        
        outputFormatOptions = new JMenuItem("Output Format");
        outputFormatOptions.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String[] currentOutputFormatting = SpriteMakerWindow.this.drawingPanel.getOutputFormatting();
				OutputFormatDialog outDialog = new OutputFormatDialog(currentOutputFormatting[0], currentOutputFormatting[1], SpriteMakerWindow.this);
				currentOutputFormatting = outDialog.showDialog();
				SpriteMakerWindow.this.drawingPanel.setPrefix(currentOutputFormatting[0]);
				SpriteMakerWindow.this.drawingPanel.setPostfix(currentOutputFormatting[1]);
			}
		});
        
        //next, set up menu items and add submenus
        propertiesMenu = new JMenu("Format Options");
        propertiesMenu.add(pixelMappingMenu);
        propertiesMenu.add(outputFormatOptions);
        propertiesMenu.add(sizeMenu);
        
        //now, set up the main menu bar
        mainMenuBar = new JMenuBar();
        mainMenuBar.add(propertiesMenu);
        
        //set this menu bar as the frame's menu
        this.setJMenuBar(mainMenuBar);
		
		this.setLayout(new GridBagLayout());
		
		//layout code
		GridBagConstraints c = new GridBagConstraints();
		
		//layout for paintbrush options
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 1;
		this.add(new JComponent(){}, c);
		
		//layout for drawing panel.
		drawingPanel = new SpriteDrawingPanel(1, 1, 12);
		drawingPanel.setPostfix(", ");
		drawingPanel.setPrefix("$");
		c.gridx = 1;
		c.gridheight = 2;
		c.weightx = 2;
		c.weighty = 2;
		this.add(drawingPanel, c);
		
		//layout for button
		c.gridheight = 1;
		c.weighty = 0;
		c.weightx = 0;
		c.gridy = 1;
		c.gridx = 0;
		getStringButton = new JButton("make string");
		getStringButton.addActionListener(new GenerateStringListener());
		this.add(getStringButton, c);
		
		//layout for text area
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 2;
		c.gridx = 0;
		c.weighty = 0;
		c.weightx = 0;
		c.gridwidth = 2;
		stringOutput = new JTextArea(10, 10);
		stringOutput.setEditable(false);
		stringOutput.setLineWrap(true);
		stringOutput.setWrapStyleWord(true);
		this.add(stringOutput, c);
		

		
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

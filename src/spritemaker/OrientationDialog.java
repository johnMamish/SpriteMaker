package spritemaker;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OrientationDialog extends JDialog
{
	class OrientationDiagram extends JPanel
	{
		private final int squareSize = 35;
		
		private ByteOrientationOption byteOrientation;
		private ByteMSBPositionOption MSBPosition;
		
		public OrientationDiagram(ByteOrientationOption currentByteOrientation, ByteMSBPositionOption currentMSBPosition)
		{
			byteOrientation = currentByteOrientation;
			MSBPosition = currentMSBPosition;
			
			//I don't think there's aynthing else to do here... I mean, there's no layout scheme, so...
		}
		
		public void toggleOrientation()
		{
			byteOrientation = ByteOrientationOption.values()[(byteOrientation.ordinal()+1)%ByteOrientationOption.values().length];
			//System.out.println(byteOrientation);
			this.repaint();
		}
		
		public void toggleMSBPos()
		{
			MSBPosition = ByteMSBPositionOption.values()[(MSBPosition.ordinal()+1)%ByteMSBPositionOption.values().length];
			//System.out.println(MSBPosition);
			this.repaint();
		}
		
		public void paint(Graphics g)
		{
			//call super method and cast graphics
			super.paint(g);
			Graphics2D painting = (Graphics2D)g;
			
			Point MSBPoint = new Point(55, 55);
			Point LSBPoint = new Point(55+squareSize*7, 55);
			
			//draw rectangle
			switch(byteOrientation)
			{
				case HORIZONTAL:
				{
					//draw horizontal rectangle
					painting.drawRect(40, 40, squareSize*8, squareSize);
					break;
				}
				
				case VERTICAL:
				{
					//change position of LSB text
					LSBPoint.x = 55;
					LSBPoint.y = 55+squareSize*7;
					
					//draw vertical rectangle
					painting.drawRect(40, 40, squareSize, squareSize*8);
					break;
				}
			}
			
			//exchange text if necessary
			switch(MSBPosition)
			{
				case MSBATFRONT:
				{
					//do nothing.  MSB/LSB text already in right order
					break;
				}
				
				case MSBATBACK:
				{
					//flip around MSB and LSB text
					Point temp = MSBPoint;
					MSBPoint = LSBPoint;
					LSBPoint = temp;
					break;
				}
			}
			
			//draw text
			painting.drawString("MSB", MSBPoint.x, MSBPoint.y);
			painting.drawString("LSB", LSBPoint.x, LSBPoint.y);
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -231339667516222271L;
	private JButton toggleOrientation;
	private JButton toggleMSBPosition;
	private OrientationDiagram userDiagram;
	private final Insets stdInsets = new Insets(10, 10, 10, 10);
	
	public OrientationDialog(JFrame owner)
	{
		super(owner, "select canvas size", true);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//layout for buttons.
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 1;
		c.insets = stdInsets;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		toggleOrientation = new JButton("toggle orientation");
		toggleOrientation.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				OrientationDialog.this.userDiagram.toggleOrientation();
			}
		});
		this.add(toggleOrientation, c);
		
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		toggleMSBPosition = new JButton("toggle MSB");
		toggleMSBPosition.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				OrientationDialog.this.userDiagram.toggleMSBPos();
			}
		});
		this.add(toggleMSBPosition, c);
		
		//layout for Orientation Diagram.
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		userDiagram = new OrientationDiagram(ByteOrientationOption.HORIZONTAL, ByteMSBPositionOption.MSBATFRONT);
		this.add(userDiagram, c);
		
		//layout for ok button
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		JButton okButton = new JButton("ok");
		okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//now, close the window
				OrientationDialog.this.setVisible(false);
				OrientationDialog.this.dispose();
			}
		});
		this.add(okButton, c);
		
		this.setSize(600, 600);
		this.setResizable(false);
	}
	
	
	
	public void showDialog()
	{
		this.setVisible(true);
		//return new String[] {prefixInput.getText(), postfixInput.getText()};
	}
}

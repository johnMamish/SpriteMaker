package spritemaker;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

public class OrientationDiagram extends JPanel
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
	
	public ByteOrientationOption getByteOrientation()
	{
		return this.byteOrientation;
	}
	
	public ByteMSBPositionOption getMSBPosition()
	{
		return this.MSBPosition;
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
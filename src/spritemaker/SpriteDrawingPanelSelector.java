package spritemaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

/**
 *
 * @author John
 * 
 * This class controls what drawing mode a SpriteDrawingPanel is in.  Each instance
 * of this class must be tied to a SpriteDrawingPanel.
 */
public class SpriteDrawingPanelSelector extends JComponent implements MouseListener
{
	private SpriteDrawingPanel subject;
	private int selectedSquare;
	private int drawingMode;
	private final int panelWidth = 2;
	private final int numSquares = 3;
	private final Color regularColor = new Color(0x505050);
	private final Color selectedColor = new Color(0x909090);
	
	
	public SpriteDrawingPanelSelector(SpriteDrawingPanel subject)
	{
		this.subject = subject;
		
		this.addMouseListener(this);
		
		drawingMode = 0;
		selectedSquare = Integer.MAX_VALUE;
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D drawing = (Graphics2D)g;
		
		Color currentSquareColor;
		
		//calculate square width
		int squareWidth = this.getWidth()/panelWidth;
		
		//paint all squares
		for(int i = 0;(panelWidth*i) < numSquares;i++)	//y direction
		{
			for(int j = 0;(((panelWidth*i)+j) < numSquares) && (j < panelWidth);j++)
			{
				if(((panelWidth*i)+j) == selectedSquare)
				{
					drawing.setColor(selectedColor);
				}
				else
				{
					drawing.setColor(regularColor);
				}
				
				drawing.fillRect(j*squareWidth, i*squareWidth, squareWidth, squareWidth);
			}
		}
	}
	
	public Point mapPixelToSquare(Point p)
	{
		int squareWidth = this.getWidth()/panelWidth;
		Point retPoint = new Point(p.x/squareWidth, p.y/squareWidth);
		
		//check to see if we clicked in a "blank area"
		if(((retPoint.y*panelWidth)+retPoint.x) > numSquares)
		{
			return new Point(-1, -1);
		}
		else if((retPoint.x == -1) || (retPoint.y == -1))
		{
			return new Point(-1, -1);
		}
		
		return retPoint;
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		Point clickedSquare = mapPixelToSquare(e.getPoint());
		System.out.println(clickedSquare);
		if(clickedSquare.x == -1)
		{
			this.selectedSquare = Integer.MAX_VALUE;
			this.drawingMode = 0;
			this.subject.setMode(this.drawingMode);
		}
		else
		{
			int controlState = ((clickedSquare.y*panelWidth)+clickedSquare.x);
			this.selectedSquare = controlState;
			this.drawingMode = controlState+1;
			this.subject.setMode(this.drawingMode);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e){}
	@Override
	public void mouseReleased(MouseEvent e){}
	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}
}

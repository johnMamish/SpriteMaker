package spritemaker;

import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

//this class is used to draw sprites with the mouse.
class SpriteDrawingPanel extends JPanel implements MouseMotionListener, MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1199824844784104333L;
	/**
	 * 
	 */
	private boolean[][] pixels;
	private int squareWidth;
	private int spriteWidth;
	private int spriteHeight;
	private ByteOrientationOption byteOrientation;
	private ByteMSBPositionOption MSBPos;
	private String prefix;
	private String postfix;
	
	public SpriteDrawingPanel()
	{
		spriteWidth = 8;
		spriteHeight = 8;
		
		//initialize array
		pixels = new boolean[spriteHeight][spriteWidth];
		for(int i = 0;i < pixels.length;i++)
		{
			for(int j = 0;j < pixels[i].length;j++)
			{
				pixels[i][j] = false;
			}
		}
		
		prevSquare = new Point();
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		
		//initialize orientation
		byteOrientation = ByteOrientationOption.HORIZONTAL;
		MSBPos = ByteMSBPositionOption.MSBATFRONT;
	}
	
	//width and height are given in bytes, NOT bits.
	public SpriteDrawingPanel(int width, int height, int squareWidth)
	{
		this.spriteWidth = width*8;
		this.spriteHeight = height*8;
		
		//initialize array
		pixels = new boolean[spriteHeight][spriteWidth];
		for(int i = 0;i < pixels.length;i++)
		{
			for(int j = 0;j < pixels[i].length;j++)
			{
				pixels[i][j] = false;
			}
		}
		
		this.squareWidth = squareWidth;
		prevSquare = new Point();
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		
		//initialize orientation
		byteOrientation = ByteOrientationOption.HORIZONTAL;
		MSBPos = ByteMSBPositionOption.MSBATFRONT;
	}	
	
	//changes the size of the sprite.
	public void setCanvasSize(int[] widthHeight)
	{
		this.spriteWidth = widthHeight[0]*8;
		this.spriteHeight = widthHeight[1]*8;
		
		//make a new array of bytes to start drawing on.
		boolean[][] newCanvas = new boolean[spriteHeight][spriteWidth];
		
		//copy the old one over
		for(int i = 0;(i < newCanvas.length) && (i < this.pixels.length);i++)
		{
			for(int j = 0;(j < newCanvas[i].length) && (j < this.pixels[i].length);j++)
			{
				newCanvas[i][j] = this.pixels[i][j];
			}
		}
		
		//reassign
		this.pixels = newCanvas;
	}
	
	public int[] getCanvasSize()
	{
		return new int[] {spriteWidth/8, spriteHeight/8};
	}
	
	public void setPrefix(String pf)
	{
		this.prefix = pf;
	}
	
	public void setPostfix(String pf)
	{
		this.postfix = pf;
	}
	
	public String[] getOutputFormatting()
	{
		return new String[] {prefix, postfix};
	}
	
	public String generateHexString()
	{
		//interpret boolean array as a hex string.
		String result = "";
		int currentNumber = 0;
		int currentX = 0;
		int currentY = 0;
		Integer fastIndex;			//value pointed by this is incremented each iteration of inner loop
		Integer slowIndex;			//value pointed by this is incremented each iteration of outer loop
		int fastBoundary;
		int slowBoundary;
		//Integer x;
		//Integer y;
		Integer InnerDirection = currentX;		//If this points to currentX, we are in HORIZONTAL orientation.  If to y, in VERTICAL orientation.
		
		//set up Integer vars to point to ints based on Orientation
		switch(byteOrientation)
		{
			case HORIZONTAL:
			{
				InnerDirection = currentX;
				break;
			}
			
			case VERTICAL:
			{
				InnerDirection = currentY;
				break;
			}
 		}
		
		//fast and slow index are "constant" for now
		fastIndex = currentX;
		slowIndex = currentY;
		
		//precondition: array is NOT jagged
		fastBoundary = pixels[0].length;
		slowBoundary = pixels.length;
		
		
		/*for(baseX = 0, baseY = 0;i < pixels.length-1;i++)
		{
			for(j = 0;j < (spriteWidth/8);j++)
			{
				currentNumber = 0;
				for(int k = 0;k < 8;k++)
				{
					currentNumber <<= 1;
					if(pixels[i][j*8+k])
					{
						currentNumber |= 1;
					}
				}
				result += prefix + Integer.toHexString(0x100 | currentNumber).substring(1) + postfix;
			}
		}
		
		for(j = 0;j < (spriteWidth/8)-1;j++)
		{
			currentNumber = 0;
			for(int k = 0;k < 8;k++)
			{
				currentNumber <<= 1;
				if(pixels[i][j*8+k])
				{
					currentNumber |= 1;
				}
			}
			result += prefix + Integer.toHexString(0x100 | currentNumber).substring(1) + postfix;
		}
		
		currentNumber = 0;
		for(int k = 0;k < 8;k++)
		{
			currentNumber <<= 1;
			if(pixels[i][j*8+k])
			{
				currentNumber |= 1;
			}
		}
		result += prefix + Integer.toHexString(0x100 | currentNumber).substring(1);*/
		
		for(slowIndex = 0;slowIndex.compareTo(slowBoundary) < 0;slowIndex++)
		{
			System.out.println(slowIndex.compareTo(slowBoundary));
			for(fastIndex = 0;fastIndex.compareTo(fastBoundary) < 0;fastIndex++)
			{
				currentNumber = 0;
				for(int k = 0;k < 8;k++)
				{
					//accumulate number
					currentNumber <<= 1;
					if(pixels[currentY][currentX])
					{
						currentNumber |= 1;
					}
					InnerDirection++;
				}
				//add currentNumber to the string
				result += prefix + Integer.toHexString(0x100 | currentNumber).substring(1) + postfix;
				
				//we are comparing pointers, NOT value, so we use ==, not .equals.
				if(fastIndex == InnerDirection)
				{
					fastIndex++;
				}
				else
				{
					slowIndex -= 8;		//if fastIndex != InnerDirection, this loop must have incremented in the direciton of the slowIndex, which is bad.  Undo it!
					fastIndex++;
				}
			}
		}
		
		return result;
	}
	
	private Point realPixelToVirtualPixel(Point p)
	{
		Point result = new Point(p.x/squareWidth, p.y/squareWidth);
		if((result.x >= spriteWidth) || (result.y >= spriteHeight))
		{
			result.x = -1;
			result.y = -1;
		}
		return result;
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		//guard against an empty array.
		/*if(pixels.length == 0)
		{
			return;
		}*/
		
		Graphics2D drawing = (Graphics2D)g;
		
		//draw the grid
		//x axis first
		//less than or equals for outside border.
		for(int i = 0;i <= pixels.length;i++)
		{
			drawing.drawLine(0, i*squareWidth, spriteWidth*squareWidth, i*squareWidth);
		}
		
		//now y axis
		for(int i = 0;i <= pixels[0].length;i++)
		{
			drawing.drawLine(i*squareWidth, 0, i*squareWidth, spriteHeight*squareWidth);
		}
		
		//fill in shaded squares
		for(int i = 0;i < pixels.length;i++)
		{
			for(int j = 0;j < pixels[i].length;j++)
			{
				Color center = null;
				if(pixels[i][j])
				{
					center = new Color(0x000000);
				}
				else
				{
					center = new Color(0xffffff);
				}
				drawing.setColor(center);
				drawing.fillRect(j*squareWidth+1, i*squareWidth+1, squareWidth-1, squareWidth-1);
			}
		}
	}

	//private variables for MouseListener section
	private Point prevSquare;
	private boolean initialMarkAction;
	
	public void mouseDragged(MouseEvent e)
	{
		//check to see if we are out of bounds
		Point vPix = this.realPixelToVirtualPixel(e.getPoint());
		if((vPix.x == -1) || (vPix.y == -1))
		{
			return;
		}
		
		//check to see if the mouse is at new coordinates
		if(!vPix.equals(prevSquare))
		{
			prevSquare = this.realPixelToVirtualPixel(e.getPoint());
			pixels[prevSquare.y][prevSquare.x] = initialMarkAction;
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		//check to make sure we are not going out of boudns
		Point vPix = this.realPixelToVirtualPixel(e.getPoint());
		if((vPix.x == -1) || (vPix.y == -1))
		{
			return;
		}
		
		//log the point we pressed at.  If we are clicking the mouse, we must not
		//have a previous point.
		prevSquare = vPix;
		initialMarkAction = !pixels[prevSquare.y][prevSquare.x];
		pixels[prevSquare.y][prevSquare.x] = initialMarkAction;
	}
	
	//we are only interested in instances where the mouse has been dragged or pressed.
	public void mouseMoved(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}

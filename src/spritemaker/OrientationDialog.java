package spritemaker;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OrientationDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -231339667516222271L;
	private JButton toggleOrientation;
	private JButton toggleMSBPosition;
	private OrientationDiagram userDiagram;
	private final Insets stdInsets = new Insets(10, 10, 10, 10);
	
	public OrientationDialog(JFrame owner, ByteOrientationOption boo, ByteMSBPositionOption bmpo)
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
		userDiagram = new OrientationDiagram(boo, bmpo);		
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
	
	public OrientationDiagram showDialog()
	{
		this.setVisible(true);
		return this.userDiagram;
	}
}

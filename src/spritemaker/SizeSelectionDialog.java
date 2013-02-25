package spritemaker;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SizeSelectionDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -231339667516222271L;
	private int width = 1;
	private int height = 1;
	private JTextField widthInput;
	private JTextField heightInput;
	private final Insets stdInsets = new Insets(10, 10, 10, 10);
	
	
	public SizeSelectionDialog(int initialWidth, int initialHeight, JFrame owner)
	{
		super(owner, "select canvas size", true);
		
		width = initialWidth;
		height = initialHeight;
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//layout for row 1.
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = stdInsets;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		JLabel widthLabel = new JLabel("width");
		this.add(widthLabel, c);
		
		c.gridx = 1;
		c.anchor = GridBagConstraints.WEST;
		//c.fill = GridBagConstraints.HORIZONTAL;
		widthInput = new JTextField(10);
		widthInput.setText(Integer.toString(this.width, 10));
		this.add(widthInput, c);
		
		//layout for row 2.
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		JLabel heightLabel = new JLabel("height");
		this.add(heightLabel, c);
		
		c.gridx = 1;
		c.anchor = GridBagConstraints.WEST;
		//c.fill = GridBagConstraints.HORIZONTAL;
		heightInput = new JTextField(10);
		heightInput.setText(Integer.toString(this.height, 10));
		this.add(heightInput, c);
		
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
				//first, update output fields
				SizeSelectionDialog.this.width = Integer.parseInt(SizeSelectionDialog.this.widthInput.getText());
				SizeSelectionDialog.this.height = Integer.parseInt(SizeSelectionDialog.this.heightInput.getText());
				
				//now, close the window
				SizeSelectionDialog.this.setVisible(false);
				SizeSelectionDialog.this.dispose();
			}
		});
		this.add(okButton, c);
		
		this.setSize(300, 200);
		this.setResizable(false);
	}
	
	
	
	public int[] showDialog()
	{
		this.setVisible(true);
		return new int[] {width, height};
	}
}

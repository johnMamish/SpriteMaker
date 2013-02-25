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

public class OutputFormatDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -231339667516222271L;
	private JTextField prefixInput;
	private JTextField postfixInput;
	private final Insets stdInsets = new Insets(10, 10, 10, 10);
	
	
	public OutputFormatDialog(String currentPrefix, String currentPostfix, JFrame owner)
	{
		super(owner, "select canvas size", true);
		
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
		JLabel widthLabel = new JLabel("prefix");
		this.add(widthLabel, c);
		
		c.gridx = 1;
		c.anchor = GridBagConstraints.WEST;
		//c.fill = GridBagConstraints.HORIZONTAL;
		prefixInput = new JTextField(10);
		prefixInput.setText(currentPrefix);
		this.add(prefixInput, c);
		
		//layout for row 2.
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		JLabel heightLabel = new JLabel("postfix");
		this.add(heightLabel, c);
		
		c.gridx = 1;
		c.anchor = GridBagConstraints.WEST;
		postfixInput = new JTextField(10);
		postfixInput.setText(currentPostfix);
		this.add(postfixInput, c);
		
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
				OutputFormatDialog.this.setVisible(false);
				OutputFormatDialog.this.dispose();
			}
		});
		this.add(okButton, c);
		
		this.setSize(300, 200);
		this.setResizable(false);
	}
	
	
	
	public String[] showDialog()
	{
		this.setVisible(true);
		return new String[] {prefixInput.getText(), postfixInput.getText()};
	}
}

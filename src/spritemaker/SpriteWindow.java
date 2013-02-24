/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spritemaker;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 *
 * @author John
 */
public class SpriteWindow extends JFrame
{
    private JTextArea filePath;
    private JTextArea charArray;
    
    private JButton convert;

    private final Insets stdInsets = new Insets(10, 10, 10, 10);
    private final Insets smallInsets = new Insets(0, 0, 0, 0);
    
    public SpriteWindow()
    {
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        
        filePath = new JTextArea(1, 20);
        GridBagConstraints filePathConstraints = new GridBagConstraints();
        filePathConstraints.gridx = 0;
        filePathConstraints.gridy = 0;
        filePathConstraints.insets = stdInsets;
        layout.setConstraints(filePath, filePathConstraints);
        this.add(filePath);
        
        convert = new JButton();
        convert.setText("Generate Array");
        convert.setSize(20, 5);
        GridBagConstraints convertConstraints = new GridBagConstraints();
        convertConstraints.gridx = 0;
        convertConstraints.gridy = 1;
        convertConstraints.insets = stdInsets;
        layout.setConstraints(convert, convertConstraints);
        this.add(convert);
        
        charArray = new JTextArea(20, 20);
        charArray.setEditable(false);
        GridBagConstraints charArrayConstraints = new GridBagConstraints();
        charArrayConstraints.fill = GridBagConstraints.BOTH;
        charArrayConstraints.gridx = 0;
        charArrayConstraints.gridy = 2;
        filePathConstraints.insets = stdInsets;
        layout.setConstraints(charArray, charArrayConstraints);
        this.add(charArray);
        
        JScrollPane outputScroll = new JScrollPane(charArray);
        layout.setConstraints(outputScroll, charArrayConstraints);
        this.add(outputScroll, charArrayConstraints);
    }

}

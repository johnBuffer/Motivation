import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.Document;

public class ScrollTextPane extends JPanel
{
	private JScrollPane scroll;
	private JTextPane textPane;
	
	public ScrollTextPane()
	{
		textPane = new JTextPane();
		scroll = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.setLayout(new GridLayout(1, 1));
		this.add(scroll);
	}
	
	public void setComponentSize(int width, int height)
	{
		textPane.setPreferredSize(new Dimension(width, height));
	}
	
	public String getText()
	{
		return textPane.getText();
	}
	
	public Vector<String> getLines()
	{
		String text = textPane.getText();
		
		text = text.replace("\r", "");
		
		String[] tab = text.split("\n");
		
		Vector<String> v = new Vector<String>();
		for (String str : tab)
			v.addElement(str);
		
		return v;
	}
	
	public void setText(String str)
	{
		if (str.length() > 0)
		{
			while (str.charAt(0) == '\n')
				str = str.substring(1);
			
			while (str.charAt(str.length()-1) == '\n')
				str = str.substring(0, str.length()-1);
			
			textPane.setText(str);
		}
	}
	
	public void alert()
	{
		textPane.setBackground(new Color(255, 200, 200));
	}
	
	public void valid()
	{
		if (textPane.getText().length() > 0)
		{
			textPane.setBackground(new Color(200, 255, 200));
		}
		else
		{
			alert();
		}
	}
}

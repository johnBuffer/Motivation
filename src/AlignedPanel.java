import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

public class AlignedPanel extends JPanel
{
	public AlignedPanel(int align, JPanel panel)
	{
		super(new FlowLayout(align));
		FlowLayout layout = (FlowLayout)this.getLayout();
		layout.setVgap(0);
		layout.setHgap(0);
		
		this.add(panel);
	}
}

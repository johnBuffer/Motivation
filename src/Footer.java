import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.apache.pdfbox.pdmodel.PDDocument;

public class Footer extends JPanel implements TextProvider
{
	private ScrollTextPane bye;
	private ScrollTextPane end;
	private ScrollTextPane signature;
	
	public Footer()
	{
		this.setLayout(new GridLayout(3, 1));
		
		bye = new ScrollTextPane();
		end = new ScrollTextPane();
		signature = new ScrollTextPane();
		
		end.setComponentSize(500, 40);
		end.setBorder(BorderFactory.createTitledBorder("Petite conclusion"));
		
		bye.setComponentSize(500, 40);
		bye.setBorder(BorderFactory.createTitledBorder("Au revoir"));
		
		signature.setComponentSize(500, 20);
		signature.setBorder(BorderFactory.createTitledBorder("Signature"));
		
		this.add(end);
		this.add(new AlignedPanel(FlowLayout.LEFT, bye));
		this.add(new AlignedPanel(FlowLayout.RIGHT, signature));
		this.setPreferredSize(new Dimension(500, 180));
	}
	
	public void setEnd(String str)
	{
		end.setText(str);
	}
	
	public void setBye(String str)
	{
		bye.setText(str);
	}
	
	public void setSignature(String str)
	{
		signature.setText(str);
	}

	@Override
	public void pushInDoc(PDDocument doc) {
		// TODO Auto-generated method stub
		PDFBuilder.addSpaceParagraphSpace();
		PDFBuilder.pushTextToDoc(doc, end.getLines(), PDFBuilder.Alignment.JUSTIFIED);
		end.valid();
		PDFBuilder.addSpaceParagraphSpace();
		PDFBuilder.pushTextToDoc(doc, bye.getLines(), PDFBuilder.Alignment.JUSTIFIED);
		bye.valid();
		PDFBuilder.addSpaceParagraphSpace(2);
		PDFBuilder.pushTextToDoc(doc, signature.getLines(), PDFBuilder.Alignment.RIGHT);
		signature.valid();
	}
}

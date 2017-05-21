import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.apache.pdfbox.pdmodel.PDDocument;

public class Body extends JPanel implements TextProvider
{
	private ScrollTextPane introduction;
	private ScrollTextPane secondParagraph;
	
	public Body()
	{
		introduction = new ScrollTextPane();
		secondParagraph = new ScrollTextPane();
		
		introduction.setComponentSize(600, 140);
		introduction.setBorder(BorderFactory.createTitledBorder("Introduction"));
		
		secondParagraph.setComponentSize(600, 140);
		secondParagraph.setBorder(BorderFactory.createTitledBorder("Développement"));
		
		this.setLayout(new GridLayout(2, 1));
		this.add(introduction);
		this.add(secondParagraph);
	}
	
	public void setIntroduction(String str)
	{
		this.introduction.setText(str);
	}
	
	public void setSecondParagraph(String str)
	{
		this.secondParagraph.setText(str);
	}

	@Override
	public void pushInDoc(PDDocument doc) {
		// TODO Auto-generated method stub
		PDFBuilder.pushTextToDoc(doc, introduction.getLines(), PDFBuilder.Alignment.JUSTIFIED);
		introduction.valid();
		PDFBuilder.addSpaceParagraphSpace();
		PDFBuilder.pushTextToDoc(doc, secondParagraph.getLines(), PDFBuilder.Alignment.JUSTIFIED);
		secondParagraph.valid();
		
	}
}

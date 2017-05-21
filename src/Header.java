import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.pdfbox.pdmodel.PDDocument;

public class Header extends JPanel implements TextProvider
{
	private ScrollTextPane salutation;
	private ScrollTextPane localization;
	private ScrollTextPane letterObject;
	
	public Header()
	{
		this.setLayout(new GridLayout(3, 1));
		
		JPanel localContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		FlowLayout layout = (FlowLayout)localContainer.getLayout();
		layout.setVgap(0);
		layout.setHgap(0);
				
		salutation = new ScrollTextPane();
		localization = new ScrollTextPane();
		letterObject = new ScrollTextPane();
		
		salutation.setComponentSize(300, 20);
		localization.setComponentSize(150, 20);
		letterObject.setComponentSize(500, 20);
		
		localization.setBorder(BorderFactory.createTitledBorder("Date et lieu"));
		letterObject.setBorder(BorderFactory.createTitledBorder("Objet"));
		salutation.setBorder(BorderFactory.createTitledBorder("Salutations"));
		
		this.add(new AlignedPanel(FlowLayout.RIGHT, localization));
		this.add(new AlignedPanel(FlowLayout.LEFT, letterObject));
		this.add(new AlignedPanel(FlowLayout.LEFT, salutation));
		this.setPreferredSize(new Dimension(500, 180));
		
		Border border = BorderFactory.createCompoundBorder(
				new EmptyBorder(20, 0, 20, 0), 
				BorderFactory.createTitledBorder("Entête"));
		
		this.setBorder(border);
	}
	
	public void setSalutation(String str)
	{
		salutation.setText(str);
	}
	
	public void setLocalization(String str)
	{
		localization.setText(str);
	}
	
	public void setObject(String str)
	{
		letterObject.setText(str);
	}

	@Override
	public void pushInDoc(PDDocument doc) {
		// TODO Auto-generated method stub
		PDFBuilder.pushTextToDoc(doc, this.localization.getLines(), PDFBuilder.Alignment.RIGHT);
		localization.valid();
		PDFBuilder.addSpaceParagraphSpace(3);
		
		Vector<String> objectLines = this.letterObject.getLines();
		objectLines.set(0, "Objet : "+objectLines.get(0));
		PDFBuilder.pushTextToDoc(doc, objectLines, PDFBuilder.Alignment.LEFT);
		letterObject.valid();
		
		PDFBuilder.addSpaceParagraphSpace(3);
		PDFBuilder.pushTextToDoc(doc, this.salutation.getLines(), PDFBuilder.Alignment.LEFT);
		salutation.valid();
		PDFBuilder.addSpaceParagraphSpace();
	}
}

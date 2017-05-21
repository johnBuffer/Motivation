import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.text.Document;

import org.apache.pdfbox.pdmodel.PDDocument;

public class ContactPanel extends JPanel implements TextProvider
{
	private ScrollTextPane address, companyAddress;
	
	public ContactPanel()
	{
		address = new ScrollTextPane();
		companyAddress = new ScrollTextPane();
		
		address.setComponentSize(200, 80);
		address.setBorder(BorderFactory.createTitledBorder("Vos coordonnées"));
		
		companyAddress.setComponentSize(200, 80);
		companyAddress.setBorder(BorderFactory.createTitledBorder("Coordonnées entrprise"));
		
		this.setLayout(new BorderLayout());
		this.add(address, BorderLayout.WEST);
		this.add(companyAddress, BorderLayout.EAST);
	}

	@Override
	public void pushInDoc(PDDocument doc) {
		// TODO Auto-generated method stub

		PDFBuilder.pushTextToDoc(doc, address.getLines(), PDFBuilder.Alignment.LEFT);
		address.valid();
		PDFBuilder.pushTextToDoc(doc, companyAddress.getLines(), PDFBuilder.Alignment.RIGHT);
		companyAddress.valid();
		PDFBuilder.addSpaceParagraphSpace();
	}

	public void setAddressText(String str) {
		// TODO Auto-generated method stub
		address.setText(str);
	}
	
	public void setCompanyAddressText(String str) {
		// TODO Auto-generated method stub
		companyAddress.setText(str);
	}
}

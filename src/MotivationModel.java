import java.io.File;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class MotivationModel {
	
	private String address;
	private String companyAddress;
	private String datePlace;
	private String object;
	private String salutation;
	private String introduction;
	private String secondParagraph;
	private String conclusion;
	private String bye;
	private String signature;
	
	public MotivationModel()
	{
		address = new String();
		companyAddress = new String();
		datePlace = new String();
		object = new String();
		salutation = new String();
		introduction = new String();
		secondParagraph = new String();
		conclusion = new String();
		bye = new String();
		signature = new String();
	}
	
	public void loadFromXML(File file)
	{
		SAXBuilder sxb = new SAXBuilder();
		org.jdom2.Document document = null;
		try
		{
			document = sxb.build(file);
		}
		catch(Exception e){}

		Element racine = document.getRootElement();
		
		this.address = this.getChidText(racine, "Address");
		this.companyAddress = this.getChidText(racine, "CompanyAddress");
		this.datePlace = this.getChidText(racine, "DatePlace");
		this.object = this.getChidText(racine, "Object");
		this.salutation = this.getChidText(racine, "Salutation");
		this.introduction = this.getChidText(racine, "Introduction");
		this.secondParagraph = this.getChidText(racine, "SecondParagraph");
		this.conclusion = this.getChidText(racine, "Conclusion");
		this.bye = this.getChidText(racine, "Bye");
		this.signature = this.getChidText(racine, "Signature");
	}
	
	private String getChidText(Element e, String childName)
	{
		Element child = e.getChild(childName);
		if (child != null)
		{
			return child.getText();
		}
		
		return "";
	}

	public String getAddress() {
		return address;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public String getDatePlace() {
		return datePlace;
	}

	public String getObject() {
		return object;
	}

	public String getSalutation() {
		return salutation;
	}

	public String getIntroduction() {
		return introduction;
	}

	public String getSecondParagraph() {
		return secondParagraph;
	}

	public String getConclusion() {
		return conclusion;
	}

	public String getBye() {
		return bye;
	}

	public String getSignature() {
		return signature;
	}
}

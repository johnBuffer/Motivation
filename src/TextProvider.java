import org.apache.pdfbox.pdmodel.PDDocument;

public interface TextProvider {
	
	void pushInDoc(PDDocument doc);
}

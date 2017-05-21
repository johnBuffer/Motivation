import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;

public class PDFBuilder {
	
	static enum Alignment 
	{
		LEFT,
		RIGHT,
		JUSTIFIED
	};
	
	private static int yLine = 0;
	
	static void reset()
	{
		PDFBuilder.yLine = 0;
	}
	
	static void addSpaceParagraphSpace()
	{
		PDFBuilder.yLine += 15;
	}
	
	static void addSpaceParagraphSpace(int n)
	{
		PDFBuilder.yLine += 15*n;
	}

	static void pushTextToDoc(PDDocument doc, Vector<String> text, PDFBuilder.Alignment alignment)
	{	
		try
		{
			if (text.size() == 0)
				return;
			
			//Setting the font to the Content stream
			PDFont font = PDTrueTypeFont.loadTTF(doc, new File("Arial.ttf"));

			int fontSize = 11;
			PDPage page = doc.getPage(0);
			
			float marginH = 60;
			float marginV = 60;
			
			float pageWidth = page.getMediaBox().getWidth()-2*marginH;
			float pageHeight = page.getMediaBox().getHeight()-marginV;
			
			PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true);
			
			contentStream.setFont(font, fontSize);
			contentStream.setLeading(0);
			
			for (int i=0; i<text.size(); i++)
			{
				String str = text.get(i);
				
				if (str.length() > 0)
				{
					contentStream.beginText();
					float width = font.getStringWidth(str) / 1000 * fontSize;
					while (width > pageWidth)
					{
						int lastSpacePos = str.lastIndexOf(" ");
						String lastWord = str.substring(lastSpacePos);
						str = str.substring(0, lastSpacePos);
						
						if (i < text.size()-1)
							text.set(i+1, lastWord+text.get(i+1));
						else
							text.add(lastWord);
						
						width = font.getStringWidth(str)/1000*fontSize;
					}
					
					if (str.charAt(0) == ' ')
						str = str.substring(1);
					
					if (alignment == PDFBuilder.Alignment.JUSTIFIED && i < text.size()-1)
					{
						contentStream.newLineAtOffset(marginH, pageHeight-yLine);
						PDFBuilder.writeLine(str, pageWidth, font, fontSize, contentStream);
					}
					else 
					{
						if (alignment == PDFBuilder.Alignment.RIGHT)
							contentStream.newLineAtOffset(marginH+pageWidth-width, pageHeight-yLine);	
						else
							contentStream.newLineAtOffset(marginH, pageHeight-yLine);
	
						contentStream.showText(str);
					}
					
					contentStream.endText();
					
					yLine += fontSize+2;
				}
			}
			
			contentStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	static private void writeLine(String str, float pageWidth, PDFont font, int fontSize, PDPageContentStream stream) throws IOException
	{
		String[] words = str.split(" ");
		float brutLineWidth = 0;
		for (String s : words)
		{
			brutLineWidth += font.getStringWidth(s) / 1000 * fontSize;
		}
		
		float spaceWidth = words.length > 1 ? (pageWidth-brutLineWidth)/(float) (words.length-1) : 1;
				
		for (String s : words)
		{
			float wordWidth = font.getStringWidth(s) / 1000 * fontSize;
			stream.showText(s);
			stream.newLineAtOffset(wordWidth + spaceWidth, 0);
		}
	}
}

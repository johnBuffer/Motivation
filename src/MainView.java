import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class MainView extends JFrame implements ActionListener
{
	private JPanel mainContainer, letter;
	private ContactPanel contact;
	private Header header;
	private Body body;
	private Footer footer;

	private JButton generateButton;
	private JButton openExplorer;
	private JToolBar toolBar;
	private JProgressBar progressBar;
	
	MotivationModel model;

	public MainView()
	{
		super("Motivation");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {}
		
		this.model = new MotivationModel();

		mainContainer = new JPanel();
		mainContainer.setLayout(new BorderLayout());

		letter = new JPanel();
		letter.setLayout(new BorderLayout());

		contact = new ContactPanel();
		header = new Header();
		body = new Body();
		footer = new Footer();

		toolBar = new JToolBar();
		toolBar.setFloatable(false);

		generateButton = new JButton("Générer");
		generateButton.addActionListener(this);

		openExplorer = new JButton("Ouvrir modèle");
		openExplorer.addActionListener(this);
		toolBar.add(openExplorer);
		
		progressBar = new JProgressBar(0, 4);
		
		JPanel generatePanel = new JPanel();
		generatePanel.setLayout(new GridLayout(2, 1));
		generatePanel.add(progressBar);
		generatePanel.add(generateButton);

		letter.add(header, BorderLayout.NORTH);
		letter.add(body, BorderLayout.CENTER);

		mainContainer.add(contact, BorderLayout.NORTH);
		mainContainer.add(letter, BorderLayout.CENTER);
		mainContainer.add(footer, BorderLayout.SOUTH);
		mainContainer.setBorder(new EmptyBorder(0, 0, 10, 0));

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(mainContainer, BorderLayout.CENTER);
		this.getContentPane().add(generatePanel, BorderLayout.SOUTH);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);

		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == this.generateButton)
		{
			PDFBuilder.reset();
			PDDocument document = new PDDocument();

			PDPage firstpage = new PDPage();
			document.addPage(firstpage);
			
			Thread t = new Thread()
			{
				public void run()
				{
					progressBar.setValue(0);
					contact.pushInDoc(document);
					progressBar.setValue(1);
					header.pushInDoc(document);
					progressBar.setValue(2);
					body.pushInDoc(document);
					progressBar.setValue(3);
					footer.pushInDoc(document);
					
					
					System.out.println("Content added");
					
					try {
						document.save(new File("new.pdf"));
						document.close();
						progressBar.setValue(4);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			
			t.run();
			
			System.out.println("ok");

		}
		else if (e.getSource() == this.openExplorer)
		{
			JFileChooser fc = new JFileChooser();
			//In response to a button click:
			int returnVal = fc.showOpenDialog(openExplorer);
			File file = fc.getSelectedFile();
			System.out.println("File : " + file.getPath());
			
			this.model.loadFromXML(file);
			this.updateViewFromModel();
		}
	}
	
	private void updateViewFromModel()
	{
		this.contact.setAddressText(model.getAddress());
		this.contact.setCompanyAddressText(model.getCompanyAddress());
		
		this.header.setLocalization(model.getDatePlace());
		this.header.setObject(model.getObject());
		this.header.setSalutation(model.getSalutation());
		
		this.body.setIntroduction(model.getIntroduction());
		this.body.setSecondParagraph(model.getSecondParagraph());
		
		this.footer.setEnd(model.getConclusion());
		this.footer.setBye(model.getBye());
		this.footer.setSignature(model.getSignature());
	}
}

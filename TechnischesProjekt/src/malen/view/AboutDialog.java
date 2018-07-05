package malen.view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import malen.buisnesslogic.PaddingService;

public class AboutDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6866089581215355741L;

	public AboutDialog(JFrame frame, String title, boolean modal) {
		super(frame, title, modal);
		
		setLayout(new BorderLayout());	
		
		JPanel boxPanel = new JPanel();
		boxPanel.setLayout(new BoxLayout(boxPanel,  BoxLayout.Y_AXIS));
		boxPanel.setBackground(Color.WHITE);
		boxPanel.setOpaque(true);
		boxPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		JTextArea info = new JTextArea(
				"Mit PaintCoos kannst du zeichnen, ein Koordinatensystem, Formen oder Bilder einfügen. \n"
				+ "PaintCoos berechnet die jeweiligen Koordinaten und speichert sie normiert auf 1 ab.\n"
				+ "Die Breite und Höhe ist auf das \"Etch a sketch\"-Board angepasst.");
		info.setAlignmentX(LEFT_ALIGNMENT);
		info.setEditable(false);
		
		JLabel gameName = new JLabel("PaintCoos");
		JLabel versionName = new JLabel("Version: 1.3");
		JLabel authorName = new JLabel("Author: Stephanie Boehning");
		JLabel uniName = new JLabel("Hochschule fuer Angewandte Wissenschaften Hamburg");
		JLabel email = new JLabel("Email: stephanie.boehning@haw-hamburg.de");
		JLabel javaVers = new JLabel("Java Version 8");

//		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
		Font font = new Font(getFont().getFontName(), Font.PLAIN, 16);
		boxPanel.setFont(font);
		gameName.setFont(font.deriveFont(Font.BOLD));
		versionName.setFont(font);
		authorName.setFont(font);
		uniName.setFont(font);
		email.setFont(font);
		javaVers.setFont(font);
		info.setFont(font);
		
		boxPanel.add(gameName);
		boxPanel.add(Box.createVerticalStrut(30));
		boxPanel.add(info);
		boxPanel.add(Box.createVerticalStrut(30));
		boxPanel.add(versionName);
		boxPanel.add(Box.createVerticalStrut(5));
		boxPanel.add(authorName);
		boxPanel.add(Box.createVerticalStrut(5));
		boxPanel.add(email);
		boxPanel.add(Box.createVerticalStrut(5));
		boxPanel.add(uniName);
		boxPanel.add(Box.createVerticalStrut(45));
		boxPanel.add(javaVers);

		add(PaddingService.createPaddings(40,  true), BorderLayout.NORTH);
		add(PaddingService.createPaddings(40,  true), BorderLayout.SOUTH);
		add(PaddingService.createPaddings(40,  false), BorderLayout.WEST);
		add(PaddingService.createPaddings(40,  false), BorderLayout.EAST);
		add(boxPanel, BorderLayout.CENTER);
		
		
		setResizable(false);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}


}

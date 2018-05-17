package malen;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		
		JLabel gameName = new JLabel("PaintCoos");
		JLabel versionName = new JLabel("Version: 1.1");
		JLabel authorName = new JLabel("Author: Stephanie Boehning");
		JLabel uniName = new JLabel("Hochschule fuer Angewandte Wissenschaften Hamburg");
		JLabel javaVers = new JLabel("Java Version 8");

		
		Font font = new Font(getFont().getFontName(), Font.PLAIN, 16);
		gameName.setFont(font.deriveFont(Font.BOLD));
		versionName.setFont(font);
		authorName.setFont(font);
		uniName.setFont(font);
		javaVers.setFont(font);
		
		boxPanel.add(gameName);
		boxPanel.add(Box.createVerticalStrut(10));
		boxPanel.add(versionName);
		boxPanel.add(Box.createVerticalStrut(5));
		boxPanel.add(authorName);
		boxPanel.add(Box.createVerticalStrut(5));
		boxPanel.add(uniName);
		boxPanel.add(Box.createVerticalStrut(45));
		boxPanel.add(javaVers);

		add(createPaddings(40,  true), BorderLayout.NORTH);
		add(createPaddings(40,  true), BorderLayout.SOUTH);
		add(createPaddings(40,  false), BorderLayout.WEST);
		add(createPaddings(40,  false), BorderLayout.EAST);
		add(boxPanel, BorderLayout.CENTER);
		
		
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * A box for padding is created, so you can use the box for the WEST, EAST, SOUTH and NORTH of the 
	 * BorderLayout. The Background color is set to white.
	 * 
	 * @param number for padding/distance
	 * @param vertical if vertical is true a vertical strut is created else a horizonal strut
	 * @return Box with a horizontal or a vertical strut
	 */
	private Box createPaddings (int number, boolean vertical) {
		
		Box box = new Box(vertical? BoxLayout.Y_AXIS : BoxLayout.X_AXIS);
		box.setBackground(Color.WHITE);
		box.setOpaque(true);
		box.add(vertical? Box.createVerticalStrut(number) : Box.createHorizontalStrut(number));

		return box;
	}
}

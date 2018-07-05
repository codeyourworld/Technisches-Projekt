package malen.view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import malen.buisnesslogic.PaddingService;

public class CoordinatesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Labels um den minimalen/maximalen x/y Wert abzufragen
	private JTextField minValX = new JTextField(12);
	private JTextField maxValX = new JTextField(12);
	private JTextField minValY = new JTextField(12);
	private JTextField maxValY = new JTextField(12);

	//soll ein Gitter im Koordinatensystem gezeichnet werden
	private JCheckBox coorsLines = new JCheckBox("Koordinatenlinien");
	
	//welchen Abstand sollen die Linien im Koordinatengitter haben
	private JTextField distLineX = new JTextField(12);
	private JTextField distLineY = new JTextField(12);
	
	//save Button, um die Eingaben abzuspeichern
	private JButton saveBtn = new JButton("Ok");
	
	
	public CoordinatesDialog(JFrame frame, String title, boolean modal) {
		super(frame, title, modal);

		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.setBackground(Color.WHITE);
	
		//Text zum Anzeigen, damit der Benutzer weiﬂ was er eingeben soll
		JLabel minXLabel = new JLabel("Minimaler x-Wert:");
		JLabel maxXLabel = new JLabel("Maximaler x-Wert:");
		JLabel minYLabel = new JLabel("Minimaler y-Wert:");
		JLabel maxYLabel = new JLabel("Maximaler y-Wert:");
		JLabel numLinesXLabel = new JLabel("Abstand zwischen den Linien auf der x-Koordinate");
		JLabel numLinesYLabel = new JLabel("Abstand zwischen den Linien auf der y-Koordinate");
		
		//fuege die Label zum Panel hinzu
		labelPanel.add(minXLabel);
		labelPanel.add(Box.createVerticalStrut(20));
		labelPanel.add(maxXLabel);
		labelPanel.add(Box.createVerticalStrut(20));
		labelPanel.add(minYLabel);
		labelPanel.add(Box.createVerticalStrut(20));
		labelPanel.add(maxYLabel);
		labelPanel.add(Box.createVerticalStrut(20));
		labelPanel.add(Box.createVerticalStrut(20));
		labelPanel.add(coorsLines);
		labelPanel.add(Box.createVerticalStrut(20));
		labelPanel.add(numLinesXLabel);
		labelPanel.add(Box.createVerticalStrut(20));
		labelPanel.add(numLinesYLabel);
		labelPanel.add(Box.createVerticalStrut(20));
		labelPanel.add(Box.createVerticalStrut(20));	//hier ist im anderen Panel der SaveButton

		//setze bei allen Componenten den Font, damit es besser aussieht
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
		minXLabel.setFont(font);
		maxXLabel.setFont(font);
		minYLabel.setFont(font);
		maxYLabel.setFont(font);
		numLinesXLabel.setFont(font);
		numLinesYLabel.setFont(font);
		minValX.setFont(font);
		maxValX.setFont(font);
		minValY.setFont(font);
		maxValY.setFont(font);
		distLineX.setFont(font);
		distLineY.setFont(font);
		saveBtn.setFont(font);
		coorsLines.setFont(font);
		
		coorsLines.setBackground(Color.WHITE);
		coorsLines.setSelected(true);

		//Panel fuer die eingegebenen Werte
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
		namePanel.setBackground(Color.WHITE);
		namePanel.add(minValX);		
		namePanel.add(Box.createVerticalStrut(20));
		namePanel.add(maxValX);
		namePanel.add(Box.createVerticalStrut(20));
		namePanel.add(minValY);
		namePanel.add(Box.createVerticalStrut(20));
		namePanel.add(maxValY);
		namePanel.add(Box.createVerticalStrut(20));
		namePanel.add(Box.createVerticalStrut(20));
		namePanel.add(Box.createVerticalStrut(20));	//beim anderen Panel sitzt an dieser Stelle die Checkbox
		namePanel.add(Box.createVerticalStrut(20));
		namePanel.add(distLineX);
		namePanel.add(Box.createVerticalStrut(20));
		namePanel.add(distLineY);
		namePanel.add(Box.createVerticalStrut(20));
		namePanel.add(saveBtn);	
		
		//fuege alle Panel zum Frame hinzu
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
		centerPanel.add(labelPanel);
		centerPanel.add(Box.createHorizontalStrut(20)); 
		centerPanel.add(namePanel);
		centerPanel.setBackground(Color.WHITE);
		
		//Abstaende fuer Aussen/Padding
		add(PaddingService.createPaddings(40, true), BorderLayout.NORTH);
		add(PaddingService.createPaddings(40, true), BorderLayout.SOUTH);
		add(PaddingService.createPaddings(40, false), BorderLayout.EAST);
		add(PaddingService.createPaddings(40, false), BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		
		setResizable(false);

		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	

	public JTextField getMinValX() {
		return minValX;
	}
	
	public JTextField getMaxValX() {
		return maxValX;
	}
	
	public JTextField getMinValY() {
		return minValY;
	}
	
	public JTextField getMaxValY() {
		return maxValY;
	}
	
	public JTextField getDistLineX() {
		return distLineX;
	}
	
	public JTextField getDistLineY() {
		return distLineY;
	}
	
	public JCheckBox getCoorsLines() {
		return coorsLines;
	}
	
	public JButton getSaveBtn() {
		return saveBtn;
	}
}

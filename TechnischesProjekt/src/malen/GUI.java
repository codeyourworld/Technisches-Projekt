package malen;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PaintPanel panel;
	private JMenuItem saveItem = new JMenuItem("Save");
	private JMenuItem saveAsItem = new JMenuItem("Save as");
	private JMenuItem finishItem = new JMenuItem("Finish");
	private JMenuItem newItem = new JMenuItem("new File");
	private JMenuItem openItem = new JMenuItem("open");
	public GUI(Koordinaten koordinaten) {
		panel = new PaintPanel(koordinaten.getPoints());	
		add(panel, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
	
		fileMenu.add(newItem);
		fileMenu.add(openItem);		
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(finishItem);
		setTitle("Paint a picture");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setVisible(true);
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int width = (int) screenSize.getWidth();
//		int height = (int) screenSize.getHeight();
//		setSize(width, height);
	}
	
	public PaintPanel getPaintPanel() {
		return panel;
	}
	
	public JMenuItem getSaveItem() {
		return saveItem;
	}

	public JMenuItem getSaveAsItem() {
		return saveAsItem;
	}
	
	public JMenuItem getNewItem() {
		return newItem;
	}
	
	public JMenuItem getFinishItem() {
		return finishItem;
	}
	
	public JMenuItem getOpenItem() {
		return openItem;
	}
}

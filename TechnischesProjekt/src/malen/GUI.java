package malen;

import java.awt.BorderLayout;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

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
	private JMenuItem backItem = new JMenuItem("back");

	private JMenuItem addCoordItem = new JMenuItem("Add coordinates");
	private JMenuItem aboutItem = new JMenuItem("About PaintCoos");

	public GUI(Koordinaten koordinaten) {
		panel = new PaintPanel(koordinaten.getPoints());	
		add(panel, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		JMenu aboutMenu = new JMenu("About");
		menuBar.add(aboutMenu);
		

		aboutMenu.setMnemonic('A');
		editMenu.setMnemonic('E');
		fileMenu.setMnemonic('F');
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		finishItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		backItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));

		//file menu
		fileMenu.add(newItem);
		fileMenu.add(openItem);		
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(finishItem);
		fileMenu.add(backItem);
		
		//edit menu
		editMenu.add(addCoordItem);
		
		//about menu
		aboutMenu.add(aboutItem);
		
		
		setTitle("PaintCoos");
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
	
	public JMenuItem getBackItem() {
		return backItem;
	}
	
	public JMenuItem getAddCoordItem() {
		return addCoordItem;
	}
	
	public JMenuItem getAboutItem() {
		return aboutItem;
	}
}

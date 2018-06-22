package malen.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Shape;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import malen.buisnesslogic.PaddingService;
import malen.model.Koordinaten;

public class PaintFrame extends JFrame {

	public static final int HEIGHT = 5*113;
	public static final int WIDTH = 5*160;
	private static final Color color = new Color(20, 200, 200);
	
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
	private JMenuItem shapeItem = new JMenuItem("Add a Shape");
	private JMenuItem picItem = new JMenuItem("Paint a picture");

	private JMenuItem calibrateItem = new JMenuItem("Calibrate");
	private JMenuItem aboutItem = new JMenuItem("About PaintCoos");

	public PaintFrame(Koordinaten koordinaten) {
		add(PaddingService.createPaddings(10, true, color), BorderLayout.NORTH);
		add(PaddingService.createPaddings(10, true, color), BorderLayout.SOUTH);
		add(PaddingService.createPaddings(10, false, color), BorderLayout.WEST);
		add(PaddingService.createPaddings(10, false, color), BorderLayout.EAST);
		panel = new PaintPanel(koordinaten.getPoints());	
		add(panel, BorderLayout.CENTER);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		

		helpMenu.setMnemonic('H');
		editMenu.setMnemonic('E');
		fileMenu.setMnemonic('F');
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		finishItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		backItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		picItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		addCoordItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
		calibrateItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		shapeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		
		//file menu
		fileMenu.add(newItem);
		fileMenu.add(openItem);		
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(finishItem);
		fileMenu.add(backItem);
		
		//edit menu
		editMenu.add(addCoordItem);
		editMenu.add(shapeItem);
		editMenu.add(picItem);
		
		//help menu
		helpMenu.add(calibrateItem);
		helpMenu.add(aboutItem);
		
		
		setTitle("PaintCoos");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setSize(WIDTH, 2*HEIGHT-getContentPane().getSize().height);
		setVisible(true);
		System.out.println("panel" + panel.getSize());

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
	
	public JMenuItem getPicItem() {
		return picItem;
	}
	
	public JMenuItem getCalibrateItem() {
		return calibrateItem;
	}
	
	public JMenuItem getShapeItem() {
		return shapeItem;
	}
}

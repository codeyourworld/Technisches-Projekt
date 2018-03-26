package malen;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

public class Controller {

	private GUI gui;
	private Koordinaten koordinaten;
	private boolean isDragged = false;
	
	public Controller(GUI gui, Koordinaten koordinaten) {
		this.gui = gui;
		this.koordinaten = koordinaten;
		reset();
	}

	private void reset () {
		gui.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if (isDragged) {
					isDragged = !isDragged;
					koordinaten.addPoint(-1, -1);							
				}				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
				koordinaten.addPoint(e.getXOnScreen(), e.getYOnScreen());							
				isDragged = true;
			}
		});
		
		gui.getSaveItem().addActionListener(l -> {
			//TODO write into file
			//Save as JPEG and Data
			File file = new File(System.getProperty("user.home"), "//Desktop//daten");
			File tmp = new File(file.getAbsolutePath() + ".txt");
			int index = 1;
			while (tmp.exists()) {
				System.out.println(tmp.getAbsolutePath() + " existiert");
				tmp = new File(file + String.valueOf(index) + ".txt");
				index++;
			}
			System.out.println(tmp.getAbsolutePath() + " existiert nicht");
			file = tmp;
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(file.getAbsolutePath());
			saveData(file.getAbsolutePath());
		});

		gui.getSaveAsItem().addActionListener(l -> {
			JFileChooser fileChooser = new JFileChooser();
			int ret = fileChooser.showSaveDialog(gui);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				System.out.println(file.getAbsolutePath());
				
				saveData(file.getAbsolutePath());
				
				//TODO Save as JPEG and Data
			}
		});
		
		gui.getOpenItem().addActionListener(l -> {
			koordinaten.removeAllPoint();
			File file = new File(System.getProperty("user.home"), "//Desktop");
			JFileChooser fileChooser = new JFileChooser(file.getAbsolutePath());
			int ret = fileChooser.showOpenDialog(gui);
			if (ret == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				String str = IOService.readFile(file.getAbsolutePath());
				
				System.out.println("\n\n\n -------LESE DATEI -----");
				System.out.println(str);
				System.out.println("\n\n\n -------ENDE LESE DATEI -----");
				String [] points = str.split("\n");
				for (String p : points) {
					String coordinates [] = p.split(",");
					try {
						int x = Integer.valueOf(coordinates[0]);
						int y = Integer.valueOf(coordinates[1]);
						this.koordinaten.addPoint(x, y);
						
					} catch (NumberFormatException e) {
						System.err.println("ERROR, Wrong number: " + p);
					}
				}
				
			}

		});
		
		gui.getFinishItem().addActionListener(l -> {
			gui.setVisible(false);
			gui.dispose();			
		});

		gui.getNewItem().addActionListener(l -> {
			gui.dispose();
			koordinaten = new Koordinaten();
			gui = new GUI(koordinaten);
			reset();
		});
		
		koordinaten.addObserver(gui.getPaintPanel());		
	}
	
	private void saveData (String filename) {
		for (Point p : koordinaten.getPoints())
			IOService.writeLine(p.toString(), filename);
	}
}

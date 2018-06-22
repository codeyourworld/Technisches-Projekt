package malen.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import malen.buisnesslogic.IOService;
import malen.buisnesslogic.Writer;
import malen.model.CoordinateSystemCreater;
import malen.model.GreyToLines;
import malen.model.IData;
import malen.model.Koordinaten;
import malen.model.Point;
import malen.view.AboutDialog;
import malen.view.CoordinatesDialog;
import malen.view.PaintFrame;

public class Controller extends Observable{

	private static final String OFFSET_FILE_NAME = "offset.txt";
	private PaintFrame gui;
	private Koordinaten koordinaten;
	private boolean isDragged = false;
	private MouseMotionListener mListener;
	private Point offset = new Point(0, 0);
	
	public Controller(PaintFrame gui, Koordinaten koordinaten) {
		this.gui = gui;
		this.koordinaten = koordinaten;
		reset();
		
	}

	private void reset () {
		koordinaten.setMaxY(gui.getPaintPanel().getSize().height);

		Object object = IOService.readObject(OFFSET_FILE_NAME);
		if(object instanceof Point) {
			offset = (Point) object;
		}
		
		//add painting points to picture
		mListener = new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent e) {
				if (isDragged) {
					isDragged = !isDragged;
					if (koordinaten.getPoints().get(koordinaten.getPoints().size()-1).getX() > 0)	
						koordinaten.setBreak();							
				}				
				setChanged();
				notifyObservers(new Point(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY()));
			}
			
			public void mouseDragged(MouseEvent e) {
				
				koordinaten.addPoint(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY());							
				isDragged = true;
			}
		};
		
		gui.addMouseMotionListener(mListener);

		// ---------------- FILE MENU --------------------
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
				e.printStackTrace();
			}
			System.out.println(file.getAbsolutePath());
			Writer writer = new Writer();
			writer.saveData(file.getAbsolutePath(), koordinaten.getPoints());
			
		});

		gui.getSaveAsItem().addActionListener(l -> {
			JFileChooser fileChooser = new JFileChooser();
			int ret = fileChooser.showSaveDialog(gui);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				System.out.println(file.getAbsolutePath());
				
				Writer writer = new Writer();
				writer.saveData(file.getAbsolutePath(), koordinaten.getPoints());
				
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
				//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				for (String p : points) {
					String coordinates [] = p.split(",");
					try {
						float x = Float.valueOf(coordinates[0])* (float)PaintFrame.WIDTH;
						float y = Float.valueOf(coordinates[1])*(float)PaintFrame.HEIGHT;
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
			gui = new PaintFrame(koordinaten);
			reset();
		});
	
		gui.getBackItem().addActionListener(l -> {
			koordinaten.deleteLastLine();
			
		});

		// ---------------- EDIT MENU --------------------
		gui.getAddCoordItem().addActionListener(l -> {
			coordinates();
		});
		
		gui.getPicItem().addActionListener(l -> {

			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showOpenDialog(gui) == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				GreyToLines lines = new GreyToLines(file.getAbsolutePath());
				lines.createCoords();
				koordinaten.addPoint(Koordinaten.PICTURE, Koordinaten.PICTURE);
				koordinaten.addList(lines.getPoints());
				gui.removeMouseMotionListener(mListener);
			}
		});
		
		// ---------------- HELP MENU --------------------
		gui.getAboutItem().addActionListener(l -> {
			new AboutDialog(gui, "About", true);
		});
//		public static int showOptionDialog(Component parentComponent,
//                Object message,
//                String title,
//                int optionType,
//                int messageType,
//                Icon icon,
//                Object[] options,
//                Object initialValue)
//         throws HeadlessException

		gui.getCalibrateItem().addActionListener(l -> {
			int value = JOptionPane.showOptionDialog(null, 
					"Die bisherige Arbeit wird beim Calibrieren gelöscht. OK?", 
					"Achtung", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null, null, null);
			if(value == JOptionPane.YES_OPTION) {
				new CalibrationController(this, gui);		
			}
			
		});
		
		koordinaten.addObserver(gui.getPaintPanel());		
		addObserver(gui.getPaintPanel());
	}
	
	private void coordinates () {
		CoordinatesDialog dialog = new CoordinatesDialog(gui, "Add Coordinates to Picture", false);
		dialog.getCoorsLines().addActionListener(l -> {
			if(dialog.getCoorsLines().isSelected()) {
				dialog.getDistLineX().setEnabled(true);
				dialog.getDistLineY().setEnabled(true);
			} else {
				dialog.getDistLineX().setEnabled(false);
				dialog.getDistLineY().setEnabled(false);				
			}
		});
		
		dialog.getSaveBtn().addActionListener(l -> {
			try {
				int minX = Integer.valueOf(dialog.getMinValX().getText());
				int maxX = Integer.valueOf(dialog.getMaxValX().getText());
				int minY = Integer.valueOf(dialog.getMinValY().getText());
				int maxY = Integer.valueOf(dialog.getMaxValY().getText());
				boolean lines = dialog.getCoorsLines().isSelected();
				IData data;  
				if (lines) {
					int anzahlXVal = Integer.valueOf(dialog.getDistLineX().getText());
					int anzahlYVal = Integer.valueOf(dialog.getDistLineY().getText());
					data = new CoordinateSystemCreater(minX, maxX, minY, maxY, lines,
							anzahlXVal, anzahlYVal, (int)gui.getSize().getWidth(), (int)gui.getSize().getHeight());
					
				} else {
					data = new CoordinateSystemCreater(minX, maxX, minY, maxY, lines,
							0, 0, (int)gui.getSize().getWidth(), (int)gui.getSize().getHeight());
					
				}
				data.start();
				koordinaten.addList(data.getCoordinates());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
			dialog.dispose();
		});
	}

	public void setOffset(Point offset) {
		this.offset = offset;
		System.out.println("Offset = " + offset);
		IOService.writeObject(offset, OFFSET_FILE_NAME);
		gui.dispose();
		koordinaten = new Koordinaten();
		gui = new PaintFrame(koordinaten);
		reset();
		

	}
	
}

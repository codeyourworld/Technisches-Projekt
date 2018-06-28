package malen.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JOptionPane;

import malen.buisnesslogic.IOService;
import malen.model.Coordinates;
import malen.model.Point;
import malen.model.Shapes;
import malen.view.AboutDialog;
import malen.view.PaintFrame;

public class Controller extends Observable{

	private static final String OFFSET_FILE_NAME = "offset.txt";
	private PaintFrame view;
	private Coordinates coordinates;
	private boolean isDragged = false;
	private MouseMotionListener mmListener;
	private MouseAdapter mouseAdapter;
	private Point offset = new Point(0, 0);
	private ShapeController shapeController = null;
	
	public Controller(PaintFrame view, Coordinates coordinates) {
		this.view = view;
		this.coordinates = coordinates;
		reset();
		
	}

	private void reset () {
		coordinates.setMaxY(view.getPaintPanel().getSize().height);

		readOffset();
		
		//add painting points to picture
		mmListener = new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent e) {
				if (isDragged) {
					isDragged = !isDragged;
					if (coordinates.getPoints().get(coordinates.getPoints().size()-1).getX() > 0)	
						coordinates.setBreak();							
				}				
				setChanged();
				notifyObservers(new Point(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY()));
			}
			
			public void mouseDragged(MouseEvent e) {
				if (shapeController == null) {
					coordinates.addPoint(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY());							
					isDragged = true;
				}
			}
		};
		
		mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				System.out.println("Click");
				if (shapeController == null) {
					coordinates.addPoint(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY());							
					isDragged = true;
					System.err.println("shapeController = null");
				}
				else if (shapeController != null && shapeController.getModel().getShape() != Shapes.None){
					if(shapeController.getModel().getStartPoint() != null) {
						shapeController.getModel().setEndPoint(new Point(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY()));
					} else {
						shapeController.getModel().setStartPoint(new Point(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY()));						
					}
				}
				
				if(shapeController != null ){
					System.out.println("shape : " + shapeController.getModel().getShape() + ", startpoint: " + shapeController.getModel().getStartPoint());
				}
			}
		};
		
		view.addMouseMotionListener(mmListener);
		view.addMouseListener(mouseAdapter);

		// ---------------- FILE MENU --------------------
		view.getSaveItem().addActionListener(l -> {
			FileService.save(coordinates);
			
		});

		view.getSaveAsItem().addActionListener(l -> {
			FileService.saveAs(view, coordinates);
		});
		
		view.getOpenItem().addActionListener(l -> {
			FileService.open(coordinates, view);
		});
		
		view.getFinishItem().addActionListener(l -> {
			view.setVisible(false);
			view.dispose();			
		});

		view.getNewItem().addActionListener(l -> {
			newPaint();
		});
	
		view.getBackItem().addActionListener(l -> {
			coordinates.deleteLastLine();
			
		});

		// ---------------- EDIT MENU --------------------
		view.getAddCoordItem().addActionListener(l -> {
			EditService.coordinates(view, coordinates);
		});
		
		view.getPicItem().addActionListener(l -> {
			EditService.addPicture(view, coordinates, mmListener);
			
		});
		
		view.getShapeItem().addActionListener(l -> {
//			EditService.addShape(view, offset, this, coordinates.getPoints().get(coordinates.getPoints().size() - 1));
			ShapeDialog shapeDialog = new ShapeDialog(view, "Add Shapes");
			shapeDialog.addWindowListener(new WindowAdapter() {
				
				@Override
				public void windowClosing(WindowEvent e) {
					disableShapes();
					
				}
			});
			shapeController = new ShapeController(shapeDialog, offset, this, coordinates.getPoints().get(coordinates.getPoints().size() - 1));
			shapeController.getModel().addObserver(view.getPaintPanel());
			//view.setAlwaysOnTop(true);
//			JOptionPane.showConfirmDialog(null, "Bitte wähl eine Form aus und klick in die graphische Oberfläche, um die Form zu zeichnen. "
//					+ "In dem du ein zweites Mal klickst fixierst du die Form. Zum Bestätigen klick auf ok.");
		});
		
		// ---------------- HELP MENU --------------------
		view.getAboutItem().addActionListener(l -> {
			new AboutDialog(view, "About", true);
		});


		view.getCalibrateItem().addActionListener(l -> {
			int value = JOptionPane.showOptionDialog(null, "Die bisherige Arbeit wird beim Calibrieren gelöscht. OK?", 
					"Achtung", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			if(value == JOptionPane.YES_OPTION) {
				new CalibrationController(this, view);		
			}
			
		});
		
		coordinates.addObserver(view.getPaintPanel());		
		addObserver(view.getPaintPanel());
	}
	
	

	public void setOffset(Point offset) {
		this.offset = offset;
		IOService.writeObject(offset, OFFSET_FILE_NAME);
		newPaint();
	}
	
	private void readOffset() {
		Object object = IOService.readObject(OFFSET_FILE_NAME);
		if(object instanceof Point) {
			offset = (Point) object;
		}
	}
	
	private void newPaint(){
		view.dispose();
		coordinates = new Coordinates();
		view = new PaintFrame(coordinates);
		reset();
	}
	
	public void addPoints(ArrayList<Point> points) {
		coordinates.addList(points);
	}

	public void disableShapes() {
		shapeController = null;
		view.getPaintPanel().disableShapes();
	}
}

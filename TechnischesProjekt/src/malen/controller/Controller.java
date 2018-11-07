package malen.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JOptionPane;

import malen.buisnesslogic.IOService;
import malen.model.Coordinates;
import malen.model.Point;
import malen.model.Shapes;
import malen.view.AboutDialog;
import malen.view.EditLineWidthDialog;
import malen.view.PaintFrame;
import malen.view.ShapeDialog;

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
				else {
					//wenn sich der punkt verschieben soll und der Referenzpunkt schon gesetzt ist
					if (shapeController != null && 
						shapeController.getModel().isMoveShape() && 
						shapeController.getModel().isSetMoveResizePoint()) {
							shapeController.getModel().setMousePoint(
									new Point(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY()));
							
							return;
					}

				}
				setChanged();
				notifyObservers(new Point(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY()));
			}
			
			public void mouseDragged(MouseEvent e) {
				if (shapeController != null && shapeController.getModel().getShape() != Shapes.None){
//					if(shapeController.getModel().isMoveShape() && shapeController.getModel().getMoveResizePoint() != null) {
//						shapeController.getModel().setMousePoint(new Point(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY()));
//					}
//					if(shapeController.getModel().isMoveShape() && shapeController.getModel().getMoveResizePoint() == null) {
//						shapeController.getModel().setMoveResizePoint(new Point(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY()));
//					}
//					return;
				}

				else if (shapeController == null) {
					coordinates.addPoint(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY());							
					isDragged = true;
				}
			}
		};
		
		mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
				//if the user starts to paint it starts with a click and then the mouse is dragged
				if (shapeController == null) {
					coordinates.addPoint(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY());							
					isDragged = true;
					
				}

				//if a shape should be painted
				else if (shapeController != null && shapeController.getModel().getShape() != Shapes.None){
					
					//if there is no reference point and a shape should be moved, a reference point is set now
					if(shapeController.getModel().isMoveShape() && !shapeController.getModel().isSetMoveResizePoint()) {
						shapeController.getModel().setMoveResizePoint(new Point(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY()));
						
					}
					
					//if there is a reference point and a shape should be moved, an end point is set now and the movement is finished for now
					else if(shapeController.getModel().isMoveShape() && shapeController.getModel().isSetMoveResizePoint()) {
						shapeController.getModel().setEndPoint(new Point(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY()));
						
					}
					
					//if the user just wants to set a shape
					else if(!shapeController.getModel().isMoveShape()) {
						if(shapeController.getModel().getStartPoint() != null && shapeController.getModel().getEndPoint() == null) {
							shapeController.getModel().setEndPoint(new Point(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY()));
							
						} else if (shapeController.getModel().getStartPoint() == null){
							
							shapeController.getModel().setStartPoint(new Point(e.getXOnScreen() + offset.getX(), e.getYOnScreen() + offset.getY()));						
						}
					}
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
		
		view.getLineWidthItem().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				EditLineWidthDialog lineWidthDialog = new EditLineWidthDialog(view, "Edit line width", false, EditLineWidthDialog.LINE_WIDTH_DIALOG);
				lineWidthDialog.getSaveBtn().addActionListener(e -> {
					view.getPaintPanel().setLineWidth(lineWidthDialog.getLineWidthSlider().getValue());
					lineWidthDialog.dispose();
				});
				lineWidthDialog.getLineWidthSlider().addChangeListener(e -> {
					lineWidthDialog.setWidth();
				}); 
				
			}
		});

		
		view.getAddCoordItem().addActionListener(l -> {
			EditService.coordinates(view, coordinates);
		});
		
		view.getPicItem().addActionListener(l -> {
			EditService.addPicture(view, coordinates, mmListener);
			
		});
		
		view.getShapeItem().addActionListener(l -> {
			ShapeDialog shapeDialog = new ShapeDialog(view, "Add Shapes");
			shapeDialog.addWindowListener(new WindowAdapter() {
				
				@Override
				public void windowClosing(WindowEvent e) {
					disableShapes();
					
				}
			});
			shapeController = new ShapeController(shapeDialog, this);
			shapeController.getModel().addObserver(view.getPaintPanel());
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

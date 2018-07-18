package malen.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import malen.controller.Controller;
import malen.model.Coordinates;
import malen.model.Point;
import malen.model.ShapeModel;
import malen.model.Shapes;

public class PaintPanel extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PICTURE_LINE_WIDTH = 1;
	private ArrayList<Point> points = new ArrayList<>();
	private Point mousePoint = new Point(0, 0);
	
	//this is needed to add shapes
	private Shapes shape = Shapes.None;
	private Point startpoint;
	private Point endpoint;
	private ShapeModel shapeModel = null;
	private int widthDefault = 10;

	public PaintPanel(ArrayList<Point> points) {
		this.points = points;
		setSize(PaintFrame.HEIGHT, PaintFrame.WIDTH);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		setBackground(Color.WHITE);

		int width = widthDefault;
		// paint line with round points between the coordinates next to each other
		for (int i = 0; i < points.size() - 1; i++) {
			g.setColor(Color.BLACK);
			if (points.get(i).getX() == Coordinates.PICTURE) {
				width = PICTURE_LINE_WIDTH;
			}
			if (points.get(i).getX() == Coordinates.BREAK) {
				width = widthDefault;
			}

			g2.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			if (points.get(i).getX() != Coordinates.BREAK && points.get(i + 1).getX() != Coordinates.BREAK)
				g2.draw(new Line2D.Float((float) points.get(i).getX(), (float) points.get(i).getY(),
						(float) points.get(i + 1).getX(), (float) points.get(i + 1).getY()));
			else if (points.get(i).getX() == Coordinates.BREAK && i - 1 >= 0) {
				g2.draw(new Line2D.Float((float) points.get(i - 1).getX(), (float) points.get(i - 1).getY(),
						(float) points.get(i + 1).getX(), (float) points.get(i + 1).getY()));
			} else if (points.get(i + 1).getX() == Coordinates.BREAK && points.size() > i + 2) {
				g2.draw(new Line2D.Float((float) points.get(i).getX(), (float) points.get(i).getY(),
						(float) points.get(i + 2).getX(), (float) points.get(i + 2).getY()));
			}
		}
		
		//paint dashed line from the last painted point to the mousepoint
		if (shape == Shapes.None || startpoint == null) {
			Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
			g2.setStroke(dashed);
			if (!points.isEmpty() && points.get(points.size() - 1).getX() == Coordinates.BREAK) {
				g2.draw(new Line2D.Float((float) points.get(points.size() - 2).getX(),
						(float) points.get(points.size() - 2).getY(), (float) mousePoint.getX(),
						(float) mousePoint.getY()));
			}
		
		} else {//paint shapes if selected
			
			//paint dashed line from the last painted point to the startpoint of the shape
			Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
			g2.setStroke(dashed);
			g.setColor(Color.BLACK);
			if (points.get(points.size() - 1).getX() == Coordinates.BREAK) {
				g2.draw(new Line2D.Float((float) points.get(points.size() - 2).getX(),
						(float) points.get(points.size() - 2).getY(), (float) startpoint.getX(),
						(float) startpoint.getY()));
			}
			
			// paint shape from start point the mouse point
			g2.setColor(Color.GREEN);
			width = widthDefault;
			g2.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			if (shape == Shapes.Line)
				g2.drawLine((int) startpoint.getX(), (int) startpoint.getY(), (int) mousePoint.getX(),
						(int) mousePoint.getY());
			if (shape == Shapes.Rectangle)
				g2.drawRect((int) startpoint.getX(), (int) startpoint.getY(),
						(int) (mousePoint.getX() - startpoint.getX()), (int) (mousePoint.getY() - startpoint.getY()));
			if (shape == Shapes.Circle) {
				//calculate the radius of the circle
//				double r = Math.sqrt(Math.pow(mousePoint.getX() - startpoint.getX(), 2.0)
//						+ Math.pow(mousePoint.getY() - startpoint.getY(), 2.0)); // pytagoras
//				r = r / Math.sqrt(2.0);
				double r = ShapeModel.getRadiusOfCircle(mousePoint, startpoint);
				
				//paint a helper rect
				g2.setColor(Color.YELLOW);
				g2.drawRect((int) startpoint.getX(), (int) startpoint.getY(), (int) (r), (int) (r));

				//paint the circle
				g2.setColor(Color.GREEN);
				g2.drawOval((int) startpoint.getX(), (int) startpoint.getY(), (int) r, (int) r);

			}
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		//The user just wants to paint
		//here we get the points of the mouse
		if (arg instanceof Point && o instanceof Controller) {
			mousePoint = (Point) arg;
			if (endpoint != null) {
				mousePoint = endpoint;
			}
		}
		//the user wants to paint a shape
		//for that we need the startpoint and the shape
		if (arg instanceof ShapeModel) {
			shapeModel = (ShapeModel) arg;
			
			//if a startpoint is null there is an error
			//so nothing is painted
			if (shapeModel.getStartPoint() == null) {
				disableShapes();
			} else {
				//if a shaped is moved in the model the startpoint and the endpoint are changed
				//in the paintComponent we paint a shape with the startpoint and the mousepoint
				if (shapeModel.isMoveShape()) {
					mousePoint = shapeModel.getEndPoint();
				}
				shape = shapeModel.getShape();
				startpoint = shapeModel.getStartPoint();
			}
		}
		if (arg instanceof Point && o instanceof ShapeModel) {
			//if a shape is moved, the end of the movement is when the end point is set
			//in the paintComponent we paint a shape with the startpoint and the mousepoint
			if (shapeModel != null && shapeModel.isMoveShape()) {
				mousePoint = shapeModel.getEndPoint();
			}

			endpoint = (Point) arg;
		}

		repaint();

	}

	public void disableShapes() {
		shape = Shapes.None;
		startpoint = null;
		endpoint = null;
	}
	
	public void setLineWidth(int width) {
		widthDefault = width;
	}
	
	public int getLineWidth() {
		return widthDefault;
	}
}

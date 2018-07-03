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

public class PaintPanel extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Point> points = new ArrayList<>();
	private Point mousePoint = new Point(0, 0);
	private Shapes shape = Shapes.None;
	private Point startpoint;
	private Point endpoint;
	private ShapeModel shapeModel = null;
	
	public PaintPanel(ArrayList<Point> points) {
		this.points = points;
		setSize(PaintFrame.HEIGHT, PaintFrame.WIDTH);
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);		
		Graphics2D g2 = (Graphics2D) g;
		setBackground(Color.WHITE);
		
		g.setColor(Color.BLACK);
		
		int width = 1;
		//paint line with round points between the coordinates next to each other
		for(int i = 0; i < points.size() - 1; i++) {
			
			if (points.get(i).getX() == Coordinates.PICTURE) {
				width = 1;
			}
			if (points.get(i).getX() == Coordinates.BREAK) {
				width = 10;
			}

			g2.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			if (points.get(i).getX() != Coordinates.BREAK && points.get(i+1).getX() != Coordinates.BREAK)
				g2.draw(new Line2D.Float((float)points.get(i).getX(), (float)points.get(i).getY(), (float)points.get(i+1).getX(), (float)points.get(i+1).getY()));
//			Path2D path = new Path2D.Double().curveTo((float)points.get(i).getX(), (float)points.get(i).getY(), (float)points.get(i+1).getX(), (float)points.get(i+1).getY()));
			else if(points.get(i).getX() == Coordinates.BREAK && i-1 >= 0) {
				g2.draw(new Line2D.Float((float)points.get(i-1).getX(), (float)points.get(i-1).getY(), (float)points.get(i+1).getX(), (float)points.get(i+1).getY()));				
			}
			else if(points.get(i+1).getX() == Coordinates.BREAK && points.size() > i+2) {
				g2.draw(new Line2D.Float((float)points.get(i).getX(), (float)points.get(i).getY(), (float)points.get(i+2).getX(), (float)points.get(i+2).getY()));
			}
			if (shape == Shapes.None || startpoint == null) {
				Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
				g2.setStroke(dashed);
				if(points.get(points.size() - 1).getX() == Coordinates.BREAK) {
					g2.draw(new Line2D.Float((float)points.get(points.size() - 2).getX(), (float)points.get(points.size() - 2).getY(), 
							(float)mousePoint.getX(), (float)mousePoint.getY()));
				}
			} else {
				//paint dashed line from last coordinate to start point of the shape
				Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
				g2.setStroke(dashed);
				if(points.get(points.size() - 1).getX() == Coordinates.BREAK) {
					g2.draw(new Line2D.Float((float)points.get(points.size() - 2).getX(), (float)points.get(points.size() - 2).getY(), 
							(float)startpoint.getX(), (float)startpoint.getY()));				
				}
				//paint shape from start point the mouse point
				g2.setColor(Color.GREEN);
				g2.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				if (shape == Shapes.Line)
					g2.drawLine((int)startpoint.getX(), (int)startpoint.getY(), (int)mousePoint.getX(), (int)mousePoint.getY());
				if (shape == Shapes.Rectangle)
					g2.drawRect((int)startpoint.getX(), (int)startpoint.getY(), (int)(mousePoint.getX() - startpoint.getX()), (int)(mousePoint.getY() - startpoint.getY()));
				if (shape == Shapes.Circle) {
//					Double r = new Double(0.0);
//					Point p = new Point();
//					Point mp = new Point();
//					Point mp = new Point((float)(mousePoint.getX() + startpoint.getX()) / 2.0f, (float)(mousePoint.getY() + startpoint.getY())/ 2.0f);
//					ShapeModel.paintCircle(mousePoint, startpoint, r, mp);
//					int distance = 0;
//					if (mousePoint.getX() - startpoint.getX() > mousePoint.getY() - startpoint.getY())
//						distance = (int)(mousePoint.getX() - startpoint.getX());
//					else
//						distance = (int)(mousePoint.getY() - startpoint.getY());

					double r = Math.sqrt(Math.pow(mousePoint.getX() - startpoint.getX(), 2.0) + Math.pow(mousePoint.getY() - startpoint.getY(), 2.0));	//pytagoras
					r = r / Math.sqrt(2.0);
					Point mp = new Point((float)(mousePoint.getX() + startpoint.getX()) / 2.0f, (float)(mousePoint.getY() + startpoint.getY())/ 2.0f);
//					System.out.println(startpoint + "\n" + mousePoint + "\n" + mp + 
//							"\nmp.getX() - startpoint.getX(): " + (mp.getX() - startpoint.getX()) + 
//							"\nmp.getY() - startpoint.getY(): " + (mp.getY() - startpoint.getY()));
					
//					Point start = new Point((float) (startpoint.getX() - 
////							((mp.getX() - startpoint.getX()) / (Math.sqrt(2)))), 
//							((mp.getX() - startpoint.getX())*(Math.sqrt(2) - 1) / (Math.sqrt(2)*2))), 
//							(float) (startpoint.getY() - 
////							((mp.getY() - startpoint.getY()) / (Math.sqrt(2)))));
//							((mp.getY() - startpoint.getY())*(Math.sqrt(2) - 1) / (Math.sqrt(2)*2))));
//					Point start = new Point((float) (startpoint.getX() - ((mp.getX() - startpoint.getX()) * ((Math.sqrt(2) - 1)/Math.sqrt(2)))), 
//							(float) (startpoint.getY() - ((mp.getY() - startpoint.getY()) * ((Math.sqrt(2) - 1)/Math.sqrt(2)))));
//					Point start = new Point((float) (startpoint.getX() - ((mp.getX() - startpoint.getX()) * ((Math.sqrt(2) - 1)/Math.sqrt(2)))), 
//							(float) (startpoint.getY() - ((mp.getY() - startpoint.getY()) * ((Math.sqrt(2) - 1)/Math.sqrt(2)))));
					g2.drawOval((int)startpoint.getX(), (int)startpoint.getY(), (int)r, (int)r);
//					g2.drawOval((int)start.getX(), (int)start.getY(), (int)r, (int)r);
//					g2.drawOval((int)start.getX(), (int)start.getY(), (int)(r + r*((Math.sqrt(2) - 1)/Math.sqrt(2))), (int)(r + r*((Math.sqrt(2) - 1)/Math.sqrt(2))));
					g2.setColor(Color.ORANGE);
					g2.drawOval((int)startpoint.getX(), (int)startpoint.getY(), 5, 5);
					g2.drawOval((int)mousePoint.getX(), (int)mousePoint.getY(), 5, 5);
					if(endpoint != null) {
						g2.drawOval((int)endpoint.getX(), (int)endpoint.getY(), 5, 5);	
						g2.drawLine((int)startpoint.getX(), (int)startpoint.getY(), (int)endpoint.getX(), (int)endpoint.getY());
					}
					g2.setColor(Color.PINK);
//					g2.drawRect((int)startpoint.getX(), (int)startpoint.getY(), (int)(mousePoint.getX() - startpoint.getX()), (int)(mousePoint.getY() - startpoint.getY()));
					g2.drawRect((int)startpoint.getX(), (int)startpoint.getY(), (int)(r), (int)(r));

				}					
			}
		} 
	}


	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Point && o instanceof Controller) {
			mousePoint = (Point) arg;
			if(endpoint != null) {
				mousePoint = endpoint;
			}
		}
		if (arg instanceof ShapeModel) {
			shapeModel = (ShapeModel) arg;
			if (shapeModel.getStartPoint() == null) {
				disableShapes();
			} else {
				if(shapeModel.isMoveShape()) {
					mousePoint = shapeModel.getEndPoint();
				}
				shape = shapeModel.getShape();
				startpoint = shapeModel.getStartPoint();				
			}
		} 
		if (arg instanceof Point && o instanceof ShapeModel) {
			if(shapeModel != null && shapeModel.isMoveShape()) {
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
}

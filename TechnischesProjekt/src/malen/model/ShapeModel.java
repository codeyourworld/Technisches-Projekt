package malen.model;

import java.util.ArrayList;
import java.util.Observable;

public class ShapeModel extends Observable{
	
	private Shapes shape = Shapes.None;
	private Point startPoint;
	private Point mousePoint;
	private Point endPoint;
	private ArrayList<Point> points = new ArrayList<>();
	private boolean isStartpointSet = false;
//	private boolean resizeShape = false;
	private boolean moveShape = false;
	private Point moveResizePoint;
	
	
	public ShapeModel() {
	}

	
	public Shapes getShape() {
		return shape;
	}
	
	public Point getStartPoint() {
		return startPoint;
	}
	
	
	/**
	 * This method sets a start point if a shape is chosen. 
	 * If there is no move action the end point is set to null.
	 * 
	 * This instance is send to all observers.
	 *  
	 * @param startPoint
	 */
	public void setStartPoint(Point startPoint) {
		
		if(shape != Shapes.None) {
			this.startPoint = startPoint;
			
			if (!isMoveShape()) {
				isStartpointSet = true;			
				endPoint = null;
			}
			setChanged();
			notifyObservers(this);
		}
	}
	
	
	/**
	 * This method calculates points to paint the given shape.
	 * The points are saved in an arraylist. 
	 * 
	 * The arraylist is send to all observers
	 * 
	 */
	public void calculatePoints() {
		if(shape == Shapes.Rectangle) {
			points.add(startPoint);
			points.add(new Point(startPoint.getX(), endPoint.getY()));
			points.add(new Point(endPoint.getX(), endPoint.getY()));
			points.add(new Point(endPoint.getX(), startPoint.getY()));
			points.add(new Point(startPoint.getX(), startPoint.getY()));
		} 
		if(shape == Shapes.Line) {
			points.add(startPoint);
			points.add(endPoint);
		}
		if(shape == Shapes.Circle) {
			Point p = new Point((endPoint.getX() - startPoint.getX())/2 + startPoint.getX(), (endPoint.getY() - startPoint.getY())/2 + startPoint.getY());
			double r = ShapeModel.getRadiusOfCircle(p, startPoint);
			double winkelgrad = 360.0;
			double winkel = 2*Math.PI/(winkelgrad);

			double winkelStartPointY = Math.asin((p.getY() - startPoint.getY())/r/2);
			if (winkelStartPointY != 0.0)
			{
				if((startPoint.getX() - p.getX()) < 0.0) {
					winkelStartPointY += Math.PI/2;
				}
			} else {
				//TODO nicht getestet
				winkelStartPointY = Math.acos((startPoint.getX() - p.getX())/(r/2));	//eigentlich winkelStartPointX
				if((startPoint.getY() - p.getY()) < 0.0) {
					winkelStartPointY += Math.PI/2;
				}				
			}
			for(int i = 0; i < (int)(2*Math.PI/winkel); i++) {
				points.add(new Point((float)(Math.cos(i*winkel - winkelStartPointY)* r)+p.getX(), (float)(Math.sin(i*winkel - winkelStartPointY)* r)+p.getY()));
			}
		}
		setChanged();
		notifyObservers(points);
	}
	
	
	/**
	 * This method calculates the radius between the start point and the mouse point. The mid point of the circle is directly between the start point and the mouse point
	 * @param mousePoint 
	 * @param startpoint
	 * @return radius
	 */
	public static double getRadiusOfCircle(Point mousePoint, Point startpoint){
		
		double r = Math.sqrt(Math.pow(mousePoint.getX() - startpoint.getX(), 2.0)
				+ Math.pow(mousePoint.getY() - startpoint.getY(), 2.0)); // pytagoras
		r = r / Math.sqrt(2.0);
		return r;
	}
	
	
	/**
	 * Returns if a startpoint is set
	 * @return true or false
	 */
	public boolean isStartpointSet() {
		return isStartpointSet;
	}

	
	/**
	 * If an end point is set it means the action is finish. This is the last point of the old action.
	 * 
	 * The end point is send to all observers
	 * 
	 * @param point
	 */
	public void setEndPoint(Point point) {

		if(isMoveShape()) {
			setMousePoint(point);
			moveShape = false;
		}
		else {
			this.endPoint = point;		
		}
		
		isStartpointSet = false;
		
		setChanged();
		notifyObservers(endPoint);
	}
	
	/**
	 * Depending of the mode this method acts differently. If a shape is still created this method just resize the shape. 
	 * If a shape should be moved this point relativ to the moveResizePoint moves the shape.
	 * 
	 * The mouse point is send to all observers
	 * 
	 * @param point
	 */
	public void setMousePoint(Point point) {
		
		if(moveShape) {
			if(moveResizePoint != null && endPoint != null){
				Point newStartPoint = new Point(
						startPoint.getX() + point.getX() - moveResizePoint.getX(), 
						startPoint.getY() + point.getY() - moveResizePoint.getY());
				setStartPoint(newStartPoint);

				endPoint = new Point(
						endPoint.getX() + point.getX() - moveResizePoint.getX(),
						endPoint.getY() + point.getY() - moveResizePoint.getY());

				moveResizePoint = point;
				mousePoint = endPoint;
			}
		}
		else {
			this.mousePoint = point;
		}

		setChanged();
		notifyObservers(mousePoint);
	}

	
	/**
	 * This method sets a shape. Valid Shapes are rectangle, circle, line.
	 * A shape is just set if there is no shape in edit mode.
	 * 
	 * The shape is send to all observers
	 * 
	 * @see Shapes
	 * @param shape which should be set
	 */
	public void setShape(Shapes shape) {

		if(isStartpointSet == false && !moveShape && this.shape == Shapes.None)	
		{
			this.shape = shape;
			setChanged();
			notifyObservers(shape);
		}
	}
	
	
	/**
	 * A shape has a start point and an end point which defines the size of the shape
	 * @return endPoint
	 */
	public Point getEndPoint() {
		return endPoint;
	}
	
	
	/**
	 * Returns the Points of a shape
	 * @return points
	 */
	public ArrayList<Point> getPoints() {
		return points;
	}


	/**
	 * If a shape is painted the shape could be moved by calling the methods setMoveResizePoint(Point moveResizePoint) and setMousePoint(Point point).
	 */
	public void setMove() {
		if(startPoint != null)
			moveShape = true;	
	}
	
	
	/**
	 * Sets the moveResizePoint. When a a shape should be moved or resized and the user clicks into the panel this point is set.
	 * @param moveResizePoint
	 */
	public void setMoveResizePoint(Point moveResizePoint) {
		this.moveResizePoint = moveResizePoint;
		moveShape = true;
	}
	
	
	/**
	 * This method returns if a shape can be moved by the user
	 * @return true if the user can move the shape, false if not
	 */
	public boolean isMoveShape() {
		return moveShape;
	}
	
	
	/**
	 * This Method returns if the moveResizePoint set. When a a shape should be moved or resized and the user clicks into the panel this point is set.
	 * @return true if the MoveResizePoint is set, false if not
	 */
	public boolean isSetMoveResizePoint() {
		return moveResizePoint != null;
	}
	
}

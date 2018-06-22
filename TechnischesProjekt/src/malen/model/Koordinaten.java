package malen.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

public class Koordinaten extends Observable{

	public static final int BREAK = -1;
	public static final int PICTURE = -4;
	private ArrayList<Point> points = new ArrayList<>();
	private Point startPoint;
	
	
	/**
	 * The method setStartPoints set the Point with the x and y coordinate as the start point. 
	 * This point is always used as start point. 
	 * @param x coordinate
	 * @param y coordinate
	 */
	private void setStartPoints() {
		points.add(startPoint);
		points.add(new Point(BREAK, BREAK));		
	}

	/**
	 * 
	 * @return ArrayList with the points
	 */
	public ArrayList<Point> getPoints() {
		return points;
	}
	
	/**
	 * This method add a point with the (x, y) coordinates and notifies the view
	 * @param x coordinate
	 * @param y coordinate
	 */
	public void addPoint (float x, float y) {
		Point p = new  Point(x, y);
		points.add(p);
		setChanged();
		notifyObservers();
	}

	/**
	 * This Method clears the list with the points and notifies the view. The start point is also removed.
	 */
	public void removeAllPoint () {
		points.clear();
		setStartPoints();
		setChanged();
		notifyObservers();		
	}
	
	/**
	 * This method deletes all points from the last painted line and notifies the view.
	 */
	public void deleteLastLine() {
		Iterator<Point> it = points.iterator();
		int index = 0;

		//finde den (-1, -1) Punkt
		for(int i = points.size() - 2; i >= 0; i--) {
			if (points.get(i).getX() == BREAK) {
				index = i;
				break;
			}
		}

		//iteriere bis zum Punkt (-1, -1)
		for (int i = 0; i <= index; i++) {
			it.next();
		}

		//entferne alle Punkte nach dem (-1, -1) Punkt
		while (it.hasNext()) {	
			it.next();
			it.remove();
		}

		//wenn die Liste leer ist, sollen die Startkoordinaten wieder zur Liste hinzugefügt werden
		if (points.size() < 2) {
			points.clear();

			setStartPoints();
		}

		//benachrichtige die View, dass sich die Modelwerte geändert haben
		setChanged();
		notifyObservers();
	}
	
	
	public void addList (ArrayList<Point> list) {
		points.addAll(list);
		setChanged();
		notifyObservers();
	}

	public void setMaxY(int height) {
		startPoint = new Point(0, height);
		if(points.isEmpty())
		{
			setStartPoints();
		}
	}
	
	public void setBreak() {
		points.add(new Point(BREAK, BREAK));
	}
}

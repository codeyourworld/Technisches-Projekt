package malen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

public class Koordinaten extends Observable{

	private ArrayList<Point> points = new ArrayList<>();
	private Point startPoint;
	
	
	/**
	 * The method setStartPoints set the Point with the x and y coordinate as the start point. 
	 * This point is always used as start point. 
	 * @param x coordinate
	 * @param y coordinate
	 */
	public void setStartPoints(int x, int y) {
		startPoint = new Point(x, y);
		points.add(startPoint);
		points.add(new Point(-1, -1));		
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
			if (points.get(i).getX() == -1) {
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
			points.add(startPoint);
			points.add(new Point(-1, -1));		

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
}

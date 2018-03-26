package malen;

import java.util.ArrayList;
import java.util.Observable;

public class Koordinaten extends Observable{

	private ArrayList<Point> points = new ArrayList<>();
	

	public ArrayList<Point> getPoints() {
		return points;
	}
	
	public void addPoint (int x, int y) {
		Point p = new  Point(x, y);
		points.add(p);
		System.out.println(p);
		setChanged();
		notifyObservers();
	}
	
	public void removeAllPoint () {
		points.clear();
		setChanged();
		notifyObservers();		
	}
	
}

package malen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

public class Koordinaten extends Observable{

	private ArrayList<Point> points = new ArrayList<>();
	
	public Koordinaten() {
		points.add(new Point(0, 0));
		points.add(new Point(-1, -1));
	}

	public ArrayList<Point> getPoints() {
		return points;
	}
	
	public void addPoint (float x, float y) {
//		if (points.get(points.size() - 1).getX() == -1) {
//			points.remove(points.size() - 1);
//		}
		Point p = new  Point(x, y);
		points.add(p);
//		System.out.println(p);
		setChanged();
		notifyObservers();
	}
	
	public void removeAllPoint () {
		points.clear();
		setChanged();
		notifyObservers();		
	}
	
	public void deleteLastLine() {
		Iterator<Point> it = points.iterator();
		int index = 0;

		
		for(int i = points.size() - 2; i >= 0; i--) {
			if (points.get(i).getX() == -1) {
				index = i;
				break;
			}
		}

		for (int i = 1; i < index; i++) {
			it.next();
		}
		
		while (it.hasNext()) {	
			it.next();
			it.remove();
		}

		if (points.size() == 0) {
			points.add(new Point(0, 0));
			points.add(new Point(-1, -1));
		}
		setChanged();
		notifyObservers();
	}
	
}

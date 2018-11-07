package malen.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class Calibration extends Observable{

	private ArrayList<Point> points = new ArrayList<>();
	private int xOffset = 0;
	private int yOffset = 0;
	private int cnt = 0;
	
	
	public Calibration(int height, int width) {
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			int y = random.nextInt(height-100);
			int x = random.nextInt(width-100);
			points.add(new Point(x, y));
		}
		
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}
	
	public void calcOffset(Point painted) {
		xOffset += points.get(cnt).getX() - painted.getX();
		yOffset += points.get(cnt).getY() - painted.getY();
		cnt++;
	}
	
	public Point getAverageOffset() {
		return new Point(xOffset/(cnt+1), yOffset/(cnt+1));
	}


	public void nextPoint() {
		setChanged();
		notifyObservers(points.get(cnt));
	}
	
	public boolean isFinished() {
		return cnt == points.size();
	}
}

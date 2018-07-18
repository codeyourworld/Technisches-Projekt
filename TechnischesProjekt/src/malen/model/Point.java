package malen.model;

import java.io.Serializable;

import malen.view.PaintFrame;

public class Point implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float y;
	private float x;
	
	public Point() {
		this(0, 0);
	}
	
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

	public void norm(float xSize, float ySize) {
		this.x /= xSize;
		this.y /= ySize;
	}
	
	public void picCoords2cartCoords(){
		y = 1 - y;
	}

	public void cartCoords2picCoords(){
		y = 1 - y;
	}
	
	public void denorm(float xSize, float ySize) {
		this.x *= xSize;
		this.y *= ySize;
	}

	@Override
	public String toString() {
		return x + "," + y;
	}
}

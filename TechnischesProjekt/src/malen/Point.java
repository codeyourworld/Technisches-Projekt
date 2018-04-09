package malen;

public class Point {

	private float y;
	private float x;
	
	public Point() {
		this(0, 0);
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
	
	@Override
	public String toString() {
		return x + "," + y;
	}
}

package malen;

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

public class PaintPanel extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Point> points = new ArrayList<>();
	private Point mousePoint = new Point(0, 0);
	
	public PaintPanel(ArrayList<Point> points) {
		this.points = points;
	}
	
//
//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		Graphics2D g2 = (Graphics2D) g;
//		setBackground(Color.WHITE);
//		
//		g.setColor(Color.BLACK);
//		
//		int width = 1;
//		//paint line with round points between the coordinates next to each other
//		for(int i = 0; i < points.size() - 1; i++) {
//			
//			if (points.get(i).getX() == Koordinaten.PICTURE) {
//				width = 1;
//			}
//			if (points.get(i).getX() == Koordinaten.BREAK) {
//				width = 10;
//			}
//
//			g2.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//			if (points.get(i).getX() != Koordinaten.BREAK && points.get(i+1).getX() != Koordinaten.BREAK)
//				g2.draw(new Line2D.Float((float)points.get(i).getX(), (float)points.get(i).getY(), (float)points.get(i+1).getX(), (float)points.get(i+1).getY()));
////			Path2D path = new Path2D.Double().curveTo((float)points.get(i).getX(), (float)points.get(i).getY(), (float)points.get(i+1).getX(), (float)points.get(i+1).getY()));
//			else if(points.get(i).getX() == Koordinaten.BREAK && i-1 >= 0) {
//				g2.draw(new Line2D.Float((float)points.get(i-1).getX(), (float)points.get(i-1).getY(), (float)points.get(i+1).getX(), (float)points.get(i+1).getY()));				
//			}
//			else if(points.get(i+1).getX() == Koordinaten.BREAK && points.size() > i+2) {
//				g2.draw(new Line2D.Float((float)points.get(i).getX(), (float)points.get(i).getY(), (float)points.get(i+2).getX(), (float)points.get(i+2).getY()));
//			}
//			Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
//			g2.setStroke(dashed);
//			if(points.get(points.size() - 1).getX() == Koordinaten.BREAK) {
//				g2.draw(new Line2D.Float((float)points.get(points.size() - 2).getX(), (float)points.get(points.size() - 2).getY(), 
//						(float)mousePoint.getX(), (float)mousePoint.getY()));
//			}
//			
//		}
//	}
//

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);		
		Graphics2D g2 = (Graphics2D) g;
		setBackground(Color.WHITE);
		
		g.setColor(Color.BLACK);
		
		int width = 1;
		//paint line with round points between the coordinates next to each other
		for(int i = 0; i < points.size() - 1; i++) {
			
			if (points.get(i).getX() == Koordinaten.PICTURE) {
				width = 1;
			}
			if (points.get(i).getX() == Koordinaten.BREAK) {
				width = 10;
			}

			g2.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			if (points.get(i).getX() != Koordinaten.BREAK && points.get(i+1).getX() != Koordinaten.BREAK)
				g2.draw(new Line2D.Float((float)points.get(i).getX(), (float)points.get(i).getY(), (float)points.get(i+1).getX(), (float)points.get(i+1).getY()));
//			Path2D path = new Path2D.Double().curveTo((float)points.get(i).getX(), (float)points.get(i).getY(), (float)points.get(i+1).getX(), (float)points.get(i+1).getY()));
			else if(points.get(i).getX() == Koordinaten.BREAK && i-1 >= 0) {
				g2.draw(new Line2D.Float((float)points.get(i-1).getX(), (float)points.get(i-1).getY(), (float)points.get(i+1).getX(), (float)points.get(i+1).getY()));				
			}
			else if(points.get(i+1).getX() == Koordinaten.BREAK && points.size() > i+2) {
				g2.draw(new Line2D.Float((float)points.get(i).getX(), (float)points.get(i).getY(), (float)points.get(i+2).getX(), (float)points.get(i+2).getY()));
			}
			Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
			g2.setStroke(dashed);
			if(points.get(points.size() - 1).getX() == Koordinaten.BREAK) {
				g2.draw(new Line2D.Float((float)points.get(points.size() - 2).getX(), (float)points.get(points.size() - 2).getY(), 
						(float)mousePoint.getX(), (float)mousePoint.getY()));
			}
			
		} 
	}


	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Point) {
			mousePoint = (Point) arg;
		}
		revalidate();
		repaint();		

	}
	
	
}

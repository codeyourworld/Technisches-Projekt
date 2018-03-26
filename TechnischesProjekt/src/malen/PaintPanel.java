package malen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
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
	
	public PaintPanel(ArrayList<Point> points) {
		this.points = points;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);		
		Graphics2D g2 = (Graphics2D) g;
		setBackground(Color.WHITE);
		
		g.setColor(Color.BLACK);
		
		//paint line with round points between the coordinates next to each other
		for(int i = 0; i < points.size() - 1; i++) {
			
			g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			if (points.get(i).getX() != -1 && points.get(i+1).getX() != -1)
				g2.draw(new Line2D.Float((float)points.get(i).getX(), (float)points.get(i).getY(), (float)points.get(i+1).getX(), (float)points.get(i+1).getY()));
//			Path2D path = new Path2D.Double().curveTo((float)points.get(i).getX(), (float)points.get(i).getY(), (float)points.get(i+1).getX(), (float)points.get(i+1).getY()));
		}
	}


	@Override
	public void update(Observable o, Object arg) {
		repaint();		
	}
	
	
}

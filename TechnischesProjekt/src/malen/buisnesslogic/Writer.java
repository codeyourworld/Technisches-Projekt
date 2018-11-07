package malen.buisnesslogic;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import malen.model.Point;
import malen.view.PaintFrame;

public class Writer {

	
	public void saveData (String filename, final ArrayList<Point> points) {
		ArrayList<Point> tmp = new ArrayList<>(points);
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		StringBuffer strBuf = new StringBuffer(tmp.size());
		float height = (float) PaintFrame.HEIGHT;
		float width = (float) PaintFrame.WIDTH;
		System.out.println(tmp.size());
		Iterator<Point> iterator = tmp.iterator();

		Point p = iterator.next();
		
		if(p.getX() == 0) {
			iterator.remove();
			p = iterator.next();
			if(p.getX() == -1 && p.getY() == -1)
				iterator.remove();
		}
		System.out.println(tmp.size());
		while(iterator.hasNext()) {
			p = iterator.next();

			//delete the negative values
			if(p.getX() < 0) {
				iterator.remove();
			} else {
				p.norm(width, height);
				p.picCoords2cartCoords();
				if(p.getX()<1.0 && p.getX()>=0 && p.getY()<1.0 && p.getY()>=0.0 )
					strBuf.append(p.toString() + "\r\n");				
			}			
		}
		
		System.out.println("Start writing data");
		IOService.writeLine(strBuf.toString(), filename);
		strBuf = null;
		System.out.println("finish writing data");
		JOptionPane.showMessageDialog(null, "Finished saving data");
	}
}

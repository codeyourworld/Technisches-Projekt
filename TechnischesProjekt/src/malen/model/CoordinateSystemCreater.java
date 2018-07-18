package malen.model;

import java.util.ArrayList;
import java.util.Collections;

public class CoordinateSystemCreater implements IData{

	private static final int DIST_Y_GRID = 50;
	private static final int DIST_X_GRID = 100;
	private static final int DIST_0_Y_LINE = 100;
	private static final int DIST_0_X_LINE = 100;
	private int minX;
	private int anzahlXVal;
	private int minY;
	private int anzahlYVal;
	private boolean lines;
	private int distX;
	private int distY;
	private int width;
	private int height;
	private ArrayList<Point> coorsList = new ArrayList<>();
	private int factor = 20;
	
	public CoordinateSystemCreater(int minX, int maxX, int minY, int maxY, boolean lines, int distX, int distY, int width, int height) {
		this.minX = minX;
		this.minY = minY;

		this.lines = lines;
		this.distX = distX;
		this.distY = distY;
		this.width = width;
		this.height = height;
		if (lines) {
			anzahlXVal = (maxX - minX)/distX;
			anzahlYVal = (maxY - minY)/distY;
		}
		else {
			anzahlXVal = 10;
			anzahlYVal = width / (height / anzahlXVal);
		}
	}
	
	/**
	 * ##############<br>
	 * #-|			#<br>
	 * #-|			#<br>
	 * #-|			#<br>
	 * #_|__________#<br>
	 * # | ' ' ' ' '#<br>
	 * ##############<br>
	 * -> <- Starte mit der Linie für die y-Koordinatensystemlinie zwischen den Pfeilen (0)<br>
	 * erstell danach die Punkte für y-Koordinatensystemlinie (1)<br>
	 * erstell danach n Punkte für "-" um die Schritte im y-Koordinatensystem anzudeuten (2)<br>
	 * <br>
	 * mach danach das gleiche für die x Achse<br>
	 *    
	 */
	public void calcPoint() {
		//Siehe Erklärung (0)
		coorsList.add(new Point(DIST_0_Y_LINE, height));

		//Siehe Erklärung (1)		
		coorsList.add(new Point(DIST_0_Y_LINE, 0));

		int smallLines = DIST_Y_GRID/2 >= factor * 2 + 5 ? DIST_Y_GRID/2 : factor * 2 + 5;
		//Siehe Erklärung (2)		
		int abstand = (height - DIST_0_X_LINE - DIST_Y_GRID)/(anzahlXVal + 1);
		int offset = height - (DIST_0_X_LINE + abstand * (anzahlXVal + 1));
		for (int i = 1; i <= anzahlXVal; i++) {
			coorsList.add(new Point(DIST_0_Y_LINE, abstand*i + offset));
			if (lines && anzahlXVal % 2 == 0 && i % 2 != 0) {
				coorsList.add(new Point(0, abstand*i + offset));	
				paintNumber(minX + (anzahlXVal - (i-1))*distX, 0, abstand*i + offset);
				coorsList.add(new Point(width - DIST_X_GRID, abstand*i + offset));				
			}
			if (lines && anzahlXVal % 2 != 0 && i % 2 == 0) {
				coorsList.add(new Point(0, abstand*i + offset));
				paintNumber(minX + (anzahlXVal - (i-1))*distX, 0, abstand*i + offset);
				coorsList.add(new Point(0, abstand*i + offset));
				coorsList.add(new Point(width - DIST_X_GRID, abstand*i + offset));				
			} else {
				coorsList.add(new Point(DIST_0_Y_LINE-smallLines, abstand*i + offset));
				coorsList.add(new Point(DIST_0_Y_LINE + smallLines, abstand*i + offset));				
			}
			coorsList.add(new Point(DIST_0_Y_LINE, abstand*i + offset));
		}
		
		//zum Koordinatenkreuz		
		coorsList.add(new Point(DIST_0_Y_LINE, height - DIST_0_X_LINE));
		coorsList.add(new Point(0, height - DIST_0_X_LINE));
		paintNumber(minX, 0, height - DIST_0_X_LINE);
		coorsList.add(new Point(DIST_0_Y_LINE, height - DIST_0_X_LINE));

		
		//Siehe Erklärung (1)		
		coorsList.add(new Point(width, height - DIST_0_X_LINE));
		
		
		//Siehe Erklärung (2)		
		abstand = (width-DIST_0_Y_LINE-DIST_X_GRID)/(anzahlYVal + 1);
		offset = DIST_0_Y_LINE;
		for (int i = 0; i <= anzahlYVal; i++) {
			coorsList.add(new Point(abstand*i + offset, height - DIST_0_X_LINE));
			if (lines && i % 2 == 0) {
				coorsList.add(new Point(abstand*i + offset, height - (DIST_0_X_LINE - smallLines)));
				paintNumber(minY + i*distY, abstand*i + offset, height - (DIST_0_X_LINE - smallLines));
				coorsList.add(new Point(abstand*i + offset, height - (DIST_0_X_LINE - smallLines)));
				coorsList.add(new Point(abstand*i + offset, height - (DIST_0_X_LINE + smallLines)));
				coorsList.add(new Point(abstand*i + offset, DIST_Y_GRID)); //end
					
			} else {
				coorsList.add(new Point(abstand*i + offset, height - (DIST_0_X_LINE + smallLines)));
				coorsList.add(new Point(abstand*i + offset, height - (DIST_0_X_LINE - smallLines)));
			}
			coorsList.add(new Point(abstand*i + offset, height - DIST_0_X_LINE));
		}

		//zum Koordinatenkreuz		
		coorsList.add(new Point(DIST_0_Y_LINE, height - DIST_0_X_LINE));
		coorsList.add(new Point(0, height - DIST_0_X_LINE));
		coorsList.add(new Point(DIST_0_Y_LINE, height - DIST_0_X_LINE));


	}
	
	private void paintNumber(int num, int x, int y) {
		
		ArrayList<Integer> nums = new ArrayList<>();
		if (num == 0)
			nums.add(0);
		while(num > 0) {
			nums.add(num%10);
			num/=10;
		}
		Collections.reverse(nums);
		ArrayList<Point> points = null;
		Numbers.fillLists();
		
		for(int cnt = 0; cnt < nums.size(); cnt++) {
			switch (nums.get(cnt)) {
			case 0:
				points = Numbers.ZERO;
				break;
			case 1:
				points = Numbers.ONE;
				break;
			case 2:
				points = Numbers.TWO;
				break;
			case 3:
				points = Numbers.THREE;
				break;
			case 4:
				points = Numbers.FOUR;
				break;
			case 5:
				points = Numbers.FIFE;
				break;
			case 6:
				points = Numbers.SIX;
				break;
			case 7:
				points = Numbers.SEVEN;
				break;
			case 8:
				points = Numbers.EIGHT;
				break;
			case 9:
				points = Numbers.NINE;
				break;

			default:
				break;
			}
			
			for(int i = 0; i < points.size(); i++) {
				//x is the x startpoint 
				//points.get(i).getX() has just values from 0 to 1, the width of a number is factor -> so the point has to be multiplied with the factor
				//start with the 1st letter factor/2 px to the right
				//there is a space of cnt*(factor/2)
				coorsList.add(new Point(points.get(i).getX()*factor + x + cnt*factor + factor/2 + cnt*(factor/2), -points.get(i).getY()*factor + y));	
			}
			
		}
		
	}
	
	@Override
	public ArrayList<Point> getCoordinates() {
		return coorsList;
	}
	
	@Override
	public void start() {
		calcPoint();
		
	}
	
	public void setNumbersFactor(int factor){
		this.factor  = factor;
		coorsList.clear();
		calcPoint();
	}
	
}

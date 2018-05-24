package bildverarbeitung;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import malen.Point;

public class GreyToLines {

	private BufferedImage image;
	private int height;
	private int width; 
	boolean found = false;
	private int MAX_GREY_VALUE = 255;
//	private int minValue = 0;
//	private int maxValue = 255;
//	private int [][] matrix = 
//		{{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};

	private int [][][] coorsMatrix = {
		//0	komplett schwarz
		{{1, 0, 0, 0, 1}, 
		{1, 0, 0, 0, 1}, 
		{1, 0, 0, 0, 1}, 
		{1, 0, 0, 0, 1}, 
		{1, 0, 0, 0, 1}},
	
		//
		{{1, 0, 0, 1, 0}, 
		{1, 0, 0, 0, 1}, 
		{1, 0, 0, 0, 1}, 
		{1, 0, 0, 0, 1}, 
		{1, 0, 0, 1, 0}},

		
		//
		{{1, 0, 0, 1, 0}, 
		{1, 0, 0, 1, 0}, 
		{1, 0, 0, 0, 1}, 
		{1, 0, 0, 1, 0}, 
		{1, 0, 0, 1, 0}},

		//
		{{1, 0, 0, 1, 0}, 
		{1, 0, 0, 1, 0}, 
		{1, 0, 0, 1, 0}, 
		{1, 0, 0, 1, 0}, 
		{1, 0, 0, 1, 0}},

		//
		{{1, 0, 1, 0, 0}, 
		{1, 0, 0, 1, 0}, 
		{1, 0, 0, 1, 0}, 
		{1, 0, 0, 1, 0}, 
		{1, 0, 1, 0, 0}},

		//
		{{1, 0, 1, 0, 0}, 
		{1, 0, 1, 0, 0}, 
		{1, 0, 0, 1, 0}, 
		{1, 0, 1, 0, 0}, 
		{1, 0, 1, 0, 0}},
		
		//
		{{1, 0, 1, 0, 0}, 
		{1, 0, 1, 0, 0}, 
		{1, 0, 1, 0, 0}, 
		{1, 0, 1, 0, 0}, 
		{1, 0, 1, 0, 0}},
		
		//
		{{1, 1, 0, 0, 0}, 
		{1, 0, 1, 0, 0}, 
		{1, 0, 1, 0, 0}, 
		{1, 0, 1, 0, 0}, 
		{1, 1, 0, 0, 0}},

		//
		{{1, 1, 0, 0, 0}, 
		{1, 1, 0, 0, 0}, 
		{1, 0, 1, 0, 0}, 
		{1, 1, 0, 0, 0}, 
		{1, 1, 0, 0, 0}},

		//
		{{1, 1, 0, 0, 0}, 
		{1, 1, 0, 0, 0}, 
		{1, 1, 0, 0, 0}, 
		{1, 1, 0, 0, 0}, 
		{1, 1, 0, 0, 0}},

		//
		{{1, 0, 0, 0, 0}, 
		{1, 1, 0, 0, 0}, 
		{1, 1, 0, 0, 0}, 
		{1, 1, 0, 0, 0}, 
		{1, 0, 0, 0, 0}},

		//
		{{1, 0, 0, 0, 0}, 
		{1, 0, 0, 0, 0}, 
		{1, 1, 0, 0, 0}, 
		{1, 0, 0, 0, 0}, 
		{1, 0, 0, 0, 0}},
		//
		{{1, 0, 0, 0, 0}, 
		{1, 0, 0, 0, 0}, 
		{1, 0, 0, 0, 0}, 
		{1, 0, 0, 0, 0}, 
		{1, 0, 0, 0, 0}}

//		//0
//		{{0, 0, 1, 0, 0}, 
//		{1, 0, 0, 0, 0}, 
//		{0, 0, 0, 0, 1}, 
//		{1, 0, 0, 0, 0}, 
//		{0, 0, 1, 0, 0}},
//		
//		//0 + 2s
//		{{0, 0, 1, 0, 0}, 
//		{1, 0, 0, 0, 0}, 
//		{0, 0, 0, 1, 0}, 
//		{1, 0, 0, 0, 0}, 
//		{0, 0, 1, 0, 0}},
//
//		//0 + 3s
//		{{0, 0, 1, 0, 0}, 
//		{1, 0, 0, 0, 0}, 
//		{0, 0, 0, 0, 0}, 
//		{0, 0, 0, 1, 0}, 
//		{0, 0, 1, 0, 0}},
//
//		//0 + 4s
//		{{0, 0, 1, 0, 0}, 
//		{0, 1, 0, 0, 0}, 
//		{0, 0, 0, 1, 0}, 
//		{0, 1, 0, 0, 0}, 
//		{0, 0, 1, 0, 0}},
//
//		//0 + 5s
//		{{0, 0, 1, 0, 0}, 
//		{0, 1, 0, 0, 0}, 
//		{0, 0, 0, 0, 0}, 
//		{0, 0, 0, 1, 0}, 
//		{0, 0, 1, 0, 0}},
//
//		//255 = 6s
//		{{0, 0, 1, 0, 0}, 
//		{0, 0, 1, 0, 0}, 
//		{0, 0, 1, 0, 0}, 
//		{0, 0, 1, 0, 0}, 
//		{0, 0, 1, 0, 0}}

	};


	private ArrayList<Point> points = new ArrayList<>();
	private int schwellwert = (MAX_GREY_VALUE%coorsMatrix.length != 0)?MAX_GREY_VALUE/coorsMatrix.length + 1: MAX_GREY_VALUE/coorsMatrix.length;	//der erste und letzte Punkt ist immer in der Mitte

	public GreyToLines(String fileName) {

		try {
			image = ImageIO.read(new File(fileName));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.height = image.getHeight();
		this.width = image.getWidth();
	}
	
	public void createCoords () {
		int greyVal = 0;
//		for(int x = 0; x < width-matrix.length; x+=matrix.length) {
//			for(int y = 0; y < height-matrix.length; y+=matrix.length) {
//				greyVal = getGreyValue(x, y);
//				for(int xi = 0; xi < coorsMatrix[0].length; xi++) {
//					for(int yj = 0; yj < coorsMatrix[0][0].length; yj++) {
//						int choise = (greyVal/schwellwert);
//						if (coorsMatrix[choise][xi][yj] == 1){
//							points.add(new Point(x + xi, y + yj));
//						}
//					}
//				}
//			}
//		}
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				greyVal = getGreyValue(x, y);
				int choise = (greyVal/schwellwert);
				for(int xi = 0; xi < coorsMatrix[0].length; xi++) {
					for(int yj = 0; yj < coorsMatrix[0][0].length; yj++) {
						if (coorsMatrix[choise][xi][yj] == 1){
							points.add(new Point((coorsMatrix[0].length * x) + xi, (coorsMatrix[0].length * y) + yj));
						}
					}
				}
			}
		}

	}
	
	
	private int getGreyValue(int x, int y){
		int val = 0;
//		for(int i = 0; i < matrix.length; i++) {
//			for(int j = 0; j < matrix.length; j++) {
//				val += image.getRGB(x, y) * matrix[i][j];
//			}
//		}
//		Color c = new Color(val/(matrix.length*2));

		val = image.getRGB(x, y);
		Color c = new Color(val);
		return c.getRed();
	}

	public ArrayList<Point> getPoints() {
		return points;
	}
	
}

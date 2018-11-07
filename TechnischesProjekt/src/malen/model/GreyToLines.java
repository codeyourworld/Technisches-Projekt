package malen.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import malen.view.PaintFrame;

public class GreyToLines {

	private BufferedImage image;
	private int height;
	private int width; 
	boolean found = false;
	private int MAX_GREY_VALUE = 255;


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

	};


	private ArrayList<Integer> greyValues = new ArrayList<>();
	private ArrayList<Point> points = new ArrayList<>();
	private int schwellwert = (MAX_GREY_VALUE%coorsMatrix.length != 0)?MAX_GREY_VALUE/coorsMatrix.length + 1: MAX_GREY_VALUE/coorsMatrix.length;	//der erste und letzte Punkt ist immer in der Mitte
	private int xOffset = 0;
	private int yOffset = 0;
	
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
		points.clear();
		int greyVal = 0;
		String value = JOptionPane.showInputDialog("Bitte geb einen Faktor an. Standard = 1");
		int factor = 1;
		try {
			factor = Integer.valueOf(value);
			
		} catch (Exception e) {
			factor = 1;
		}
//		if (PaintFrame.HEIGHT / (height-yOffset) > PaintFrame.WIDTH/(width-xOffset)) {
//			factor = (int) Math.ceil(PaintFrame.WIDTH/((width - xOffset) * coorsMatrix[0].length));
//		}
//		else {
//			factor = (int)  Math.ceil(PaintFrame.HEIGHT / ((height - yOffset) * coorsMatrix[0].length));
//		}
//		System.out.println(factor + ", coorsMatrix.length" + coorsMatrix[0].length);
//		factor = 2;
		for(int x = 0; x < width - xOffset; x+=2) {
			for(int y = 0; y < height - yOffset; y++) {
				greyVal = getGreyValue(x + xOffset, y + yOffset);
				int choise = (greyVal/schwellwert);
				if(!greyValues.contains(choise)) {
					greyValues.add(choise);
					System.out.println("GreyValue : " + greyVal + ", schwellwert: " + choise);
				}
				for(int yj = 0; yj < coorsMatrix[0][0].length; yj++) {
					for(int xi = 0; xi < coorsMatrix[0].length; xi++) {
					
						if (coorsMatrix[choise][yj][xi] == 1){
//							if (coorsMatrix[choise][xi][yj] == 1){
							/*
							 * x is the picture coordinate
							 * every px has the width of coorsMatrix[0].length * factor
							 * xi is the x value of coorsMatrix where the zig zag line are defined
							 */
//							int xValue = ((coorsMatrix[0].length) * x * factor) + xi*factor;
//							int yValue = ((coorsMatrix[0].length) * y * factor) + yj*factor;
							int xValue = ((coorsMatrix[0].length -1) * x * factor) + xi*factor;
							int yValue = ((coorsMatrix[0].length-1) * y * factor) + yj*factor;
							points.add(new Point(xValue, yValue));
//							points.add(new Point((coorsMatrix[0].length * x * factor) + xi*factor, (coorsMatrix[0].length * y) + yj * factor));
						}
					}
				}
			}
				
			if (x % 2 == 0){
				for(int y = height - yOffset - 1; y >= 0; y--) {
					if(x + 1 + xOffset >= width) {
						break;
					}
					greyVal = getGreyValue(x + 1 + xOffset, y + yOffset);
					int choise = (greyVal/schwellwert);
						
						
					for(int yj = coorsMatrix[0][0].length - 1; yj >= 0; yj--) {
						for(int xi = 0; xi < coorsMatrix[0].length; xi++) {
						
							if (coorsMatrix[choise][yj][xi] == 1){
	//							if (coorsMatrix[choise][xi][yj] == 1){
								/*
								 * x is the picture coordinate
								 * every px has the width of coorsMatrix[0].length * factor
								 * xi is the x value of coorsMatrix where the zig zag line are defined
								 */
								
//								int xValue = ((coorsMatrix[0].length) * (x + 1) * factor) + xi*factor;
//								int yValue = ((coorsMatrix[0].length) * y * factor) + yj*factor;
								int xValue = ((coorsMatrix[0].length-1) * (x + 1) * factor) + xi*factor;
								int yValue = ((coorsMatrix[0].length-1) * y * factor) + yj*factor;
								points.add(new Point(xValue, yValue));
	//							points.add(new Point((coorsMatrix[0].length * x * factor) + xi*factor, (coorsMatrix[0].length * y) + yj * factor));
							}
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

	public void setOffset(int x, int y) {
		xOffset = x;
		yOffset = y;
		
	}
	
}

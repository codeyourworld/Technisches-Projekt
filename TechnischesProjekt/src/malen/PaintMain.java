package malen;

import malen.controller.Controller;
import malen.model.Coordinates;
import malen.view.PaintFrame;


public class PaintMain {

	public static void main(String[] args) {
		Coordinates koordinaten = new Coordinates();
    	PaintFrame frame = new PaintFrame(koordinaten);
		new Controller(frame, koordinaten);

	}

}


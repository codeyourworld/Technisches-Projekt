package malen;

import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import malen.controller.Controller;
import malen.model.Coordinates;
import malen.view.CalibrationDialog;
import malen.view.PaintFrame;


public class PaintMain {

	public static void main(String[] args) {
		Coordinates koordinaten = new Coordinates();
    	PaintFrame frame = new PaintFrame(koordinaten);
		Controller controller = new Controller(frame, koordinaten);

	}

}

//TODO: bei 0/0 anfangen
//TODO: normieren auf 1
//TODO: absetzen , rückgängig

package malen;

import malen.controller.Controller;
import malen.model.Koordinaten;
import malen.view.PaintFrame;

public class PaintMain {

	public static void main(String[] args) {
		Koordinaten koordinaten = new Koordinaten();
		PaintFrame gui = new PaintFrame(koordinaten);
		Controller controller = new Controller(gui, koordinaten);

	}

}

//TODO: bei 0/0 anfangen
//TODO: normieren auf 1
//TODO: absetzen , rückgängig

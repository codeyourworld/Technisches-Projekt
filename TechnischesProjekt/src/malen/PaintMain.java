package malen;

public class PaintMain {

	public static void main(String[] args) {
		Koordinaten koordinaten = new Koordinaten();
		GUI gui = new GUI(koordinaten);
		Controller controller = new Controller(gui, koordinaten);

	}

}

//TODO: bei 0/0 anfangen
//TODO: normieren auf 1
//TODO: absetzen , r�ckg�ngig
package malen.buisnesslogic;


import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;

public class PaddingService {

	public static Box createPaddings (boolean vertical) {
		return PaddingService.createPaddings(40, vertical, Color.WHITE);
	}

	
	public static Box createPaddings (int number, boolean vertical) {
		return PaddingService.createPaddings(number, vertical, Color.WHITE);
	}

	public static Box createPaddings (int number, boolean vertical, Color color) {
		
		Box box = new Box(vertical? BoxLayout.Y_AXIS : BoxLayout.X_AXIS);
		box.setBackground(color);
		box.setOpaque(true);
		box.add(vertical? Box.createVerticalStrut(number) : Box.createHorizontalStrut(number));

		return box;
	}

	
}

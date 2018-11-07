package malen.buisnesslogic;


import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;

public class PaddingService {

	/**
	 * A box for padding is created, so you can use the box for the WEST, EAST, SOUTH and NORTH of the 
	 * BorderLayout. The Background color is set to white.
	 * The number for padding/distance is 40.
	 * 
	 * @param vertical if vertical is true a vertical strut is created else a horizonal strut
	 * @return Box with a horizontal or a vertical strut
	 */
	public static Box createPaddings (boolean vertical) {
		return PaddingService.createPaddings(40, vertical, Color.WHITE);
	}

	
	/**
	 * A box for padding is created, so you can use the box for the WEST, EAST, SOUTH and NORTH of the 
	 * BorderLayout. The Background color is set to white.
	 * 
	 * @param number for padding/distance
	 * @param vertical if vertical is true a vertical strut is created else a horizonal strut
	 * @return Box with a horizontal or a vertical strut
	 */
	public static Box createPaddings (int number, boolean vertical) {
		return PaddingService.createPaddings(number, vertical, Color.WHITE);
	}

	/**
	 * A box for padding is created, so you can use the box for the WEST, EAST, SOUTH and NORTH of the 
	 * BorderLayout. 
	 * 
	 * @param color is the Background color
	 * @param number for padding/distance
	 * @param vertical if vertical is true a vertical strut is created else a horizonal strut
	 * @return Box with a horizontal or a vertical strut
	 */
	public static Box createPaddings (int number, boolean vertical, Color color) {
		
		Box box = new Box(vertical? BoxLayout.Y_AXIS : BoxLayout.X_AXIS);
		box.setBackground(color);
		box.setOpaque(true);
		box.add(vertical? Box.createVerticalStrut(number) : Box.createHorizontalStrut(number));

		return box;
	}

	
}

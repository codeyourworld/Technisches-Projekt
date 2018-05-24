package malen;

import java.util.ArrayList;

public class Numbers {

	public static final ArrayList<Point> ZERO = new ArrayList<>();
	public static final ArrayList<Point> ONE = new ArrayList<>();
	public static final ArrayList<Point> TWO = new ArrayList<>();
	public static final ArrayList<Point> THREE = new ArrayList<>();
	public static final ArrayList<Point> FOUR = new ArrayList<>();
	public static final ArrayList<Point> FIFE = new ArrayList<>();
	public static final ArrayList<Point> SIX = new ArrayList<>();
	public static final ArrayList<Point> SEVEN = new ArrayList<>();
	public static final ArrayList<Point> EIGHT = new ArrayList<>();
	public static final ArrayList<Point> NINE = new ArrayList<>();
	
	
	public static final void fillLists(){
		//  _
		// | |
		// |_|
		ZERO.add(new Point(0, 0));
		ZERO.add(new Point(1, 0));
		ZERO.add(new Point(1, 2));
		ZERO.add(new Point(0, 2));
		ZERO.add(new Point(0, 0));
		
		// /|
		// _|
		ONE.add(new Point(0, 0));
		ONE.add(new Point(1, 0));
		ONE.add(new Point(1, 2));
		ONE.add(new Point(0, 1));
		ONE.add(new Point(1, 2));
		ONE.add(new Point(1, 0));
		ONE.add(new Point(0, 0));

		//  _
		//  _|
		// |_
		TWO.add(new Point(0, 0));
		TWO.add(new Point(1, 0));
		TWO.add(new Point(0, 0));
		TWO.add(new Point(0, 1));
		TWO.add(new Point(1, 1));
		TWO.add(new Point(1, 2));
		TWO.add(new Point(0, 2));
		TWO.add(new Point(1, 2));
		TWO.add(new Point(1, 1));
		TWO.add(new Point(0, 1));
		TWO.add(new Point(0, 0));
		
		//  _
		//  _|
		//  _|
		THREE.add(new Point(0, 0));
		THREE.add(new Point(1, 0));
		THREE.add(new Point(1, 2));
		THREE.add(new Point(0, 2));
		THREE.add(new Point(1, 2));
		THREE.add(new Point(1, 1));
		THREE.add(new Point(0, 1));
		THREE.add(new Point(1, 1));
		THREE.add(new Point(1, 0));
		THREE.add(new Point(0, 0));

		//  
		// |_|
		//  _|
		FOUR.add(new Point(0, 0));
		FOUR.add(new Point(1, 0));
		FOUR.add(new Point(1, 2));
		FOUR.add(new Point(1, 1));
		FOUR.add(new Point(0, 1));
		FOUR.add(new Point(0, 2));
		FOUR.add(new Point(0, 1));
		FOUR.add(new Point(1, 1));
		FOUR.add(new Point(1, 0));
		FOUR.add(new Point(0, 0));

		//  _
		// |_
		//  _|
		FIFE.add(new Point(0, 0));
		FIFE.add(new Point(1, 0));
		FIFE.add(new Point(1, 1));
		FIFE.add(new Point(0, 1));
		FIFE.add(new Point(0, 2));
		FIFE.add(new Point(1, 2));
		FIFE.add(new Point(0, 2));
		FIFE.add(new Point(0, 1));
		FIFE.add(new Point(1, 1));
		FIFE.add(new Point(1, 0));
		FIFE.add(new Point(0, 0));
		
		//  _
		// |_
		// |_|
		SIX.add(new Point(0, 0));
		SIX.add(new Point(0, 2));
		SIX.add(new Point(1, 2));
		SIX.add(new Point(0, 2));
		SIX.add(new Point(0, 1));
		SIX.add(new Point(1, 1));
		SIX.add(new Point(1, 0));
		SIX.add(new Point(0, 0));

		//  _
		//   |
		//  _|
		SEVEN.add(new Point(0, 0));
		SEVEN.add(new Point(1, 0));
		SEVEN.add(new Point(1, 2));
		SEVEN.add(new Point(0, 2));
		SEVEN.add(new Point(1, 2));
		SEVEN.add(new Point(1, 0));
		SEVEN.add(new Point(0, 0));

		//  _
		// |_|
		// |_|
		EIGHT.add(new Point(0, 0));
		EIGHT.add(new Point(1, 0));
		EIGHT.add(new Point(1, 2));
		EIGHT.add(new Point(0, 2));
		EIGHT.add(new Point(0, 1));
		EIGHT.add(new Point(1, 1));
		EIGHT.add(new Point(0, 1));
		EIGHT.add(new Point(0, 0));
		
		//  _
		// |_|
		//  _|
		NINE.add(new Point(0, 0));
		NINE.add(new Point(1, 0));
		NINE.add(new Point(1, 2));
		NINE.add(new Point(0, 2));
		NINE.add(new Point(0, 1));
		NINE.add(new Point(1, 1));
		NINE.add(new Point(1, 0));
		NINE.add(new Point(0, 0));

}
	
}

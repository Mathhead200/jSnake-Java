package snake;

import javax.swing.ImageIcon;
import com.mathhead200.grid.GridItem;


public class SnakeFood extends GridItem
{
	private int pointValue;

	public SnakeFood() {
		super();
		int rand = (int)(Math.random() * 10);
		if( rand >= 9 ) {
			setIcon( new ImageIcon(JSnake.FOOD_FILES[2]) );
			pointValue = 5;
		} else if( rand >= 6 ) {
			setIcon( new ImageIcon(JSnake.FOOD_FILES[1]) );
			pointValue = 2;
		} else {
			setIcon( new ImageIcon(JSnake.FOOD_FILES[0]) );
			pointValue = 1;
		}
	}


	public int getPointValue() {
		return pointValue;
	}
}

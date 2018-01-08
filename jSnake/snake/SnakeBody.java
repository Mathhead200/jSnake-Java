package snake;

import com.mathhead200.grid.*;
import java.util.ArrayList;


public class SnakeBody extends Snake
{
	ArrayList<Dir> dirQueue;

	public SnakeBody(int s) {
		super(JSnake.BODY_FILE);
		dirQueue = new ArrayList<Dir>(s);
		for( int i = 0; i < s; i++ )
			dirQueue.add(Dir.SOUTH);
	}


	protected void bump(GridItem item) throws GridException {}

	public void move(Dir d) throws NullParentBoxException, NullParentGridException, GridItemIsDeadException {
		Dir dir = dirQueue.remove(0);
		super.move(dir);
		dirQueue.add(d);
	}
}

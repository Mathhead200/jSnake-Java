package snake;

import com.mathhead200.grid.*;


public abstract class Snake extends MovableGridItem
{
	public Snake(String url) {
		super(url);
	}

	abstract protected void bump(GridItem item) throws GridException;

	protected void movedOffGrid(int x, int y) throws GridException {
		int[] pos = this.getBoxCord();
		Box[][] boxes = this.getParentGrid().getBoxes();
		int _x = (pos[0] + x + boxes.length) % boxes.length - pos[0],
			_y = (pos[1] + y + boxes[0].length) % boxes[0].length - pos[1];
		move( _x, _y );
	}

	public void move(Dir d) throws NullParentBoxException, NullParentGridException, GridItemIsDeadException {
		move( d == Dir.WEST ? -1 : d == Dir.EAST ? 1 : 0, d == Dir.NORTH ? -1 : d == Dir.SOUTH ? 1 : 0 );
	}

	protected Dir cardinalDirTo(GridItem item) throws NullParentBoxException {
		int[] ic = item.getBoxCord(),
			tc = this.getBoxCord();
		int x = ic[0] - tc[0],
			y = ic[1] - tc[1];
		return x > 0 ? Dir.EAST : x < 0 ? Dir.WEST : y > 0 ? Dir.SOUTH : Dir.NORTH;
	}
}

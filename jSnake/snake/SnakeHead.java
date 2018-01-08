package snake;

import java.util.ArrayList;
import com.mathhead200.grid.*;
import com.mathhead200.MH;


public class SnakeHead extends Snake implements GridActor, Runnable
{
	private Dir dir = Dir.SOUTH;
	private ArrayList<SnakeBody> body;
	private int waitTime;
	private Thread gameThread;
	private boolean hasTurned = false;

	public SnakeHead(Box b, int numOfSnakeBodys, int speed) throws ParentBoxIsSetException {
		super(JSnake.HEAD_FILE);
		try {
			b.addGridItem(this);
		} catch (GridItemIsDeadException e) {}
		body = new ArrayList<SnakeBody>(numOfSnakeBodys);
		waitTime = speed;
		gameThread = new Thread(this, "Game Thread");
		for( int i = 1; i <= numOfSnakeBodys; i++ ) {
			try {
				this.add( new SnakeBody(i) );
			} catch (GridItemIsDeadException e) { e.printStackTrace(); }
		}
	}


	protected void bump(GridItem item) throws GridException {
		if( item instanceof SnakeFood ) {
			SnakeFood food = (SnakeFood)item;
			{
				SnakeBody sb = new SnakeBody(0);
				ArrayList<Dir> q = body.get( body.size() - 1 ).dirQueue;
				sb.dirQueue.add( q.get(0) );
				for( Dir d : q )
					sb.dirQueue.add(d);
				this.add(sb);
			} {
				Dir dir = this.cardinalDirTo(food);
				food.kill();
				JSnake.score += food.getPointValue();
				JSnake.status.setText( "+" + food.getPointValue() + " (" + JSnake.score + " pts)" );
				super.move(dir);
				/* -- Explanation of "super.move(dir)" --
				 * Bump is called by the first line of SnakeHead#move(Dir)
				 * The following lines do not run until this method returns,
				 * so there is no need to move the SnakeBodys in SnakeHead#body twice.
				 */
			} try {
				MH.getRandomEmptyBox( this.getParentGrid() ).addGridItem( new SnakeFood() );
			} catch(NullPointerException e) {
				gameThread.interrupt(); //All boxes are filled, no where to add the new SnakeFood
			}
		} else {
			gameThread.interrupt(); //Bumped into yourself or a barrier
		}
	}

	public void run() {
		JSnake.score = 0;
		long startTime = System.currentTimeMillis();
		do {
			hasTurned = false;
			try {
				move(dir);
				Thread.sleep(waitTime);
			} catch(Exception e) {
				break;
			}
		} while(true);
		long endTime = System.currentTimeMillis();
		JSnake.status.setText(
			"Game over! Score: " + JSnake.score + " pts, Time: " + (endTime - startTime) / 1000 + " sec" );
		for( Box[] bs : JSnake.grid.getBoxes() )
			for( Box b : bs )
				if( b.getGridItem() != null && !(b.getGridItem() instanceof Snake) )
					b.getGridItem().kill();
		this.kill();
		for( SnakeBody sb : body )
			try {
				Thread.sleep(50);
				sb.kill();
			} catch(InterruptedException e) { e.printStackTrace(); }
		JSnake.grid.getMenuBar().getMenu(0).setEnabled(true);
	}

	public void keyDown(int k) throws GridException {
		if( hasTurned )
			return;

		Dir prvDir = dir;

		if( k == 37 && dir != Dir.EAST )
			dir = Dir.WEST;
		else if( k == 38 && dir != Dir.SOUTH )
			dir = Dir.NORTH;
		else if( k == 39 && dir != Dir.WEST )
			dir = Dir.EAST;
		else if( k == 40 && dir != Dir.NORTH )
			dir = Dir.SOUTH;

		if( prvDir != dir )
			hasTurned = true;
	}

	public void keyUp(int k) throws GridException {}
	public void mouseDown(Box box, int b) throws GridException {}
	public void mouseIn(Box box, Dir d) throws GridException {}
	public void mouseOut(Box b, Dir d) throws GridException {}
	public void mouseUp(Box box, int b) throws GridException {}

	public void move(Dir d) throws NullParentBoxException, NullParentGridException, GridItemIsDeadException {
		super.move(d);
		for( SnakeBody sb : body )
			sb.move(d);
	}

	public Thread getGameThread() {
		return gameThread;
	}

	public void add(SnakeBody sb) throws GridItemIsDeadException {
		body.add(sb);
		int x = 0, y = 0;
		for( SnakeBody b : body ) {
			Dir d = b.dirQueue.get(0);
			if( d == Dir.NORTH )
				y += 1;
			else if( d == Dir.SOUTH )
				y -= 1;
			else if( d == Dir.WEST )
				x += 1;
			else if( d == Dir.EAST )
				x -= 1;
		}
		try {
			int[] c = this.getBoxCord();
			Box[][] bs = this.getParentGrid().getBoxes();
			this.getParentGrid().getBoxAt(
				( c[0] + x + bs.length ) % bs.length,
				( c[1] + y + bs[0].length ) % bs[0].length
			).addGridItem(sb);
		} catch(ParentBoxIsSetException e) {
			e.printStackTrace();
		} catch(NullParentBoxException e) {
			e.printStackTrace();
		}
	}
}

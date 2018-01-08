package com.mathhead200.grid;

import javax.swing.ImageIcon;
import java.awt.Image;


/**
 * As subclass of GridItem,
 * a MovableGridItem represents a GridItem that will be capable of moving from Box to Box
 * as to not be limited to its original parent Box and in turn move around the Grid.
 * @author Christopher D'Angelo
 * @author JBD Computers &trade;
 * @version 2.0
 * @see <a href="grid/Read Me.html#MovableGridItem">Read Me.html</a>
 */
public abstract class MovableGridItem extends GridItem
{
	/**
	 * calls - <code>super( url, n, b )</code>
	 * @param url - location of icon used to display the item
	 * @param n - name
	 * @param b - parent box
	 * @throws ParentBoxIsSetException
	 * @see GridItem#GridItem(String, String, Box)
	 */
	public MovableGridItem(String url, String n, Box b) throws ParentBoxIsSetException {
		super(url, n, b);
	}

	/**
	 * calls - <code>super( url, n )</code>
	 * @param url - location of icon used to display the item
	 * @param n - name
	 * @see GridItem#GridItem(String, String)
	 */
	public MovableGridItem(String url, String n) {
		super(url, n);
	}

	/**
	 * calls - <code>super( url, b )</code>
	 * @param url - location of icon used to display the item
	 * @param b - name
	 * @throws ParentBoxIsSetException
	 * @see GridItem#GridItem(String, Box)
	 */
	public MovableGridItem(String url, Box b) throws ParentBoxIsSetException {
		super(url, b);
	}

	/**
	 * calls - <code>super( url )</code>
	 * @param url - location of icon used to display the item
	 * @see GridItem#GridItem(String)
	 */
	public MovableGridItem(String url) {
		super(url);
	}

	/**
	 * calls - <code>super()</code>
	 * @see GridItem#GridItem()
	 */
	public MovableGridItem() {
		super();
	}

	/**
	 * calls - <code>super( ico, n )</code>
	 * @param ico - icon
	 * @param n - name
	 * @see GridItem#GridItem(ImageIcon, String)
	 */
	public MovableGridItem(Image ico, String n) {
		super(ico, n);
	}

	/**
	 * calls - <code>super( ico, n, b )</code>
	 * @param ico - icon
	 * @param n - name
	 * @param b - a box to place the GridItem in
	 * @throws ParentBoxIsSetException
	 * @see GridItem#GridItem(ImageIcon, String, Box)
	 */
	public MovableGridItem(Image ico, String n, Box b) throws ParentBoxIsSetException {
		super(ico, n, b);
	}

	/**
	 * calls - <code>super( ico, b )</code>
	 * @param ico - icon
	 * @param b - a box to add the GridItem to
	 * @throws ParentBoxIsSetException
	 * @see GridItem#GridItem(ImageIcon, Box)
	 */
	public MovableGridItem(Image ico, Box b) throws ParentBoxIsSetException {
		super( ico, b );
	}

	/**
	 * calls - <code>super( ico )</code>
	 * @param ico - icon
	 * @see GridItem#GridItem(ImageIcon)
	 */
	public MovableGridItem(Image ico) {
		super( ico );
	}


	/**
	 * invoked automatically when the {@link #move(int, int)} method causes the item to try and move to a box
	 * that already contains another GridItem
	 * @see #move(int, int)
	 * @param item - the target box's item
	 * @throws GridException
	 */
	abstract protected void bump(GridItem item) throws GridException;

	/**
	 * invoked automatically when the move() method causes the item to try and move
	 * passed the bounds of its parent Grid
	 * @see #move(int, int)
	 * @param x - the first int value used when {@link #move(int, int)} was called
	 * @param y - the second int value used when {@link #move(int, int)} was called
	 * @throws GridException
	 */
	abstract protected void movedOffGrid(int x, int y) throws GridException;

	/**
	 * moves the item <b>x</b> boxes right (east) and <b>y</b> boxes down (south),
	 * this method was made to be called to from within some public method of a subclass of MovableGridItem,
	 * i.e. - <b>{ <br />
	 * &nbsp; Bug b = new Bug(); <br />
	 * &nbsp; b.moveNorth(); <br />
	 * }</b> <i>//assuming</i> <br />
	 * <b>public class Bug extends MovableGridItem { <br />
	 * &nbsp; public void moveNorth() { move( 0, -1 ); } <br />
	 * }</b>
	 * @param x - spaces to move right (negative values move left)
	 * @param y - spaces to move down (negative values move up)
	 * @throws NullParentBoxException - if the item is not in a box
	 * @throws NullParentGridException - if the item's parent Box is not in a Grid
	 * @throws GridItemIsDeadExcpetion - if the item has invoked kill()
	 */
	protected void move(int x, int y) throws NullParentBoxException, NullParentGridException, GridItemIsDeadException
	{
		if( this.getParentBox() == null ) {
			throw new NullParentBoxException(); }
		else if( this.getParentBox().getParentGrid() == null ) {
			throw new NullParentGridException(); }

		int[] cord = this.getParentBox().getBoxCord();
		try {
			Box destBox = this.getParentBox().getParentGrid().getBoxAt(cord[0]+x, cord[1]+y);

			if( destBox.getGridItem() != null ) {
				try {
					this.bump( destBox.getGridItem() );
				} catch(GridException e) {
					e.printStackTrace( System.out );
				}
			}
			else {
				destBox.setGridItem(this);
			}
		}
		catch(IndexOutOfBoundsException e) {
			try {
				this.movedOffGrid(x, y);
			} catch(GridException f) {
				f.printStackTrace( System.out );
			}
		}
	}
}

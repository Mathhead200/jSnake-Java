package com.mathhead200.grid;


/**
 * This interface was made to be implemented by a GridItem.
 * It allows the item to act on events sent from the keyboard to its parent grid.
 * @author Christopher D'Angelo
 * @author JBD Computers &trade;
 * @version 1.0
 * @see <a href="grid/Read Me.html#GridActor">Read Me.html</a>
 */
public interface GridActor
{
	/**
	 * invoked when a key is released
	 * @param k - key code
	 * @throws GridException
	 */
	public void keyUp(int k) throws GridException;

	/**
	 * invoked when a key is pressed down
	 * @param k - key code
	 * @throws GridException
	 */
	public void keyDown(int k) throws GridException;

	/**
	 * invoked when a mouse button is pressed down
	 * @param b - button code
	 * @throws GridException
	 */
	public void mouseDown(Box box, int b) throws GridException;

	/**
	 * invoked when a mouse button is released
	 * @param b - button code
	 * @throws GridException
	 */
	public void mouseUp(Box box, int b) throws GridException;

	/**
	 * invoked when the mouse enters the parent box
	 * @param d - direction
	 * @throws GridException
	 */
	public void mouseIn(Box box, Dir d) throws GridException;

	/**
	 * invoked when the mouse exits the parent box
	 * @param d - direction
	 * @throws GridException
	 */
	public void mouseOut(Box b, Dir d) throws GridException;
}

package com.mathhead200.grid;

import javax.swing.*;
import java.awt.*;


/**
 * These are items to be placed on the 2D grid.
 * All GridItems can be placed within a parent Box.
 * If that Box is on a Grid then the GridItem will display there.
 * @author Christopher D'Angelo
 * @author JBD Computers &trade;
 * @version 2.0
 * @see <a href="grid/Read Me.html#GridItem">Read Me.html</a>
 */
public abstract class GridItem
{
	private Box parentBox;
	private ImageIcon icon;
	private String name;
	private boolean alive = true;

	/**
	 * creates a new GridItem
	 * @param url - where the icon is found used to display the GridItem
	 * @param n - name
	 */
	public GridItem(String url, String n)
	{
		icon = new ImageIcon(url);
		name = n;
	}

	/**
	 * synonymous with - <code>{ <br />
	 * &nbsp; GridItem g = new GridItem( url, n ); <br />
	 * &nbsp; b.addGridItem( g ); <br />
	 * }</code>
	 * @param url - where the icon is found to display the GridItem
	 * @param n - name
	 * @param b - a box to place the GridItem in
	 * @throws ParentBoxIsSetException
	 * @see #GridItem(String, String)
	 * @see Box#addGridItem(GridItem)
	 */
	public GridItem(String url, String n, Box b) throws ParentBoxIsSetException
	{
		this( url, n );
		try {
			b.addGridItem(this);
		} catch(GridItemIsDeadException e) {}
	}

	/**
	 * synonymous with - <code>new GridItem( url, "", b )</code>;
	 * @param url - where the icon is found to display the GridItem
	 * @param b - a box to place the GridItem in
	 * @throws ParentBoxIsSetException
	 * @see #GridItem(String, String, Box)
	 */
	public GridItem(String url, Box b) throws ParentBoxIsSetException {
		this( url, "", b );
	}

	/**
	 * synonymous with - <code>new GridItem(url, "");</code>
	 * @param url - where the icon is found to display the GridItem
	 * @see #GridItem(String, String)
	 */
	public GridItem(String url) {
		this( url, "" );
	}

	/**
	 * synonymous with - <code>new GridItem("grid/default_griditem.dmi");</code> -
	 * this icon is supplied by the grid package
	 * @see #GridItem(String)
	 */
	public GridItem() {
		this("com/mathhead200/grid/default_griditem.dmi");
	}

	/**
	 * creates a new GridItem
	 * @param ico - icon
	 * @param n - name
	 */
	public GridItem(Image ico, String n)
	{
		icon = new ImageIcon(ico);
		name = n;
	}

	/**
	 * synonymous with - <code>{ <br />
	 * &nbsp; GridItem g = new GridItem( ico, n ) <br />
	 * &nbsp; b.addGridItem(g); <br />
	 * }</code>
	 * @param ico - icon
	 * @param n - name
	 * @param b - a box to place the GridItem in
	 * @throws ParentBoxIsSetException
	 */
	public GridItem(Image ico, String n, Box b) throws ParentBoxIsSetException
	{
		this( ico, n );
		try {
			b.addGridItem(this);
		} catch(GridItemIsDeadException e) {}
	}

	/**
	 * synonymous with - <code>new GridItem( ico, "", b );</code>
	 * @param ico - icon
	 * @param b - a box to add the GridItem to
	 * @throws ParentBoxIsSetException
	 */
	public GridItem(Image ico, Box b) throws ParentBoxIsSetException {
		this( ico, "", b );
	}

	/**
	 * synonymous with - <code>new GridItem( ico, "" );</code>
	 * @param ico - icon
	 */
	public GridItem(Image ico) {
		this( ico, "" );
	}


	public String toString() {
		return name;
	}


	/**
	 * @return the box with which this GridItem belongs to
	 */
	public Box getParentBox() {
		return parentBox;
	}

	/**
	 * synonymous with - <b>this.getParentBox().getParentGrid()</b>
	 * @throws NullParentBoxException - if getParentBox() returns null
	 * @see Box#getParentGrid()
	 */
	public Grid getParentGrid() throws NullParentBoxException {
		if( this.parentBox == null ) {
			throw new NullParentBoxException();
		}
		return parentBox.getParentGrid();
	}

	/**
	 * @return the javax.swing.ImageIcon that is used to display your GridItem
	 */
	public ImageIcon getIcon() {
		return this.icon;
	}

	/**
	 * @return the name of this GridItem,
	 * the name of a GridItem is a String that represents the GridItem,
	 * as well the name will be displayed on hover
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * synonymous with - <b>this.getParentBox().getBoxCord()</b>
	 * @return
	 * @throws NullParentBoxException - if getParentBox() returns null
	 * @see Box#getBoxCord()
	 */
	public int[] getBoxCord() throws NullParentBoxException {
		if( this.getParentBox() == null ) {
			throw new NullParentBoxException(); }
		return this.getParentBox().getBoxCord();
	}

	/**
	 * @return false if the GridItem has ever invoked the kill() method, else it returns true
	 */
	public boolean isAlive() {
		return this.alive;
	}


	void setParentBox(Box b) {
		this.parentBox = b;
	}

	/**
	 * sets the GridItems icon
	 * @param i - the icon that will be used to display the GridItem
	 */
	public void setIcon(ImageIcon i) {
		this.icon = i;
		if( this.getParentBox() != null )
			this.getParentBox().setIcon(i);
	}

	/**
	 * set the GridItem's name
	 * @param n - name
	 */
	public void setName(String n) {
		this.name = n;
	}

	/**
	 * synonymous with - <b>b.addGridItem(this)</b>
	 * @param b - intended parent box
	 * @throws ParentBoxIsSetException - if the GridItem has a parent box
	 * @throws GridItemIsDeadException - if the GridItem has invoked kill()
	 * @see Box#addGridItem(GridItem)
	 */
	public void addToBox(Box b) throws ParentBoxIsSetException, GridItemIsDeadException {
		b.addGridItem(this);
	}

	/**
	 * removes the GridItem from its parent box ergo removing it from the grid
	 * @throws GridItemIsDeadException - if the GridItem has even invoked the kill() method
	 * @throws NullParentBoxException - if the GridItem dosn't belong to any parent box
	 */
	public void removeFromGrid() throws GridItemIsDeadException, NullParentBoxException {
		if( !this.isAlive() ) {
			throw new GridItemIsDeadException(); }
		else if( this.getParentBox() == null ) {
			throw new NullParentBoxException(); }

		this.getParentBox().setGridItem( null );
		this.parentBox = null;
	}

	/**
	 * used to delete the GridItem and insure it can not be re-placed in any box
	 */
	public void kill() {
		try {
			this.removeFromGrid();
		} catch(GridItemIsDeadException e) {
		} catch(NullParentBoxException e) {
		}
		this.alive = false;
	}

	/**
	 * a fail-safe that will allow the GridItem to behave as if kill() was never invoked on it
	 */
	public void revive() {
		this.alive = true;
	}

	/**
	 * called automatically by {@link Grid#save(String)} or {@link Grid#overwrite(String)}
	 * @return a unique save-string that holds enough information to reconstruct the object
	 * via the {@link #load(String)} method. Should not contain any line breaks!
	 */
	public String save() {
		return this.getIcon().getDescription().replace(";", ";;") + ";" + this.getName();
	}

	/**
	 * called automatically by {@link Grid#load(String)},
	 * must override for all subclasses of {@link #GridItem} that wish to be able to save and load properly,
	 * append a NonSavable annotation to the classes which do not to avoid throwing errors at load time
	 * @param save - the same unique save-string returned by {@link #save(String)}
	 * @return
	 */
	public static GridItem load(String save) {
		return null;
	}

	/**
	 * @param save - the same unique save-string returned by {@link #save(String)}
	 * @return an array to retrieve properties for a general GridItem save string
	 * assuming {@link #save()} was called from this abstract parent class
	 */
	protected static String[] loadStrings(String save) {
		java.util.regex.Pattern patt = java.util.regex.Pattern.compile("(;;)*;");
		java.util.regex.Matcher match = patt.matcher(save);
		java.util.ArrayList<String> loadStrings = new java.util.ArrayList<String>();
		int lastEnd = 0;
		while( match.find() )
			loadStrings.add( save.substring(lastEnd, (lastEnd = match.end()) - 1) );
		loadStrings.add( save.substring(lastEnd) );
		return loadStrings.toArray(new String[0]);
	}
}

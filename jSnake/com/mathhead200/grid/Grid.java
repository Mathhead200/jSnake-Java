package com.mathhead200.grid;

import java.awt.*;

import javax.swing.*;
import java.io.*;


/**
 * The 2-dimensional world in which all grid programs exist.
 * Having only width and height all grids contain a matrix of Boxes in which to store their GridItems.
 * @author Christopher D'Angelo
 * @author JBD Computers &trade;
 * @version 2.1
 * @see <a href="grid/Read Me.html#Grid">Read Me.html</a>
 */
@SuppressWarnings("serial")
public class Grid extends JFrame
{
	private final JPanel myStuff = new JPanel(new GridBagLayout());
	private Box[][] boxes;
	private String srcFile = null;
	/**
	 * represents the attributes that the grid was created with
	 */
	protected byte attributes = 0;

	/**
	 * <i>attribute:</i> do not call to {@link Grid#setVisible(true)} at creation
	 */
	public static final byte HIDDEN = 1 << 0;
	public static final byte CENTERED = 1 << 1;


	/**
	 * creates a grid
	 * @param x - width
	 * @param y - height
	 * @param t - background color
	 * @param a - attributes added as a list of arguments or separated by the byte-wise "or" operator <code>|</code>
	 */
	public Grid(int x, int y, Color t, byte...a)
	{
		for( byte val : a ) {
			this.attributes |= val;
		}

		this.setTitle("Grid");
		boxes = new Box[x][y];
		for( int i=0; i<x; i++ ) {
			for( int j=0; j<y; j++ ) {
				boxes[i][j] = new Box(t);
				boxes[i][j].setParentGrid(this);

				GridBagConstraints c = new GridBagConstraints();
				c.gridx = i;
				c.gridy = j;
				this.myStuff.add(boxes[i][j], c);
			}
		}
		this.setContentPane(myStuff);
		this.pack();
		if( this.hasAttribute(Grid.CENTERED) ) {
			Dimension screenSize = this.getToolkit().getScreenSize();
			this.setLocation(
				( screenSize.width - this.getWidth() ) / 2,
				( screenSize.height - this.getHeight() ) / 2
			);
		}
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setVisible( !this.hasAttribute(Grid.HIDDEN) );
		GridEventHandler gridHandler = new GridEventHandler(this);
		this.addKeyListener( gridHandler );
	}

	/**
	 * synonymous with - <b>Grid( x, y, new java.awt.Color(0xffffff) )</b>
	 * @param x - width
	 * @param y - height
	 * @param a - attributes, added as a list of arguments or separated by the byte-wise "or" operator <code>|</code>
	 * @see #Grid(int, int, Color)
	 */
	public Grid(int x, int y, byte...a) {
		this( x, y, new Color(0xffffff), a );
	}


	public String toString() {
		String outStr = "{";
		for( int i = 0; i < boxes.length; i++ ) {
			outStr += "{";
			for( int j = 0; j < boxes[0].length; j++ ) {
				outStr += boxes[i][j];
				if( j < boxes[0].length - 1 )
					outStr += ",";
			}
			outStr += "}";
			if( i < boxes.length - 1 )
				outStr += ",";
		}
		return outStr + "}";
	}


	/**
	 * @return a multidimensional array of boxes that make up the grid
	 */
	public Box[][] getBoxes() {
		return this.boxes;
	}

	/**
	 * synonymous with - <b>getBoxes()[x][y]</b>
	 * @param x - x position, start at 0, oriented left to right
	 * @param y - y position, start at 0, oriented top to bottom
	 * @return a box found at coordinate (x, -y) on the grid,
	 *  where the upper-left most box of the grid is at the origin
	 * @see #getBoxes()
	 */
	public Box getBoxAt(int x, int y) {
		return this.boxes[x][y];
	}

	/**
	 * @param b - target box
	 * @return two integers in the form of {x, y} where - <b>getBoxAt(x, y)</b> - would return <b>b</b>,
	 *  or <b>null</b> if <b>b</b> dosn't exist on the grid
	 */
	public int[] findBoxCord(Box b) {
		for( int i=0; i<this.boxes.length; i++ ) {
			for( int j=0; j<this.boxes[0].length; j++ ) {
				if( b.equals(this.boxes[i][j]) ) {
					return new int[]{i, j};
				}
			}
		}
		return null;
	}

	/**
	 * synonymous with - <b>findBoxCord( i.getParentBox() )</b>
	 * @param i - target item
	 * @throws NullParentBoxException - if <b>i</b> dosn't have a parent Box
	 * @see #findBoxCord(Box)
	 */
	public int[] findBoxCord(GridItem i) throws NullParentBoxException {
		if( i.getParentBox() == null ) {
			throw new NullParentBoxException(); }
		return this.findBoxCord( i.getParentBox() );
	}


	/**
	 * sets all the boxes in the grid to those in the <b>b</b> matrix
	 * @param b - a box matrix to replace the ones on currently the grid
	 * @throws WrongGridSizeException - if <b>b</b>'s size is different from that of the size of the grid
	 */
	public void setBoxes(Box[][] b) throws WrongGridSizeException
	{
		if( b.length != this.boxes.length || b[0].length != this.boxes[0].length ) {
			throw new WrongGridSizeException();
		}
		for( int x=0; x<b.length; x++ ) {
			for( int y=0; y<b[0].length; y++ ) {
				setBoxAt( b[x][y], x, y, false );
			}
		}
		this.validate();
	}

	private void setBoxAt(Box b, int x, int y, boolean validate)
	{
		this.myStuff.remove(this.boxes[x][y]);

		this.boxes[x][y] = b;
		b.setParentGrid(this);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		this.myStuff.add(b, c);
		if(validate)
			this.myStuff.validate();
	}

	/**
	 * sets the box at coordinate (x, -y) in the grid to b,
	 * where the upper-left most box is at the origin
	 * @param b - box to put on the grid
	 * @param x - x position, start at 0, oriented left to right
	 * @param y - y position, start at 0, oriented top to bottom
	 */
	public void setBoxAt(Box b, int x, int y) {
		this.setBoxAt(b, x, y, true);
	}

	/**
	 * @return all GridItems that exist on this Grid and are also GridActors
	 */
	public GridActor[] findGridActors() {
		GridActor[] a = new GridActor[0];
		Box[][] b = this.getBoxes();
		for( int i=0; i<b.length; i++ ) {
			for( int j=0; j<b[0].length; j++ ) {
				if( b[i][j].getGridItem() instanceof GridActor ) {
					GridActor[] newA = new GridActor[a.length + 1];
					System.arraycopy(a, 0, newA, 0, a.length);
					newA[newA.length - 1] = (GridActor)b[i][j].getGridItem();
					a = newA;
				}
			}
		}
		return a;
	}

	/**
	 * called automatically when a key is pressed down on the keyboard,
	 * causes all GridActors on the grid to invoke - <b>keyDown( k )</b>
	 * @param k - key code
	 * @throws GridException
	 * @see GridActor#keyDown(int)
	 */
	protected void keyWasPressed(int k) throws GridException {
		GridActor[] actors = this.findGridActors();
		for( GridActor a : actors ) {
			a.keyDown(k);
		}
	}

	/**
	 * called automatically when a key is released on the keyboard,
	 * causes all GridActors on the grid to invoke - <b>keyUp( k )</b>
	 * @param k - key code
	 * @throws GridException
	 * @see GridActor#keyUp(int)
	 */
	protected void keyWasReleased(int k) throws GridException {
		GridActor[] actors = this.findGridActors();
		for( GridActor a : actors ) {
			a.keyUp(k);
		}
	}


	/**
	 * saves the current grid and items to file, will not overwrite if file already exists,
	 * use a .jgs (short for a "Java Grid Source" file) extension
	 * @param filename - the name of the savefile
	 * @throws IOException
	 * @return false if a file or directory with <b>filename</b> already exists
	 * or if the file couldn't be created, true if the file was created
	 * @see #overwrite(String)
	 */
	public boolean save(String filename) throws IOException, NonSavableGridException {
		File fileOut = new File(filename);
		if( fileOut.createNewFile() ) {
			overwrite(filename);
			return true;
		}
		return false;
	}

	/**
	 * saves the current grid and items to file
	 * @param filename - the name of the savefile
	 * @throws IOException
	 * @see #save(String)
	 */
	public void overwrite(String filename) throws IOException, NonSavableGridException {
		if( this.getClass().isAnnotationPresent(NonSavable.class) )
			throw new NonSavableGridException( this.getClass().getName() );
		FileWriter fOut = new FileWriter( new File(filename) );
		String endl = String.format("%n");
		fOut.write( this.getBoxes().length + "x" + this.getBoxes()[0].length + endl );
		Integer color = null;
		for( Box[] bs : this.getBoxes() ) {
			for( Box b : bs ) {
				Integer c = b.getTurf().getRGB();
				if( c.equals(color) )
					fOut.write("\"");
				else {
					fOut.write( ""+c );
					color = c;
				}
				fOut.write(
					(
					b.getGridItem() != null
						&& !b.getClass().isAnnotationPresent(NonSavable.class)
						&& !b.getGridItem().getClass().isAnnotationPresent(NonSavable.class) ?
					":" + b.getGridItem().getClass().getName() + endl + b.getGridItem().save() :
					""
					) + endl
				);
			}
		}
		fOut.close();
	}

	/**
	 * loads a grid from file
	 * @param filename - the name of the save file
	 * @param a - attributes added as a list of arguments or separated by the byte-wise "or" operator <code>|</code>
	 * @return a copy of the Grid saved by {@link #save(String)} or {@link #overwrite(String)}
	 * @throws IOException - If file dosn't exist
	 * @throws NumberFormatException - if there was an error in the save file
	 * @throws ArrayIndexOutOfBoundsException - if there was an error in the save file
	 */
	public static Grid load(String filename, byte...a) throws IOException {
		BufferedReader fIn = new BufferedReader( new FileReader(new File(filename)) );
		String[] s = fIn.readLine().split("x", 2);
		Grid grid = new Grid( Integer.parseInt(s[0]), Integer.parseInt(s[1]), a );
		int i = 0;
		Integer color = null;
		for( String line = fIn.readLine(); line != null; line = fIn.readLine() ) {
			if( line.substring(0, 1).equals("#") )
				continue;
			int colonPos = line.indexOf(":");
			String colorNum = line.substring( 0, colonPos != -1 ? colonPos : line.length() );
			int c;
			if( colorNum.equals("\"") )
				c = color;
			else {
				c = Integer.parseInt(colorNum);
				color = c;
			}
			grid.getBoxAt( i / grid.getBoxes()[0].length, i % grid.getBoxes()[0].length ).setTurf(
				new java.awt.Color(c) );
			if( colonPos != -1 ) {
				try {
					grid.getBoxAt( i / grid.getBoxes()[0].length, i % grid.getBoxes()[0].length ).addGridItem(
						(GridItem)Class.forName( line.substring(colonPos+1) ).getMethod("load", String.class).invoke(null, fIn.readLine() )
					);
				} catch(ParentBoxIsSetException e) {
				} catch(GridItemIsDeadException e) {
				} catch(NoSuchMethodException e) {
				} catch(java.lang.reflect.InvocationTargetException e) {
				} catch(IllegalAccessException e) {
				} catch(ClassNotFoundException e) {
					System.err.println("Class " + line.substring(colonPos+1) + " could not be found.");
				}
			}
			i++;
		}
		fIn.close();

		grid.srcFile = filename;
		return grid;
	}

	/**
	 * get the file name used to load the current Grid
	 * @return the file name used to load the current Grid,
	 * or <b>null</b> if the Grid was not created via {@link #load(String)}
	 */
	public String getSrcFile() {
		return this.srcFile;
	}

	/**
	 * @param a - attribute or attributes in question
	 * @return true if this Grid has all the attribute(s), false otherwise
	 * or false if called with <b>a</b>.length == 0
	 */
	protected boolean hasAttribute(byte...a) {
		byte $_ = 0;
		for( byte b : a )
			$_ |= b;
		return (this.attributes & $_) == $_;
	}
}

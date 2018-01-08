package com.mathhead200.grid;


/**
 * GridItemIsDeadException is thrown when particular methods are invoked on a GridItem that has been killed.
 * @author Christopher D'Angelo
 * @author JBD Computers &trade;
 * @version 1.1
 * @see GridItem#kill()
 */
@SuppressWarnings("serial")
public class GridItemIsDeadException extends GridException
{
	public GridItemIsDeadException() {
		super();
	}

	public GridItemIsDeadException(String message) {
		super(message);
	}

	public GridItemIsDeadException(String message, Throwable cause) {
		super(message, cause);
	}

	public GridItemIsDeadException(Throwable cause) {
		super(cause);
	}
}

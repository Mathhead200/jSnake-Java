package com.mathhead200.grid;


/**
 * GridException is a super class for all Exceptions that can be thrown in the grid.* package.
 * @author Christopher D'Angelo
 * @author JBD Computers &trade;
 * @version 1.1
 */
@SuppressWarnings("serial")
public class GridException extends Exception
{
	public GridException() {
		super();
	}

	public GridException(String message) {
		super(message);
	}

	public GridException(String message, Throwable cause) {
		super(message, cause);
	}

	public GridException(Throwable cause) {
		super(cause);
	}
}

package com.mathhead200.grid;


/**
 * WrongGridSizeException is thrown exclusively by {@link Grid#setBoxes(Box[][])}.
 * @author Christopher D'Angelo
 * @author JBD Computers &trade;
 * @version 1.1
 * @see Grid#setBoxes(Box[][])
 */
@SuppressWarnings("serial")
public class WrongGridSizeException extends GridException
{
	public WrongGridSizeException() {
		super();
	}

	public WrongGridSizeException(String message) {
		super(message);
	}

	public WrongGridSizeException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongGridSizeException(Throwable cause) {
		super(cause);
	}
}

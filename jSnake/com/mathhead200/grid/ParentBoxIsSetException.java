package com.mathhead200.grid;


/**
 * ParentBoxIsSetException is thrown when a GridItem attempts to add itself to a Box, but has already
 * been previously added to one.
 * @author Christopher D'Angelo
 * @author JBD Computers &trade;
 * @version 1.1
 * @see GridItem#removeFromGrid()
 */
@SuppressWarnings("serial")
public class ParentBoxIsSetException extends GridException
{
	public ParentBoxIsSetException() {
		super();
	}

	public ParentBoxIsSetException(String message) {
		super(message);
	}

	public ParentBoxIsSetException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParentBoxIsSetException(Throwable cause) {
		super(cause);
	}
}

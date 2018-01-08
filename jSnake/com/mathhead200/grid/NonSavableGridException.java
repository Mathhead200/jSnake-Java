package com.mathhead200.grid;


@SuppressWarnings("serial")
public class NonSavableGridException extends GridException
{
	public NonSavableGridException() {
		super();
	}

	public NonSavableGridException(String message) {
		super(message);
	}

	public NonSavableGridException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonSavableGridException(Throwable cause) {
		super(cause);
	}
}

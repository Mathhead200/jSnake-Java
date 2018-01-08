package com.mathhead200.grid;

import java.awt.event.*;


/**
 * @author Christopher D'Angelo
 * @author JBD Computer &trade;
 * @version 1.0a
 */
class GridEventHandler implements KeyListener
{
	private final Grid grid;

	GridEventHandler(Grid g) {
		grid = g;
	}

	//KeyListener methods
	public void keyPressed(KeyEvent e) {
		try {
			grid.keyWasPressed( e.getKeyCode() );
		} catch(GridException f) {
			f.printStackTrace( System.out );
		}
	}

	public void keyReleased(KeyEvent e) {
		try {
			grid.keyWasReleased( e.getKeyCode() );
		} catch(GridException f) {
			f.printStackTrace( System.out );
		}
	}

	public void keyTyped(KeyEvent e) {}
}

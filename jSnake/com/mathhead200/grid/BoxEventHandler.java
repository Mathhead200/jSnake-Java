package com.mathhead200.grid;

import java.awt.event.*;


public class BoxEventHandler implements MouseListener
{
	private final Box box;

	BoxEventHandler(Box b) {
		box = b;
	}

	private Dir getDir(java.awt.Point p) {
		if( p.y < 8 )
			if( p.x < 8 ) return Dir.NORTHWEST;
			else if( p.x < 24 ) return Dir.NORTH;
			else return Dir.NORTHEAST;
		else if( p.y < 24 )
			if( p.x < 8 ) return Dir.WEST;
			else if( p.x < 24 ) return null;
			else return Dir.EAST;
		else
			if( p.x < 8 ) return Dir.SOUTHWEST;
			else if( p.x < 24 ) return Dir.SOUTH;
			else return Dir.SOUTHEAST;
	}

	//MouseListener
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {
		try {
			box.mouseWasEntered( getDir(e.getPoint()) );
		} catch(GridException f) {
			f.printStackTrace( System.out );
		}
	}

	public void mouseExited(MouseEvent e) {
		try {
			box.mouseWasExited( getDir(e.getPoint()) );
		} catch(GridException f) {
			f.printStackTrace( System.out );
		}
	}

	public void mousePressed(MouseEvent e) {
		try {
			box.mouseWasPressed( e.getButton() );
		} catch(GridException f) {
			f.printStackTrace( System.out );
		}
	}

	public void mouseReleased(MouseEvent e) {
		try {
			box.mouseWasReleased( e.getButton() );
		} catch(GridException f) {
			f.printStackTrace( System.out );
		}
	}
}

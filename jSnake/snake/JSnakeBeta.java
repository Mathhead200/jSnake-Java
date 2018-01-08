package snake;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class JSnakeBeta extends JFrame
{
	private enum Dir {
		NORTH, EAST, SOUTH, WEST
	}

	private JLabel gameArea = new JLabel();

	public JSnakeBeta() {
		super("jSnake");
		this.setContentPane( new JPanel( new GridBagLayout() ) );
		this.getContentPane().add( gameArea, new GridBagConstraints() );

		this.setSize(800, 600);
		this.centerFrame();
		this.setMenuBar( new MenuBar() );
		this.getMenuBar().add( new Menu("Start") );
		this.getMenuBar().getMenu(0).add( new MenuItem("Slow") );
		this.getMenuBar().getMenu(0).getItem(0).addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame(1);
			}
		});
		this.getMenuBar().getMenu(0).add( new MenuItem("Medium") );
		this.getMenuBar().getMenu(0).getItem(1).addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame(2);
			}
		});
		this.getMenuBar().getMenu(0).add( new MenuItem("Fast") );
		this.getMenuBar().getMenu(0).getItem(2).addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame(4);
			}
		});

		this.getContentPane().setBackground( new Color(0xbbccee) );

		gameArea.setPreferredSize( new Dimension(750, 500) );
		gameArea.setMinimumSize( new Dimension(700, 500) );
		gameArea.setBorder( new LineBorder(Color.GRAY, 3) );
		gameArea.setBackground(Color.WHITE);
		gameArea.setOpaque(true);

		this.validate();
		this.setVisible(true);
	}


	public void centerFrame() {
		Dimension fDim = this.getSize(),
			sDim = this.getToolkit().getScreenSize();
		this.setLocation( (sDim.width - fDim.width) / 2, (sDim.height - fDim.height) / 2 );
	}

	public void startGame(int speed) {
		//play game!
	}


	public static void main(String[] args) {
		new JSnakeBeta();
	}
}

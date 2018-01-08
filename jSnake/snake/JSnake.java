package snake;

import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import com.mathhead200.MH;
import com.mathhead200.grid.*;


public class JSnake
{
	private static class Key {
		public static final String
			WIDTH = "width",
			HEIGHT = "height",
			HEAD_FILE = "snake.head",
			BODY_FILE = "snake.body",
			FOOD_FILES = "snake.food";
	}

	private static class Default {
		public static final String
			WIDTH = "45",
			HEIGHT = "30",
			HEAD_FILE = "res/head.png",
			BODY_FILE = "res/body.png",
			FOOD_FILES[] = new String[] {
				"res/food_0.png",
				"res/food_1.png",
				"res/food_2.png"
			};
	}

	public static final File SETTINGS_FILE = new File("res/settings.properties");
	public static final int WIDTH, HEIGHT;
	public static final String HEAD_FILE, BODY_FILE, FOOD_FILES[] = new String[Default.FOOD_FILES.length];

	static {
		Properties setProp = new Properties();
		if( !SETTINGS_FILE.isFile() ) {
			try{
				SETTINGS_FILE.createNewFile();
				FileOutputStream fOut = new FileOutputStream(SETTINGS_FILE);
				setProp.setProperty( Key.WIDTH, Default.WIDTH );
				setProp.setProperty( Key.HEIGHT, Default.HEIGHT );
				setProp.setProperty( Key.HEAD_FILE, Default.HEAD_FILE );
				setProp.setProperty( Key.BODY_FILE, Default.BODY_FILE );
				for( int i = 0; i < FOOD_FILES.length; i++ )
					setProp.setProperty( Key.FOOD_FILES + "." + i, Default.FOOD_FILES[i] );
				setProp.store(fOut, "jSnake Settings");
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				WIDTH = Integer.parseInt(Default.WIDTH);
				HEIGHT = Integer.parseInt(Default.HEIGHT);
				HEAD_FILE = Default.HEAD_FILE;
				BODY_FILE = Default.BODY_FILE;
				for( int i = 0; i < FOOD_FILES.length; i++ )
					FOOD_FILES[i] = Default.FOOD_FILES[i];
			}
		} else {
			try {
				FileInputStream fIn = new FileInputStream(SETTINGS_FILE);
				setProp.load(fIn);
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				WIDTH = Integer.parseInt( setProp.getProperty( Key.WIDTH, Default.WIDTH ) );
				HEIGHT = Integer.parseInt( setProp.getProperty( Key.HEIGHT, "30" ) );
				HEAD_FILE = setProp.getProperty( Key.HEAD_FILE, Default.HEAD_FILE );
				BODY_FILE = setProp.getProperty( Key.BODY_FILE, Default.BODY_FILE );
				for( int i = 0; i < FOOD_FILES.length; i++ )
					FOOD_FILES[i] = setProp.getProperty( Key.FOOD_FILES + "." + i, Default.FOOD_FILES[i] );
			}
		}
	}

	static final Grid grid = new Grid( WIDTH, HEIGHT, Grid.HIDDEN );
	static final JLabel status = new JLabel();
	static int score;

	public static void main(String[] args)
	{
		grid.setTitle("jSnake");
		grid.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		grid.getContentPane().setBackground( new Color(0xbbddff) );
		grid.setMenuBar( new MenuBar() );
		grid.getMenuBar().add( new Menu("Start") );
		grid.getMenuBar().getMenu(0).add( new MenuItem("Slow") );
		grid.getMenuBar().getMenu(0).getItem(0).addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SnakeHead snake = new SnakeHead( grid.getBoxAt(WIDTH/2, HEIGHT/2), 2, 200 );
					MH.getRandomEmptyBox(grid).addGridItem( new SnakeFood() );
					grid.getMenuBar().getMenu(0).setEnabled(false);
					snake.getGameThread().start();
				} catch(GridException f) { f.printStackTrace(); }
			}
		});
		grid.getMenuBar().getMenu(0).add( new MenuItem("Medium") );
		grid.getMenuBar().getMenu(0).getItem(1).addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SnakeHead snake = new SnakeHead( grid.getBoxAt(WIDTH/2, HEIGHT/2), 2, 75 );
					MH.getRandomEmptyBox(grid).addGridItem( new SnakeFood() );
					grid.getMenuBar().getMenu(0).setEnabled(false);
					snake.getGameThread().start();
				} catch(GridException f) { f.printStackTrace(); }
			}
		});
		grid.getMenuBar().getMenu(0).add( new MenuItem("Fast") );
		grid.getMenuBar().getMenu(0).getItem(2).addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SnakeHead snake = new SnakeHead( grid.getBoxAt(WIDTH/2, HEIGHT/2), 2, 40 );
					MH.getRandomEmptyBox(grid).addGridItem( new SnakeFood() );
					grid.getMenuBar().getMenu(0).setEnabled(false);
					snake.getGameThread().start();
				} catch(GridException f) { f.printStackTrace(); }
			}
		});
		{
			JFrame frame = new JFrame("jSnake");
			frame.setContentPane( new JPanel( new GridLayout(2, 1, 0, 4) ) );
			JProgressBar pBar = new JProgressBar( JProgressBar.HORIZONTAL, 0, (WIDTH - 1) * (HEIGHT - 1) );
			frame.getContentPane().add( new JLabel(" Loading...") );
			frame.getContentPane().add(pBar);
			frame.pack();
			MH.centerFrame(frame);
			frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			frame.validate();
			frame.setVisible(true);

			for( int y = 0; y < HEIGHT; y++ ) {
				for( int x = 0; x < WIDTH; x++ ) {
					pBar.setValue( y * WIDTH + x );
					Box b = new Box();
					b.setBorder( new EmptyBorder(0, 0, 0, 0) );
					b.setPreferredSize( new Dimension(16, 16) );
					b.setMinimumSize( new Dimension(16, 16) );
					grid.setBoxAt(b, x, y);
				}
			}
			status.setPreferredSize( new Dimension(1, 16) );
			status.setMinimumSize( new Dimension(1, 16) );
			status.setBackground( new Color(0xeeffaa) );
			status.setOpaque(true);
			grid.getContentPane().add( status, new GridBagConstraints(
				0, HEIGHT, WIDTH, 1,
				0, 0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
				new Insets(2, 0, 0 ,0), 3, 3
			) );
			grid.pack();
			grid.setSize( grid.getWidth() + 50, grid.getHeight() + 50 );
			MH.centerFrame(grid);
			grid.validate();
			frame.dispose();
		}
		grid.setVisible(true);
	}
}

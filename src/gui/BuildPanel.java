package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

@SuppressWarnings("serial")
public class BuildPanel extends JPanel {
	public static final  int EMPTY = 0;
	public static final  int STREET = 1;
	public static final  int GARBAGE_BIN = 2;
	public static final  int GAS_STATION = 3;
	public static final  int GARBAGE_DEPOSIT = 4;

	//images needed
	private BufferedImage cell;
	private BufferedImage currentImg;

	private BufferedImage roadV;
	private BufferedImage roadH;
	private BufferedImage roadL;
	private BufferedImage roadR;
	private BufferedImage roadT;
	private BufferedImage roadB;
	private BufferedImage roadX;
	private BufferedImage bin;
	private BufferedImage gas;
	private BufferedImage dump;

	private BufferedImage[] icons;

	//other fields
	private int mapSizeH;
	private int mapSizeV;

	//constants
	private final int X = 20;
	private final int Y = 20;
	private int width = 50;

	private int[][] board = null;
	private int current;


	public void mouseHandler(int x, int y) {
		System.out.printf("(%d,%d)\n", x, y);

		int x1 = X;
		int x2 = X + width * mapSizeH;
		int y1 = Y;
		int y2 = Y + width * mapSizeV;


		if( x1 <= x && x <= x2 && y1 <= y && y <= y2) {
			int i = ((x - x1)/ width ) % mapSizeH;
			int j = ((y - y1 )/ width ) % mapSizeV;


			board[i][j] = current;


		}
		repaint();
	}

	/**
	 * Create the panel.
	 */
	public BuildPanel() {

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg) {
				if (arg.getButton() == MouseEvent.BUTTON1) {
					mouseHandler(arg.getX(), arg.getY());
				}
			}


		});


		mapSizeH = 10;
		mapSizeV = 10;

		initBoard();
		readImages();

		current = STREET;
		currentImg = icons[STREET];
	}

	private void initBoard() {

		int copy[][] = board != null ? board.clone() : null;


		board = new int[mapSizeH][mapSizeV];

		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board[i].length; j++)
				board[i][j] = EMPTY;

		if(copy != null) {
			for(int i = 0; i < board.length; i++)
				for(int j = 0; j < board[i].length; j++) {
					try {
						board[i][j] = copy[i][j];
					}
					catch(ArrayIndexOutOfBoundsException x) {
						continue;
					}
				}
		}

	}

	private void readImages() {
		try {
			cell = ImageIO.read(new File("resources/cell.png"));
			roadV = ImageIO.read(new File("resources/roadV.png"));
			roadH = ImageIO.read(new File("resources/roadH.png"));
			roadR = ImageIO.read(new File("resources/roadR.png"));
			roadL = ImageIO.read(new File("resources/roadL.png"));
			roadT = ImageIO.read(new File("resources/roadT.png"));
			roadB = ImageIO.read(new File("resources/roadB.png"));

			icons = new BufferedImage[5];
			icons[STREET] = ImageIO.read(new File("resources/roadX.png"));
			icons[GARBAGE_BIN] = ImageIO.read(new File("resources/bin.png"));
			icons[GARBAGE_DEPOSIT] = ImageIO.read(new File("resources/dump.png"));
			icons[GAS_STATION] = ImageIO.read(new File("resources/gas.png"));
			icons[EMPTY] = cell;



			roadX = icons[STREET];
			bin = icons[GARBAGE_BIN];
			gas = icons[GAS_STATION];
			dump = icons[GARBAGE_DEPOSIT];

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		/*for(int i = 0; i < mapSizeH; i++) {
			for(int j = 0; j < mapSizeV; j++)
				g.drawImage(cell, X + i * width, Y + j * width, width, width, null);
		}*/

		for(int i = 0; i < mapSizeH; i++) {
			for(int j = 0; j < mapSizeV; j++)
				g.drawImage(icons[board[i][j]], X + i * width, Y + j * width, width, width, null);
		}


	}

	public void setMapSize(int h, int v) {
		mapSizeH = h;
		mapSizeV = v;


		//size of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//height of the task bar
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;

		//available size of the screen 
		//setLocation(screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());

		int maxY = screenSize.height - taskBarSize*2 /*- getHeight()*/ - Y;
		int maxX = screenSize.width /*- getWidth()*/ - X - 160;
		System.out.printf("screenSize.height: %d   taskBarSize: %d   getHeight(): %d\n", screenSize.height, taskBarSize,getHeight());
		System.out.printf("MaxX: %d   MaxY: %d\n", maxX, maxY);

		System.out.println(maxX / mapSizeH);

		//if(max / mapSizeH < L)

		int mH = (int) Math.floor(maxX / mapSizeH) < 50 ? (int) Math.floor(maxX / mapSizeH) : 50;
		int mV = (int) Math.floor(maxY / mapSizeV) < 50 ? (int) Math.floor(maxY / mapSizeV) : 50;

		width = mH < mV ? mH : mV;

		initBoard();
		repaint();
	}

	public void setIcon(int modo) {
		current = modo;
		currentImg = icons[modo];
	}

}

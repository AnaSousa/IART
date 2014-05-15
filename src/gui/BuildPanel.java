package gui;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import logic.ProgramData;
import logic.Truck;

import org.jgrapht.graph.DefaultEdge;

@SuppressWarnings("serial")
public class BuildPanel extends JPanel {
	
	//TODO: mudar valores das constantes de acordo com a classe Node
	public static final  int EMPTY = 0;
	public static final  int STREET = 1;
	public static final  int GARBAGE_BIN = 2;
	public static final  int GAS_STATION = 3;
	public static final  int GARBAGE_DEPOSIT = 4;
	public static final  int STREET_UP = 5;
	public static final  int STREET_DOWN = 6;
	public static final  int STREET_HORIZONTAL_BASE = 7;
	public static final  int STREET_RIGHT = 7;
	public static final  int STREET_LEFT = 8;
	public static final  int CROSSROAD = 44444;

	public static final  int MOUSE_BTN1 = 0;
	public static final  int MOUSE_BTN2 = 1;
	public static final  int MOUSE_BTN3 = 2;

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

	//for direction purpose
	private int horizontal_index = 0;
	private int vertical_index = 0;
	final private int[] HORIZONTAL_DIRECTIONS = {STREET_RIGHT, STREET_LEFT, STREET};
	final private int[] VERTICAL_DIRECTIONS = {STREET_UP, STREET_DOWN, STREET};

	private boolean isAllowed(int x, int y) {
		int p[] = {EMPTY, EMPTY, EMPTY, EMPTY};
		int d[] = {EMPTY, EMPTY, EMPTY, EMPTY};

		/*
		 * 5 2 6
		 * 1   3
		 * 7 4 8
		 */
		int x1 = x - 1;
		int y1 = y;
		int x2 = x;
		int y2 = y - 1;
		int x3 = x + 1;
		int y3 = y;
		int x4 = x;
		int y4 = y + 1;
		int x5 = x - 1;
		int y5 = y - 1;
		int x6 = x + 1;
		int y6 = y - 1;
		int x7 = x - 1;
		int y7 = y + 1;
		int x8 = x + 1;
		int y8 = y + 1;

		if(0 <= x1 && x1 < mapSizeH && 0 <= y1 && y1 < mapSizeV)
			p[0] = board[x1][y1];
		if(0 <= x2 && x2 < mapSizeH && 0 <= y2 && y2 < mapSizeV)
			p[1] = board[x2][y2];
		if(0 <= x3 && x3 < mapSizeH && 0 <= y3 && y3 < mapSizeV)
			p[2] = board[x3][y3];
		if(0 <= x4 && x4 < mapSizeH && 0 <= y4 && y4 < mapSizeV)
			p[3] = board[x4][y4];

		if(0 <= x5 && x5 < mapSizeH && 0 <= y5 && y5 < mapSizeV)
			d[0] = board[x5][y5];
		if(0 <= x6 && x6 < mapSizeH && 0 <= y6 && y6 < mapSizeV)
			d[1] = board[x6][y6];
		if(0 <= x6 && x6 < mapSizeH && 0 <= y6 && y6 < mapSizeV)
			d[2] = board[x6][y6];
		if(0 <= x6 && x6 < mapSizeH && 0 <= y6 && y6 < mapSizeV)
			d[3] = board[x6][y6];


		if(current != STREET)
			for(int i : p) 
				if (i == GARBAGE_BIN || i == GARBAGE_DEPOSIT || i == GAS_STATION) 
					return false;


		/*switch (current)
		{
		case GARBAGE_BIN || GARBAGE_DEPOSIT:
			for(int i : p) 
				if (i == GARBAGE_BIN || i == GARBAGE_DEPOSIT || i == GAS_STATION) 
					return false;
			break;
		case STREET:
			int cont = 0;
			for(int i : p) 
				if (i == STREET) 
					cont++;
			for(int i : d) 
				if (i == STREET) 
					cont++;

			if(cont > 4)
				return false;

			//if(p[0] == STREET && p[1] == STREET)
			break;
		case GARBAGE_DEPOSIT:
			for(int i : p) 
				if (i == GARBAGE_BIN || i == GARBAGE_DEPOSIT || i == GAS_STATION) 
					return false;
			break;


		}*/

		return true;
	}

	public void mouseHandler(int x, int y, int btn) {
		//System.out.printf("(%d,%d)\n", x, y);


		int x1 = X;
		int x2 = X + width * mapSizeH;
		int y1 = Y;
		int y2 = Y + width * mapSizeV;


		if( x1 <= x && x <= x2 && y1 <= y && y <= y2) {
			int i = ((x - x1)/ width ) % mapSizeH;
			int j = ((y - y1 )/ width ) % mapSizeV;

			switch(btn) {
			case MOUSE_BTN1:
				if(isAllowed(i, j))
					board[i][j] = current;
				break;
			case MOUSE_BTN2:
				board[i][j] = EMPTY;
				break;
			case MOUSE_BTN3:
				setDirection(i,j);
				break;
			}

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
				if (arg.getButton() == MouseEvent.BUTTON1)
					mouseHandler(arg.getX(), arg.getY(), MOUSE_BTN1);
				else if (arg.getButton() == MouseEvent.BUTTON2)
					mouseHandler(arg.getX(), arg.getY(), MOUSE_BTN2);
				else if (arg.getButton() == MouseEvent.BUTTON3)
					mouseHandler(arg.getX(), arg.getY(), MOUSE_BTN3);
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

			icons = new BufferedImage[9];

			icons[STREET] = ImageIO.read(new File("resources/roadX.png"));
			icons[GARBAGE_BIN] = ImageIO.read(new File("resources/bin.png"));
			icons[GARBAGE_DEPOSIT] = ImageIO.read(new File("resources/dump.png"));
			icons[GAS_STATION] = ImageIO.read(new File("resources/gas.png"));
			icons[EMPTY] = cell;
			icons[STREET_DOWN] = roadB;
			icons[STREET_UP] = roadT;
			icons[STREET_LEFT] = roadL;
			icons[STREET_RIGHT] = roadR;




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
			for(int j = 0; j < mapSizeV; j++) {
				g.drawImage(icons[board[i][j] != EMPTY ? STREET : EMPTY], X + i * width, Y + j * width, width, width, null);
				g.drawImage(icons[board[i][j]], X + i * width, Y + j * width, width, width, null);
			}
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

	void setDirection(int x, int y) {

		if (!(board[x][y] == STREET || (STREET_UP <= board[x][y] && board[x][y]  <= STREET_LEFT)))
			return;

		int p[] = {EMPTY, EMPTY, EMPTY, EMPTY};

		/*
		 * 5 2 6
		 * 1   3
		 * 7 4 8
		 */
		int x1 = x - 1;
		int y1 = y;
		int x2 = x;
		int y2 = y - 1;
		int x3 = x + 1;
		int y3 = y;
		int x4 = x;
		int y4 = y + 1;

		if(0 <= x1 && x1 < mapSizeH && 0 <= y1 && y1 < mapSizeV)
			p[0] = board[x1][y1];
		if(0 <= x2 && x2 < mapSizeH && 0 <= y2 && y2 < mapSizeV)
			p[1] = board[x2][y2];
		if(0 <= x3 && x3 < mapSizeH && 0 <= y3 && y3 < mapSizeV)
			p[2] = board[x3][y3];
		if(0 <= x4 && x4 < mapSizeH && 0 <= y4 && y4 < mapSizeV)
			p[3] = board[x4][y4];



		int cont = 0;

		for(int i : p) 
			if(i == STREET || (STREET_UP <= i && i  <= STREET_LEFT)) 
				cont++;


		if(isEmpty(p[1]) && isEmpty(p[3])) {
			int i = x;
			while(i < mapSizeH) {
				if (!isStreet(board[i][y]))
					break;
				board[i][y] = HORIZONTAL_DIRECTIONS[horizontal_index];
				i++;
			}

			i = x - 1;
			while(i >= 0) {
				System.out.println(i);
				if (!isStreet(board[i][y]))
					break;
				board[i][y] = HORIZONTAL_DIRECTIONS[horizontal_index];
				i--;
			}


			horizontal_index = (horizontal_index + 1) % 3;
		} 
		else if (isEmpty(p[0]) && isEmpty(p[2])) {
			int j = y;
			while(j < mapSizeH) {
				if (!isStreet(board[x][j]))
					break;
				board[x][j] = VERTICAL_DIRECTIONS[vertical_index];
				j++;
			}

			j = y - 1;
			while(j >= 0) {
				System.out.println(j);
				if (!isStreet(board[x][j]))
					break;
				board[x][j] = VERTICAL_DIRECTIONS[vertical_index];
				j--;
			}


			vertical_index = (vertical_index + 1) % 3;
		}
	}

	public boolean isStreet(int x) {
		if(x == STREET || (STREET_UP <= x && x <= STREET_LEFT))
			return true;
		return false;
	}
	
	public boolean isEmpty(int x) {
		return x == EMPTY;
	}
	

	public void setIcon(int modo) {
		current = modo;
		currentImg = icons[modo];
	}

	public void startAlgorithm() {
		
		Node initial = null, n = null;
		boolean first=true;
		
		Class<? extends DefaultEdge> edgeClass = null;
		ProgramData data = ProgramData.getInstance();
		Graph graph = new Graph(edgeClass);

		for(int x=0; x<board.length; x++) {
			for(int y=0; y<board[x].length; y++) {
				
				if(checkCrossroad(x,y)) {
					if(first)
					{
						initial=new Node(CROSSROAD);
						first=false;
					} else 
						n= new Node(CROSSROAD);

				} else {
					if(first)
					{
						initial=new Node(board[x][y]);
						first=false;
					} else 
						n= new Node(board[x][y]);
				}
				n.setPosition(x, y);
				graph.addVertex(n);
			}
		}
		
		/*Node n1 = new Node(Node.CROSSROAD);
		n1.setPosition(0, 0);
		graph.addVertex(n1); 
		graph.addEdge(n1,n2,1000,false);
		*/
		Truck truck = new Truck(200,100);
		graph.calculateDistances();
		data.setTruck(truck);
		data.setGraph(graph);
		Queue<Edge> s = data.searchPath(initial, n);
		data.getGraph().setTruckPath(s);
	}

	private boolean checkCrossroad(int x, int y) {
		// mapSizeH mapSizeV
		
		/*
		 * 5 2 6
		 * 1   3
		 * 7 4 8
		 */
		int x1 = x-1, y1 = y,
				x2 = x, y2 = y-1,
				x3 = x+1, y3 = y,
				x4 = x, y4 = y+1,
				x5 = x-1, y5 = y-1,
				x6 = x+1, y6 = y-1,
				x7 = x-1, y7 = y+1,
				x8 = x+1, y8 = y+1;
		
		//TODO
		
		
		return false;
	}

}

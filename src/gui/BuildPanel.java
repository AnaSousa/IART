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
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.naming.ldap.StartTlsRequest;
import javax.swing.JPanel;

import logic.ProgramData;
import logic.Truck;

import org.jgrapht.graph.DefaultEdge;

@SuppressWarnings("serial")
public class BuildPanel extends JPanel {

	public static final int EMPTY = 0;
	public static final int STREET = 1;
	public static final int GARBAGE_BIN = 2;
	public static final int GAS_STATION = 3;
	public static final int GARBAGE_DEPOSIT = 4;
	public static final int STREET_UP = 5;
	public static final int STREET_DOWN = 6;
	public static final int STREET_HORIZONTAL_BASE = 7;
	public static final int STREET_RIGHT = 7;
	public static final int STREET_LEFT = 8;
	public static final int INITIAL_POSITION = 9;

	public int[] mapping = { 0, Node.SIMPLE_NODE, Node.GARBAGE_CONTAINER,
			Node.PETROL_STATION, Node.DUMP };

	public static final int MOUSE_BTN1 = 0;
	public static final int MOUSE_BTN2 = 1;
	public static final int MOUSE_BTN3 = 2;
	

	// images needed
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
	private BufferedImage start;

	private BufferedImage[] icons;

	// other fields
	private int mapSizeH;
	private int mapSizeV;

	// constants
	private final int X = 20;
	private final int Y = 20;
	private int width = 50;

	private int[][] board = null;
	private int current;

	private boolean hasDump = false;
	private boolean hasInitialPosition = false;

	// for direction purpose
	private int horizontal_index = 0;
	private int vertical_index = 0;
	final private int[] HORIZONTAL_DIRECTIONS = { STREET_RIGHT, STREET_LEFT,
			STREET };
	final private int[] VERTICAL_DIRECTIONS = { STREET_UP, STREET_DOWN, STREET };
	

	private boolean isAllowed(int x, int y) {
		int p[] = { EMPTY, EMPTY, EMPTY, EMPTY };
		int d[] = { EMPTY, EMPTY, EMPTY, EMPTY };

		if((current == GARBAGE_DEPOSIT && hasDump) || (current == INITIAL_POSITION && hasInitialPosition ))
			return false;
		/*
		 * 5 2 6 1 3 7 4 8
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

		if (0 <= x1 && x1 < mapSizeH && 0 <= y1 && y1 < mapSizeV)
			p[0] = board[x1][y1];
		if (0 <= x2 && x2 < mapSizeH && 0 <= y2 && y2 < mapSizeV)
			p[1] = board[x2][y2];
		if (0 <= x3 && x3 < mapSizeH && 0 <= y3 && y3 < mapSizeV)
			p[2] = board[x3][y3];
		if (0 <= x4 && x4 < mapSizeH && 0 <= y4 && y4 < mapSizeV)
			p[3] = board[x4][y4];

		if (0 <= x5 && x5 < mapSizeH && 0 <= y5 && y5 < mapSizeV)
			d[0] = board[x5][y5];
		if (0 <= x6 && x6 < mapSizeH && 0 <= y6 && y6 < mapSizeV)
			d[1] = board[x6][y6];
		if (0 <= x6 && x6 < mapSizeH && 0 <= y6 && y6 < mapSizeV)
			d[2] = board[x6][y6];
		if (0 <= x6 && x6 < mapSizeH && 0 <= y6 && y6 < mapSizeV)
			d[3] = board[x6][y6];

		if (current != STREET)
			for (int i : p)
				if (i == GARBAGE_BIN || i == GARBAGE_DEPOSIT
				|| i == GAS_STATION)
					return false;

		/*
		 * switch (current) { case GARBAGE_BIN || GARBAGE_DEPOSIT: for(int i :
		 * p) if (i == GARBAGE_BIN || i == GARBAGE_DEPOSIT || i == GAS_STATION)
		 * return false; break; case STREET: int cont = 0; for(int i : p) if (i
		 * == STREET) cont++; for(int i : d) if (i == STREET) cont++;
		 * 
		 * if(cont > 4) return false;
		 * 
		 * //if(p[0] == STREET && p[1] == STREET) break; case GARBAGE_DEPOSIT:
		 * for(int i : p) if (i == GARBAGE_BIN || i == GARBAGE_DEPOSIT || i ==
		 * GAS_STATION) return false; break;
		 * 
		 * 
		 * }
		 */
		if(current == GARBAGE_DEPOSIT)
			hasDump = true;
		else if (current == INITIAL_POSITION)
			hasInitialPosition = true;
		return true;
	}

	public void mouseHandler(int x, int y, int btn) {
		// System.out.printf("(%d,%d)\n", x, y);

		int x1 = X;
		int x2 = X + width * mapSizeH;
		int y1 = Y;
		int y2 = Y + width * mapSizeV;

		if (x1 <= x && x <= x2 && y1 <= y && y <= y2) {
			int i = ((x - x1) / width) % mapSizeH;
			int j = ((y - y1) / width) % mapSizeV;

			switch (btn) {
			case MOUSE_BTN1:
				if (isAllowed(i, j)) 
					board[i][j] = current;
				break;
			case MOUSE_BTN2:
				if(board[i][j] == GARBAGE_DEPOSIT)
					hasDump = false;
				else if (board[i][j] == INITIAL_POSITION)
					hasInitialPosition = false;
				board[i][j] = EMPTY;
				break;
			case MOUSE_BTN3:
				setDirection(i, j);
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

		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				board[i][j] = EMPTY;

		if (copy != null) {
			for (int i = 0; i < board.length; i++)
				for (int j = 0; j < board[i].length; j++) {
					try {
						board[i][j] = copy[i][j];
					} catch (ArrayIndexOutOfBoundsException x) {
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
			start = ImageIO.read(new File("resources/start.png"));

			icons = new BufferedImage[10];

			icons[STREET] = ImageIO.read(new File("resources/roadX.png"));
			icons[GARBAGE_BIN] = ImageIO.read(new File("resources/bin.png"));
			icons[GARBAGE_DEPOSIT] = ImageIO
					.read(new File("resources/dump.png"));
			icons[GAS_STATION] = ImageIO.read(new File("resources/gas.png"));
			icons[EMPTY] = cell;
			icons[STREET_DOWN] = roadB;
			icons[STREET_UP] = roadT;
			icons[STREET_LEFT] = roadL;
			icons[STREET_RIGHT] = roadR;
			icons[INITIAL_POSITION] = start;

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

		/*
		 * for(int i = 0; i < mapSizeH; i++) { for(int j = 0; j < mapSizeV; j++)
		 * g.drawImage(cell, X + i * width, Y + j * width, width, width, null);
		 * }
		 */

		for (int i = 0; i < mapSizeH; i++) {
			for (int j = 0; j < mapSizeV; j++) {
				g.drawImage(icons[board[i][j] != EMPTY ? STREET : EMPTY], X + i
						* width, Y + j * width, width, width, null);
				g.drawImage(icons[board[i][j]], X + i * width, Y + j * width,
						width, width, null);
			}
		}

	}

	public void setMapSize(int h, int v) {
		mapSizeH = h;
		mapSizeV = v;

		// size of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// height of the task bar
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(
				getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;

		// available size of the screen
		// setLocation(screenSize.width - getWidth(), screenSize.height -
		// taskBarSize - getHeight());

		int maxY = screenSize.height - taskBarSize * 2 /*- getHeight()*/- Y;
		int maxX = screenSize.width /*- getWidth()*/- X - 160;
		System.out.printf(
				"screenSize.height: %d taskBarSize: %d getHeight(): %d\n",
				screenSize.height, taskBarSize, getHeight());
		System.out.printf("MaxX: %d MaxY: %d\n", maxX, maxY);

		System.out.println(maxX / mapSizeH);

		// if(max / mapSizeH < L)

		int mH = (int) Math.floor(maxX / mapSizeH) < 50 ? (int) Math.floor(maxX
				/ mapSizeH) : 50;
		int mV = (int) Math.floor(maxY / mapSizeV) < 50 ? (int) Math.floor(maxY
				/ mapSizeV) : 50;

		width = mH < mV ? mH : mV;

		initBoard();
		repaint();
	}

	void setDirection(int x, int y) {

		if (!(board[x][y] == STREET || (STREET_UP <= board[x][y] && board[x][y] <= STREET_LEFT)))
			return;

		int p[] = { EMPTY, EMPTY, EMPTY, EMPTY };

		/*
		 * 5 2 6 1 3 7 4 8
		 */
		int x1 = x - 1;
		int y1 = y;
		int x2 = x;
		int y2 = y - 1;
		int x3 = x + 1;
		int y3 = y;
		int x4 = x;
		int y4 = y + 1;

		if (0 <= x1 && x1 < mapSizeH && 0 <= y1 && y1 < mapSizeV)
			p[0] = board[x1][y1];
		if (0 <= x2 && x2 < mapSizeH && 0 <= y2 && y2 < mapSizeV)
			p[1] = board[x2][y2];
		if (0 <= x3 && x3 < mapSizeH && 0 <= y3 && y3 < mapSizeV)
			p[2] = board[x3][y3];
		if (0 <= x4 && x4 < mapSizeH && 0 <= y4 && y4 < mapSizeV)
			p[3] = board[x4][y4];

		int cont = 0;

		for (int i : p)
			if (i == STREET || (STREET_UP <= i && i <= STREET_LEFT))
				cont++;

		if (isEmpty(p[1]) && isEmpty(p[3])) {
			int i = x;
			while (i < mapSizeH) {
				if (!isStreet(board[i][y]))
					break;
				board[i][y] = HORIZONTAL_DIRECTIONS[horizontal_index];
				i++;
			}

			i = x - 1;
			while (i >= 0) {
				System.out.println(i);
				if (!isStreet(board[i][y]))
					break;
				board[i][y] = HORIZONTAL_DIRECTIONS[horizontal_index];
				i--;
			}

			horizontal_index = (horizontal_index + 1) % 3;
		} else if (isEmpty(p[0]) && isEmpty(p[2])) {
			int j = y;
			while (j < mapSizeH) {
				if (!isStreet(board[x][j]))
					break;
				board[x][j] = VERTICAL_DIRECTIONS[vertical_index];
				j++;
			}

			j = y - 1;
			while (j >= 0) {
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
		if (x == STREET || (STREET_UP <= x && x <= STREET_LEFT))
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

		Class<? extends DefaultEdge> edgeClass = null;
		ProgramData data = ProgramData.getInstance();
		data.setMultiple(100);
		Graph graph = new Graph(edgeClass);

		Vector<Node> nodes = new Vector<Node>();
		Node n = null;
		int dump_index = 0;

		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {

				if (board[x][y] != EMPTY) {
					if (checkCrossroad(x, y))
						n = new Node(Node.CROSSROAD);
					else if (board[x][y] == STREET_UP
							|| board[x][y] == STREET_DOWN
							|| board[x][y] == STREET_RIGHT
							|| board[x][y] == STREET_LEFT)
						n = new Node(Node.SIMPLE_NODE);
					else
						n = new Node(mapping[board[x][y]]);

					n.setPosition(x, y);
					graph.addVertex(n);
					nodes.addElement(n);

					if (board[x][y] == GARBAGE_DEPOSIT)
						dump_index = n.getIntegerId();
				}
			}
		}
		for (int i = 0; i < nodes.size(); i++) {
			System.out.println("No: " + nodes.get(i).getIntegerId()
					+ ", tipo: " + nodes.get(i).getType() + ", x, y: "
					+ nodes.get(i).getX() + ", " + nodes.get(i).getY());
		}
		int type = -1;

		for (int i = 0; i < nodes.size(); i++) {
			for (int j = i + 1; j < nodes.size(); j++) {

				if (!(nodes.get(i).getType() == Node.SIMPLE_NODE || nodes
						.get(j).getType() == Node.SIMPLE_NODE)) {
					type = hasEdge(nodes.get(i).getX(), nodes.get(i).getY(),
							nodes.get(j).getX(), nodes.get(j).getY());

					if (type != -1) {
						System.out.println("Aresta: "
								+ nodes.get(i).getIntegerId() + ", "
								+ nodes.get(j).getIntegerId() + "," + type);

						if (type == STREET) {
							graph.addEdge(nodes.get(i), nodes.get(j), 0, false);
						} else if (type == STREET_DOWN || type == STREET_RIGHT)
							graph.addEdge(nodes.get(i), nodes.get(j), 0, true);
						else if (type == STREET_UP || type == STREET_LEFT)
							graph.addEdge(nodes.get(j), nodes.get(i), 0, true);
					}
				}
			}
		}
		// TODO: adicionar arestas do 1º e ultimo nos

		/*
		 * Node n1 = new Node(Node.CROSSROAD); n1.setPosition(0, 0);
		 * graph.addVertex(n1); graph.addEdge(n1,n2,1000,false);
		 */
		Truck truck = new Truck(200, 100);
		graph.calculateDistances();
		data.setTruck(truck);
		data.setGraph(graph);
		/*
		 * System.out.println("No: " + nodes.get(0).getIntegerId() + ", tipo: "
		 * + nodes.get(0).getType() + ", x, y: " + nodes.get(0).getX() + ", " +
		 * nodes.get(0).getY());//
		 */
		Queue<Edge> s = data.searchPath(nodes.get(0), nodes.get(dump_index));
		data.getGraph().setTruckPath(s);
		MainWindow window = new MainWindow();
		window.frmAAlgorithmWaste.setVisible(true);
	}

	private boolean checkCrossroad(int x, int y) {

		if (board[x][y] == GARBAGE_BIN || board[x][y] == GAS_STATION
				|| board[x][y] == GARBAGE_DEPOSIT)
			return false;
		/*
		 * 5 2 6 1 3 7 4 8
		 */
		int x1 = x - 1, y1 = y, x2 = x, y2 = y - 1, x3 = x + 1, y3 = y, x4 = x, y4 = y + 1;

		if (insideMap(x1, y1) && insideMap(x4, y4)) {
			if (board[x1][y1] != EMPTY && board[x4][y4] != EMPTY)
				return true;
		}
		if (insideMap(x3, y3) && insideMap(x4, y4)) {
			if (board[x3][y3] != EMPTY && board[x4][y4] != EMPTY)
				return true;
		}
		if (insideMap(x1, y1) && insideMap(x2, y2)) {
			if (board[x1][y1] != EMPTY && board[x2][y2] != EMPTY)
				return true;
		}
		if (insideMap(x2, y2) && insideMap(x3, y3)) {
			if (board[x2][y2] != EMPTY && board[x3][y3] != EMPTY)
				return true;
		}
		return false;
	}

	private boolean insideMap(int x1, int y1) {

		return (x1 >= 0 && x1 < mapSizeH) && (y1 >= 0 && y1 < mapSizeV);
	}

	private int hasEdge(int x1, int y1, int x2, int y2) {

		int type = hasEdgeLeft(x1, y1, x2, y2);

		if (type == -1) {
			type = hasEdgeRight(x1, y1, x2, y2);
			if (type == -1) {
				type = hasEdgeUp(x1, y1, x2, y2);
				if (type == -1) {
					type = hasEdgeDown(x1, y1, x2, y2);
				}
			}
		}
		return type;
	}

	/*
	 * public static final int EMPTY = 0; public static final int STREET = 1;
	 * public static final int GARBAGE_BIN = 2; public static final int
	 * GAS_STATION = 3; public static final int GARBAGE_DEPOSIT = 4; public
	 * static final int STREET_UP = 5; public static final int STREET_DOWN = 6;
	 * public static final int STREET_RIGHT = 7; public static final int
	 * STREET_LEFT = 8;
	 * 
	 * public int[] mapping= {0, Node.SIMPLE_NODE, Node.GARBAGE_CONTAINER,
	 * Node.PETROL_STATION, Node.DUMP};
	 */

	private int hasEdgeDown(int x1, int y1, int x2, int y2) {

		int y = y1 + 1, firstType = -1;

		if (x1 != x2 || y1 > y2)
			return -1;

		if (y2 == y1 + 1)
			return STREET;

		if (y != y2 && insideMap(x2, y)) {
			firstType = board[x2][y];
			y++;
		}

		if (firstType != STREET && firstType != STREET_UP
				&& firstType != STREET_DOWN && firstType != STREET_RIGHT
				&& firstType != STREET_LEFT)
			return -1;

		while (y < y2) {

			if (insideMap(x2, y)) {

				if (board[x2][y] != firstType)
					return -1;

				y++;
			} else
				return -1;
		}

		return firstType;
	}

	private int hasEdgeUp(int x1, int y1, int x2, int y2) {

		int y = y1 - 1, firstType = -1;

		if (x1 != x2 || y1 < y2)
			return -1;

		if (y2 == y1 - 1)
			return STREET;

		if (y != y2 && insideMap(x2, y)) {
			firstType = board[x2][y];
			y--;
		}

		if (firstType != STREET && firstType != STREET_UP
				&& firstType != STREET_DOWN && firstType != STREET_RIGHT
				&& firstType != STREET_LEFT)
			return -1;

		while (y > y2) {

			if (insideMap(x2, y)) {

				if (board[x2][y] != firstType)
					return -1;

				y--;
			} else
				return -1;
		}

		return firstType;
	}

	private int hasEdgeRight(int x1, int y1, int x2, int y2) {

		int x = x1 + 1, firstType = -1;

		if (y1 != y2 || x1 > x2)
			return -1;

		if (x2 == x1 + 1)
			return STREET;

		if (x != x2 && insideMap(x, y2)) {
			firstType = board[x][y2];
			x++;
		}

		if (firstType != STREET && firstType != STREET_UP
				&& firstType != STREET_DOWN && firstType != STREET_RIGHT
				&& firstType != STREET_LEFT)
			return -1;

		while (x < x2) {

			if (insideMap(x, y2)) {

				if (board[x][y2] != firstType)
					return -1;

				x++;
			} else
				return -1;
		}

		return firstType;
	}

	private int hasEdgeLeft(int x1, int y1, int x2, int y2) {

		int x = x1 - 1, firstType = -1;

		if (y1 != y2 || x1 < x2)
			return -1;

		if (x2 == x1 - 1)
			return STREET;

		if (x != x2 && insideMap(x, y2)) {
			firstType = board[x][y2];
			x--;
		}

		if (firstType != STREET && firstType != STREET_UP
				&& firstType != STREET_DOWN && firstType != STREET_RIGHT
				&& firstType != STREET_LEFT)
			return -1;

		while (x > x2) {

			if (insideMap(x, y2)) {

				if (board[x][y2] != firstType)
					return -1;

				x--;
			} else
				return -1;
		}

		return firstType;
	}

}
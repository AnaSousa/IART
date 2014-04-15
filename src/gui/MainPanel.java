package gui;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	Graph graph;
	Node [][] nodes;
	ArrayList<Edge> edges;
	Queue<Edge> path;

	private BufferedImage roadV;
	private BufferedImage roadH;
	private BufferedImage roadX;
	private BufferedImage bin;
	private BufferedImage bin2;
	private BufferedImage gas;
	private BufferedImage dump;
	private BufferedImage grass;
	private BufferedImage truckB;
	private BufferedImage truckT;
	private BufferedImage truckL;
	private BufferedImage truckR;
	private BufferedImage truck;

	private float truckX = 0;
	private float truckY = 0;
	private int truckSpeed = 75;
	private final int X = 15;
	private final int Y = 5;
	private final int L = 50;

	public MainPanel(Graph graph) {

		this.graph = graph;
		initGraphInfo();

		try {
			roadV = ImageIO.read(new File("resources/roadV.png"));
			roadH = ImageIO.read(new File("resources/roadH.png"));
			roadX = ImageIO.read(new File("resources/roadX.png"));
			bin = ImageIO.read(new File("resources/bin.png"));
			bin2 = ImageIO.read(new File("resources/bin2.png"));
			gas = ImageIO.read(new File("resources/gas.png"));
			dump = ImageIO.read(new File("resources/dump.png"));
			grass = ImageIO.read(new File("resources/grass.jpg"));
			truckB = ImageIO.read(new File("resources/truckB.png"));
			truckT = ImageIO.read(new File("resources/truckT.png"));
			truckR = ImageIO.read(new File("resources/truckR.png"));
			truckL = ImageIO.read(new File("resources/truckL.png"));
			truck = truckR;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initGraphInfo() {
		edges = graph.getEdges();
		TreeSet<Node> tmp_nodes = new TreeSet<Node>();
		int maxX = -1;
		int maxY = -1;

		for(Edge i : edges) {
			Node n1 = i.getSource();
			Node n2 = i.getTarget();

			maxX = n1.getX() > maxX ? n1.getX() : maxX;
			maxY = n1.getY() > maxY ? n1.getY() : maxY;
			maxX = n2.getX() > maxX ? n2.getX() : maxX;
			maxY = n2.getY() > maxY ? n2.getY() : maxY;

			tmp_nodes.add(n1);
			tmp_nodes.add(n2);
		}

		this.nodes = new Node[maxX + 1][maxY + 1];

		for(Node i : tmp_nodes)
			nodes[i.getX()][i.getY()] = i;

		graph.calculateDistances();
		
		path = new LinkedList<Edge>();
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.drawImage(roadH, 70, 70,75,75,null);

		for(int x = 0; x < nodes.length; x++) {
			for(int y = 0; y< nodes[x].length; y++) {
				g.drawImage(grass, X + x * L, Y + y * L, L, L, null);
				if(nodes[x][y] != null) {
					g.drawImage(roadX, X + x * L, Y + y * L, L, L, null);
					if(nodes[x][y].getType() == Node.GARBAGE_CONTAINER) 
						g.drawImage(bin, X + x * L, Y + y * L, L, L, null);
					else if(nodes[x][y].getType() == Node.PETROL_STATION) 
						g.drawImage(gas, X + x * L, Y + y * L, L, L, null);
					else if(nodes[x][y].getType() == Node.DUMP) 
						g.drawImage(dump, X + x * L, Y + y * L, L, L, null);
				}
			}	
		}

		for(Edge i : edges) {
			

			int x1,x2,y1,y2, deltax, deltay;

			x1 = i.getSource().getX();
			x2 = i.getTarget().getX();
			y1 = i.getSource().getY();
			y2 = i.getTarget().getY();

			deltax = x1 - x2;
			deltay = y1 - y2;

			//System.out.printf("V1(%d,%d) V2(%d,%d) Dx=%d Dy=%d",x1,y1,x2,y2,deltax,deltay);
			if(deltax == 0) {
				BufferedImage image = i.isDirected() ? roadX : roadV;
				//System.out.printf("  X\n");
				int inc = deltay < 0 ? 1 : -1;

				int y = y1 + inc;
				while(y != y2) {
					g.drawImage(image, X + x1 * L, Y + y * L, L, L, null);
					y += inc;
				}
			}
			else if(deltay == 0) {
				BufferedImage image = i.isDirected() ? roadX : roadH;
				//System.out.printf("  Y\n");
				int inc = deltax < 0 ? 1 : -1;

				int x = x1 + inc;

				while(x != x2) {
					g.drawImage(image, X + x * L, Y + y1 * L, L, L, null);
					x += inc;
				}
			}
			else
				System.out.println("NSADUIDKASJ");

		}
		
		g.drawImage(truck,(int) (X + truckX * L), (int)(Y+L + truckY * L),L,L, null);
		
		Toolkit.getDefaultToolkit().sync(); // necessary for linux users to draw  and animate image correctly
		g.dispose();
		
	}

	public void startSimulation() {
		new Timer(truckSpeed,paintTimer).start();
		setDoubleBuffered(true);
	}
	
	Action paintTimer = new AbstractAction() { // functionality of our timer:
		public void actionPerformed(ActionEvent e) {
		
			
			truckX += 0.1;
			repaint();

		}
	};

}

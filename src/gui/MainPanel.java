package gui;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	Graph graph;
	Node [][] nodes;
	ArrayList<Edge> edges;

	private BufferedImage roadV;
	private BufferedImage roadH;
	private BufferedImage roadX;
	
	private final int X = 50;
	private final int Y = 50;
	private final int L = 75;

	public MainPanel(Graph graph) {

		this.graph = graph;
		initGraphInfo();

		try {
			roadV = ImageIO.read(new File("resources/roadV.png"));
			roadH = ImageIO.read(new File("resources/roadH.png"));
			roadX = ImageIO.read(new File("resources/roadX.png"));
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

	}

	@Override
	protected void paintComponent(Graphics g) {
		//g.drawImage(roadV, 200, 200,75,75,null);

		for(int x = 0; x < nodes.length; x++) {
			for(int y = 0; y< nodes[x].length; y++) {
				if(nodes[x][y] != null)
					g.drawImage(roadX, X + x * L, Y + y * L, L, L, null);
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
			
			if(deltax == 0) {
				int inc = deltay < 0 ? -1 : 1;
				
				int y = y1 + inc;
				
				while(y1 != y2) {
					g.drawImage(roadV, X + x1 * L, Y + y * L, L, L, null);
					y += inc;
				}
			}
			else if(deltay == 0) {
				
			}
			else
				System.out.println("NSADUIDKASJ");
			
		}
		
		




	}

}

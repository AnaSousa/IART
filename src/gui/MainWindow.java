package gui;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow {
	private MainPanel panel;
	private Graph graph;

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initGraph();
		initialize();
		
	}

	private void initGraph() {
		Class<? extends DefaultEdge> edgeClass = null;
		graph = new Graph(edgeClass);
		Node n1 = new Node(Node.CROSSROAD);
		n1.setPosition(1, 1);
		Node n2 = new Node(Node.CROSSROAD);
		n2.setPosition(3, 1);
		Node n3 = new Node(Node.CROSSROAD);
		n3.setPosition(6, 1);
		Node n4 = new Node(Node.CROSSROAD);
		n4.setPosition(9, 1);
		Node n5 = new Node(Node.CROSSROAD);
		n5.setPosition(3, 4);
		Node n6 = new Node(Node.PETROL_STATION);
		n6.setPosition(1, 4);
		Node n7 = new Node(Node.CROSSROAD);
		n7.setPosition(1, 8);
		Node n8 = new Node(Node.DUMP);
		n8.setPosition(8, 8);
		Node n9 = new Node(Node.CROSSROAD);
		n9.setPosition(3, 6);
		Node n10 = new Node(Node.CROSSROAD);
		n10.setPosition(6, 6);
		Node n11 = new Node(Node.CROSSROAD);
		n11.setPosition(6, 4);
		Node n12 = new Node(Node.GARBAGE_CONTAINER);
		n12.setPosition(9, 4);
		
		
		
		
		graph.addVertex(n1);
		graph.addVertex(n2);
		graph.addVertex(n3);
		graph.addVertex(n4);
		graph.addVertex(n5);
		graph.addVertex(n6);
		graph.addVertex(n7);
		graph.addVertex(n8);
		graph.addVertex(n9);
		graph.addVertex(n10);
		graph.addVertex(n11);
		graph.addVertex(n12);

		graph.addEdge(n1,n2,2,false);
		graph.addEdge(n3,n2,3,true);
		graph.addEdge(n3,n4,3,false);
		graph.addEdge(n2,n5,3,false);
		//graph.addEdge(n2,n5,false);
		graph.addEdge(n3,n11,3,false);
		graph.addEdge(n11,n12,3,false);
		graph.addEdge(n11,n10,2,false);
		graph.addEdge(n5,n9,2,false);
		graph.addEdge(n5,n6,2,false);
		graph.addEdge(n6,n7,4,false);
		graph.addEdge(n8,n7,7,false);
		graph.addEdge(n9,n10,3,false);
		
		DijkstraShortestPath<Node, Edge> b = new DijkstraShortestPath<Node, Edge>(graph, n1, n4);
		System.out.println(b.getPath());
		System.out.println(b.getPathLength());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 702, 534);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new MainPanel(graph);
		frame.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton btnStartSimulation = new JButton("Start simulation");
		btnStartSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.startSimulation();
			}
		});
		panel_1.add(btnStartSimulation);
	}

}

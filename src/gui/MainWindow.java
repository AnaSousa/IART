package gui;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import logic.ProgramData;
import logic.Truck;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

public class MainWindow {
	private MainPanel panel;
	private JFrame frmAAlgorithmWaste;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow(1);
					window.frmAAlgorithmWaste.setVisible(true);
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
		test1();
		initialize();

	}

	/**
	 * Create the application.
	 */
	public MainWindow(int test) {

		switch(test) {
		case 1:
			test1();
			break;
		case 2:
			test2();
			break;
		case 3:
			test3();
			break;
		}

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmAAlgorithmWaste = new JFrame();
		frmAAlgorithmWaste.setIconImage(Toolkit.getDefaultToolkit().getImage("resources\\truckIcon.png"));
		frmAAlgorithmWaste.setTitle("A* Algorithm: Waste collection problem");
		frmAAlgorithmWaste.setBounds(100, 100, 600, 610);
		frmAAlgorithmWaste.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new MainPanel();
		frmAAlgorithmWaste.getContentPane().add(panel);

		JPanel panel_1 = new JPanel();
		frmAAlgorithmWaste.getContentPane().add(panel_1, BorderLayout.SOUTH);

		JButton btnStartSimulation = new JButton("Start simulation");
		btnStartSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.startSimulation();
			}
		});
		panel_1.add(btnStartSimulation);
	}

	/**
	 * @return the frmAAlgorithmWaste
	 */
	public JFrame getFrmAAlgorithmWaste() {
		return frmAAlgorithmWaste;
	}

	private void test3() {

		Class<? extends DefaultEdge> edgeClass = null;
		ProgramData data = ProgramData.getInstance();
		Graph graph = new Graph(edgeClass);

		Node n1 = new Node(Node.CROSSROAD);
		n1.setPosition(1, 0);
		Node n2 = new Node(Node.PETROL_STATION);
		n2.setPosition(4, 0);
		Node n3 = new Node(Node.CROSSROAD);
		n3.setPosition(7, 0);
		Node n4 = new Node(Node.CROSSROAD);
		n4.setPosition(9, 0);
		Node n5 = new Node(Node.PETROL_STATION);
		n5.setPosition(10, 0);

		Node n6 = new Node(Node.GARBAGE_CONTAINER);
		n6.setPosition(4, 2);

		Node n7 = new Node(Node.CROSSROAD);
		n7.setPosition(4, 3);
		Node n8 = new Node(Node.GARBAGE_CONTAINER);
		n8.setPosition(5, 3);
		Node n9 = new Node(Node.CROSSROAD);
		n9.setPosition(7, 3);

		Node n10 = new Node(Node.GARBAGE_CONTAINER);
		n10.setPosition(1, 5);
		Node n11 = new Node(Node.CROSSROAD);
		n11.setPosition(4, 5);
		Node n12 = new Node(Node.GARBAGE_CONTAINER);
		n12.setPosition(9, 5);

		Node n13 = new Node(Node.GARBAGE_CONTAINER);
		n13.setPosition(6, 7);
		Node n14 = new Node(Node.CROSSROAD);
		n14.setPosition(9, 7);
		Node n15 = new Node(Node.CROSSROAD);
		n15.setPosition(4, 7);

		Node n16 = new Node(Node.DUMP);
		n16.setPosition(4, 8);

		graph.addVertex(n1); graph.addVertex(n2);
		graph.addVertex(n3); graph.addVertex(n4);
		graph.addVertex(n5); graph.addVertex(n6);
		graph.addVertex(n7); graph.addVertex(n8);
		graph.addVertex(n9); graph.addVertex(n10);
		graph.addVertex(n11); graph.addVertex(n12);
		graph.addVertex(n13); graph.addVertex(n14);
		graph.addVertex(n15); graph.addVertex(n16); 

		graph.addEdge(n1,n2,2000,false);
		graph.addEdge(n1,n10,2000,true);
		graph.addEdge(n2,n6,1500,false);
		graph.addEdge(n6,n7,500,false);
		graph.addEdge(n10,n11,2000,true);
		graph.addEdge(n11,n15,1000,false);
		graph.addEdge(n15,n16,500,false);
		graph.addEdge(n15,n13,2000,false);
		graph.addEdge(n13,n14,3000,false);
		graph.addEdge(n7,n8,2000,false);
		graph.addEdge(n8,n9,1000,false);
		graph.addEdge(n9,n3,2000,false);
		graph.addEdge(n3,n4,2000,false);
		graph.addEdge(n4,n5,1000,false);
		graph.addEdge(n4,n12,2000,false);
		graph.addEdge(n12,n14,2000,false);
		graph.addEdge(n7,n11,1000,false);

		Truck truck = new Truck(400,10);
		data.setTruck(truck);
		data.setGraph(graph);
		Queue<Edge> s = data.searchPath( n1, n16);
		data.getGraph().setTruckPath(s);
	}

	private void test2() {

		Class<? extends DefaultEdge> edgeClass = null;
		ProgramData data = ProgramData.getInstance();
		Graph graph = new Graph(edgeClass);

		Node n1 = new Node(Node.CROSSROAD);
		n1.setPosition(0, 0);
		Node n2 = new Node(Node.GARBAGE_CONTAINER);
		n2.setPosition(2, 0);
		Node n3 = new Node(Node.CROSSROAD);
		n3.setPosition(4, 0);
		Node n4 = new Node(Node.GARBAGE_CONTAINER);
		n4.setPosition(6, 0);
		Node n5 = new Node(Node.CROSSROAD);
		n5.setPosition(8, 0);
		Node n6 = new Node(Node.PETROL_STATION);
		n6.setPosition(10, 0);

		Node n7 = new Node(Node.PETROL_STATION);
		n7.setPosition(0, 4);
		Node n8 = new Node(Node.GARBAGE_CONTAINER);
		n8.setPosition(2, 4);
		Node n9 = new Node(Node.CROSSROAD);
		n9.setPosition(4, 4);
		Node n10 = new Node(Node.GARBAGE_CONTAINER);
		n10.setPosition(6, 4);
		Node n11 = new Node(Node.CROSSROAD);
		n11.setPosition(8, 4);
		Node n12 = new Node(Node.PETROL_STATION);
		n12.setPosition(10, 4);

		Node n13 = new Node(Node.PETROL_STATION);
		n13.setPosition(0, 8);
		Node n14 = new Node(Node.GARBAGE_CONTAINER);
		n14.setPosition(2, 8);
		Node n15 = new Node(Node.CROSSROAD);
		n15.setPosition(4, 8);
		Node n16 = new Node(Node.GARBAGE_CONTAINER);
		n16.setPosition(6, 8);
		Node n17 = new Node(Node.CROSSROAD);
		n17.setPosition(8, 8);
		Node n18 = new Node(Node.DUMP);
		n18.setPosition(10, 8);

		Node n19 = new Node(Node.GARBAGE_CONTAINER);
		n19.setPosition(0, 6);
		Node n20 = new Node(Node.GARBAGE_CONTAINER);
		n20.setPosition(8, 2);

		graph.addVertex(n1); graph.addVertex(n2);
		graph.addVertex(n3); graph.addVertex(n4);
		graph.addVertex(n5); graph.addVertex(n6);
		graph.addVertex(n7); graph.addVertex(n8);
		graph.addVertex(n9); graph.addVertex(n10);
		graph.addVertex(n11); graph.addVertex(n12);
		graph.addVertex(n13); graph.addVertex(n14);
		graph.addVertex(n15); graph.addVertex(n16);
		graph.addVertex(n17); graph.addVertex(n18);
		graph.addVertex(n19); graph.addVertex(n20);

		graph.addEdge(n1,n2,1000,false);
		graph.addEdge(n2,n3,1000,false);
		graph.addEdge(n3,n4,1000,false);
		graph.addEdge(n3,n4,1000,false);
		graph.addEdge(n4,n5,1000,false);
		graph.addEdge(n5,n6,1000,false);

		graph.addEdge(n7,n8,1000,false);
		graph.addEdge(n8,n9,1000,false);
		graph.addEdge(n9,n10,1000,false);
		graph.addEdge(n10,n11,1000,false);
		graph.addEdge(n11,n12,1000,false);

		graph.addEdge(n13,n14,1000,false);
		graph.addEdge(n14,n15,1000,false);
		graph.addEdge(n15,n16,1000,false);
		graph.addEdge(n16,n17,1000,false);
		graph.addEdge(n17,n18,1000,false);

		graph.addEdge(n1,n7,2000,false);
		graph.addEdge(n7,n19,1000,false);
		graph.addEdge(n19,n13,1000,false);
		graph.addEdge(n3,n9,2000,false);
		graph.addEdge(n9,n15,2000,false);
		graph.addEdge(n5,n20,1000,false);
		graph.addEdge(n20,n11,1000,false);
		graph.addEdge(n11,n17,2000,false);

		Truck truck = new Truck(200,100);
		data.setTruck(truck);
		data.setGraph(graph);
		Queue<Edge> s = data.searchPath( n1, n18);
		data.getGraph().setTruckPath(s);
	}

	private void test1() {
		Class<? extends DefaultEdge> edgeClass = null;
		ProgramData data = ProgramData.getInstance();
		Graph graph = new Graph(edgeClass);
		Node n1 = new Node(Node.CROSSROAD);
		n1.setPosition(1, 1);
		Node n2 = new Node(Node.CROSSROAD);
		n2.setPosition(3, 1);
		Node n3 = new Node(Node.CROSSROAD);
		n3.setPosition(6, 1);
		Node n4 = new Node(Node.CROSSROAD);
		n4.setPosition(9, 1);
		Node n6 = new Node(Node.CROSSROAD);
		n6.setPosition(3, 4);
		Node n5 = new Node(Node.GARBAGE_CONTAINER);
		n5.setPosition(1, 4);
		Node n7 = new Node(Node.CROSSROAD);
		n7.setPosition(6, 4);
		Node n8 = new Node(Node.GARBAGE_CONTAINER);
		n8.setPosition(9, 4);
		Node n9 = new Node(Node.CROSSROAD);
		n9.setPosition(3, 6);
		Node n10 = new Node(Node.GARBAGE_CONTAINER);
		n10.setPosition(6, 6);
		Node n11 = new Node(Node.PETROL_STATION);
		n11.setPosition(1, 8);
		Node n12 = new Node(Node.CROSSROAD);
		n12.setPosition(6, 8);
		Node n13 = new Node(Node.DUMP);
		n13.setPosition(9, 8);

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
		graph.addVertex(n13);

		graph.addEdge(n1,n2,2000,false);
		graph.addEdge(n2,n6,2000,false);
		graph.addEdge(n3,n2,3000,true);
		graph.addEdge(n3,n4,3000,false);
		graph.addEdge(n3,n7,3000,false);
		graph.addEdge(n5,n6,3000,false);
		graph.addEdge(n7,n8,3000,false);
		graph.addEdge(n7,n10,3000,false);
		graph.addEdge(n9,n10,3000,false);
		graph.addEdge(n6,n9,3000,false);
		graph.addEdge(n5,n11,3000,false);
		graph.addEdge(n10,n12,3000,false);
		graph.addEdge(n11,n12,3000,false);
		graph.addEdge(n12,n13,3000,false);
		graph.addEdge(n8,n13,3000,false);

		DijkstraShortestPath<Node, Edge> b = new DijkstraShortestPath<Node, Edge>(graph, n1, n12);
		DijkstraShortestPath<Node, Edge> c = new DijkstraShortestPath<Node, Edge>(graph, n4, n8);
		Truck truck = new Truck(500,200);
		data.setTruck(truck);
		data.setGraph(graph);

		Queue<Edge> s = data.searchPath( n1, n13);

		for(Edge e : s)
		{
			System.out.print(e.getSourceId()+"->"+e.getTargetId());
		}
		System.out.println();
		data.getGraph().setTruckPath(s);
		System.out.println(b.getPath());
		System.out.println(b.getPathLength());
		System.out.println();
		System.out.println(c.getPath());
		System.out.println(c.getPathLength());
	}

}

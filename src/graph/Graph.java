package graph;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

@SuppressWarnings("serial")
public class Graph extends DirectedWeightedPseudograph<Node, Edge>{
	Queue<Edge> truckPath;
	

	@SuppressWarnings("unchecked")
	public Graph(Class<? extends DefaultEdge> edgeClass) {
		super((Class<? extends Edge>) edgeClass);
	}

	public boolean addVertex() {
		Node n=new Node();
		return super.addVertex(n);
	}

	public boolean addVertex(int type) {
		Node n=new Node(type);
		return super.addVertex(n);
	}

	public boolean addVertex(Node node) {
		return super.addVertex(node);
	}


	public Edge addEdge(Node n1, Node n2) {

		if(containsVertex(n1) && containsVertex(n2)) {
			Edge e=new Edge(n1, n2);
			if(addEdge(n1, n2, e))
				return e;
			else 
				return null;
		}
		return null;
	}

	public Edge addEdge(Node n1, Node n2, boolean directed) {
		return addEdge(n1,n2,0,directed);
	}
	
	public Edge addEdge(Node n1, Node n2, int weight, boolean directed) {

		if(containsVertex(n1) && containsVertex(n2)) {
			Edge e=new Edge(n1, n2, weight, directed);
			
			if(directed) {
				if(addEdge(n1, n2, e)) 
					return e;
			}
			else {
				Edge e1 = new Edge(n2, n1, weight, directed);
				if(addEdge(n1, n2, e) && addEdge(n2, n1, e1))
					return e;
			}
		}
		return null;
	}

	public Edge addEdge(Node n1, Node n2, int weight) {

		if(containsVertex(n1) && containsVertex(n2)) {
			Edge e=new Edge(n1, n2, weight);
			if(addEdge(n1, n2, e))
				return e;
			else 
				return null;
		}
		return null;
	}

	public Edge addEdge(Edge edge) {

		if(containsVertex(edge.getSource()) && containsVertex(edge.getTarget())) {
			if(addEdge(edge.getSource(), edge.getTarget(), edge))
				return edge;
			else 
				return null;
		}
		return null;
	}

	public Set<Edge> getSetEdges() {
		return edgeSet();
	}

	public ArrayList<Edge> getEdges() {
		return new ArrayList<Edge>(getSetEdges());
	}

	public void calculateDistances() { //TODO
		TreeSet<Node> nodes = getNodes();


		calcultateDistanceDump(nodes);
		calculateDistanceStation(nodes);

		for(Node i : nodes) {
			System.out.printf("%d  Dump: %d  Station: %d\n", i.getId(), i.getDistanceToDump(), i.getDistanceToPetrolStation());
		}
	}

	private void calculateDistanceStation(TreeSet<Node> nodes) {
		ArrayList<Node> n = new ArrayList<Node>();

		for(Node i: nodes)
			if(i.getType() == Node.PETROL_STATION)
				n.add(i);

		for(Node i: n) {
			BellmanFordShortestPath<Node, Edge> shortestPath = new BellmanFordShortestPath<Node, Edge>(this, i);
			for(Node j : nodes)
				if(!j.equals(i))
					j.setDistanceToStation((int) shortestPath.getCost(j));
				else
					j.setDistanceToStation(0);
		}

	}

	private void calcultateDistanceDump(TreeSet<Node> nodes) {
		ArrayList<Node> n = new ArrayList<Node>();

		for(Node i: nodes)
			if(i.getType() == Node.DUMP)
				n.add(i);

		for(Node i: n) {
			BellmanFordShortestPath<Node, Edge> shortestPath = new BellmanFordShortestPath<Node, Edge>(this, i);
			for(Node j : nodes)
				if(!j.equals(i))
					j.setDistanceToDump((int) shortestPath.getCost(j));
				else
					j.setDistanceToDump(0);
		}
	}

	public TreeSet<Node> getNodes() {
		ArrayList<Edge> edges = getEdges();
		TreeSet<Node> nodes = new TreeSet<Node>();

		for(Edge i : edges) {
			nodes.add(i.getSource());
			nodes.add(i.getTarget());
		}

		return nodes;
	}

	/**
	 * @return the truckPath
	 */
	public Queue<Edge> getTruckPath() {
		return truckPath;
	}

	/**
	 * @param truckPath the truckPath to set
	 */
	public void setTruckPath(Queue<Edge> truckPath) {
		this.truckPath = truckPath;
	}
}

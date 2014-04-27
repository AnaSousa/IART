package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

public class Graph extends DirectedWeightedPseudograph<Node, Edge> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7380596101842362848L;
	Queue<Edge> truckPath;

	@SuppressWarnings("unchecked")
	public Graph(Class<? extends DefaultEdge> edgeClass) {
		super((Class<? extends Edge>) edgeClass);
	}

	public boolean addVertex() {
		Node n = new Node();
		return super.addVertex(n);
	}

	public boolean addVertex(int type) {
		Node n = new Node(type);
		return super.addVertex(n);
	}

	public boolean addVertex(Node node) {
		return super.addVertex(node);
	}

	public Edge addEdge(Node n1, Node n2) {

		if (containsVertex(n1) && containsVertex(n2)) {
			Edge e = new Edge(n1, n2);
			if (addEdge(n1, n2, e)) {
				n1.addAdjacent(e);
				return e;
			} else
				return null;
		}
		return null;
	}

	public Edge addEdge(Node n1, Node n2, boolean directed) {
		return addEdge(n1, n2, 0, directed);
	}

	public Edge addEdge(Node n1, Node n2, int weight, boolean directed) {

		if (containsVertex(n1) && containsVertex(n2)) {
			Edge e = new Edge(n1, n2, weight, directed);

			if (directed) {
				if (addEdge(n1, n2, e))
				{
					n1.addAdjacent(e);
					return e;
				}
			} else {
				Edge e1 = new Edge(n2, n1, weight, directed);
				if (addEdge(n1, n2, e) && addEdge(n2, n1, e1))
				{
					n1.addAdjacent(e);
					n2.addAdjacent(e1);
					return e;
				}
			}
		}
		return null;
	}

	public Edge addEdge(Node n1, Node n2, int weight) {

		if (containsVertex(n1) && containsVertex(n2)) {
			Edge e = new Edge(n1, n2, weight);
			if (addEdge(n1, n2, e))
			{
				n1.addAdjacent(e);
				return e;
			}
			else
				return null;
		}
		return null;
	}

	public Edge addEdge(Edge edge) {

		if (containsVertex(edge.getSource())
				&& containsVertex(edge.getTarget())) {
			if (addEdge(edge.getSource(), edge.getTarget(), edge))
			{
				Node n = edge.getSource();
				n.addAdjacent(edge);
				edge.setSource(n);
				return edge;
			}
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

	public void calculateDistances() { // TODO
		HashMap<Integer, Node> nodes = getNodes();

		calculateDistanceDump(nodes);
		calculateDistanceStation(nodes);

		for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
			System.out.printf("%d  Dump: %d  Station: %d\n",entry.getValue()
					.getId(), entry.getValue().getDistanceToDump(), entry
					.getValue().getDistanceToPetrolStation());
		}
	}

	private void calculateDistanceStation(HashMap<Integer, Node> nodes) {
		ArrayList<Node> n = new ArrayList<Node>();

		for (Map.Entry<Integer, Node> entry : nodes.entrySet())
			if (entry.getValue().getType() == Node.PETROL_STATION)
				n.add(entry.getValue());

		for (Node i : n) {
			for (Map.Entry<Integer, Node> entry : nodes.entrySet())
				
				if (!entry.getValue().equals(i)) {
					BellmanFordShortestPath<Node, Edge> shortestPath = new BellmanFordShortestPath<Node, Edge>(
							this, entry.getValue());

					Node tmp = entry.getValue();
					tmp.setDistanceToStation(
							(int) shortestPath.getCost(i),
							shortestPath.getPathEdgeList(i));
					entry.setValue(tmp);

				} else {
					Node tmp = entry.getValue();
					tmp.setDistanceToStation(0, null);
					entry.setValue(tmp);
					entry.setValue(tmp);

				}
		}
		/*
		for (Node i : n) {
			BellmanFordShortestPath<Node, Edge> shortestPath = new BellmanFordShortestPath<Node, Edge>(
					this, i);
			for (Map.Entry<Integer, Node> entry : nodes.entrySet())
				if (!entry.getValue().equals(i))
					entry.getValue().setDistanceToStation(
							(int) shortestPath.getCost(entry.getValue()),
									shortestPath.getPathEdgeList(entry.getValue()));
				else
					entry.getValue().setDistanceToStation(0, null);
		}*/

	}

	private void calculateDistanceDump(HashMap<Integer, Node> nodes) {
		ArrayList<Node> n = new ArrayList<Node>();

		for (Map.Entry<Integer, Node> entry : nodes.entrySet())
			if (entry.getValue().getType() == Node.DUMP)
				n.add(entry.getValue());

		for (Node i : n) {
			for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
				Node tmp = entry.getValue();
				if (!entry.getValue().equals(i)) {
					BellmanFordShortestPath<Node, Edge> shortestPath = new BellmanFordShortestPath<Node, Edge>(this,entry.getValue());
					tmp.setDistanceToDump(
							(int) shortestPath.getCost(i));
					entry.setValue(tmp);
				}

				else
					{
					tmp.setDistanceToDump(0);
					entry.setValue(tmp);
					}
			}

		}
	}

	public HashMap<Integer, Node> getNodes() {
		ArrayList<Edge> edges = getEdges();
		HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
		for (Edge i : edges) {
			nodes.put(i.getSourceId(), i.getSource());
			nodes.put(i.getTargetId(), i.getTarget());
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
	 * @param truckPath
	 *            the truckPath to set
	 */
	public void setTruckPath(Queue<Edge> truckPath) {
		this.truckPath = truckPath;
	}

	public double calcManhattanDistance(Node a, Node b) {
		return (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()));
	}

	public ArrayList<Edge> getAdjacentEdges(int id) {
		return this.getNodes().get(id).getAdjacents();
	}


}

package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

@SuppressWarnings("serial")
public class Graph extends DirectedWeightedPseudograph<Node, Edge> {
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
				n1.addAdjacent(n2);
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
					return e;
			} else {
				Edge e1 = new Edge(n2, n1, weight, directed);
				if (addEdge(n1, n2, e) && addEdge(n2, n1, e1))
					return e;
			}
		}
		return null;
	}

	public Edge addEdge(Node n1, Node n2, int weight) {

		if (containsVertex(n1) && containsVertex(n2)) {
			Edge e = new Edge(n1, n2, weight);
			if (addEdge(n1, n2, e))
				return e;
			else
				return null;
		}
		return null;
	}

	public Edge addEdge(Edge edge) {

		if (containsVertex(edge.getSource())
				&& containsVertex(edge.getTarget())) {
			if (addEdge(edge.getSource(), edge.getTarget(), edge))
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

	public void calculateDistances() { // TODO
		HashMap<Integer, Node> nodes = getNodes();

		calculateDistanceDump(nodes);
		calculateDistanceStation(nodes);

		for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
			System.out.printf("%d  Dump: %d  Station: %d\n", entry.getValue()
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
					DijkstraShortestPath<Node, Edge> shortestPath = new DijkstraShortestPath<Node, Edge>(
							this, entry.getValue(), i);

					Node tmp = entry.getValue();
					tmp.setDistanceToStation(
							(int) shortestPath.getPathLength(),
							shortestPath.getPathEdgeList());
					entry.setValue(tmp);

				} else {
					Node tmp = entry.getValue();
					tmp.setDistanceToStation(0, null);
					entry.setValue(tmp);
					entry.setValue(tmp);

				}
		}

	}

	private void calculateDistanceDump(HashMap<Integer, Node> nodes) {
		ArrayList<Node> n = new ArrayList<Node>();

		for (Map.Entry<Integer, Node> entry : nodes.entrySet())
			if (entry.getValue().getType() == Node.DUMP)
				n.add(entry.getValue());

		for (Node i : n) {
			BellmanFordShortestPath<Node, Edge> shortestPath = new BellmanFordShortestPath<Node, Edge>(
					this, i);
			for (Map.Entry<Integer, Node> entry : nodes.entrySet())
				if (!entry.getValue().equals(i))
					entry.getValue().setDistanceToDump(
							(int) shortestPath.getCost(entry.getValue()));
				else
					entry.getValue().setDistanceToDump(0);
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
		return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
	}

	public ArrayList<Node> getAdjacentNodes(int id) {
		return this.getNodes().get(id).getAdjacents();
	}

	public ArrayList<Node> searchAStar(Node origin, Node destination) {
		Map<Integer, AStarNode> openSet = new HashMap<Integer, AStarNode>();
		PriorityQueue<AStarNode> priorityQueue = new PriorityQueue<AStarNode>(
				20, new AStarNodeComparator());
		Map<Integer, AStarNode> closeSet = new HashMap<Integer, AStarNode>();
		AStarNode start = new AStarNode(origin, 0, Integer.MAX_VALUE);
		openSet.put(origin.getId(), start);
		priorityQueue.add(start);

		AStarNode goal = null;
		while (openSet.size() > 0) {
			AStarNode x = priorityQueue.poll();
			openSet.remove(x.getId());
			if (x.getId() == destination.getId()) {
				// found
				System.out.println("Found target Node " + x.getId());
				goal = x;
				break;
			} else {
				closeSet.put(x.getId(), x);
				ArrayList<Node> neighbors = getAdjacentNodes(x.getId());
				for (Node neighbor : neighbors) {
					AStarNode visited = closeSet.get(neighbor.getId());
					if (visited == null) {
						double g = x.getDistanceSource()
								+ calcManhattanDistance(x.getNode(), neighbor);

						AStarNode n = openSet.get(neighbor.getId());

						if (n == null) {
							// not in the open set
							n = new AStarNode(
									neighbor,
									g,
									calcManhattanDistance(neighbor, destination));
							n.setCameFrom(x);
							openSet.put(neighbor.getId(), n);
							priorityQueue.add(n);
						} else if (g < n.getDistanceSource()) {
							// Have a better route to the current node, change
							// its parent
							n.setCameFrom(x);
							n.setDistanceSource(g);
							n.setDistanceTarget(calcManhattanDistance(neighbor,
									destination));
						}
					}
				}
			}
		}

		// after found the target, start to construct the path
		if (goal != null) {
			Stack<Node> stack = new Stack<Node>();
			ArrayList<Node> list = new ArrayList<Node>();
			stack.push(goal.getNode());
			AStarNode parent = goal.getCameFrom();
			while (parent != null) {
				stack.push(parent.getNode());
				parent = parent.getCameFrom();
			}

			while (stack.size() > 0) {

				list.add(stack.pop());
			}
			return list;
		}

		return null;
	}
}

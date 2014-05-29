package logic;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class AStarAlgorithm {
	public static Queue<Edge> searchAStar(Graph g, Node origin, Truck t) {
		HashMap<Integer, AStarNode> openSet = new HashMap<Integer, AStarNode>();

		PriorityQueue<AStarNode> priorityQueue = new PriorityQueue<AStarNode>(
				20, new AStarNodeComparator());
		HashMap<Integer, AStarNode> closeSet = new HashMap<Integer, AStarNode>();
		AStarNode start = new AStarNode(origin, 1, 0, Integer.MAX_VALUE);
		openSet.put(origin.getId(), start);
		priorityQueue.add(start);
		int trucksLeft = 0;
		int maxTrucks = 0;
		AStarNode goal = null;
		while (openSet.size() > 0) {

			AStarNode x = priorityQueue.poll();
			openSet.remove(x.getId());
			if (x.getGarbagesPassed().size() == t.getGarbagesToPass().size()) {
				goal = x;
				break;
			} else {
				closeSet.put(x.getId(), x);
				ArrayList<Edge> neighbors = g.getAdjacentEdges(x.getId());
				for (Edge neighborEdge : neighbors) {
					Node neighbor = neighborEdge.getTarget();
					trucksLeft = (neighbor.getType() == Node.GARBAGE_CONTAINER &&t.getGarbagesToPass().contains(neighbor)) ? 1
							: 0;
					trucksLeft = (x.getGarbagesPassed().size() + trucksLeft == 0 ? 1
							: (x.getGarbagesPassed().size() + trucksLeft) * 100);
					double passed = x.getDistance() / trucksLeft;
					AStarNode n = openSet.get(neighbor.getId());
					if (n == null) {

						// not in the open set
						n = new AStarNode(neighbor, trucksLeft,
								(x.getDistance() + (int) neighborEdge
										.getWeight()));
						n.setCameFrom(x);
						double distanceLeft = manhattanLeft(g, n,
								t.getGarbagesToPass());
						if (n.getNode().getType() == Node.PETROL_STATION)
							n.setFuelSpent(0);
						else
							n.setFuelSpent(x.getFuelSpent()
									+ neighborEdge.getWeight());
						n.recalculateGarbagesPassed(t.getGarbagesToPass()
								.size(), distanceLeft);
						openSet.put(neighbor.getId(), n);
						if (maxTrucks < n.getGarbagesPassed().size())
							maxTrucks = n.getGarbagesPassed().size();
						priorityQueue.add(n);
					} else {
						if (passed < n.getG()) {

							// Have a better route to the current node,
							// change
							// its parent
							n.setCameFrom(x);
							n.setG(passed);
							double distanceLeft = manhattanLeft(g, n,
									t.getGarbagesToPass());
							n.recalculateGarbagesPassed(t.getGarbagesToPass()
									.size(), distanceLeft);
							if (maxTrucks < n.getGarbagesPassed().size())
								maxTrucks = n.getGarbagesPassed().size();

						}

					}
				}
			}
		}

		// after found the target, start to construct the path
		if (goal != null) {
			Stack<Node> stack = new Stack<Node>();
			ArrayList<Node> list = new ArrayList<Node>();
			Queue<Edge> edges = new LinkedList<Edge>();
			stack.push(goal.getNode());
			AStarNode parent = goal.getCameFrom();
			while (parent != null) {
				stack.push(parent.getNode());
				parent = parent.getCameFrom();
			}

			while (stack.size() > 0) {

				list.add(stack.pop());
			}
			Node finalNode = list.get(list.size() - 1);
			while (list.size() > 1) {
				Node n = list.get(0);
				for (Edge e : n.getAdjacents()) {
					if (e.getTarget() == list.get(1)) {
						edges.add(e);
						list.remove(0);
						break;
					}
				}
			}
			if (finalNode.getPathToDump() != null)
				for (int i = 0; i < finalNode.getPathToDump().size(); i++) {
					edges.add(finalNode.getPathToDump().get(i));
				}
			System.out.println("A* calculation passed");
			return edges;
		}
		System.out.println("A* calculation failed");
		return null;
	}

	private static double manhattanLeft(Graph g, AStarNode actual,
			ArrayList<Node> totalGarbages) {
		HashSet<Node> nodes = actual.getGarbagesPassed();
		double returned = 0;
		Node dump;
		if (actual.getNode().getType() == Node.DUMP)
			dump = null;
		else
			dump = actual.getNode().getPathToDump()
					.get(actual.getNode().getPathToDump().size() - 1)
					.getTarget();

		for (Node n : totalGarbages) {
			if (!(nodes == null)) {
				if (!nodes.contains(n.getId()))

					returned += g.calcManhattanDistance(actual.getNode(), n);
			} else
				break;

		}
		if (dump != null)
			returned += g.calcManhattanDistance(actual.getNode(), dump);
		return returned;
	}

}

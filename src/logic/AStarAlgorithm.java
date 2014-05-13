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
			if (x.getGarbagesPassed().size() == t.getGarbagesPassed().size()) {
				goal = x;
				break;
			} else {
				closeSet.put(x.getId(), x);
				ArrayList<Edge> neighbors = g.getAdjacentEdges(x.getId());
				for (Edge neighborEdge : neighbors) {
					Node neighbor = neighborEdge.getTarget();
					trucksLeft = neighbor.getType() == Node.GARBAGE_CONTAINER ? 1
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
								t.getGarbagesPassed());
						n.recalculateGarbagesPassed(t.getGarbagesPassed()
								.size(), distanceLeft);
						openSet.put(neighbor.getId(), n);
						if (maxTrucks < n.getGarbagesPassed().size())
							maxTrucks = n.getGarbagesPassed().size();
						priorityQueue.add(n);
						System.out.println("Teste 1 " + n.getGarbagesPassed());
						System.out.println("Teste 2 " + t.getGarbagesPassed());
					} else {
						if (passed < n.getG()) {
							System.out.println("Passed=" + passed);
							System.out.println("N G=" + n.getG());
							// Have a better route to the current node,
							// change
							// its parent
							n.setCameFrom(x);
							n.setG(passed);
							double distanceLeft = manhattanLeft(g, n,
									t.getGarbagesPassed());
							n.recalculateGarbagesPassed(t.getGarbagesPassed()
									.size(), distanceLeft);
							if (maxTrucks < n.getGarbagesPassed().size())
								maxTrucks = n.getGarbagesPassed().size();
							System.out.println(n.getGarbagesPassed());

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
			if (finalNode.getPathToDump() != null)
				for (int i = finalNode.getPathToDump().size() - 1; i >= 0; i--) {
					System.out.println("Teste");
					edges.add(finalNode.getPathToDump().get(i));
				}
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
			return edges;
		}

		return null;
	}

	private static Queue<Edge> refinateAlgorithm(Queue<Edge> edges) {
		boolean isProcessing = true;
		Queue<Edge> returned = new LinkedList<Edge>();
		ArrayList<Edge> path = new ArrayList<Edge>();
		while (isProcessing) {
			path.add(edges.poll());
			if (path.size() >= 4)
				if (path.get(path.size() - 1).equals(path.get(path.size() - 3))
						&& path.get(path.size() - 2).equals(
								path.get(path.size() - 4))) {
					path.remove(path.size() - 1);
					path.remove(path.size() - 1);
					path.remove(path.size() - 1);
					isProcessing = false;
				}
			if (edges.size() == 0)
				isProcessing = false;
		}

		while (path.size() > 0) {
			returned.add(path.get(0));
			path.remove(0);
		}
		System.out.println("Returned= " + returned);
		return returned;

	}

	/*
	 * public static Queue<Edge> aStarAlgorithm(Graph g, Node origin, Truck t) {
	 * Queue<Edge> returned = searchAStar(g, origin, t); Queue<Edge> path = new
	 * LinkedList<Edge>(); int garbagesLeft = t.getGarbagesPassed().size(); Node
	 * originNode = null; while (garbagesLeft > 0) { while (returned.size() > 0)
	 * { Edge e = returned.poll(); if (e.getSource().getType() ==
	 * Node.GARBAGE_CONTAINER) {
	 * 
	 * ArrayList<Integer> garbages = t.getGarbagesPassed(); for (int i = 0; i <
	 * garbages.size(); i++) if (garbages.get(i) == e.getSource().getId()) {
	 * garbagesLeft--; garbages.remove(i); } t.setGarbagesPassed(garbages); } if
	 * (e.getTarget().getType() == Node.GARBAGE_CONTAINER) {
	 * 
	 * ArrayList<Integer> garbages = t.getGarbagesPassed(); for (int i = 0; i <
	 * garbages.size(); i++) if (garbages.get(i) == e.getTarget().getId()) {
	 * garbagesLeft--; garbages.remove(i); } t.setGarbagesPassed(garbages); } if
	 * (returned.size() == 0) { originNode = e.getTarget(); } path.add(e); }
	 * returned = searchAStar(g, originNode, t); } return path; }
	 */

	private static double manhattanLeft(Graph g, AStarNode actual,
			ArrayList<Node> totalGarbages) {
		HashSet<Integer> nodes = actual.getGarbagesPassed();
		double returned = 0;
		for (Node n : totalGarbages) {
			if (!(nodes == null)) {
				if (!nodes.contains(n.getId()))

					returned += g.calcManhattanDistance(actual.getNode(), n);
			} else
				break;

		}
		return returned;
	}

}

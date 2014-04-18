package logic;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStarAlgorithm {
	public ArrayList<Node> searchAStar(Graph g, Node origin, Node destination) {
		double fuelSpent = 500;
		HashMap<Integer, AStarNode> openSet = new HashMap<Integer, AStarNode>();
		PriorityQueue<AStarNode> priorityQueue = new PriorityQueue<AStarNode>(
				20, new AStarNodeComparator());
		HashMap<Integer, AStarNode> closeSet = new HashMap<Integer, AStarNode>();
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
				ArrayList<Edge> neighbors = g.getAdjacentEdges(x.getId());
				for (Edge neighborEdge : neighbors) {
					Node neighbor=neighborEdge.getTarget();
					AStarNode visited = closeSet.get(neighbor.getId());
					if (visited == null) {
						double distSource = x.getDistanceSource()
								+ g.calcManhattanDistance(x.getNode(), neighbor);

						AStarNode n = openSet.get(neighbor.getId());

						if (n == null) {
							// not in the open set
							n = new AStarNode(neighbor, distSource,
									g.calcManhattanDistance(neighbor,
											destination));
							n.setCameFrom(x);
							openSet.put(neighbor.getId(), n);
							priorityQueue.add(n);
						} else if ((int) distSource
								+ neighbor.getDistanceToPetrolStation() <= fuelSpent)
							if (distSource < n.getDistanceSource()) {
								// Have a better route to the current node,
								// change
								// its parent
								n.setCameFrom(x);
								fuelSpent -= g.calcManhattanDistance(
										x.getNode(), neighbor);
								n.setDistanceSource(distSource);

								n.setDistanceTarget(g.calcManhattanDistance(
										neighbor, destination));
							} else {
								for(Node petrol : x.getNode().getPathToPetrolStation())
								{
								//TODO rethink how to do this part	
								}
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

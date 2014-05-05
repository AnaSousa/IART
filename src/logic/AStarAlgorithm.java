package logic;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class AStarAlgorithm {
	public static Queue<Edge> searchAStar(Graph g, Node origin,
			Node destination, Truck t) {
		HashMap<Integer, AStarNode> openSet = new HashMap<Integer, AStarNode>();

		PriorityQueue<AStarNode> priorityQueue = new PriorityQueue<AStarNode>(
				20, new AStarNodeComparator());
		HashMap<Integer, AStarNode> closeSet = new HashMap<Integer, AStarNode>();
		AStarNode start = new AStarNode(origin.getId(), origin, null,
				Double.MAX_VALUE, Double.MAX_VALUE);
		openSet.put(origin.getId(), start);
		priorityQueue.add(start);
		AStarNode goal = null;
		boolean finalState = false;
		while (openSet.size() > 0) {

			AStarNode x = priorityQueue.poll();
			if(x.getId()==8 || x.getId()==108)
				System.out.println("Teste");
			openSet.remove(x.getId());
			if (finalState) {
				// found
				goal = x;
				break;
			} else {
				closeSet.put(x.getId(), x);
				ArrayList<Edge> neighbors = g.getAdjacentEdges(x.getNode()
						.getId());
				for (Edge neighborEdge : neighbors) {
					Node neighbor = neighborEdge.getTarget();
					System.out.println("Id="+neighbor.getId()+"; X="+neighbor.getX()+"; Y="+neighbor.getY());
					int weight = (neighbor.getType() == Node.GARBAGE_CONTAINER ? x
							.getWeight() + 1 : x.getWeight());
					double passed = (x.getDistance() + neighborEdge.getWeight())
							/ (weight * 100);
					AStarNode n = openSet
							.get((weight * 100 + neighbor.getId()));
					if (n == null && weight > 1) {
						weight--;
						n = openSet.get(weight * 100 + neighbor.getId());
					}
					if (n == null) {
						System.out.println("Non exhistent");
						// not in the open set
						n = new AStarNode(weight * 100 + neighbor.getId(),
								neighbor, x, passed, (t.getGarbagesPassed()
										.size() - weight) * 1000);
						n.setH((t.getGarbagesPassed().size() - weight) * 1000
								+ n.calculateManhattanLeft());
						n.setWeight(weight);
						openSet.put(n.getId(), n);
						if(n.getId()==8 || n.getId()==108)
							System.out.println("Teste");

						priorityQueue.add(n);
					} else {
						if(n.getId()==8 || n.getId()==108)
							System.out.println("Teste");
						System.out.println("Id="+(weight*100+neighbor.getId())+"; N G="+n.getG()+"; Passed="+passed);
						if (n.getG() > passed && weight >= n.getWeight()
								|| weight > n.getWeight()) {
							// Have a better route to the current node,
							// change
							// its parent
							n.setWeight(weight);
							n.setG(passed);
							n.setCameFrom(x);
							n.setH((t.getGarbagesPassed().size() - weight)
									* 10000 + n.calculateManhattanLeft());
							if (n.getH() > 3000)
								System.out.println("Teste 2");
							System.out.println("G=" + passed);
							System.out.println("H=" + n.getH());
							System.out.println("Weight=" + n.getWeight());
							if (n.getWeight() == t.getGarbagesPassed().size()) {
								goal = n;
								break;
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
			Queue<Edge> edges = new LinkedList<Edge>();
			stack.push(goal.getNode());
			AStarNode parent = goal.getCameFrom();
			while (parent != null) {
				System.out.println("Teste");
				stack.push(parent.getNode());
				parent = parent.getCameFrom();
			}

			while (stack.size() > 0) {
				System.out.println("Teste lista");
				list.add(stack.pop());
			}

			while (list.size() > 1) {
				System.out.println("Teste retorno");
				Node n = list.get(0);
				for (Edge e : n.getAdjacents()) {
					if (e.getTarget() == list.get(1)) {
						edges.add(e);
						list.remove(0);
						break;
					}
				}
			}
			System.out.println(edges);
			return edges;
		}

		return null;
	}
}
